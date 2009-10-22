package jatools.dom;

import jatools.dataset.Dataset;
import jatools.dataset.Key;
import jatools.dom.sort.ComparatorFactoryCache;
import jatools.dom.sort.NodeComparatorFactory;
import jatools.dom.src.NodeSource;
import jatools.engine.script.Script;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.6 $
  */
public abstract class ElementBase extends NodeBase implements Element {
    static final NamedNodeMapImpl EMPTY = new NamedNodeMapImpl();
    private int position;
    protected ElementBase[] children;
    private NodeSource source;
    private Map clientProperties;

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object value() {
        return this;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Key getKey() {
        if (this.getSource().getKeyExpression() != null) {
            Object[] tmp = (Object[]) this.getRoot().getScript()
                                          .eval(this.getSource().getKeyExpression());
            return new Key(tmp);
        } else {
            return Dataset.STAR;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public void removeAttribute(String name) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param oldAttr DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param localName DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public void removeAttributeNS(String namespaceURI, String localName)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getParent() {
        return this.getParentNode();
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     * @param value DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public void setAttribute(String name, String value)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param newAttr DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param newAttr DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param qualifiedName DOCUMENT ME!
     * @param value DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
        throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE, DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public short getNodeType() {
        return Node.ELEMENT_NODE;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getUserData() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getPRE() {
        return this.getPreviousSibling();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getNEXT() {
        return this.getNextSibling();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getPosition() {
        return position;
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     */
    public void setPosition(int index) {
        this.position = index;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final ElementBase[] getChildElements() {
        if (this.children == null) {
            this.children = NodeFactory.createChildNodes(this.getRoot().getScript(), this);
            setChildrenIndex();
        }

        return (ElementBase[]) this.children;
    }

    protected void setChildrenIndex() {
        if (this.children != null) {
            for (int i = 0; i < this.children.length; i++) {
                children[i].setPosition(i);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Root getRoot() {
        Node n = this;

        while (n instanceof ElementBase) {
            if (n instanceof Root) {
                return (Root) n;
            } else {
                n = n.getParentNode();
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NodeSource getSource() {
        return this.source;
    }

    /**
     * DOCUMENT ME!
     *
     * @param source DOCUMENT ME!
     */
    public void setSource(NodeSource source) {
        this.source = source;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tag DOCUMENT ME!
     * @param command DOCUMENT ME!
     */
    public void sort(String tag, String command) {
        NodeSource src = this.getSource().getChildByTagName(tag);

        if (src != null) {
            NodeComparatorFactory f = ComparatorFactoryCache.getInstance(src.getClass());

            if (f != null) {
                Arrays.sort(getChildElements(), f.create(command));
                setChildrenIndex();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param prop DOCUMENT ME!
     * @param obj DOCUMENT ME!
     */
    public final void setClientProperty(String prop, Object obj) {
        if (this.clientProperties == null) {
            this.clientProperties = new HashMap();
        }

        this.clientProperties.put(prop, obj);
    }

    /**
     * DOCUMENT ME!
     *
     * @param prop DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final Object getClientProperty(String prop) {
        if (this.clientProperties != null) {
            return clientProperties.get(prop);
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param script DOCUMENT ME!
     */
    public void open(Script script) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param script DOCUMENT ME!
     */
    public void close(Script script) {
    }
}
