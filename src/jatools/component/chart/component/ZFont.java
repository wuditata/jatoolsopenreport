/*
 * Author: John.
 *
 * º¼ÖÝ½Ü´´Èí¼þ All Copyrights Reserved.
 */
package jatools.component.chart.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import jatools.accessor.AutoAccessor;


/**
 *
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $
 * @author $author$
 *
 */
public class ZFont extends AutoAccessor {
    Color foreColor;
    Color backColor;
    
    
    int style;
    int size;
    String face;

    /**
     * Creates a new ZFont object.
     *
     * @param face
     *            DOCUMENT ME!
     * @param style
     *            DOCUMENT ME!
     * @param size
     *            DOCUMENT ME!
     * @param foreColor
     *            DOCUMENT ME!
     * @param backColor
     *            DOCUMENT ME!
     */
    public ZFont(String face, int style, int size, Color foreColor,
        Color backColor) {
        this.face = face;
        this.size = size;
        this.backColor = backColor;
        this.foreColor = foreColor;
        this.style = style;
    }

    public ZFont() {
    }

    /**
     * @param g
     *            DOCUMENT ME!
     * @param text
     *            DOCUMENT ME!
     */
    public void paint(Graphics g, String text) {
        paint(g, text, 0, 0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param g
     *            DOCUMENT ME!
     * @param text
     *            DOCUMENT ME!
     * @param x
     *            DOCUMENT ME!
     * @param y
     *            DOCUMENT ME!
     */
    public void paint(Graphics g, String text, int x, int y) {
        if ((text != null) && !text.equals("")) { //
            g.setFont(asFont());

            FontMetrics fm = g.getFontMetrics();
            int textHeight = fm.getHeight();

            if (backColor != null) {
                int textWidth = fm.stringWidth(text);
                g.setColor(backColor);
                g.fillRect(x, y, textWidth, textHeight);
            }

            g.setColor(foreColor);
            g.drawString(text, x, y + textHeight);
        }
    }

    /**
     * @return DOCUMENT ME!
     */
    public Font asFont() {
        return new Font(face, style, size);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Color getBackColor() {
        return backColor;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getFace() {
        return face;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Color getForeColor() {
        return foreColor;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getSize() {
        return size;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getStyle() {
        return style;
    }

    /**
     * DOCUMENT ME!
     *
     * @param style
     *            DOCUMENT ME!
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * DOCUMENT ME!
     *
     * @param foreColor
     *            DOCUMENT ME!
     */
    public void setForeColor(Color foreColor) {
        this.foreColor = foreColor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param face
     *            DOCUMENT ME!
     */
    public void setFace(String face) {
        this.face = face;
    }

    /**
     * DOCUMENT ME!
     *
     * @param backColor
     *            DOCUMENT ME!
     */
    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param size
     *            DOCUMENT ME!
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ZFont:"); //
        buffer.append(" foreColor: "); //
        buffer.append(foreColor);
        buffer.append(" backColor: "); //
        buffer.append(backColor);
        buffer.append(" style: "); //
        buffer.append(style);
        buffer.append(" size: "); //
        buffer.append(size);
        buffer.append(" face: "); //
        buffer.append(face);
        buffer.append("]"); //

        return buffer.toString();
    }

    public Object clone() {
        ZFont inst = new ZFont();
        inst.foreColor = this.foreColor;
        inst.backColor = this.backColor;
        inst.style = this.style;
        inst.size = this.size;
        inst.face = this.face;

        return inst;
    }
}
