package jatools.dom;

import bsh.BSHStar;
import bsh.UtilEvalError;

import jatools.dataset.Dataset;
import jatools.dataset.RowSet;

import jatools.engine.script.Script;

import org.apache.xerces.dom3.DOMConfiguration;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.8 $
  */
public class DatasetNode extends DatasetBasedNode implements Document {
    protected static final String _propertyDataset = "Dataset";
    protected static final String _propertyDatasetElement = "DatasetElement";
    protected static final String _propertyDatasetDumbArray = "DatasetDumbArray";
    public final static short NOT_IMPL_ERROR_CODE = DOMException.NO_MODIFICATION_ALLOWED_ERR;
    public final static String NOT_IMPL_ERROR_MSG = "Not implemented, this is a read-only document.";
    private Dataset dataset = null;
    RootNode doc;
    boolean prepared;
    private String tag;
    private Script lastScript;
    Map names0;
    Map names1;
    String globalID;

    /**
    * Creates a new DatasetNode object.
    *
    * @param p DOCUMENT ME!
    * @param ds DOCUMENT ME!
    * @param tag DOCUMENT ME!
    */
    public DatasetNode(Node p, Dataset ds, String tag) {
        this._parent = (DatasetBasedNode) p;

        this.tag = tag;
        setDataset(ds);

        globalID = (String) this._parent.getRoot().registerElement(this);

        if (ds != null) {
            ds.setGlobalID(globalID);
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void normalizeDocument() {
        // TODO Auto-generated method stub
    }

    /**
     * DOCUMENT ME!
     *
     * @param n DOCUMENT ME!
     * @param namespaceURI DOCUMENT ME!
     * @param qualifiedName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Node renameNode(Node n, String namespaceURI, String qualifiedName)
        throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param documentURI DOCUMENT ME!
     */
    public void setDocumentURI(String documentURI) {
        // TODO Auto-generated method stub
    }

    /**
     * DOCUMENT ME!
     *
     * @param strictErrorChecking DOCUMENT ME!
     */
    public void setStrictErrorChecking(boolean strictErrorChecking) {
        // TODO Auto-generated method stub
    }

    /**
     * DOCUMENT ME!
     *
     * @param xmlStandalone DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public void setXmlStandalone(boolean xmlStandalone)
        throws DOMException {
        // TODO Auto-generated method stub
    }

    /**
     * DOCUMENT ME!
     *
     * @param xmlVersion DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public void setXmlVersion(String xmlVersion) throws DOMException {
        // TODO Auto-generated method stub
    }

    /**
     * DOCUMENT ME!
     *
     * @param source DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Node adoptNode(Node source) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getDocumentURI() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public org.w3c.dom.DOMConfiguration getDomConfig() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getInputEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean getStrictErrorChecking() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getXmlEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean getXmlStandalone() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getXmlVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRowCount() {
        Dataset data = getDataset();

        if (data != null) {
            return data.getRowCount();
        } else {
            return 0;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     * @param script DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NodeNameSpace getNameSpace2(ElementBase e, Script script) {
        Object key = e.getSource();

        Map names = null;

        if (this.lastScript != script) {
            this.lastScript = script;
            names0 = null;
            names1 = null;
        }

        if (script.getStackType() == 0) {
            if (names0 == null) {
                names0 = new HashMap();
            }

            names = names0;
        } else {
            if (names1 == null) {
                names1 = new HashMap();
            }

            names = names1;
        }

        NodeNameSpace name = (NodeNameSpace) names.get(key);

        if (name == null) {
            name = new NodeNameSpace();

            try {
                name.setLocalVariable("$" + e.getTagName(), new NodeValue(name));
                name.setLocalVariable(e.getTagName(), new jatools.dom.misc.NodeVariable(name));
            } catch (UtilEvalError e1) {
                e1.printStackTrace();
            }

            names.put(key, name);
        }

        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public synchronized Dataset getDataset() {
        return (dataset);
    }

    /**
     * DOCUMENT ME!
     *
     * @param keys DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DatasetBasedNode find(Object[] keys) {
        int stars = 0;

        for (int i = keys.length - 1; i >= 0; i--) {
            if (keys[i] == BSHStar.STAR) {
                stars++;
            } else {
                break;
            }
        }

        if (stars > 0) {
            Object[] tmp = new Object[keys.length - stars];
            System.arraycopy(keys, 0, tmp, 0, tmp.length);
            keys = tmp;
        }

        DatasetBasedNode de = this;

        for (int i = 0; i < keys.length; i++) {
        }

        return de;
    }

    /**
     * DOCUMENT ME!
     *
     * @param key DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DatasetBasedNode find(Object key) {
        return null;
    }

    private synchronized void setDataset(Dataset ds) {
        dataset = ds;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Attr createAttribute(String name) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param qualifiedName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public CDATASection createCDATASection(String data)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Comment createComment(String data) {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DocumentFragment createDocumentFragment() {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param tagName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Element createElement(String tagName) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param qualifiedName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Element createElementNS(String namespaceURI, String qualifiedName)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public EntityReference createEntityReference(String name)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param target DOCUMENT ME!
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public ProcessingInstruction createProcessingInstruction(String target, String data)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param data DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Text createTextNode(String data) {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param importedNode DOCUMENT ME!
     * @param deep DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Node importNode(Node importedNode, boolean deep)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DocumentType getDoctype() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Element getDocumentElement() {
        return (Element) this.getParentNode();
    }

    /**
     * DOCUMENT ME!
     *
     * @param elementId DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Element getElementById(String elementId) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DOMImplementation getImplementation() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getLocalName() {
        return tag;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getNamespaceURI() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Document getOwnerDocument() {
        return _parent.getOwnerDocument();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPrefix() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getChildElementsLocalName() {
        return "CONTINENT";
    }

    /**
     * DOCUMENT ME!
     */
    public void clear() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isPrepared() {
        return prepared;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public RowSet getRowSet() {
        return this.getDataset().getRowSet();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getGlobalID() {
        return globalID;
    }

    /**
     * DOCUMENT ME!
     *
     * @param globalID DOCUMENT ME!
     */
    public void setGlobalID(String globalID) {
        this.globalID = globalID;
    }
}
