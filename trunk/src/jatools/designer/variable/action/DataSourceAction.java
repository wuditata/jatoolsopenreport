package jatools.designer.variable.action;

import jatools.data.reader.DatasetReader;
import jatools.designer.Main;
import jatools.designer.data.DatasetPreviewer;
import jatools.designer.data.DatasetReaderFactory;
import jatools.designer.data.SimpleDatasetReaderFactory;
import jatools.designer.variable.DatasetTreeNodeValue;
import jatools.designer.variable.TreeNodeValue;
import jatools.designer.variable.XmlSourceTree;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.GroupNodeSource;
import jatools.dom.src.NodeSource;
import jatools.swingx.MessageBox;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.18 $
 */
public class DataSourceAction extends AbstractAction {
    public static final int ADD_JDBC = 0;

    public static final int DELETE = 7;
    public static final int MODIFY = 8;
    public static final int PREVIEW = 10;
    public static final int UPDATE_OPTIONS = 11;
    private int type;
    private Component c;
    private NodeSource nodeSource;
    private DefaultMutableTreeNode defaultMutableTreeNode;

    /**
     * Creates a new DataSourceAction object.
     *
     * @param name DOCUMENT ME!
     * @param c DOCUMENT ME!
     * @param defaultMutableTreeNode DOCUMENT ME!
     * @param type DOCUMENT ME!
     */
    public DataSourceAction(String name, Component c,
        DefaultMutableTreeNode defaultMutableTreeNode, int type) {
        super(name);
        this.c = c;
        this.defaultMutableTreeNode = defaultMutableTreeNode;

        TreeNodeValue nodeValue = (TreeNodeValue) defaultMutableTreeNode.getUserObject();
        this.nodeSource = nodeValue.getNodeSource();
        this.type = type;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        switch (type) {
        case ADD_JDBC:

            add(type);

            break;

        case DELETE:
            deleteAction();

            break;

        case MODIFY:
            modifyAction();

            break;

        case PREVIEW:
            preview();

            break;

        

        }
    }

   

    void invalidate() {
        ((DatasetTreeNodeValue) this.defaultMutableTreeNode.getUserObject()).invalidate();
    }

    /**
     * DOCUMENT ME!
     *
     * @param type DOCUMENT ME!
     */
    public void add(int type) {
        DatasetReaderFactory proxyFactory = SimpleDatasetReaderFactory.getIntance(type);

        if (proxyFactory != null) {
            DatasetReader proxy = proxyFactory.create(Main.getInstance(), (XmlSourceTree) c);

            if (proxy != null) {
                String tagName = proxy.getName();
                DatasetNodeSource datasetNodeSource = new DatasetNodeSource(tagName, proxy);
                nodeSource.add(datasetNodeSource);
                updateTree(defaultMutableTreeNode, type);

                if (c instanceof XmlSourceTree) {
                    ((XmlSourceTree) c).selectNodeSource(datasetNodeSource);
                }
            }
        }
    }

    void preview() {
        try {
            DatasetPreviewer preview = new DatasetPreviewer(Main.getInstance());
            preview.setLocationRelativeTo(Main.getInstance());
            preview.setReader(((DatasetNodeSource) nodeSource).getReader());
            preview.show();
        } catch (Exception ex) {
            ex.printStackTrace();

            MessageBox.error(Main.getInstance(), ex.getMessage());
        }
    }

    private void deleteAction() {
        //        int option = JOptionPane.showConfirmDialog(c, "删除之后不能恢复，确定删除？", "提示",
        //                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        //
        //        if (option == JOptionPane.OK_OPTION) {
        ArrayList brother = nodeSource.getParent().getChildren();
        nodeSource.getParent().remove(brother.indexOf(nodeSource));
        updateTree(defaultMutableTreeNode, DELETE);

        //        }
    }

    private void modifyAction() {
        //        if (containOwnGroup(nodeSource)) {
        //            int option = JOptionPane.showConfirmDialog(c, "修改将影响子节点Group，确定修改？", "提示",
        //                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        //
        //            if (option != JOptionPane.OK_OPTION) {
        //                return;
        //            }
        //        }
        DatasetNodeSource dns = (DatasetNodeSource) nodeSource;
        DatasetReader proxy = dns.getReader();
        DatasetReaderFactory proxyFactory = SimpleDatasetReaderFactory.getIntance(proxy.getType());

        if (proxyFactory.edit(proxy, Main.getInstance(), (XmlSourceTree) c)) {
            invalidate();
            dns.setTagName(proxy.getName());
            updateTree(defaultMutableTreeNode, MODIFY);
        }
    }

   

    private void updateTree(DefaultMutableTreeNode defaultMutableTreeNode, int type) {
        if (c instanceof XmlSourceTree) {
            XmlSourceTree tree = (XmlSourceTree) c;

            switch (type) {
            case ADD_JDBC:
          
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
}
