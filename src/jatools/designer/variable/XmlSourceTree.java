package jatools.designer.variable;

import jatools.designer.App;
import jatools.designer.DocumentVariableNameChecker;
import jatools.designer.ReportEditor;
import jatools.designer.data.NameChecker;
import jatools.designer.data._Variable;
import jatools.designer.variable.popup.TreePopupFactory;
import jatools.dom.src.ArrayNodeSource;
import jatools.dom.src.CrossIndexNodeSource;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.GroupNodeSource;
import jatools.dom.src.IndexNodeSource;
import jatools.dom.src.NodeSource;
import jatools.dom.src.RootNodeSource;
import jatools.dom.src.RowNodeSource;
import jatools.util.CursorUtil;
import jatools.util.Util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.19 $
  */
public class XmlSourceTree extends JTree implements NameChecker, DragSourceListener,
    DragGestureListener, ActionListener, DropTargetListener {
    static Icon root_icon = Util.getIcon("/jatools/icons/datatree/root.gif");
    static Icon dataset_icon = Util.getIcon("/jatools/icons/datatree/dataset.gif");
    static Icon index_icon = Util.getIcon("/jatools/icons/datatree/index.gif");
    static Icon cross_icon = Util.getIcon("/jatools/icons/datatree/cross.gif");
    static Icon row_icon = Util.getIcon("/jatools/icons/datatree/row.gif");
    static Icon group_icon = Util.getIcon("/jatools/icons/datatree/group.gif");
    static Icon xml_icon = Util.getIcon("/jatools/icons/datatree/cross.gif");
    static Icon array_icon = Util.getIcon("/jatools/icons/datatree/array.gif");
    private VariableTreeModel treeModel;
    private NodeSource rootNodeSource;

    private ReportEditor editor = null;
    private SourceTreeRenderer treeRenderer;
    private boolean dirty = false;
    Rectangle selectedFieldArea = null;
    Rectangle selectedTagArea = null;
    Object datasetSelectedObject = null;
    private boolean enablePopup = true;

    /**
     * Creates a new XmlSourceTree object.
     */
    public XmlSourceTree() {
        setShowsRootHandles(true);
        enablePopup = true;
        setRowHeight(0);
        addMouseListener(new TreeMouserListener());
      
        treeModel = new VariableTreeModel(null);
        setModel(treeModel);

        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
        new DropTarget(this, this);
        this.setShowsRootHandles(true);
        this.addTreeExpansionListener(new TreeExpandingListener());
        treeRenderer = new SourceTreeRenderer();
        setCellRenderer(treeRenderer);
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == ReportEditor.OPEN) {
            this.setRootNodeSource(((ReportEditor) e.getSource()).getDocument().getNodeSource());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param rootNodeSource DOCUMENT ME!
     */
    public void initTreeData(NodeSource rootNodeSource) {
        setDirty(true);
        this.rootNodeSource = rootNodeSource;
        initTree((RootNodeSource) rootNodeSource);

        selectedFieldArea = null;
        datasetSelectedObject = null;
        repaint();
    }

    /**
     * DOCUMENT ME!
     */
    public void updateTree() {
        this.initTree((RootNodeSource) rootNodeSource);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtn DOCUMENT ME!
     */
    public void updateTreeAfterNodeAdded2(final DefaultMutableTreeNode dtn) {
        if (dtn != null) {
            if (dtn.getChildCount() == 0) {
                dtn.add(new DefaultMutableTreeNode(new Boolean(true)));
            } else {
                dtn.removeAllChildren();
                dtn.add(new DefaultMutableTreeNode(new Boolean(true)));
            }

            expand(dtn);
            treeModel.reload(dtn);
            setDirty(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtn DOCUMENT ME!
     * @param newSource DOCUMENT ME!
     */
    public void updateTreeAfterNodeModified(final DefaultMutableTreeNode dtn, NodeSource newSource) {
        if (dtn != null) {
            TreeNodeValue treeNodeValue = getTreeNodeValue(newSource, 0);
            dtn.setUserObject(treeNodeValue);
            treeModel.reload(dtn);
            setDirty(true);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtn DOCUMENT ME!
     */
    public void updateTreeAfterNodeDeleted(final DefaultMutableTreeNode dtn) {
        if (dtn != null) {
            if (dtn.getParent() != null) {
                DefaultMutableTreeNode dt = (DefaultMutableTreeNode) dtn.getParent();
                dt.remove(dtn);

                if (dt.getChildCount() > 0) {
                    dtn.removeAllChildren();
                    dtn.add(new DefaultMutableTreeNode(new Boolean(true)));
                }

                treeModel.reload(dt);
                setDirty(true);
            }
        }
    }

    //    private ArrayList getNotIndexFields(String[] indexes, String[] fields) {
    //        ArrayList _indexes = ArrayUtil.toVector(indexes);
    //        ArrayList _fields = ArrayUtil.toVector(fields);
    //        _fields.removeAll(_indexes);
    //
    //        return _fields;
    //    }
    //
    //    private String[] getFields(DatasetReader reader) {
    //        String[] fieldNames = Dataset.getFieldNames(reader);
    //
    //        return fieldNames;
    //    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getDatasetSelectedObject() {
        return datasetSelectedObject;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ReportEditor getEditor() {
        return editor;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isEnablePopup() {
        return enablePopup;
    }

    /**
     * DOCUMENT ME!
     */
    public void expandTree() {
        TreeNode root = (TreeNode) treeModel.getRoot();
        expandAll(this, new TreePath(root), true);
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();
        this.expand(node);

        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    private void selectNodeSource(DefaultMutableTreeNode node, Object nodeSource) {
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();

                TreeNodeValue value = (TreeNodeValue) n.getUserObject();

                if ((value != null) && (value.getNodeSource() == nodeSource)) {
                    this.setSelectionPath(new TreePath(n.getPath()));

                    return;
                }

                selectNodeSource(n, nodeSource);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param nodeSource DOCUMENT ME!
     */
    public void selectNodeSource(Object nodeSource) {
        this.selectNodeSource((DefaultMutableTreeNode) this.getModel().getRoot(), nodeSource);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getSeclectedObject() {
        TreePath path = this.getSelectionPath();
        TreeNodeValue nodeValue = null;

        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            nodeValue = (TreeNodeValue) node.getUserObject();
        }

        return nodeValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param dge DOCUMENT ME!
     */
    public void dragGestureRecognized(DragGestureEvent dge) {
        if (getSeclectedObject() == null) {
            return;
        }

        TreeNodeValue ns = (TreeNodeValue) getSeclectedObject();
        _Variable var = ns;
        NodeSource nodeSource = ns.getNodeSource();
        boolean allowed = false;

        if ((nodeSource instanceof DatasetNodeSource) || (nodeSource instanceof GroupNodeSource) ||
                (nodeSource instanceof RowNodeSource) || nodeSource instanceof ArrayNodeSource ||
                nodeSource instanceof IndexNodeSource) {
            allowed = true;
        }

        if (allowed) {
            if (nodeSource instanceof DatasetNodeSource) {
                if (datasetSelectedObject != null) {
                    if (datasetSelectedObject instanceof DatasetNodeSource) {
                        var = ns;
                    } else if (datasetSelectedObject instanceof String) {
                        FiledVariable field = new FiledVariable("$." +
                                datasetSelectedObject.toString(), 0,
                                "$." + datasetSelectedObject.toString());
                        var = field;
                    }
                }
            }

            if (nodeSource instanceof ArrayNodeSource) {
                if (datasetSelectedObject != null) {
                    if (datasetSelectedObject instanceof ArrayNodeSource) {
                        var = ns;
                    } else if (datasetSelectedObject instanceof String) {
                    	String sel = "$"+nodeSource.getTagName()+"."+datasetSelectedObject.toString();
                        FiledVariable field = new FiledVariable(sel,
                                0, sel);
                        var = field;
                    }
                }
            }
        }

        if (((var != null) && var.isSettable()) || allowed) {
            dge.startDrag(CursorUtil.CLICK_PLAY_CURSOR, var);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param dsde DOCUMENT ME!
     */
    public void dragEnter(DragSourceDragEvent dsde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dsde DOCUMENT ME!
     */
    public void dragOver(DragSourceDragEvent dsde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dsde DOCUMENT ME!
     */
    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dsde DOCUMENT ME!
     */
    public void dragDropEnd(DragSourceDropEvent dsde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dse DOCUMENT ME!
     */
    public void dragExit(DragSourceEvent dse) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param editor DOCUMENT ME!
     */
    public void setEditor(ReportEditor editor) {
        this.editor = editor;

        if ((this.editor != null) && (editor.getDocument() != null)) {
            setRootNodeSource(editor.getDocument().getNodeSource());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param root DOCUMENT ME!
     */
    public void setRootNodeSource(NodeSource root) {
        initTreeData(root);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dirty DOCUMENT ME!
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * DOCUMENT ME!
     *
     * @param enablePopup DOCUMENT ME!
     */
    public void setEnablePopup(boolean enablePopup) {
        this.enablePopup = enablePopup;
    }

    /**
     * DOCUMENT ME!
     *
     * @param source DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public void check(String source) throws Exception {
        DocumentVariableNameChecker checker = new DocumentVariableNameChecker(editor.getDocument());
        checker.check(source);
    }

    /**
     * DOCUMENT ME!
     *
     * @param g DOCUMENT ME!
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSelectedAttributeNode(g);
    }

    private void paintSelectedAttributeNode(Graphics g) {
        if (selectedFieldArea != null) {
            Color selectedBackGround = UIManager.getColor("Tree.selectionBackground");
            g.setPaintMode();
            g.setColor(selectedBackGround);
            g.drawRect(selectedFieldArea.x, selectedFieldArea.y, selectedFieldArea.width - 2,
                selectedFieldArea.height - 2);
        }
    }

    void initTree(RootNodeSource rootNodeSource) {
        TreeNodeValue nodeValue = getTreeNodeValue(rootNodeSource, 0);
        nodeValue.setDisplay(rootNodeSource.getTagName());

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(nodeValue);
        treeModel.setRoot(rootNode);
        treeModel.reload();

        if (hasChildren(rootNodeSource)) {
            rootNode.add(new DefaultMutableTreeNode(new Boolean(true)));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtn DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean expand(DefaultMutableTreeNode dtn) {
        if (dtn.getChildCount() > 0) {
            DefaultMutableTreeNode flag = (DefaultMutableTreeNode) dtn.getFirstChild();
            Object obj = flag.getUserObject();

            if (!(obj instanceof Boolean)) {
                return false;
            }
        }

        Object o = dtn.getUserObject();
        dtn.removeAllChildren();

        TreeNodeValue nodeValue = (TreeNodeValue) o;
        ArrayList v = nodeValue.getNodeSource().getChildren();

        for (int i = 0; i < v.size(); i++) {
            NodeSource ns = (NodeSource) v.get(i);
            ns.setParent(nodeValue.getNodeSource());

            if (hasChildren(ns)) {
                TreeNodeValue tnv = this.getTreeNodeValue(ns, 0);

                DefaultMutableTreeNode _dnt = new DefaultMutableTreeNode(tnv);
                dtn.add(_dnt);
                _dnt.add(new DefaultMutableTreeNode(new Boolean(true)));
            } else {
                TreeNodeValue tnv = getTreeNodeValue(ns, 0);
                DefaultMutableTreeNode _dnt = new DefaultMutableTreeNode(tnv);
                dtn.add(_dnt);
            }
        }

        //tree.setSelectionPath(treePath.getParentPath().pathByAddingChild(newNode));
        return true;
    }

    boolean hasChildren(NodeSource ns) {
        return ns.getChildren().size() > 0;
    }

    TreeNodeValue getTreeNodeValue(NodeSource ns, int permission) {
        TreeNodeValue result = null;

        if (ns instanceof DatasetNodeSource) {
            result = new DatasetTreeNodeValue(ns, ns.getTagName(), 0, ns.getTagName());
        } else {
            result = new TreeNodeValue(ns, ns.getTagName(), 0, ns.getTagName());
        }

        return result;
    }

    private boolean isDragAccetable(DropTargetDragEvent dtde) {
        return (dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void dragEnter(DropTargetDragEvent dtde) {
        if (!isDragAccetable(dtde)) {
            dtde.rejectDrag();

            return;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void dragOver(DropTargetDragEvent dtde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable trans = dtde.getTransferable();
        DataFlavor[] flavors = trans.getTransferDataFlavors();

        Point dropPoint = dtde.getLocation();
        TreePath treePath = getPathForLocation(dropPoint.x, dropPoint.y);

        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(_Variable.thisFlavor)) {
                try {
                    _Variable var = (_Variable) trans.getTransferData(flavors[i]);

                    if (var instanceof TreeNodeValue) {
                        TreeNodeValue tnv = (TreeNodeValue) var;

                        if (treePath != null) {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
                            TreeNodeValue nodeValue = (TreeNodeValue) node.getUserObject();

                            if (!nodeValue.equals(tnv)) {
                                if ((nodeValue.getNodeSource().getParent() == null) ||
                                        !nodeValue.getNodeSource().getParent()
                                                      .equals(tnv.getNodeSource().getParent())) {
                                    JOptionPane.showConfirmDialog(this, App.messages.getString("res.234"), App.messages.getString("res.55"),
                                        JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);

                                    return;
                                }

                                NodeSource parent = nodeValue.getNodeSource().getParent();
                                int indexStart = parent.getChildren().indexOf(tnv.getNodeSource());
                                int indexTarget = parent.getChildren()
                                                        .indexOf(nodeValue.getNodeSource());
                                ArrayList childs = parent.getChildren();
                                ArrayList v = new ArrayList();

                                if (indexStart < indexTarget) {
                                    for (int j = 0; j < indexStart; j++) {
                                        v.add(childs.get(j));
                                    }

                                    for (int j = indexStart + 1; j < (indexTarget + 1); j++) {
                                        v.add(childs.get(j));
                                    }

                                    v.add(childs.get(indexStart));

                                    for (int j = indexTarget + 1; j < childs.size(); j++) {
                                        v.add(childs.get(j));
                                    }
                                } else {
                                    for (int j = 0; j < indexTarget; j++) {
                                        v.add(childs.get(j));
                                    }

                                    v.add(childs.get(indexStart));

                                    for (int j = indexTarget; j < indexStart; j++) {
                                        v.add(childs.get(j));
                                    }

                                    for (int j = indexStart + 1; j < childs.size(); j++) {
                                        v.add(childs.get(j));
                                    }
                                }

                                childs.clear();
                                parent.setChildren(v);
                            }

                            //               childs.removeAllElements();
                            //               parent.setChildren(v);
                            parentNode.removeAllChildren();
                            parentNode.add(new DefaultMutableTreeNode(new Boolean(true)));
                            this.expand(parentNode);
                            treeModel.reload(parentNode);
                            setDirty(true);
                        }

                        int r = this.getRowForLocation(dropPoint.x, dropPoint.y);
                        this.setSelectionInterval(r, r);
                    }

                    //>>>>>>> 1.15
                    //                    }
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        dtde.dropComplete(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dte DOCUMENT ME!
     */
    public void dragExit(DropTargetEvent dte) {
    }

    String[] getColumns(DefaultMutableTreeNode node) {
        if (node.getUserObject() instanceof DatasetTreeNodeValue) {
            DatasetTreeNodeValue nodeValue = (DatasetTreeNodeValue) node.getUserObject();

            return nodeValue.getColumns();
        } else if (node.getParent() != null) {
            return getColumns((DefaultMutableTreeNode) node.getParent());
        } else {
            return new String[0];
        }
    }

    class TreeMouserListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            Point p = e.getPoint();
            TreePath treePath = getPathForLocation(p.x, p.y);

            if (treePath != null) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                TreeNodeValue nodeValue = (TreeNodeValue) node.getUserObject();
                NodeSource nodeSource = nodeValue.getNodeSource();

                if (e.getClickCount() == 2) {
                    if (nodeSource instanceof DatasetNodeSource &&
                            datasetSelectedObject instanceof String) {
                        String v = ((DatasetNodeSource) nodeSource).getTagName() + "." +
                            datasetSelectedObject.toString();
                        XmlSourceTree.this.firePropertyChange("select.value", null, v);
                        datasetSelectedObject = null;
                    } else if (nodeSource instanceof ArrayNodeSource &&
                            datasetSelectedObject instanceof String) {
                        String v = ((ArrayNodeSource) nodeSource).getTagName() + "." +
                            datasetSelectedObject.toString();
                        XmlSourceTree.this.firePropertyChange("select.value", null, v);
                        datasetSelectedObject = null;
                    } else {
                        String newPath = null;

                        if (e.isControlDown()) {
                            newPath = nodeSource.getFullPath();
                        } else {
                            newPath = nodeSource.getTagName();
                        }

                        XmlSourceTree.this.firePropertyChange("select.value", null, newPath);
                    }

                    datasetSelectedObject = null;
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (enablePopup) {
                if (e.isPopupTrigger()) {
                    int row = getRowForLocation(e.getX(), e.getY());

                    if (row != -1) {
                        setSelectionInterval(row, row);

                        TreePath path = getPathForRow(row);
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        TreeNodeValue nodeValue = (TreeNodeValue) node.getUserObject();

                        JPopupMenu menu = TreePopupFactory.createPopup(XmlSourceTree.this,
                                nodeValue.getSourceType(), node);

                        if (menu != null) {
                            Rectangle b = getPathBounds(getSelectionPath());

                            menu.show(e.getComponent(), e.getX(), b.y + b.height);
                        }
                    }
                }
            }
        }

        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            TreePath treePath = getPathForLocation(p.x, p.y);
            int row = XmlSourceTree.this.getRowForPath(treePath);
            setSelectionInterval(row, row);

            if (treePath != null) {
                int x = 0;
                int y = 0;
                int x0 = e.getX();
                int y0 = e.getY();
                Rectangle rec = XmlSourceTree.this.getPathBounds(treePath);
                x = x0 - rec.x;
                y = y0 - rec.y;

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                TreeNodeValue nodeValue = (TreeNodeValue) node.getUserObject();
                NodeSource nodeSource = nodeValue.getNodeSource();

                if (nodeSource instanceof DatasetNodeSource) {
                    DatasetNodeSource datasetNodeSource = (DatasetNodeSource) nodeSource;
                    String[] fields = getColumns(node);

                    Rectangle r = (Rectangle) treeRenderer.map.get("[" + row + "]" +
                            datasetNodeSource.toString());

                    if ((r != null) && r.contains(x, y)) {
                        selectedTagArea = new Rectangle(r.x + rec.x, rec.y, r.width, r.height);
                        datasetSelectedObject = datasetNodeSource;

                        selectedFieldArea = null;
                        repaint();
                    } else {
                        if (nodeSource instanceof CrossIndexNodeSource) {
                            CrossIndexNodeSource crossSource = (CrossIndexNodeSource) nodeSource;
                            String[] index1 = crossSource.getIndexFields();
                            String[] index2 = crossSource.getIndexFields2();

                            r = null;

                            for (int i = 0; i < index1.length; i++) {
                                r = (Rectangle) treeRenderer.map.get("[" + row + "]" + "index1" +
                                        index1[i]);

                                if ((r != null) && r.contains(x, y)) {
                                    selectedFieldArea = new Rectangle(r.x + rec.x, rec.y, r.width,
                                            r.height);
                                    datasetSelectedObject = index1[i];
                                    repaint();

                                    break;
                                }
                            }

                            for (int i = 0; i < index2.length; i++) {
                                r = (Rectangle) treeRenderer.map.get("[" + row + "]" + "index2" +
                                        index2[i]);

                                if ((r != null) && r.contains(x, y)) {
                                    selectedFieldArea = new Rectangle(r.x + rec.x, rec.y, r.width,
                                            r.height);
                                    datasetSelectedObject = index2[i];
                                    repaint();

                                    break;
                                }
                            }

                            for (int i = 0; i < fields.length; i++) {
                                r = (Rectangle) treeRenderer.map.get("[" + row + "]" + fields[i]);

                                if ((r != null) && r.contains(x, y)) {
                                    selectedFieldArea = new Rectangle(r.x + rec.x, rec.y, r.width,
                                            r.height);
                                    datasetSelectedObject = fields[i];
                                    repaint();

                                    break;
                                }
                            }
                        } else {
                            for (int i = 0; i < fields.length; i++) {
                                r = (Rectangle) treeRenderer.map.get("[" + row + "]" + fields[i]);

                                if ((r != null) && r.contains(x, y)) {
                                    selectedFieldArea = new Rectangle(r.x + rec.x, rec.y, r.width,
                                            r.height);
                                    datasetSelectedObject = fields[i];
                                    repaint();
                                }
                            }
                        }
                    }
                } else if (nodeSource instanceof ArrayNodeSource) {
                    ArrayNodeSource arraySource = (ArrayNodeSource) nodeSource;
                    Rectangle r = (Rectangle) treeRenderer.map.get("[" + row + "]" +
                            arraySource.getTagName());

                    if ((r != null) && r.contains(x, y)) {
                        selectedTagArea = new Rectangle(r.x + rec.x, rec.y, r.width, r.height);
                        datasetSelectedObject = arraySource;

                        selectedFieldArea = null;
                        repaint();
                    } else {
                        String[] v = arraySource.getFields();

                        if (v != null) {
                            for (int i = 0; i < v.length; i++) {
                                r = (Rectangle) treeRenderer.map.get("[" + row + "]" + v[i]);

                                if ((r != null) && r.contains(x, y)) {
                                    selectedFieldArea = new Rectangle(r.x + rec.x, rec.y, r.width,
                                            r.height);
                                    datasetSelectedObject = v[i];
                                    repaint();
                                }
                            }
                        }
                    }
                } else {
                    selectedFieldArea = null;
                    datasetSelectedObject = null;
                    repaint();
                }
            } else {
                selectedFieldArea = null;
                datasetSelectedObject = null;
                repaint();
            }
        }
    }

    class TreeExpandingListener implements TreeExpansionListener {
        public void treeCollapsed(TreeExpansionEvent event) {
        }

        public void treeExpanded(TreeExpansionEvent event) {
            TreePath path = event.getPath();
            final DefaultMutableTreeNode dtn = (DefaultMutableTreeNode) path.getLastPathComponent();
            Thread runner = new Thread() {
                    public void run() {
                        if ((dtn != null) && expand(dtn)) {
                            Runnable runnable = new Runnable() {
                                    public void run() {
                                        treeModel.reload(dtn);
                                    }
                                };

                            SwingUtilities.invokeLater(runnable);
                        }
                    }
                };

            runner.start();
        }
    }
}
