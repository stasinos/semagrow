package eu.semagrow.repository.impl;

import eu.semagrow.repository.SemagrowRepositoryConnection;
import eu.semagrow.query.SemagrowQuery;
import eu.semagrow.query.impl.SemagrowSailBooleanQuery;
import eu.semagrow.query.impl.SemagrowSailTupleQuery;
import org.openrdf.query.*;
import org.openrdf.query.parser.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.base.RepositoryConnectionWrapper;
import org.openrdf.repository.sail.*;

/**
 * Created by angel on 6/10/14.
 */
//public class SemagrowSailRepositoryConnection extends SailRepositoryConnection
public class SemagrowSailRepositoryConnection extends RepositoryConnectionWrapper
        implements SemagrowRepositoryConnection {

    public SemagrowSailRepositoryConnection(SemagrowSailRepository repository,
                                            RepositoryConnection baseConnection)
    {
        super(repository, baseConnection);
    }

    @Override
    public SemagrowQuery prepareQuery( QueryLanguage ql, String queryString, String baseURI )
            throws MalformedQueryException, RepositoryException
    {
        ParsedQuery parsedQuery = QueryParserUtil.parseQuery(ql, queryString, baseURI);

        if( parsedQuery instanceof ParsedTupleQuery ) {
            return new SemagrowSailTupleQuery((ParsedTupleQuery)parsedQuery, queryString, this.getDelegate());
        }
        else if( parsedQuery instanceof ParsedBooleanQuery ) {
            return new SemagrowSailBooleanQuery((ParsedBooleanQuery)parsedQuery,  queryString, this.getDelegate());
        }
        else {
        	throw new java.lang.UnsupportedOperationException(
        			parsedQuery.getClass().getCanonicalName() + " is not supported" );
        }
    }

    @Override
    public SemagrowQuery prepareQuery( QueryLanguage ql, String queryString )
            throws MalformedQueryException, RepositoryException {
        return prepareQuery(ql, queryString, null);
    }

    @Override
    public SemagrowSailTupleQuery prepareTupleQuery(QueryLanguage ql, String queryString, String baseURI)
            throws MalformedQueryException, RepositoryException {
        ParsedTupleQuery parsedQuery = QueryParserUtil.parseTupleQuery(ql, queryString, baseURI);
        return new SemagrowSailTupleQuery(parsedQuery, queryString, this.getDelegate());
    }

    @Override
    public SemagrowSailBooleanQuery prepareBooleanQuery(QueryLanguage ql, String queryString, String baseURI)
            throws MalformedQueryException, RepositoryException {
        ParsedBooleanQuery parsedQuery = QueryParserUtil.parseBooleanQuery(ql, queryString, baseURI);
        return new SemagrowSailBooleanQuery(parsedQuery, queryString, this.getDelegate());
    }

    @Override
    public SemagrowSailTupleQuery prepareTupleQuery(QueryLanguage ql, String queryString)
            throws MalformedQueryException, RepositoryException {
        return prepareTupleQuery(ql, queryString, null);
    }

    @Override
    public SemagrowSailBooleanQuery prepareBooleanQuery(QueryLanguage ql, String queryString)
            throws MalformedQueryException, RepositoryException {
        return prepareBooleanQuery(ql, queryString, null);
    }

    @Override
    public SailRepositoryConnection getDelegate() throws RepositoryException { return (SailRepositoryConnection) super.getDelegate(); }
}
