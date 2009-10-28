/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package jatools.swingx;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * @author zhou
 *
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class SimpleTreeNode extends DefaultMutableTreeNode {
    public static final String ALIAS2 = "alias";
    int type;
    Icon icon;
    Map properites;
    boolean disabled;

    /**
     * Creates a new ZSimpleTreeNode object.
     *
     * @param alias DOCUMENT ME!
     * @param userObject DOCUMENT ME!
     * @param icon DOCUMENT ME!
     * @param nodeType DOCUMENT ME!
     */
    public SimpleTreeNode(Object userObject, Icon icon, int nodeType) {
        super(userObject);
        setIcon(icon);
        this.type = nodeType;
    }

    /**
     * Creates a new ZSimpleTreeNode object.
     *
     * @param userObject DOCUMENT ME!
     * @param icon DOCUMENT ME!
     */
    public SimpleTreeNode(Object userObject, Icon icon) {
        this(userObject, icon, 0);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        String alias = null;

//        if (this.properites != null) {
//            alias = (String) this.properites.get(ALIAS);
//        }

        if (alias == null) {
            return super.toString();
        } else {
            return userObject + " <" + alias + ">"; // //$NON-NLS-2$
        }
    }

    /**
     * @return Returns the icon.
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            The icon to set.
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * @return Returns the nodeType.
     */
    public int getType() {
        return type;
    }

    /**
     * @param nodeType The nodeType to set.
     */
    public void setType(int nodeType) {
        this.type = nodeType;
    }

    public void setProperty(String prop, Object value) {
        if (this.properites == null) {
            this.properites = new HashMap();
        }

        this.properites.put(prop, value);
    }

    public Object getProperty(String key) {
        if (this.properites != null) {
            return this.properites.get(key);
        } else {

            return null;
        }
    }

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
