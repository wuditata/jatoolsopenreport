package jatools.designer.variable;

import jatools.dom.src.CrossIndexNodeSource;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.IndexNodeSource;
import jatools.util.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class SourceTreeRenderer extends JPanel implements TreeCellRenderer {
    public static HashMap map;
    static Icon root_icon = Util.getIcon("/jatools/icons/datatree/root.gif");
    static Icon dataset_icon = Util.getIcon("/jatools/icons/datatree/dataset.gif");
    static Icon index_icon = Util.getIcon("/jatools/icons/datatree/index.gif");
    static Icon cross_icon = Util.getIcon("/jatools/icons/datatree/cross.gif");
    static Icon row_icon = Util.getIcon("/jatools/icons/datatree/row.gif");
    static Icon group_icon = Util.getIcon("/jatools/icons/datatree/group.gif");
    static Icon xml_icon = Util.getIcon("/jatools/icons/datatree/xml.gif");
    static Icon array_icon = Util.getIcon("/jatools/icons/datatree/array.gif");
    static Color selectedBackGround = UIManager.getColor("Tree.selectionBackground");
    static final String NODE = "node";
    static final Color labelColor = UIManager.getColor("TableHeader.background");
    static final Border labelBorder = UIManager.getBorder("TableHeader.cellBorder");

    /**
     * Creates a new SourceTreeRenderer object.
     */
    public SourceTreeRenderer() {
        super();
        map = new HashMap();
        this.setOpaque(false);
    }

    /**
     * DOCUMENT ME!
     *
     * @param tree DOCUMENT ME!
     * @param value DOCUMENT ME!
     * @param selected DOCUMENT ME!
     * @param expanded DOCUMENT ME!
     * @param leaf DOCUMENT ME!
     * @param row DOCUMENT ME!
     * @param hasFocus DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
        boolean expanded, boolean leaf, int row, boolean hasFocus) {
        this.removeAll();

        if (value != null) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            Object nodeValue = treeNode.getUserObject();

            setBackground(Color.white);

            if (nodeValue instanceof Boolean) {
                return new JLabel("正在加载...");
            }

            if (nodeValue instanceof TreeNodeValue) {
                TreeNodeValue tnv = (TreeNodeValue) nodeValue;
                int type = tnv.getSourceType();

                switch (type) {
                case TreeNodeValue.ROOT_NODE_SOURCE:
                    setLayout(new BorderLayout());

                    IconPanel label = new IconPanel(tnv.toString(), root_icon);

                    add(label, BorderLayout.WEST);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    break;

                case TreeNodeValue.CROSS_DATASET_NODE_SOURCE:
                    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

                    CrossIndexNodeSource crossIndexDataset = (CrossIndexNodeSource) tnv.getNodeSource();
                    String[] index = crossIndexDataset.getIndexFields();
                    String[] index2 = crossIndexDataset.getIndexFields2();
                    String tagName = crossIndexDataset.getTagName();
                    label = new IconPanel(tagName, cross_icon);
                    label.putClientProperty(NODE, "[" + row + "]" + crossIndexDataset.toString());
                    add(label);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    add(new JLabel("{"));

                    for (int i = 0; i < index.length; i++) {
                        JLabel button = new JLabel(index[i]);
                        button.setOpaque(true);
                        button.setBackground(labelColor);
                        button.setFont(new Font("宋体", Font.PLAIN, 12));
                        button.setBorder(labelBorder);

                        button.putClientProperty(NODE, "[" + row + "]" + "index1" + index[i]);
                        add(button);
                    }

                    add(new JLabel("}"));

                    add(new JLabel("{"));

                    for (int i = 0; i < index2.length; i++) {
                        JLabel button = new JLabel(index2[i]);
                        button.setOpaque(true);
                        button.setBackground(labelColor);
                        button.setFont(new Font("宋体", Font.PLAIN, 12));
                        button.setBorder(labelBorder);

                        button.putClientProperty(NODE, "[" + row + "]" + "index2" + index2[i]);
                        add(button);
                    }

                    add(new JLabel("}"));

                    break;

                case TreeNodeValue.INDEX_DATASET_NODE_SOURCE:
                    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

                    IndexNodeSource indexDataset = (IndexNodeSource) tnv.getNodeSource();
                    index = indexDataset.getIndexFields();

                    tagName = indexDataset.getTagName();
                    label = new IconPanel(tagName, index_icon);
                    label.putClientProperty(NODE, "[" + row + "]" + indexDataset.toString());
                    add(label);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    add(new JLabel("{"));

                    for (int i = 0; i < index.length; i++) {
                        JLabel button = new JLabel(index[i]);
                        button.setOpaque(true);
                        button.setBackground(labelColor);
                        button.setFont(new Font("宋体", Font.PLAIN, 12));
                        button.setBorder(labelBorder);

                        button.putClientProperty(NODE, "[" + row + "]" + index[i]);
                        add(button);
                    }

                    add(new JLabel("}"));

                    break;

                case TreeNodeValue.DATASET_NODE_SOURCE:
                    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

                    DatasetNodeSource dataSource = (DatasetNodeSource) tnv.getNodeSource();
                    String[] fields = ((XmlSourceTree) tree).getColumns(treeNode);
                    tagName = dataSource.getTagName();
                    label = new IconPanel(tagName, dataset_icon);
                    label.putClientProperty(NODE, "[" + row + "]" + dataSource.toString());
                    add(label);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    if (fields != null) {
                        for (int i = 0; i < fields.length; i++) {
                            JLabel button = new JLabel(fields[i]);
                            button.setOpaque(true);
                            button.setBackground(labelColor);
                            button.setFont(new Font("宋体", Font.PLAIN, 12));
                            button.setBorder(labelBorder);
                            button.putClientProperty(NODE, "[" + row + "]" + fields[i]);
                            add(button);
                        }
                    }

                    break;

                case TreeNodeValue.GROUP_NODE_SOURCE:
                    setLayout(new BorderLayout());
                    label = new IconPanel(tnv.toString(), group_icon);

                    add(label, BorderLayout.WEST);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    break;

                case TreeNodeValue.ROW_NODE_SOURCE:
                    setLayout(new BorderLayout());
                    label = new IconPanel(tnv.toString(), row_icon);
                    add(label, BorderLayout.WEST);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    break;

                case TreeNodeValue.XML_NODE_SOURCE:
                    setLayout(new BorderLayout());
                    label = new IconPanel(tnv.toString(), xml_icon);
                    add(label, BorderLayout.WEST);

                    if (selected) {
                        label.setBackground(selectedBackGround);
                        label.setForeground(Color.white);
                    }

                    break;
                }
            }
        }

        return this;
    }

    /**
     * DOCUMENT ME!
     */
    public void validate() {
        super.validate();

        if (map != null) {
            Component[] components = this.getComponents();

            for (int i = 0; i < components.length; i++) {
                Object o = ((JComponent) components[i]).getClientProperty(NODE);
                Rectangle rec = components[i].getBounds();
                map.put(o, rec);
            }
        }
    }

    private ArrayList getNotIndexFields(String[] indexes, String[] fields) {
        ArrayList _indexes = ArrayUtil.toVector(indexes);
        ArrayList _fields = ArrayUtil.toVector(fields);
        _fields.removeAll(_indexes);

        return _fields;
    }

    private ArrayList getNotIndexFields(String[] indexes, String[] index2, String[] fields) {
        ArrayList _indexes = ArrayUtil.toVector(indexes);
        ArrayList _indexes2 = ArrayUtil.toVector(index2);
        ArrayList _fields = ArrayUtil.toVector(fields);

        _fields.removeAll(_indexes);
        _fields.removeAll(_indexes2);

        return _fields;
    }

    class IconPanel extends JPanel {
        JLabel iconLabel;
        JLabel textLabel;

        IconPanel(String text, Icon icon) {
            super();
            this.setLayout(new BorderLayout(0, 0));
            iconLabel = new JLabel(icon, JLabel.LEFT);

            textLabel = new JLabel(text, JLabel.CENTER) {
                        public Dimension getPreferredSize() {
                            Dimension p = super.getPreferredSize();
                            p.width = Math.max(30, p.width);

                            return p;
                        }
                    };

            textLabel.setFont(new Font("宋体", Font.PLAIN, 12));
            textLabel.setOpaque(true);
            textLabel.setBackground(Color.white);
            this.add(iconLabel, BorderLayout.WEST);
            this.add(textLabel, BorderLayout.CENTER);
        }

        public void setBackground(Color c) {
            super.setBackground(Color.white);

            if (textLabel != null) {
                textLabel.setBackground(c);
            }
        }

        public void setForeground(Color c) {
            super.setForeground(c);

            if (textLabel != null) {
                textLabel.setForeground(c);
            }
        }
    }
}
