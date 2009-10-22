package jatools.designer.variable;


import jatools.designer.data._Variable;
import jatools.dom.src.CrossIndexNodeSource;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.GroupNodeSource;
import jatools.dom.src.IndexNodeSource;
import jatools.dom.src.NodeSource;
import jatools.dom.src.RootNodeSource;
import jatools.dom.src.RowNodeSource;
import jatools.dom.src.XmlNodeSource;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class TreeNodeValue extends _Variable implements SourceType {
    private NodeSource nodeSource;
    private String display;

    /**
     * Creates a new TreeNodeValue object.
     *
     * @param nodeSource DOCUMENT ME!
     * @param dispalyName DOCUMENT ME!
     * @param pessison DOCUMENT ME!
     * @param variableName DOCUMENT ME!
     */
    public TreeNodeValue(NodeSource nodeSource, String dispalyName, int pessison,
        String variableName) {
        super(dispalyName, pessison, variableName);
        this.nodeSource = nodeSource;
        this.setDisplay(dispalyName);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return display;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getSourceType() {
        if (nodeSource instanceof CrossIndexNodeSource) {
            return CROSS_DATASET_NODE_SOURCE;
        } else if (nodeSource instanceof IndexNodeSource) {
            return INDEX_DATASET_NODE_SOURCE;
        } else if (nodeSource instanceof DatasetNodeSource) {
            return DATASET_NODE_SOURCE;
        } else if (nodeSource instanceof RootNodeSource) {
            return ROOT_NODE_SOURCE;
        } else if (nodeSource instanceof RowNodeSource) {
            return ROW_NODE_SOURCE;
        } else if (nodeSource instanceof GroupNodeSource) {
            return GROUP_NODE_SOURCE;
        } else if (nodeSource instanceof XmlNodeSource) {
            return XML_NODE_SOURCE;
        }

        return -2;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NodeSource getNodeSource() {
        return nodeSource;
    }

    /**
     * DOCUMENT ME!
     *
     * @param display DOCUMENT ME!
     */
    public void setDisplay(String display) {
        this.display = display;
    }
}
