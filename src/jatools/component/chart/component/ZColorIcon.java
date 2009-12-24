/*
 * Created on 2004-1-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package jatools.component.chart.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;


public class ZColorIcon extends ZEmptyIcon {
	Color color = Color.red;

	/**
	 * Creates a new ColorIcon object.
	 *
	 * @param width DOCUMENT ME!
	 * @param height DOCUMENT ME!
	 */
	public ZColorIcon(int width, int height) {
		super(width, height);
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
	 * @param g DOCUMENT ME!
	 * @param x DOCUMENT ME!
	 * @param y DOCUMENT ME!
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(x, y, getIconWidth(), getIconHeight());
		g.setColor(Color.black);
		g.drawRect(x, y, getIconWidth(), getIconHeight());
	}
	/**
	 * @return Returns the color.
	 */
	public Color getColor() {
		return color;
	}

}