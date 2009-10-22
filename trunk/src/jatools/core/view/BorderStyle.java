package jatools.core.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class BorderStyle implements BorderBase {
    /**
     * DOCUMENT ME!
     */
    public static final Map colors = new HashMap();

    static {
        colors.put("black", Color.black);
        colors.put("red", Color.red);
        colors.put("yellow", Color.yellow);
        colors.put("green", Color.green);
        colors.put("blue", Color.blue);
        colors.put("orange", Color.orange);
        colors.put("gray", Color.gray);
    }

    private Color color = Color.black;
    private String style = BORDER_STYLE_SOLID;
    private int thickness = 1;

    /**
     * Creates a new BorderStyle object.
     *
     * @param thickness DOCUMENT ME!
     * @param style DOCUMENT ME!
     * @param color DOCUMENT ME!
     */
    public BorderStyle(int thickness, String style, Color color) {
        this.thickness = thickness;
        this.style = style;
        this.color = color;
    }

    /**
     * Creates a new BorderStyle object.
     */
    public BorderStyle() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param obj DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object obj) {
        if (obj instanceof BorderStyle) {
            BorderStyle an = (BorderStyle) obj;

            if (this.thickness != an.thickness) {
                return false;
            }

            if (this.style != an.style) {
                return false;
            }

            boolean eq = (this.color == null) ? (an.color == null) : this.color.equals(an.color);

            return eq;
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getStyle() {
        return style;
    }

    /**
     * DOCUMENT ME!
     *
     * @param style DOCUMENT ME!
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Color getColor() {
        return color;
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
     * @return DOCUMENT ME!
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * DOCUMENT ME!
     *
     * @param thickness DOCUMENT ME!
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return thickness + "px " + style + " " + StyleAttributes.toString(color);
    }

    /**
     * DOCUMENT ME!
     *
     * @param styleText DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static BorderStyle parse(String styleText) {
        IndexedStyleAttributes parser = new IndexedStyleAttributes(styleText);

        BorderStyle style = new BorderStyle();

        style.setThickness(parser.getPx(0, 1));
        style.setStyle(parser.getString(1, "solid"));
        style.setColor(parser.getColor(2, Color.black));

        return style;
    }
}
