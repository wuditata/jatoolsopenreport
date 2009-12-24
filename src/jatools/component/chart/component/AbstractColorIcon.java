package jatools.component.chart.component;

import javax.swing.Icon;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public abstract class AbstractColorIcon implements Icon {
    int width;
    int height;

    /**
     * Creates a new AbstractColorIcon object.
     *
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public AbstractColorIcon(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getIconHeight() {
        return this.height;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getIconWidth() {
        return this.width;
    }

    protected abstract void setStyle(FillStyleInterface style);
}
