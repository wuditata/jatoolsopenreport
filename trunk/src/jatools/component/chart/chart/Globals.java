/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.awt.Image;
import java.io.Serializable;
import java.util.Locale;

/**
 * Certain information needs to be available to almost all chart  classes.  This includes things like whether a chart should have 3-D  effects, how large is the drawing surface, etc.  The Globals class  is used to store all this information.  For standard chart  graphs, this class is instantiated once, and then references are  propagated to the classes that require them.  Since Java passes  this information by reference, a global change requires only a  change to the Globals class stored in the top-level Chart class. A couple of fairly obscure, but necessary components in this class  include stringRotator and image.  Since graphics strings are rotated  with raster operations, and because these operations require access to  the drawing canvas used by all the other drawing classes, this  information must be available globally.  
 * @see jatools.component.chart.chart.Gc
 * @see jatools.component.chart.chart.RotateString
 * @see  jatools.component.chart.chart.DisplayList
 */

public class Globals implements Serializable {
	int              	xOffset = 15;
	int              	yOffset = 15;
	boolean				threeD = false;
	int					maxY;
	int					maxX;
	RotateString		stringRotator;
	transient Image		image;
	boolean				useDisplayList = false;
	DisplayList			displayList;
	public boolean				java2Capable = false;
        public Locale locale = Locale.getDefault();

/**
 *	Does nothing.
 */
	public Globals(){
		try{
                        String version = System.getProperty("java.version");
						int index = version.indexOf(".");
                        double mainVersion = Double.valueOf
							(version.substring(index-1,index+2)).doubleValue();
                        if (mainVersion >= 1.2) {
                            // lets double check here so we don't get an exception thrown later
                            Class c = Class.forName("java.awt.Graphics2D");
                            java2Capable = true;
                        }
                        else 
                            java2Capable = false;
		}
		catch(Exception e){
			java2Capable = false;
		}

	}
/**
 *	Initialize several internal variables with the given arguments.
 *@param	topY		Height of the chart
 *@param	xDepth		X offset for doing 3-D chart
 *@param	yDepth		Y offset for doing 3-D chart
 *@param	showDepth	To show, or not to show 3-D chart
 *@param	r		Internal variable used by RotateString class
 *@param	i		Internal image used by RotateString class
 */
	public Globals(	int 		topY, 
			int 		xDepth,
			int 		yDepth,
			boolean		showDepth,
			RotateString	r,
			Image		i){
		this();
		maxY = topY;
		xOffset = xDepth;
		yOffset = yDepth;
		threeD = showDepth;
		stringRotator = r;
		image = i;

	}
/**
 * Returns the current DisplayList
 * @return  	Current display list
 * @uml.property  name="displayList"
 */
	public DisplayList getDisplayList(){
		return displayList;
	}
/**
 * Returns the internal image used by RotateString class.  This will return null unless an Image class has been installed with setImage.
 * @return  	Chart image used by RotateString class
 * @see  jatools.component.chart.chart.RotateString
 * @uml.property  name="image"
 */
	public Image getImage(){
		return image;
	}
/**
 * Returns the maximum X value (in pixels) for this chart
 * @return  	Maximum X value
 * @uml.property  name="maxX"
 */
	public int getMaxX(){
		return maxX;
	}
/**
 * Returns the maximum Y value (in pixels) for this chart
 * @return  	Maximum Y value
 * @uml.property  name="maxY"
 */
	public int getMaxY(){
		return maxY;
	}
/**
 * Return this chart's RotateString class
 * @return  	A RotateString class used by other chart classes
 * @uml.property  name="stringRotator"
 */
	public RotateString getStringRotator(){
		return stringRotator;
	}
/**
 * Activates a global DisplayList for this entire chart
 * @return  	We are, or are not using display list
 * @see jatools.component.chart.chart._Chart
 * @see  jatools.component.chart.chart.DisplayList
 * @uml.property  name="useDisplayList"
 */
	public boolean getUseDisplayList(){
		return useDisplayList;
	}
/**
 * Returns the X offset used by 3-D charts.
 * @return  	X offset
 * @uml.property  name="xOffset"
 */
	public int getXOffset(){
		return xOffset;
	}
/**
 * Returns the Y offset used by 3-D charts.
 * @return  	Y offset
 * @uml.property  name="yOffset"
 */
	public int getYOffset(){
		return yOffset;
	}
/**
 * Inquires whether this chart should use 3-D effects
 * @return  	True if charts will be drawn with 3-D effect
 * @uml.property  name="threeD"
 */
	public boolean isThreeD(){
		return threeD;
	}
/**
 * Installs a new display list.
 * @param d  	A new display list
 * @uml.property  name="displayList"
 */
	public void setDisplayList(DisplayList d){
		displayList = d;
		d.globals = this;
	}
/**
 * Set the internal image used by RotateString class.
 * @param i  	New image used by RotateString class
 * @see  jatools.component.chart.chart.RotateString
 * @uml.property  name="image"
 */
	public void setImage(Image i){
		image = i;
	}
/**
 * Set the maximum X value (in pixels).  Usually this is set  automatically by calls to resize methods.
 * @param i  	New maximum Y value
 * @uml.property  name="maxX"
 */
	public void setMaxX(int i){
		maxX = i;
	}
/**
 * Set the maximum Y value (in pixels).  Usually this is set  automatically by calls to resize methods.
 * @param i  	New maximum Y value
 * @uml.property  name="maxY"
 */
	public void setMaxY(int i){
		maxY = i;
	}
/**
 * Installs a new RotateString class for use by the entire chart
 * @param r  	New RotateString class
 * @uml.property  name="stringRotator"
 */
	public void setStringRotator(RotateString r){
		stringRotator = r;
	}
/**
 * Sets the 3-D effects value for this chart
 * @param depth  	True if chart should show a 3-D effect
 * @uml.property  name="threeD"
 */
	public void setThreeD(boolean depth){
		threeD = depth;
	}
/**
 * Enable or disable this chart's DisplayList
 * @param onOff  	True to enable DisplayList
 * @uml.property  name="useDisplayList"
 */
	public void setUseDisplayList(boolean onOff){
		if(onOff&&(displayList==null))
			displayList = new DisplayList(this);
		useDisplayList = onOff;
	}
/**
 * Set the X offset used by 3-D charts.
 * @param x  	New X offset
 * @uml.property  name="xOffset"
 */
	public void setXOffset(int x){
		xOffset = x;
	}
/**
 * Set the Y offset used by 3-D charts.
 * @param y  	New Y offset
 * @uml.property  name="yOffset"
 */
	public void setYOffset(int y){
		yOffset = y;
	}



/**
 * Returns the locale for this chart
 * @return  java.util.Locale
 * @uml.property  name="locale"
 */
public java.util.Locale getLocale() {
	return locale;
}

/**
 * Sets the desired locale for this chart, generally only used by the Chart constructor. If you wish to change locale from the default, pass in a locale to the Chart constructor.
 * @param newLocale  java.util.Locale
 * @uml.property  name="locale"
 */
public void setLocale(java.util.Locale newLocale) {
	locale = newLocale;
}
}
