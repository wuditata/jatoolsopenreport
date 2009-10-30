package jatools.engine.script.debug;

import jatools.designer.App;
import jatools.designer.Main;
import jatools.engine.System2;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import bsh.EvalError;
import bsh.Interpreter;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ScriptDebugger extends JDialog {
    private JXTreeTable treeTable = null;
    private Interpreter it;

    /**
     * Creates a new ScriptDebugger object.
     *
     * @param it DOCUMENT ME!
     */
    public ScriptDebugger(Interpreter it) {
        super(Main.getInstance(), App.messages.getString("res.37"), true);

        this.it = it;

        ArrayList vars = new ArrayList();
        it.getNameSpace().getVariableNames();//(vars);

        Map root = new HashMap();
        Collections.sort(vars);

        Iterator i = vars.iterator();

        while (i.hasNext()) {
            String name = (String) i.next();

            final String filters = ",bsh,ALL,$styleClass,$printer,";

            if ((filters.indexOf(name) > -1) || name.startsWith("$$")) {
                continue;
            }

            try {
                root.put(name, it.get(name));
            } catch (EvalError e) {
                e.printStackTrace();
            }
        }

        treeTable = new JXTreeTable(new ScriptDebuggerModel(generateTreeTableModel(root)));
        treeTable.setTreeCellRenderer(new DefaultTreeCellRenderer() {
                public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree,
                    Object value, boolean sel, boolean expanded, boolean leaf, int row,
                    boolean hasFocus) {
                    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                        hasFocus);
                    setIcon(null);

                    return this;
                }
                ;
            });

        treeTable.addTreeExpansionListener(new TreeExpansionListener() {
                public void treeCollapsed(TreeExpansionEvent event) {
                }

                public void treeExpanded(TreeExpansionEvent event) {
                    DefaultMutableTreeTableNode n = (DefaultMutableTreeTableNode) event.getPath()
                                                                                       .getLastPathComponent();

                    if (n instanceof VariableNode) {
                        VariableNode mynode = (VariableNode) n;
                        DefaultMutableTreeTableNode node = null;

                        DefaultTreeTableModel mode = (DefaultTreeTableModel) treeTable.getTreeTableModel();

                        if (!mynode.explored) {
                            ScriptDebuggerBean sb = (ScriptDebuggerBean) mynode.getUserObject();
                            Class cls = sb.getValue().getClass();

                            List fieldNodes = new ArrayList();

                            getFieldNodes(sb.getValue(), cls, fieldNodes);

                            Collections.sort(fieldNodes, new NodeComparator());

                            for (int i = 0; i < fieldNodes.size(); i++) {
                                node = (DefaultMutableTreeTableNode) fieldNodes.get(i);
                                mode.insertNodeInto(node, n, n.getChildCount());
                                mynode.explored = true;
                            }
                        }
                    }
                }
            });

        this.getContentPane().add(new JScrollPane(treeTable));
        this.setSize(new Dimension(300, 200));

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * DOCUMENT ME!
     *
     * @param it DOCUMENT ME!
     */
    public static void debug(Interpreter it) {
        if (System2.isDesignTime()) {
            new ScriptDebugger(it);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param val DOCUMENT ME!
     * @param cls DOCUMENT ME!
     * @param list DOCUMENT ME!
     */
    public void getFieldNodes(Object val, Class cls, List list) {
        DefaultMutableTreeTableNode node = null;
        Object value = null;
        Field[] f = cls.getDeclaredFields();

        for (int i = 0; i < f.length; i++) {
            f[i].setAccessible(true);

            try {
                value = f[i].get(val);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            ScriptDebuggerBean bean = new ScriptDebuggerBean(f[i].getName(), value);

            if ((value == null) || value instanceof String || value instanceof Integer ||
                    value instanceof Boolean) {
                node = new DefaultMutableTreeTableNode(bean);
            } else {
                node = new VariableNode(bean);
            }

            list.add(node);
            f[i].setAccessible(false);
        }

        cls = cls.getSuperclass();

        if (cls != null) {
            getFieldNodes(val, cls, list);
        }
    }

    private DefaultMutableTreeTableNode generateTreeTableModel(Map c) {
        DefaultMutableTreeTableNode root = new DefaultMutableTreeTableNode(new ScriptDebuggerBean(
                    "root", "root"));
        DefaultMutableTreeTableNode node = null;
        Object[] names = c.keySet().toArray();
        Arrays.sort(names);

        for (int i = 0; i < names.length; i++) {
            String name = (String) names[i];
            Object val = null;

            try {
                val = it.eval(name);
            } catch (EvalError e) {
            }

            ScriptDebuggerBean bean = new ScriptDebuggerBean(name, val);

            if ((val == null) || val instanceof String || val instanceof Integer ||
                    val instanceof Boolean) {
                node = new DefaultMutableTreeTableNode(bean);
            } else {
                node = new VariableNode(bean);
            }

            root.add(node);
        }

        return root;
    }

    class NodeComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            DefaultMutableTreeTableNode n1 = (DefaultMutableTreeTableNode) o1;
            DefaultMutableTreeTableNode n2 = (DefaultMutableTreeTableNode) o2;
            ScriptDebuggerBean b1 = (ScriptDebuggerBean) n1.getUserObject();
            ScriptDebuggerBean b2 = (ScriptDebuggerBean) n2.getUserObject();

            return b1.getName().compareTo(b2.getName());
        }
    }

    class MyHighlighter extends AbstractHighlighter {
        protected Component doHighlight(Component component, ComponentAdapter adapter) {
            System.err.println("Calling doHighlight with component" +
                component.getClass().getName() + " adapter " + adapter.getClass().getName());

            return null;
        }
    }

    class VariableNode extends DefaultMutableTreeTableNode {
        boolean explored;

        public VariableNode(Object node) {
            super(node);
        }

        public boolean isLeaf() {
            return false;
        }
    }
}
