package jatools.dom.src;

import jatools.accessor.PropertyDescriptor;
import jatools.component.ComponentConstants;
import jatools.data.reader.DatasetReader;
import jatools.dataset.Dataset;
import jatools.dataset.DatasetException;
import jatools.dom.DatasetNode;
import jatools.engine.script.Script;

import org.w3c.dom.Node;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.4 $
  */
public class DatasetNodeSource extends NodeSource {
    DatasetReader reader;
    String tag;
    

    /**
     * Creates a new DatasetNodeSource object.
     *
     * @param reader DOCUMENT ME!
     */
    public DatasetNodeSource(String tag, DatasetReader reader) {
        this.tag = tag;
        this.reader = reader;
    }
    
    

    /**
     * Creates a new DatasetNodeSource object.
     */
    public DatasetNodeSource() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public PropertyDescriptor[] getRegistrableProperties() {
        return new PropertyDescriptor[] {
            ComponentConstants._CHILDREN,
            ComponentConstants._READER,
            new PropertyDescriptor("TagName", String.class, PropertyDescriptor.SERIALIZABLE), 
            new PropertyDescriptor("AfterCreate", String.class, PropertyDescriptor.SERIALIZABLE),
            new PropertyDescriptor("BeforeCreate", String.class, PropertyDescriptor.SERIALIZABLE),
            new PropertyDescriptor("KeyExpression", String.class, PropertyDescriptor.SERIALIZABLE)
        };
    }

    /**
    * DOCUMENT ME!
    *
    * @param script DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
    public Node create(Node parent, Script script) {
        try {
        	this.doBeforeCreate(script);
            Dataset ds = reader.read(script, -1);
            DatasetNode result = new DatasetNode(parent, ds, this.getTagName());
            result.setSource(this);
            
            this.doAfterCreate(script,result);      

            return result;
        } catch (DatasetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
   

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getTagName() {
        return this.tag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tag DOCUMENT ME!
     */
    public void setTagName(String tag) {
        this.tag = tag;
    }

    /**
     * DOCUMENT ME!
     *
     * @param parent DOCUMENT ME!
     * @param script DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node[] createNodeList(Node parent, Script script) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isNodeListSource() {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DatasetReader getReader() {
        return reader;
    }

    /**
     * DOCUMENT ME!
     *
     * @param reader DOCUMENT ME!
     */
    public void setReader(DatasetReader reader) {
        this.reader = reader;
    }
}
