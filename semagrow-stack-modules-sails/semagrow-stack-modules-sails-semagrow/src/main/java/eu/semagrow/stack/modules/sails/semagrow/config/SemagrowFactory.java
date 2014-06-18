package eu.semagrow.stack.modules.sails.semagrow.config;

import eu.semagrow.stack.modules.sails.semagrow.SemagrowSail;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.Sail;
import org.openrdf.sail.StackableSail;
import org.openrdf.sail.config.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by angel on 5/29/14.
 */
public class SemagrowFactory implements SailFactory {

    public static final String SAIL_TYPE = "semagrow:SemagrowSail";

    public String getSailType() {
        return SAIL_TYPE;
    }

    public SailImplConfig getConfig() {
        return new SemagrowConfig();
    }

    public Sail getSail(SailImplConfig sailImplConfig) throws SailConfigException {

        assert sailImplConfig instanceof SemagrowConfig;

        SemagrowSail sail = new SemagrowSail();
        Sail metadataSail = getMetadataSail(((SemagrowConfig) sailImplConfig).getMetadataConfig());

        try {
            initializeMetadata(metadataSail, ((SemagrowConfig) sailImplConfig).getMetadataFile());
            sail.setBaseSail(metadataSail);
            return sail;
        } catch (Exception e) {
            throw new SailConfigException(e);
        }
    }

    public Sail getMetadataSail(SailImplConfig sailImplConfig) throws SailConfigException {
        return createSailStack(sailImplConfig);
    }

    public void initializeMetadata(Sail metadata, File file)
            throws RepositoryException, IOException, RDFParseException {
        Repository repository = new SailRepository(metadata);
        RepositoryConnection conn = null;

        try {
            repository.initialize();
            conn = repository.getConnection();
            conn.add(file, "file://" + file.getAbsoluteFile(), RDFFormat.TURTLE);
        } finally {
            if (conn != null)
                conn.close();
        }
    }

    public Sail createSailStack(SailImplConfig sailImplConfig) throws SailConfigException {
        SailFactory factory = SailRegistry.getInstance().get(sailImplConfig.getType());
        Sail sail;
        if (factory != null)
            sail = factory.getSail(sailImplConfig);
        else
            throw new SailConfigException("Not valid sail config");

        if (sailImplConfig instanceof DelegatingSailImplConfig) {
            SailImplConfig delegateConfig = ((DelegatingSailImplConfig)sailImplConfig).getDelegate();
            addDelegate(sail, delegateConfig);
        }

        return sail;
    }

    public void addDelegate(Sail upperSail, SailImplConfig delegateConfig) throws SailConfigException {
        if (upperSail instanceof StackableSail) {
            Sail delegateSail = createSailStack(delegateConfig);
            ((StackableSail)upperSail).setBaseSail(delegateSail);
        }
    }

}
