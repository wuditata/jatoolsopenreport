package jatools.dom;


import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * Implements the meta-data attribute
 * @author Tal Rotbart <tal@netbox.com>
 * @version 1.0
 * @created        09/11/2003
 * @copyright (C) Copyright 2003 by Tal Rotbart, Manspace. Licensed under the LGPL -- See LICENSE.TXT
 */
public class FieldAttr extends NodeBase implements Attr {
    GroupNode _rowElement;


    FieldAttr(GroupNode rowElement) {
    	this._rowElement = rowElement;

    }

    /****************************************************************
    * Attr. impl
    */

    /**
     * @see org.w3c.dom.Attr#getName()
     */
    public String getName() {
        return ((getPrefix() != null) ? (getPrefix() + ":") : "") + getLocalName();
    }

    /**
     * @see org.w3c.dom.Attr#getOwnerElement()
     */
    public Element getOwnerElement() {
        return this._rowElement;
    }

    /**
     * @see org.w3c.dom.Attr#getSpecified()
     */
    public boolean getSpecified() {
        return true;
    }

    /**
     * @see org.w3c.dom.Attr#getValue()
     */
    public String getValue() {
    	return this._rowElement.getUserData()+"";
    }

    /**
     * @see org.w3c.dom.Attr#setValue(java.lang.String)
     */
    public void setValue(String value) throws DOMException {
        throw new DOMException(DatasetNode.NOT_IMPL_ERROR_CODE,
            DatasetNode.NOT_IMPL_ERROR_MSG);
    }

    /**
     * @see org.w3c.dom.Node#cloneNode(boolean)
     */
    public Node cloneNode(boolean deep) {
        return this;
    }

    /**
     * @see org.w3c.dom.Node#getAttributes()
     */
    public NamedNodeMap getAttributes() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getChildNodes()
     */
    public NodeList getChildNodes() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getFirstChild()
     */
    public Node getFirstChild() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getLastChild()
     */
    public Node getLastChild() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getLocalName()
     */
    public String getLocalName() {
      

        return "key";
    }

    /**
     * @see org.w3c.dom.Node#getNamespaceURI()
     */
    public String getNamespaceURI() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getNextSibling()
     */
    public Node getNextSibling() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getNodeType()
     */
    public short getNodeType() {
        return Node.ATTRIBUTE_NODE;
    }

    /**
     * @see org.w3c.dom.Node#getNodeValue()
     */
    public String getNodeValue() throws DOMException {
        return getValue();
    }

    /**
     * @see org.w3c.dom.Node#getOwnerDocument()
     */
    public Document getOwnerDocument() {
        return _rowElement.getOwnerDocument();
    }

    /**
     * @see org.w3c.dom.Node#getParentNode()
     */
    public Node getParentNode() {
        return _rowElement;
    }

    /**
     * @see org.w3c.dom.Node#getPrefix()
     */
    public String getPrefix() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#getPreviousSibling()
     */
    public Node getPreviousSibling() {
        return null;
    }

    /**
     * @see org.w3c.dom.Node#hasAttributes()
     */
    public boolean hasAttributes() {
        return false;
    }

    /**
     * @see org.w3c.dom.Node#hasChildNodes()
     */
    public boolean hasChildNodes() {
        return false;
    }

}
