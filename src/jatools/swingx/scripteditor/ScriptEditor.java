package jatools.swingx.scripteditor;

import java.awt.Component;

import javax.swing.JTextPane;
import javax.swing.plaf.ComponentUI;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ScriptEditor extends JTextPane {
    private boolean asTemplate;

    /**
    * Creates a new Highlighter object.
    *
    * @param args DOCUMENT ME!
    */
    public ScriptEditor() {
        this(false);
    }

    /**
     * Creates a new ScriptEdtor object.
     *
     * @param template DOCUMENT ME!
     */
    public ScriptEditor(boolean asTemplate) {
        setEditorKit(new HighlightKit(asTemplate));
        this.asTemplate = asTemplate;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean getScrollableTracksViewportWidth() {
        Component parent = getParent();
        ComponentUI ui = getUI();

        return (parent != null) ? (ui.getPreferredSize(this).width <= parent.getSize().width) : true;
    }

    
   

}
