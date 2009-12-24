package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ColorIcon extends AbstractColorIcon {
      Color color;

    /**
     * Creates a new ColorIcon object.
     *
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public ColorIcon(int width, int height,int x) {
        super(width, height);
    }

    /**
     * Creates a new ColorIcon object.
     *
     * @param d DOCUMENT ME!
     */
    public ColorIcon(Dimension d) {
        super(d.width, d.height);
    }

    /**
     * DOCUMENT ME!
     *
     * @param style DOCUMENT ME!
     */
    public void setStyle(FillStyleInterface style) {
        this.color = ((SingleColor) style).color;
    }

    /**
     * DOCUMENT ME!
     *
     * @param color DOCUMENT ME!
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * DOCUMENT ME!
     *
     * @param c DOCUMENT ME!
     * @param g1 DOCUMENT ME!
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     */
    public void paintIcon(Component c, Graphics g1, int x, int y) {
        Graphics g = g1.create();

       
        if (color != Gc.TRANSPARENT) {
            g.setColor(color);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        } 

        g.setColor(Color.black);
        g.drawRect(x, y, getIconWidth(), getIconHeight());
        g.dispose();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Color getColor() {
        return color;
    }
}
