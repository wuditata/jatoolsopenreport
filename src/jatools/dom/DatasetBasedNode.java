package jatools.dom;

import jatools.dataset.Dataset;
import jatools.dataset.RowSet;
import jatools.dom.field.FixedNodeField;
import jatools.engine.script.Script;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import bsh.CallStack;
import bsh.Interpreter;
import bsh.Primitive;
import bsh.PropertyGetter;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.13 $
  */
public abstract class DatasetBasedNode extends ElementBase implements NodeSortable, NodeList,
    PropertyGetter {
    protected DatasetBasedNode _parent = null;

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getParentNode() {
        return this._parent;
    }

    /**
     * DOCUMENT ME!
     *
     * @param col DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object valueAt(int col) {
        RowSet index = this.getRowSet();

        return (index == null) ? null : index.valueAt(col);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public abstract RowSet getRowSet();



    /**
     * DOCUMENT ME!
     *
     * @param prop DOCUMENT ME!
     * @param callstack DOCUMENT ME!
     * @param interpreter DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getProperty(String prop, CallStack callstack, Interpreter interpreter) {
        Dataset data = getDataset();

        if (data != null) {
            int col = data.getColumnIndex(prop);

            if (col > -1) {
                return new FixedNodeField(col, this);
            }
        }

        return Primitive.VOID;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object field(String name) {
        Dataset data = getDataset();

        if (data != null) {
            int col = data.getColumnIndex(name);

            if (col > -1) {
                return new FixedNodeField(col, this);
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public abstract Dataset getDataset();

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public abstract String getChildElementsLocalName();

    /**
     * DOCUMENT ME!
     */
    public abstract void clear();

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NodeList getElementsByTagName(String name) {
        if (name.endsWith(getChildElementsLocalName())) {
            return this;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param localName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        if (namespaceURI.equals(getNamespaceURI()) &&
                (localName.equals(getChildElementsLocalName()))) {
            return this;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NodeList getChildNodes() {
        return this;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getNextSibling() {
        Element[] siblings = _parent.getChildElements();
        int index = this.getPosition();

        if ((index + 1) < siblings.length) {
            return siblings[index + 1];
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getPreviousSibling() {
        Element[] siblings = _parent.getChildElements();
        int index = this.getPosition();

        if (index > 0) {
            return siblings[index - 1];
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getFirstChild() {
        if (getLength() <= 0) {
            return null;
        }

        return item(0);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node getLastChild() {
        if (getLength() <= 0) {
            return null;
        }

        return item(getLength() - 1);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasChildNodes() {
        return (getLength() > 0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getAttribute(String name) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Attr getAttributeNode(String name) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param localName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Attr getAttributeNodeNS(String namespaceURI, String localName) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param localName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getAttributeNS(String namespaceURI, String localName) {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasAttribute(String name) {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param namespaceURI DOCUMENT ME!
     * @param localName DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasAttributeNS(String namespaceURI, String localName) {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param deep DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node cloneNode(boolean deep) {
        return this;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NamedNodeMap getAttributes() {
        return EMPTY;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws DOMException DOCUMENT ME!
     */
    public String getNodeValue() throws DOMException {
        return "";
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasAttributes() {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getLength() {
        return getChildElements().length;
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Node item(int index) {
        return getChildElements()[index];
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DatasetNode getDatasetRoot() {
        Node n = this;

        while (n instanceof DatasetBasedNode) {
            if (n instanceof DatasetNode) {
                return (DatasetNode) n;
            } else {
                n = n.getParentNode();
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param script DOCUMENT ME!
     */
    public void open(Script script) {
        NodeNameSpace name = this.getDatasetRoot().getNameSpace2(this, script);
        name.push(this);
        script.pushNameSpace(name);

        script.getKeyStack(script.getStackType()).push(getKey());
    }

    /**
     * DOCUMENT ME!
     *
     * @param script DOCUMENT ME!
     */
    public void close(Script script) {
        script.getKeyStack(script.getStackType()).pop();

        NodeNameSpace name = (NodeNameSpace) script.popNameSpace();
        name.pop();
    }

    /**
     * DOCUMENT ME!
     *
     * @param col DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object[] valuesAt(int col) {
        RowSet index = this.getRowSet();

        return (index == null) ? null : index.valuesAt(col);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public RowList getRowList() {
        return this.getRowSet();
    }

	public TypeInfo getSchemaTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public Document getOwnerDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTextContent() throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getUserData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEqualNode(Node arg) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameNode(Node other) {
		// TODO Auto-generated method stub
		return false;
	}

	public String lookupNamespaceURI(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	public String lookupPrefix(String namespaceURI) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTextContent(String textContent) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
}
