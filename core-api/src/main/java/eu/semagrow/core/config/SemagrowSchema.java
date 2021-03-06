package eu.semagrow.core.config;

import org.openrdf.model.Namespace;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.NamespaceImpl;
import org.openrdf.model.impl.ValueFactoryImpl;


/**
 *
 * @author Angelos Charalambidis
 */
public class SemagrowSchema {

    public static final String NAMESPACE = "http://schema.semagrow.eu/";

    public static final String PREFIX = "semagrow";
    public static final URI SOURCESELECTOR ;

    public static Namespace NS = new NamespaceImpl(PREFIX, NAMESPACE);

    public static final URI METADATAINIT;

    public static final URI EXECUTORBATCHSIZE;

    public static final URI QUERYTRANSFORMDB;
    public static final URI QUERYTRANSFORMUSER;
    public static final URI QUERYTRANSFORMPASSWORD;

    // queryLog handler
    // source selection
    // decomposition algorithm
    //      cost estimation
    //              cardinality estimation
    // load initial metadata ?

    static  {
        ValueFactory vf = ValueFactoryImpl.getInstance();
        METADATAINIT = vf.createURI(NAMESPACE, "metadataInit");
        EXECUTORBATCHSIZE = vf.createURI(NAMESPACE, "executorBatchSize");
        QUERYTRANSFORMDB = vf.createURI(NAMESPACE, "queryTransformDB");
        QUERYTRANSFORMUSER = vf.createURI(NAMESPACE, "queryTransformUser");
        QUERYTRANSFORMPASSWORD = vf.createURI(NAMESPACE, "queryTransformPass");
        SOURCESELECTOR = vf.createURI(NAMESPACE, "sourceSelector");
    }
}
