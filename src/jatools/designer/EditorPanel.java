package jatools.designer;

import jatools.ReportDocument;
import jatools.designer.property.PropertyPanel;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class EditorPanel extends JSplitPane {
    EditorTabPanel tabbedPanel;
    PropertyPanel propertTable;
    double lastProportion;

    /**
     * Creates a new EditorPanel object.
     */
    public EditorPanel() {
        super(HORIZONTAL_SPLIT, true);

        tabbedPanel = new EditorTabPanel();

        propertTable = new PropertyPanel();

        setDividerLocation(300);
        setOneTouchExpandable(true);

        JTabbedPane propTab = new JTabbedPane();
        
        DefaultReportsTreePanel dr = new DefaultReportsTreePanel();
        
        CurrentReportsTreePanel cr = new CurrentReportsTreePanel();
        
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, cr, dr);
        sp.setDividerLocation(200);
        sp.setOneTouchExpandable(true);

        propTab.addTab(App.messages.getString("res.90"), propertTable);
        propTab.addTab(App.messages.getString("res.91"), sp);

        setLeftComponent(propTab);
        setRightComponent(tabbedPanel);

        new PropertyEditorAndRendererSupport(propertTable, propertTable.getTable());

        this.setName("EditorPanel");
        propTab.setSelectedIndex(1);
    }

    /**
     * DOCUMENT ME!
     */
    public void toggleSplit() {
        double proportionalLocation = ((double) this.getDividerLocation()) / (getWidth() -
            getDividerSize());

        if (proportionalLocation == 1.0) {
            setDividerLocation(lastProportion);
        } else {
            setDividerLocation(1.0);
            this.lastProportion = proportionalLocation;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param aa DOCUMENT ME!
     */
    public void addEditorChangeListener(ChangeListener aa) {
        this.tabbedPanel.addChangeListener(aa);
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     * @param tip DOCUMENT ME!
     * @param doc DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ReportEditor createEditor(String name, String tip, ReportDocument doc) {
        return tabbedPanel.createEditor(name, tip, doc);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ReportEditor getActiveEditor() {
        return tabbedPanel.getActiveEditor();
    }

    /**
     * DOCUMENT ME!
     *
     * @param editor DOCUMENT ME!
     */
    public void setActiveEditor(ReportEditor editor) {
        tabbedPanel.setActiveEditor(editor);
    }

    /**
     * DOCUMENT ME!
     *
     * @param editor DOCUMENT ME!
     * @param name DOCUMENT ME!
     * @param tip DOCUMENT ME!
     */
    public void setPrompt(ReportEditor editor, String name, String tip) {
        this.tabbedPanel.setPrompt(editor, name, tip);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public PropertyPanel getPropertTable() {
        return propertTable;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Iterator iterator() {
        ArrayList editors = new ArrayList();

        for (int i = 0; i < this.tabbedPanel.getTabCount(); i++) {
            Component c = this.tabbedPanel.getComponentAt(i);
            editors.add(c);
        }

        return editors.iterator();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public EditorTabPanel getTabbedPanel() {
        return tabbedPanel;
    }

    /**
     * DOCUMENT ME!
     */
    public void resetTitle() {
        tabbedPanel.resetTitle();
    }
}
