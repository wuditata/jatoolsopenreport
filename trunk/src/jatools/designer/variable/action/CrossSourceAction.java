package jatools.designer.variable.action;


import jatools.designer.variable.TreeNodeValue;
import jatools.designer.variable.XmlSourceTree;
import jatools.designer.variable.dialog.CrossIndexDialog;
import jatools.dom.src.CrossIndexNodeSource;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.NodeSource;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class CrossSourceAction extends AbstractAction {
    public static final int ADD = 0;
    public static final int DELETE = 1;
    public static final int MODIFY = 2;
    public static final int DEFINE_COLUMN = 3;
    private int type;
    private Component c;
    private NodeSource nodeSource;
    private DefaultMutableTreeNode defaultMutableTreeNode;

    /**
     * Creates a new CrossSourceAction object.
     *
     * @param name DOCUMENT ME!
     * @param c DOCUMENT ME!
     * @param defaultMutableTreeNode DOCUMENT ME!
     * @param type DOCUMENT ME!
     */
    public CrossSourceAction(String name, Component c,
        DefaultMutableTreeNode defaultMutableTreeNode, int type) {
        super(name);
        this.type = type;
        this.c = c;
        this.defaultMutableTreeNode = defaultMutableTreeNode;

        TreeNodeValue nodeValue = (TreeNodeValue) defaultMutableTreeNode.getUserObject();
        this.nodeSource = nodeValue.getNodeSource();
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        switch (type) {
        case ADD:
            addCross();

            break;

        case DELETE:
            delCross();

            break;

        case MODIFY:
            modifyCross();

            break;
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void addCross() {
        if (this.nodeSource instanceof DatasetNodeSource) {
            CrossIndexNodeSource cross = new CrossIndexNodeSource(null, null, null);
            cross = CrossIndexDialog.getNodeSource((DatasetNodeSource) this.nodeSource, cross, c);

            if (cross != null) {
                nodeSource.add(cross);
                updateTree(defaultMutableTreeNode, ADD);

                if (c instanceof XmlSourceTree) {
                    ((XmlSourceTree) c).selectNodeSource(cross);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void delCross() {
        ArrayList brother = nodeSource.getParent().getChildren();
        nodeSource.getParent().remove(brother.indexOf(nodeSource));
        updateTree(defaultMutableTreeNode, DELETE);
    }

    /**
     * DOCUMENT ME!
     */
    public void modifyCross() {
        CrossIndexNodeSource dns = (CrossIndexNodeSource) nodeSource;
        CrossIndexNodeSource indexNodeSource = CrossIndexDialog.getNodeSource((DatasetNodeSource) dns.getParent(),
                (CrossIndexNodeSource) dns, c);

        if (indexNodeSource != null) {
            updateTree(defaultMutableTreeNode, MODIFY);
        }
    }

    private void updateTree(DefaultMutableTreeNode defaultMutableTreeNode, int type) {
        if (c instanceof XmlSourceTree) {
            XmlSourceTree tree = (XmlSourceTree) c;

            switch (type) {
            case ADD:
                tree.updateTreeAfterNodeAdded2(defaultMutableTreeNode);

                break;

            case MODIFY:
                tree.updateTreeAfterNodeModified(defaultMutableTreeNode, nodeSource);

                break;

            case DELETE:
                tree.updateTreeAfterNodeDeleted(defaultMutableTreeNode);

                break;
            }
        }
    }
}
