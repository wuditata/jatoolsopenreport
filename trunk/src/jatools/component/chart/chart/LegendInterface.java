/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Provides a standard set of methods for dealing with chart Legends in  a uniform way.  Using a LegendInterface rather than a specific  Legend instance provides more flexibility in creating and managing  subclasses and user interfaces.
 * @see jatools.component.chart.chart.Legend
 * @see jatools.component.chart.chart.LineLegend
 * @see  jatools.component.chart.chart.PieLegend
 */

public interface LegendInterface {

	public void draw(Graphics g) ;
	//Accessors
	public 	Gc	getBackgroundGc() ;
	/**
	 * @return
	 * @uml.property  name="backgroundVisible"
	 */
	public	boolean	getBackgroundVisible() ;
	/**
	 * @return
	 * @uml.property  name="iconGap"
	 */
	public	double	getIconGap() ;
	/**
	 * @return
	 * @uml.property  name="iconHeight"
	 */
	public	double	getIconHeight() ;
	/**
	 * @return
	 * @uml.property  name="iconWidth"
	 */
	public	double	getIconWidth() ;
	/**
	 * @return
	 * @uml.property  name="labelColor"
	 */
	public	Color	getLabelColor() ;
	/**
	 * @return
	 * @uml.property  name="labelFont"
	 */
	public	Font	getLabelFont() ;
	/**
	 * @return
	 * @uml.property  name="invertLegend"
	 */
	public  boolean getInvertLegend();
	/**
	 * @return
	 * @uml.property  name="llX"
	 */
	public	double	getLlX() ;
	/**
	 * @return
	 * @uml.property  name="llY"
	 */
	public	double	getLlY() ;
	public	double	getUrX() ;
	public	double	getUrY() ;
	/**
	 * @return
	 * @uml.property  name="useDisplayList"
	 */
	public boolean          getUseDisplayList();
	/**
	 * @return
	 * @uml.property  name="verticalLayout"
	 */
	public	boolean	getVerticalLayout() ;
	public void resize(int w, int h) ;
	public void	setBackgroundGC(Gc g) ;
	/**
	 * @param vis
	 * @uml.property  name="backgroundVisible"
	 */
	public void	setBackgroundVisible(boolean vis) ;
	/**
	 * @param d
	 * @uml.property  name="iconGap"
	 */
	public void setIconGap(double d) ;
	/**
	 * @param d
	 * @uml.property  name="iconHeight"
	 */
	public void setIconHeight(double d) ;
	/**
	 * @param d
	 * @uml.property  name="iconWidth"
	 */
	public void setIconWidth(double d) ;
	/**
	 * @param trueFalse
	 * @uml.property  name="invertLegend"
	 */
	public void setInvertLegend(boolean trueFalse);
	/**
	 * @param c
	 * @uml.property  name="labelColor"
	 */
	public 	void	setLabelColor(Color c) ;
	/**
	 * @param f
	 * @uml.property  name="labelFont"
	 */
	public void	setLabelFont(Font f) ;
	/**
	 * @param d
	 * @uml.property  name="llX"
	 */
	public void setLlX(double d) ;
	/**
	 * @param d
	 * @uml.property  name="llY"
	 */
	public void setLlY(double d) ;
	/**
	 * @param onOff
	 * @uml.property  name="useDisplayList"
	 */
	public void setUseDisplayList(boolean onOff);
	/**
	 * @param trueFalse
	 * @uml.property  name="verticalLayout"
	 */
	public void setVerticalLayout(boolean trueFalse) ;
	public String   toString() ;
}
