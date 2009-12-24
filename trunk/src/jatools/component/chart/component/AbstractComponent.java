package jatools.component.chart.component;

import jatools.component.chart.customizer.ChangeListener;

import javax.swing.JPanel;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public abstract class AbstractComponent extends JPanel {
    protected ChangeListener parent;

    /**
     * DOCUMENT ME!
     *
     * @param l DOCUMENT ME!
     */
    public void addChangeListener(ChangeListener l) {
        parent = l;
    }

    /**
     * DOCUMENT ME!
     */
    public void removeChangeListener() {
        parent = null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param object DOCUMENT ME!
     */
    public abstract void setValue(Object object);

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public abstract Object getValue();

    /**
     * DOCUMENT ME!
     *
     * @param enabled DOCUMENT ME!
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}
