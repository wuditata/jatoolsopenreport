package jatools.designer.variable;


import jatools.designer.App;
import jatools.designer.EditorView;
import jatools.designer.Main;
import jatools.designer.ReportEditor;
import jatools.util.Util;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class XmlPanel extends JPanel implements EditorView {
    private XmlSourceTree variableTree;
    private JTabbedPane tabbedPane;

    /**
     * Creates a new XmlPanel object.
     */
    public XmlPanel() {
        initUI();
        Main.getInstance().registerEditorView(this);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        variableTree = new XmlSourceTree();

        JScrollPane scrollPane = new JScrollPane(variableTree);

        tabbedPane.addTab(App.messages.getString("res.259"), Util.getIcon("/jatools/icons/srctree.gif"), scrollPane);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * DOCUMENT ME!
     *
     * @param editor DOCUMENT ME!
     */
    public void setEditor(ReportEditor editor) {
        if (tabbedPane.getSelectedIndex() == 0) {
            variableTree.setEditor(editor);
            variableTree.setDirty(false);
        } else {
            variableTree.setDirty(true);
        }
    }
}
