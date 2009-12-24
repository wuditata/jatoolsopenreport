/*
 * Created on 2005-3-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.applet;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;




/**
 * @author java
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ChartUtil {
    /**
     * DOCUMENT ME!
     *
     * @param s DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Color getColor(String s) {
       
        if (s.equalsIgnoreCase("black")) {
            return Color.black;
        }

        if (s.equalsIgnoreCase("white")) {
            return Color.white;
        }

        if (s.equalsIgnoreCase("lightGray")) {
            return Color.lightGray;
        }

        if (s.equalsIgnoreCase("gray")) {
            return Color.gray;
        }

        if (s.equalsIgnoreCase("darkGray")) {
            return Color.darkGray;
        }

        if (s.equalsIgnoreCase("red")) {
            return Color.red;
        }

        if (s.equalsIgnoreCase("pink")) {
            return Color.pink;
        }

        if (s.equalsIgnoreCase("orange")) {
            return Color.orange;
        }

        if (s.equalsIgnoreCase("yellow")) {
            return Color.yellow;
        }

        if (s.equalsIgnoreCase("green")) {
            return Color.green;
        }

        if (s.equalsIgnoreCase("magenta")) {
            return Color.magenta;
        }

        if (s.equalsIgnoreCase("cyan")) {
            return Color.cyan;
        }

        if (s.equalsIgnoreCase("blue")) {
            return Color.blue;
        }

        if (s.equalsIgnoreCase("transparent")) {
            return Gc.TRANSPARENT;
        }

        try {
            if (s.startsWith("0x")) {
                s = s.substring(2);
            }
            
            
            
            

            return new Color(Integer.parseInt(s, 16));
        } catch (NumberFormatException e) {
        	e.printStackTrace() ;
        }

        return Color.black;
    }

    /**
     * @param prop
     * @return
     */
    public static Font getFont(String prop) {
        if (prop == null) {
            return Gc.defaultFont;
        } else {
            String[] propArr = prop.split(",");

            return new Font(propArr[0], Integer.parseInt(propArr[2]), Integer.parseInt(propArr[1]));
        }
    }

    /**
     * @param font
     * @return
     */
    public static String toString(Font font) {
        if (font == null) {
            return null;
        } else {
            return font.getName() + "," + font.getSize() + "," + font.getStyle();
        }
    }

    /**
     * @param color
     * @return
     */
    public static String toString(Color color) {
    	if(color == null){
    		return "transparent";
    	}
        return "0x" + Integer.toHexString(color.getRGB()& 0xffffff);
    }
    
    /**
     * @param CategoryLabelPositions
     * @return
     */
    public static CategoryLabelPositions createRightRotationLabelPositions(
            double angle) {
        return new CategoryLabelPositions(
        		new CategoryLabelPosition(
                        RectangleAnchor.BOTTOM, TextBlockAnchor.CENTER_RIGHT,
                        TextAnchor.CENTER_RIGHT, Math.PI / 2.0,
                        CategoryLabelWidthType.RANGE, 0.30f
                    ), // TOP
                    new CategoryLabelPosition(
                        RectangleAnchor.TOP, TextBlockAnchor.CENTER_LEFT,
                        TextAnchor.CENTER_LEFT, -angle,
                        CategoryLabelWidthType.RANGE, 0.30f
                    ), // BOTTOM
                    new CategoryLabelPosition(
                        RectangleAnchor.RIGHT, TextBlockAnchor.TOP_CENTER,
                        TextAnchor.TOP_CENTER, Math.PI / 2.0,
                        CategoryLabelWidthType.CATEGORY, 0.90f
                    ), // LEFT
                    new CategoryLabelPosition(
                        RectangleAnchor.LEFT, TextBlockAnchor.BOTTOM_CENTER,
                        TextAnchor.BOTTOM_CENTER, Math.PI / 2.0,
                        CategoryLabelWidthType.CATEGORY, 0.90f
                    ) // RIGHT
        );
    }
    
    public static void main(String[] args) {
    	
		System.out.println(toString(getColor("0x90ffab")));
		System.out.println(Integer.parseInt("FFF9999", 16));
	}
}
