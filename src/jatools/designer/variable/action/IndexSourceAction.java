package jatools.designer.variable.action;

import jatools.designer.variable.TreeNodeValue;
import jatools.designer.variable.XmlSourceTree;
import jatools.designer.variable.dialog.IndexDialog;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.GroupNodeSource;
import jatools.dom.src.IndexNodeSource;
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
 * @version $Revision: 1.9 $
  */
public class IndexSourceAction extends AbstractAction {
    public static final int ADD = 0;
    public static final int DELETE = 1;
    public static final int MODIFY = 2;
    public static final int DEFINE_COLUMN = 3;
    private int type;
    private Component c;
    private NodeSource nodeSource;
    private DefaultMutableTreeNode defaultMutableTreeNode;

    /**
     * Creates a new IndexSourceAction object.
     *
     * @param name DOCUMENT ME!
     * @param c DOCUMENT ME!
     * @param defaultMutableTreeNode DOCUMENT ME!
     * @param type DOCUMENT ME!
     */
    public IndexSourceAction(String name, Component c,
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
            addIndex();

            break;

        case DELETE:
            deleteIndex();

            break;

        case MODIFY:
            modifyIndex();

            break;

      
        }
    }

    private void addIndex() {
        IndexNodeSource indexed = new IndexNodeSource(null, null);
        IndexNodeSource indexNodeSource = IndexDialog.getNodeSource((DatasetNodeSource) nodeSource,
                indexed, c);

        if (indexNodeSource != null) {
            nodeSource.add(indexNodeSource);
            updateTree(defaultMutableTreeNode, ADD);
            if (c instanceof XmlSourceTree) 
                ((XmlSourceTree) c).selectNodeSource(indexNodeSource);
        }
    }

    private void deleteIndex() {
//        int option = JOptionPane.showConfirmDialog(c, "删除之后不能恢复，确定删除？", "提示",
//                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
//
//        if (option == JOptionPane.OK_OPTION) {
            ArrayList brother = nodeSource.getParent().getChildren();
            nodeSource.getParent().remove(brother.indexOf(nodeSource));
            updateTree(defaultMutableTreeNode, DELETE);
//        }
    }

    private void modifyIndex() {
//        if (containOwnGroup(nodeSource)) {
//            int option = JOptionPane.showConfirmDialog(c, "修改将影响子节点Group，确定修改？", "提示",
//                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
//
//            if (option != JOptionPane.OK_OPTION) {
//                return;
//            }
//        }

        IndexNodeSource indexNodeSource = IndexDialog.getNodeSource((DatasetNodeSource) nodeSource.getParent(),
                (IndexNodeSource) nodeSource, c);

        if (indexNodeSource != null) {
            updateTree(defaultMutableTreeNode, MODIFY);
        }
    }

    private boolean containOwnGroup(NodeSource ns) {
        boolean hasGroup = false;
        ArrayList children = ns.getChildren();

        for (int i = 0; i < children.size(); i++) {
            NodeSource _ns = (NodeSource) children.get(i);

            if (_ns instanceof GroupNodeSource) {
                hasGroup = true;

                break;
            } else if (_ns instanceof DatasetNodeSource) {
                hasGroup = false;

                break;
            } else {
                hasGroup = containOwnGroup(_ns);
            }
        }

        return hasGroup;
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
