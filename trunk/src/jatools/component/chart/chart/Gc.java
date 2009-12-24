/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * This is a general purpose graphics class for drawing functionality  and for storing graphical attributes.  Most other chart classes  contain at least one Gc instance, which is used for storing things  like line color and fill color, as well as drawing methods.   Internally, chart uses a "lower left" origin, which is more  natural to most chart styles than Java's "top left" origin.  The  Gc class converts all incoming coordinates into chart's "lower  left" origin.
 * @see jatools.component.chart.chart.RotateString
 * @see  jatools.component.chart.chart.Globals
 */

public class Gc implements Serializable {

	protected Color					fillColor;
	transient protected Image		image;
	transient protected byte[]   imageBytes;
	protected Globals				globals;
	protected Color					lineColor;
	protected int					lineStyle = -1;
	protected int					lineWidth = 1;
	protected boolean				outlineFills = false;

	protected static Color[] colors = {
		Color.blue,
		Color.red,
		Color.green,
		Color.cyan,
		Color.orange,
		Color.pink,
		Color.yellow,
		Color.magenta,
		Color.lightGray,
		Color.darkGray,
		Color.blue.darker(),
		Color.red.darker(),
		Color.green.darker(),
		Color.cyan.darker(),
		Color.orange.darker(),
		Color.pink.darker(),
		Color.yellow.darker(),
		Color.magenta.darker(),
		Color.lightGray.darker(),
		Color.black
	};


/**
 *	A utility color - Gc.TRANSPARENT items will not be drawn
 */
	public static Color	TRANSPARENT = null;

	//labelling formats
/**
 *	default number format
 */
	public final	static	int	DEFAULT_FORMAT = 0;
/**
 *	American style numbers with commas and a decimal period (e.g. 1,000.00)
 */
	public final	static	int	COMMA_FORMAT = 1;
/**
 *	European style numbers with periods and a comma at the decimal point
 *	(e.g. 1.000,00)
 */
	public final	static	int	EURO_FORMAT = 2;

	//used for unique bar/pie colors
	static int		individualCount; 

	//corresponds with axis side for convenience
/**
 *	string below given point
 */
	public static final int keepBELOW = 0;
/**
 *	string to the left of given point
 */
	public static final int keepLEFT = 1;
/**
 *	string above given point
 */
	public static final int keepABOVE = 2;
/**
 *	string to the right of given point
 */
	public static final int keepRIGHT = 3;

	public static Font defaultFont = null;

	private static boolean isInitialized = false;

	public final static int MK_NONE = -1;
	public final static int MK_SQUARE = 0;
	public final static int MK_DIAMOND = 1;
	public final static int MK_CIRCLE = 2;
	public final static int MK_TRIANGLE = 3;

	protected int markerSize = 6;
	protected int markerStyle = MK_NONE;

	public final static int FILL_SOLID = -1;
	public final static int FILL_GRADIENT = 0;
	public final static int FILL_TEXTURE = 1;

	protected int fillStyle = FILL_SOLID;

	public final static int LINE_SOLID = -1;
	public final static int LINE_DASH = 0;
	public final static int LINE_DOT = 1;
	public final static int LINE_DOT_DASH = 2;

	public final static String LINE_BREAK = "|";

	static {
		try{
			init();
		}
		catch(Throwable t){
			System.out.println("can't initialize javachart.chart.Gc");
		}
	}

	transient GcHelper gcHelper = null;
/**
 *      Sets the fill, marker, and line color based on the dataset
 *	index number.
 *@param        dataSetNumber   Dataset index number
 *@param        g   		This chart's Globals class
 */

	public Gc(int dataSetNumber, Globals	g) {

		globals = g;
		if(dataSetNumber<colors.length){
			assignColors(colors[dataSetNumber]);
		}
		else
			assignColors(randomColor());
	}
/**
 *      Sets the fill, marker, and line color to a user specified color.
 *@param        clr     User's color
 *@param	g	This chart's Globals class
 */
	public Gc(Color        clr,
		Globals	g) {
		globals = g;
		assignColors(clr);
	}
/**
 *      Sets the fill, marker, and line color to black.
 *@param        g	This chart's Globals class
 */
	public Gc(Globals g) {
		globals		= g;
		fillColor	= Color.black;
		lineColor	= Color.black;
	}
/**
 *      Sets the fill, marker, and line to a unique color.
 *@param        unique  True if color should be unique
 *@param        g  	This chart's Globals class
 */
	public Gc(boolean      unique,
		Globals	g, boolean isPie) {
		globals = g;
		if(!unique) return;
		int colorLoop = 10;
		if(isPie)
				colorLoop = colors.length;
 
		if(individualCount%colorLoop>=colors.length)
			assignColors(randomColor());
		else
			assignColors(colors[individualCount%colorLoop]);
                
		individualCount++;
	}
        
	//utility functions
	private void assignColors(Color clr) {
		fillColor       = clr;
		lineColor       = clr;
	}
/**
 *      Draws an arc using the current fill color and chart's coordinates.
 *@param        g       	The affected Graphics class
 *@param        center  	Center point
 *@param        heightWidth     Size of arc
 *@param        startAngle      Starting angle
 *@param        endAngle        Ending angle
 */
	public void drawArc(  Graphics g,
		Point   center,
		Point   heightWidth,
		int	startAngle,
		int	endAngle) {
                
				int	urx, ury;

		if(lineColor == TRANSPARENT)
			return;

		int	nPoints;
		double xVal, yVal;
		float x[], y[];
		double	increment = Math.PI / 180; //360 points in circ
		if(endAngle<0){
			increment = -increment;
		}

		double startRadians = (double)(startAngle)/180.0*Math.PI;
		double totalRadians = (double)(endAngle)/180.0*Math.PI;
		nPoints = (int) (Math.abs(totalRadians / increment));
		if(nPoints<2) // hmmm... very skinny slice
			return;
		//we want at least a triangle: two radians and center
		x = new float[nPoints];
		y = new float[x.length];
		//x[nPoints] = center.x;
		//y[nPoints] = center.y;
		double currentRadians = startRadians;
		//put any points around the edge
		for(int i=0;i<nPoints;i++){
			if(i==nPoints-1){
				currentRadians = startRadians + totalRadians;
			}
			xVal = center.x + Math.cos(currentRadians) * (heightWidth.x/2);
			yVal = center.y + Math.sin(currentRadians) * (heightWidth.y/2);
			x[i] = (float)xVal;
			y[i] = (float)yVal;
			currentRadians += increment;
		}
				Point[] arcArray = new Point[x.length];
				for(int i=0;i<x.length;i++){
					arcArray[i] = new Point((int)x[i],(int)y[i]);
		}
		drawPolyline(g, arcArray);
           
           
				/** 
				 * 3.2 version:

		int	urx, ury;

		if(fillColor == TRANSPARENT)
			return;
		urx = center.x - (heightWidth.x/2);
		ury = globals.maxY - center.y - (heightWidth.y/2);
		g.setColor(fillColor);
		g.drawArc(urx, ury, heightWidth.x, heightWidth.y, startAngle, endAngle);
				 **/
	}
/**
 *      Draw an image centered at a given point using Javachart's coordinate
 *      system.
 *@param        g       The affected Graphics
 *@param        pt      Center of image
 */
	public void drawImage(Graphics 	g,
					Point		pt ) {
		if(image != null) {
			g.drawImage(image, (pt.x - image.getWidth(null)/2), 
				globals.maxY - pt.y - image.getHeight(null)/2, null);
		}
	}
/**
 *      A drawLine that uses chart's coordinate system.
 *      Uses the current lineColor.
 *@param        g       The affected Graphics class
 *@param        startx  X coordinate of starting point
 *@param        starty  Y coordinate of starting point
 *@param        endx    X coordinate of end point
 *@param        endy    Y coordinate of end point
 */
	public void drawLine(  Graphics g,
		int       startx,
		int       starty,
		int       endx,
		int       endy) {
		if(lineColor == TRANSPARENT)
			return;
		g.setColor(lineColor);
		
		if(globals.java2Capable){
			if(gcHelper==null){
				gcHelper = new GcHelper(this);
			}
			gcHelper.drawLine(g, new Point(startx, starty), new Point(endx, endy));
			return;
		}

		if(lineWidth > 1){
			Point[] pts = new Point[2];
			pts[0] = new Point(startx, starty);
			pts[1] = new Point(endx, endy);
			drawThickLine(g, pts, lineWidth, globals);
			return;
		}
		g.drawLine(startx, globals.maxY - starty, endx, globals.maxY - endy);
	}
/**
 *	Draws a line in the current line color using chart's coordinates
 *@param	g	Graphics class to draw
 *@param	start	Start point
 *@param	end	End point
 */
	public void drawLine(  Graphics g,
		Point       start,
		Point       end) {

		if(lineColor == TRANSPARENT)
			return;
		g.setColor(lineColor);
		
		if(globals.java2Capable){
			if(gcHelper==null){
				gcHelper = new GcHelper(this);
			}
			gcHelper.drawLine(g, start, end);
			return;
		}
		
		if(lineWidth<2)
			g.drawLine(start.x, globals.maxY - start.y, end.x, globals.maxY - end.y);
		else {
			Point[] p = new Point[2];
			p[0] = start;
			p[1] = end;
			drawThickLine(g, p, lineWidth, globals);
		}
	}
/**
 * Draws a marker in the specified size and type at the specified location
 * @param x int
 * @param y int
 * @param type int
 * @param size int
 */
protected static void drawMarker(Graphics g, int x, int y, int type, int size) {
	int halfSize = size/2;
	switch(type){
		case MK_SQUARE:
		{
			g.fillRect(x-halfSize, y-halfSize, size, size);
			break;
		}
		case MK_DIAMOND:
		{
			int xArr[] = new int[4];
			int yArr[] = new int[4];
			xArr[0] = x - halfSize;
			yArr[0] = y;
			xArr[1] = x;
			yArr[1] = y + halfSize;
			xArr[2] = x + halfSize;
			yArr[2] = y;
			xArr[3] = x;
			yArr[3] = y - halfSize;
			g.fillPolygon(xArr, yArr, 4);
			break;
		}
		case MK_CIRCLE:
		{
			g.fillOval(x-halfSize, y-halfSize, size, size);
			break;
		}
		case MK_TRIANGLE:
		{
			int xArr[] = new int[3];
			int yArr[] = new int[3];
			xArr[0] = x - halfSize;
			yArr[0] = y + halfSize;
			xArr[1] = x;
			yArr[1] = y - halfSize;
			xArr[2] = x + halfSize;
			yArr[2] = y + halfSize;
			g.fillPolygon(xArr, yArr, 3);
		}
	}
}
/**
 *      Fills a polygon using the current fillColor, using an array
 *      of points in chart's coordinate system.
 *@param        g       The affected graphics
 *@param        pts     Array of polygon's points
 */
	public void drawPolygon(Graphics 	g,
					Point		pts[] ) {
		int	i;
		int xarr[];
		int yarr[];

		if (fillColor != TRANSPARENT) {
			// return;
			if (globals.java2Capable
					&& ((fillStyle == FILL_GRADIENT) || (fillStyle == FILL_TEXTURE))) {
				if (gcHelper == null) {
					gcHelper = new GcHelper(this);
				}
				gcHelper.drawPolygon(g, pts);
				//return;
			}else{
			g.setColor(fillColor);
			xarr = new int[pts.length];
			yarr = new int[pts.length];
			for (i = 0; i < pts.length; i++) {
				xarr[i] = pts[i].x;
				yarr[i] = globals.maxY - pts[i].y;
			}
			g.fillPolygon(xarr, yarr, pts.length);
			}
		}
		if (outlineFills) {
			if (lineColor != TRANSPARENT) {
				drawPolyline(g, pts);
				drawLine(g, pts[0], pts[pts.length - 1]);
			}
		}
	}
/**
 *      Draws a polyline using the current lineColor.
 *@param        g       The affected graphics
 *@param        pts     Array of polyline's points
 */
	public void drawPolyline(	Graphics 	g,
			Point		pts[] ) {
		int	i;

		if(lineColor == TRANSPARENT)
			return;
		g.setColor(lineColor);
		if(pts.length == 1) {
			return;
		}

		if(globals.java2Capable){
			if(gcHelper==null){
				gcHelper = new GcHelper(this);
			}
			gcHelper.drawPolyline(g, pts);
			return;
		}
		
		if(lineWidth > 1){
			drawThickLine(g, pts, lineWidth, globals);
			return;
		}

		for(i=1;i<pts.length;i++) {
			g.drawLine(pts[i-1].x, globals.maxY - pts[i-1].y, 
				pts[i].x, globals.maxY - pts[i].y);
		}
	}
/**
 *      Draws markers in the current markerSize and markerType and fillColor
 *@param        g       The affected graphics
 *@param        pts     Array of marker points
 */
	public void drawPolymarker(	Graphics 	g,
			Point		pts[] ) {
		int	i;

		if(markerStyle == MK_NONE)
			return;
			
		if(fillColor == TRANSPARENT)
			return;

		g.setColor(fillColor);

		for(i=0;i<pts.length;i++) {
			drawMarker(g, pts[i].x, globals.maxY - pts[i].y, markerStyle, markerSize);
		}
	}
/**
 *	Draws a string in the current color and Font at the specified location,
 *	position, and angle.  This method attempts to handle strings in a 
 *	"smart" fashion, adjusting alignment points based on whether the 
 *	string should remain above, below, to the left, or to the right of 
 *	the specified location.  Strings with a rotation angle are aligned
 *	with one of the corners of the string's extent box.  Strings with
 *	an angle of 0 are centered at the location.
 *
 *@param	g		Graphics affected
 *@param	startx		X position of string
 *@param	starty		Y position of string
 *@param	alignment	keepABOVE, keepBELOW, etc.
 *@param	angle		Rotation angle in degrees
 *@param	fm		A FontMetrics class for the unrotated string
 *@param	str		The String to draw
 */
	public void drawSmartString(Graphics g,
		int	startx,
		int	starty,
		int	alignment,
		int	angle,
		FontMetrics	fm,
			String  str) 
	{
		if(globals.java2Capable){
			try{
				GcHelper.drawSmartString(g, startx, starty, alignment, angle, fm, str, globals);
				return;
			}
			catch(Exception useAWT){
			}
		}
		
		Rectangle	extent;
		int		adjX = 0, adjY = 0, adjAngle = 0;
		
		StringTokenizer strtoke = new StringTokenizer(str, LINE_BREAK);
		int lines = strtoke.countTokens();
		String currentLine;
		int lineHeight = fm.getMaxAscent();

		if(g.getColor() == TRANSPARENT)
			return;
		if((str == null)||(str.length()==0))
			return;
		//should work w/o rotation if stringRotator isn't initialized
		if(globals.stringRotator != null){
			globals.stringRotator.setFont(g.getFont());
			globals.stringRotator.setColor(g.getColor());
		}
		else
			angle = 0;
		switch (alignment) {
			case keepBELOW :
			{
				if (angle == 0){				
					while(strtoke.hasMoreElements()){
						currentLine = strtoke.nextToken();
						drawString(g, startx-(int)
								   (fm.stringWidth(currentLine)/2), 
								   starty-(lineHeight*1) + adjY, 
								   currentLine);
						adjY -= (lineHeight + (int)(lineHeight/5) + 1);
					}
					return;
				}
				if((angle < -90)||(angle > 90))
					adjAngle = 90;
				else
					adjAngle = angle;
				extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, fm);
				if (adjAngle == 90){
					adjX = startx + fm.getMaxAscent()/2;
					adjY = starty - extent.height;
				}
				else if(adjAngle > 0) {
					adjX = startx - extent.width + fm.getMaxAscent();
					adjY = starty - extent.height;
				}
				else{
					adjX = startx - fm.getMaxAscent()/2;
					adjY = starty - fm.getMaxAscent()/2;
				}
				break;
			}
			case keepABOVE :
			{
				if (angle == 0){
					//shift up first
					adjY = (lineHeight + ((int)(lineHeight/5)+1))*(lines-1);
					while(strtoke.hasMoreElements()){
						currentLine = strtoke.nextToken();
						drawString(g, startx-(int)
								   (fm.stringWidth(currentLine)/2), 
								   starty + adjY, currentLine);
						adjY -= (lineHeight + (int)(lineHeight/5) + 1);
					}
					return;
				}
				if((angle < -90)||(angle > 90))
					adjAngle = 90;
				else
					adjAngle = angle;
				extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, fm);
				if (adjAngle == 90){
					adjX = startx + fm.getMaxAscent()/2;
					adjY = starty;
				}
				if (adjAngle == -90){
					adjX = startx - fm.getMaxAscent()/2;
					adjY = starty + extent.height;
				}
				else if (angle>0) {
					adjX = startx;
					adjY = starty;
				}
				else {
					adjX = startx - extent.width + fm.getMaxAscent()/2;
					adjY = starty + extent.height - fm.getMaxAscent();
				}
				break;
			}
			case keepRIGHT :
			{
				if (angle == 0){
					//shift up by half
					adjY = (int)((lineHeight + 
									((int)(lineHeight/5)+1))*(lines-1) - 
								   lineHeight)/2;
					while(strtoke.hasMoreElements()){
						currentLine = strtoke.nextToken();
						drawString(g, startx, starty + adjY, currentLine);
						adjY -= (lineHeight + (int)(lineHeight/5) + 1);
					}
					return;
				}
				if((angle < -90)||(angle > 90))
					adjAngle = 90;
				else
					adjAngle = angle;
				extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, fm);
				if(angle>0)
					adjX = startx + fm.getMaxAscent();
				else
					adjX = startx;
				if(angle==90)
					adjY = starty - fm.stringWidth(str)/2;
				else if(angle== -90)
					adjY = starty + fm.stringWidth(str)/2;
				else
					adjY = starty;
				break;
			}
			case keepLEFT :
			{
				if (angle == 0){
					//shift up by half
					adjY = (int)((lineHeight + 
								  ((int)(lineHeight/5)+1))*(lines-1) - 
								 lineHeight)/2;
					while(strtoke.hasMoreElements()){
						currentLine = strtoke.nextToken();
						drawString(g, startx - fm.stringWidth(currentLine) , 
								   starty + adjY, currentLine);
						adjY -= (lineHeight + (int)(lineHeight/5) + 1);
					}
					return;
				}
				if((angle < -90)||(angle > 90))
					adjAngle = 90;
				else
					adjAngle = angle;
				extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, fm);
				adjX = startx - extent.width + fm.getMaxAscent();
				if(angle==90){
					adjY = starty - fm.stringWidth(str)/2;
					adjX = startx;
				}
				else if (angle == -90){
					adjY = starty + fm.stringWidth(str)/2;
					adjX -= fm.getMaxAscent();
				}
				else if(angle > 0)
					adjY = starty - extent.height + fm.getMaxAscent()/2;
				else {
					adjY = starty + extent.height - fm.getMaxAscent();
					adjX -= fm.getMaxAscent()/2;
				}
				break;
			}
			default : return;
		}

		globals.stringRotator.drawString(str, adjX, globals.maxY - adjY, adjAngle, globals.image);
	}
/**
 *      A drawString that uses chart's coordinate system.
 *      Also handles null-pointer exceptions.
 *@param        g       The affected Graphics
 *@param        startx  X coordinate of starting point
 *@param        starty  Y coordinate of starting point
 *@param        str     The string to be drawn
 */
	public void drawString(Graphics g,
		int       startx,
		int       starty,
		String  str) 
	{
		if(g.getColor() == TRANSPARENT)
			return;
		try{
			g.drawString(str, startx, globals.maxY - starty);
		}
		catch (NullPointerException e) {
						//nothing
		}
	}
	private static void drawThickLine(Graphics g, Point[] pts, int lw, Globals gl){

		if(pts.length==0)
			return;
		double[] xVals = new double[pts.length];
		double[] yVals = new double[pts.length];
		double width = (double)lw;
		for(int i=0;i<pts.length;i++){
			xVals[i] = (double)pts[i].x;
			yVals[i] = (double)(gl.maxY - pts[i].y);
		}
		double[] xFactor = new double[xVals.length-1];
		double[] yFactor = new double[xVals.length-1];
		for(int i=1;i<xVals.length;i++){
			double atan = Math.atan2(xVals[i] - xVals[i-1], yVals[i] - yVals[i-1]);
			xFactor[i-1] = Math.cos(atan);
			yFactor[i-1] = Math.sin(atan);
		}

		int[] xRect = new int[4];
		int[] yRect = new int[4];
		int endTopX, endTopY, endBottomX, endBottomY;

		for(int i=1;i<xVals.length;i++){
			xRect[0] = (int)(xVals[i-1]+width*xFactor[i-1]);
			yRect[0] = (int)(yVals[i-1]-width*yFactor[i-1]);
			xRect[1] = (int)(xVals[i]+width*xFactor[i-1]);
			yRect[1] = (int)(yVals[i]-width*yFactor[i-1]);
			xRect[2] = (int)(xVals[i]-width*xFactor[i-1]);
			yRect[2] = (int)(yVals[i]+width*yFactor[i-1]);
			xRect[3] = (int)(xVals[i-1]-width*xFactor[i-1]);
			yRect[3] = (int)(yVals[i-1]+width*yFactor[i-1]);
			g.fillPolygon(xRect, yRect, 4);
		}

		for(int i=1;i<xVals.length-1;i++)
			g.fillOval((int)(xVals[i]-width),
			(int)(yVals[i]-width), 
			(int)width*2, 
			(int)width*2); //bug in oval routine???

		//control line
		/**
		g.setColor(Color.red);
		for(int i=1;i<xVals.length;i++){
			g.drawLine((int)xVals[i-1], (int)yVals[i-1], (int)xVals[i], (int)yVals[i]);
		}
		*/

	}
/**
 *      Fills an Arc in the current fill color from a center point
 *@param        g       	The affected Graphics
 *@param        center  	Center point
 *@param        heightWidth     Size of arc
 *@param        startAngle      Starting angle
 *@param        endAngle        Ending angle
 */
	public void fillArc(  Graphics g,
		Point   center,
		Point   heightWidth,
		int	startAngle,
		int	endAngle) {

		int	urx, ury;

//		if(fillColor == TRANSPARENT)
//			return;

			/*******************************/

		int	nPoints;
		double xVal, yVal;
		float x[] = null;
		float y[] = null;
		double	increment = Math.PI / 180; //360 points in circ

		if(endAngle==0)
			return;
			
		double startRadians = (double)(startAngle)/180.0*Math.PI;
		double totalRadians = (double)(endAngle)/180.0*Math.PI;
		nPoints = (int) (totalRadians / increment);

		if(nPoints<2){ // hmmm... very skinny slice - make a triangle
			x = new float[3];
			y = new float[3];
			x[2] = center.x;
			y[2] = center.y;
			x[0] = (float)(center.x + Math.cos(startRadians)* (heightWidth.x/2));
			y[0] = (float)(center.y + Math.sin(startRadians)* (heightWidth.y/2));
			x[1] = (float)(center.x + Math.cos(startRadians+totalRadians)* (heightWidth.x/2));
			y[1] = (float)(center.y + Math.sin(startRadians+totalRadians)* (heightWidth.y/2));
			
		}
		else{
			
			//we want at least a triangle: two radians and center
			x = new float[nPoints+1];
			y = new float[x.length];
			x[nPoints] = center.x;
			y[nPoints] = center.y;
			double currentRadians = startRadians;
			//put any points around the edge
			for(int i=0;i<nPoints;i++){
				if(i==nPoints-1){
					currentRadians = startRadians + totalRadians;
				}
				xVal = center.x + Math.cos(currentRadians) * (heightWidth.x/2);
				yVal = center.y + Math.sin(currentRadians) * (heightWidth.y/2);
				x[i] = (float)xVal;
				y[i] = (float)yVal;
				currentRadians += increment;
			}
		}
		
		if(globals.java2Capable){
			getHelper().drawPolygon(g, x, y);
		}
		else{
			Point[] arcArray = new Point[x.length];
			for(int i=0;i<x.length;i++){
				arcArray[i] = new Point((int)Math.round(x[i]),(int)Math.round(y[i]));
			}
			drawPolygon(g, arcArray);
		}
		/***** end of 3.2 version */

		/***************************** java's fillArc has roundoff issues - doesn't outline well 
		urx = center.x - (heightWidth.x/2);
		ury = globals.maxY - center.y - (heightWidth.y/2);
		g.setColor(fillColor);
		g.fillArc(urx, ury, heightWidth.x, heightWidth.y, startAngle, endAngle);
		if(outlineFills){
			g.setColor(lineColor);
			g.drawArc(urx, ury, heightWidth.x, heightWidth.y, startAngle, endAngle);
		}
		***/
	}
	//graphics methods
/**
 *	Fills a rectangle with the current fill color
 *@param	g	Graphics class to draw
 *@param	ll	lower left corner of rectangle
 *@param	ur	upper right corner of rectangle
 */
	public void fillRect(  Graphics g,
		Point   ll,
		Point   ur) {
		int llx, urx;
		if (ll.x > ur.x) {
			llx = ur.x;
			urx = ll.x;
		} else {
			llx = ll.x;
			urx = ur.x;
		}
		if (fillColor != TRANSPARENT) {

			if (globals.java2Capable
					&& ((fillStyle == FILL_GRADIENT) || (fillStyle == FILL_TEXTURE))) {
				if (gcHelper == null) {
					gcHelper = new GcHelper(this);
				}
				gcHelper.fillRect(g, ll, ur);
				// return;
			} else {
				g.setColor(fillColor);
				if (ur.y > ll.y)
					g.fillRect(llx, globals.maxY - ur.y, urx - llx, ur.y
									- ll.y);
				else
					g.fillRect(llx, globals.maxY - ll.y, urx - llx, ll.y
									- ur.y);
			}
		}
		if (outlineFills) {
			if (lineColor == TRANSPARENT)
				return;
			g.setColor(lineColor);
			if (ur.y > ll.y)
				g.drawRect(llx, globals.getMaxY() - ur.y, urx - llx, ur.y
						- ll.y);
			else
				g.drawRect(llx, globals.getMaxY() - ll.y, urx - llx, ll.y
						- ur.y);
		}
		
	}
/**
 *	Formats a numeric string into one with commas and periods, according
 *	to the specified format
 *@param	s	Starting string
 *@param	format	DEFAULT_FORMAT, COMMA_FORMAT, or EURO_FORMAT
 *@return	a formatted string
 *@deprecated replaced by much more effective Format classes
 */
	public static String formattedLabel(String s, 
			int format, int labelPrecision){
		char	comma, dot, newChars[], oldChars[];
		int	decimalLocation, firstChar, i, j;
		//truncate to label precision
		i = s.lastIndexOf(".");
		if (i != -1) 
		{
			int length = s.length();
			//strip off trailing ".0" in java 1.1
			if((labelPrecision==0)||
				((i == length - 2)&&(s.charAt(length-1) == '0')))
				s =  s.substring(0,i);
			else if(length > 1 + i + labelPrecision)
				s = s.substring(0, i+1+labelPrecision);
		}

		if(format == DEFAULT_FORMAT)
			return s;

		if(format == COMMA_FORMAT){
			comma=',';
			dot='.';
		}
		else{
			comma='.';
			dot=',';
		}

		decimalLocation = s.indexOf('.');
		if(decimalLocation == -1)
			decimalLocation = s.length();
		else if(format == EURO_FORMAT)
			s = s.replace('.', ',');

		if((decimalLocation%3)!=0)
			newChars = new char[s.length() + (decimalLocation/3) ];
		else
			newChars = new char[s.length() + (decimalLocation/3) - 1 ];
		oldChars = s.toCharArray();

		for(i=oldChars.length-1, j=newChars.length-1; i>=decimalLocation; i--){
			newChars[j] = oldChars[i];
			j--;
		}

		if(oldChars[0] == '-'){
			firstChar = 1;
		}
		else
			firstChar = 0;

		for(j=0,i=0;i<decimalLocation;i++, j++){
			if((decimalLocation-i)%3==0){
				if((j>firstChar)&&(i<decimalLocation)){
					newChars[j] = comma;
					j++;
				}
			}
			newChars[j] = oldChars[i];
		}
		return new String(newChars);
	}
/**
 * Returns the current fillColor.
 * @return        Fill color
 * @uml.property  name="fillColor"
 */
	public Color    getFillColor() {
		return fillColor;
	}
/**
 * Returns the Image used with this Gc.  For Line and Regress charts this Image is used for data markers.  For Bar and Column charts, this Image is the tile for the data bars. Currently not implemented for Bar and Colum charts.
 * @return        Marker image
 * @uml.property  name="image"
 */
	public Image    getImage() {
		return image;
	}
/**
 * Returns the current lineColor.
 * @return        Line color
 * @uml.property  name="lineColor"
 */
	public Color    getLineColor() {
		return lineColor;
	}
/**
 * Returns the current lineStyle. Currently not implemented
 * @return        Line style
 * @uml.property  name="lineStyle"
 */
	public int      getLineStyle() {
		return lineStyle;
	}
/**
 * Returns the current lineWidth This integer value is the number of pixels wide this Gc will draw lines.
 * @return        Line width
 * @uml.property  name="lineWidth"
 */
	public int      getLineWidth() {
		return lineWidth;
	}
/**
 * Returns the marker size. The return value is in pixels
 * @return        Marker size
 * @uml.property  name="markerSize"
 */
	public int      getMarkerSize() {
		return markerSize;
	}
/**
 * Returns the marker style.
 * @return        Marker style
 * @uml.property  name="markerStyle"
 */
	public int      getMarkerStyle() {
		return markerStyle;
	}
/**
 * Returns true if this Gc is set up to outline polygons
 * @return  boolean
 * @uml.property  name="outlineFills"
 */
public boolean getOutlineFills() {
	return outlineFills;
}

	/**
	 *  Returns the width of a label taking line breaks into account
	 *  @param fm a font metrics class initialized for this label
	 *  @param str the label string
	 *  @return label width
	 */

	protected static int getStringWidth(FontMetrics fm, String str){
		int width = 0;
		int lineWidth;
		String currentLine;
		StringTokenizer st = new StringTokenizer(str, LINE_BREAK);
		while(st.hasMoreTokens()){
			currentLine = st.nextToken();
			lineWidth = fm.stringWidth(currentLine);
			if(lineWidth>width)
				width = lineWidth;
		}
		return width;
	}
	
	/**
	 *  Returns the height of a label taking line breaks into account
	 *  @param fm a font metrics class initialized for this label
	 *  @param str the label string
	 *  @return label height
	 */

	protected static int getStringHeight(FontMetrics fm, String str){
		int height = 0;
		int lineHeight = fm.getMaxAscent();
		StringTokenizer st = new StringTokenizer(str, LINE_BREAK);
		int lines = st.countTokens();
		height = lineHeight * lines + ((int)(lineHeight/5)+1)*(lines-1);
		return height;
	}

/**
 * initializes default Font, Color, etc.
 */
private static synchronized void init() {
	defaultFont = new Font("TimesRoman", Font.PLAIN, 12);
	TRANSPARENT = new Color(1,1,1);
}
/**
 *	Converts a numeric String that contains scientific notation into
 *	a String without scientific notation
 *@param	s	String to convert
 *@param	eIndex	location of the "e" in this string
 *@return	expanded number
 *@deprecated replaced by much more effective Format classes
 */
	public static String nonSciNumberStr(String s, int eIndex){
		int	i, mantissa, decimalLocation;
		String	trimmedString;
		char	zeroes[] = null;

		i = s.indexOf('+');
		if(i==-1)
			mantissa = Integer.parseInt(s.substring(eIndex+1));
		else
			mantissa = Integer.parseInt(s.substring(i+1));
		trimmedString = (s.substring(0, eIndex)).trim();
		decimalLocation = trimmedString.indexOf('.');
		if(decimalLocation == -1){
			if(mantissa>0)
				i = mantissa;
			else
				i = -mantissa;
		}
		else{
			if(mantissa>0)
				i = mantissa - (trimmedString.length() - decimalLocation) + 1;
			else
				i = -mantissa - decimalLocation;
		}
		if(i > 0){
			zeroes = new char[i];
			for(i=0;i<zeroes.length;i++)
				zeroes[i] = '0';
		}

		if(decimalLocation == -1)
			if(mantissa<0)
				return("0." + new String(zeroes) + trimmedString);
			else
				return(trimmedString + new String(zeroes));
		else  //take out decimal point
			if(mantissa<0)
				return("0." + new String(zeroes) + trimmedString.substring(0,decimalLocation) + trimmedString.substring(decimalLocation+1));
			else
				if(zeroes != null)
					return(trimmedString.substring(0,decimalLocation) + trimmedString.substring(decimalLocation+1) + new String(zeroes));
				else{
					//i < 0, zeroes is null, number looks like this 2.345E2, which needs to become 234.5
					int newDecimalLoc = trimmedString.length() + i;
					return(trimmedString.substring(0,decimalLocation) + trimmedString.substring(decimalLocation+1, newDecimalLoc) + '.' + trimmedString.substring(newDecimalLoc));
				}
	}
	private Color randomColor() {
		float	r, g, b;
		r = (float)Math.random();
		g = (float)Math.random();
		b = (float)Math.random();
		return new Color(r,g,b);
	}
/**
 * Sets the current fillColor.
 * @param c        Fill color
 * @uml.property  name="fillColor"
 */
	public void	setFillColor(Color c) {
		fillColor = c;
		if(gcHelper!=null){
			gcHelper.gradient = null;
			gcHelper.texture = null;
		}
	}
/**
 * Assigns an Image for use with this Gc.  For Line and Regress charts this Image is used to create data markers.  For Bar and Column charts, this Image is used as a tile for the data bars. Currently not implemented for Bar and Colum charts.
 * @param i        Marker image
 * @uml.property  name="image"
 */
	public void	setImage(Image i) {
		image = i;
	}
/**
 * Sets the current lineColor.
 * @param c        Line color
 * @uml.property  name="lineColor"
 */
	public void	setLineColor(Color c) {
		lineColor = c;
	}
/**
 * Sets the current lineStyle. For example setLineStyle(Gc.LINE_DASH)
 * @param i        Line style
 * @uml.property  name="lineStyle"
 */
	public void	setLineStyle(int i) {
		lineStyle = i;
		if(gcHelper!=null){
			gcHelper.stroke = null;
		}
	}
/**
 * Sets the current lineWidth Sets the number of pixels wide for this Gc's lines and polylines.
 * @param i        Line width
 * @uml.property  name="lineWidth"
 */
	public void	setLineWidth(int i) {
		lineWidth = i;
		if(gcHelper!=null){
			gcHelper.stroke = null;
		}
	}
/**
 * Sets the marker size in pixels
 * @param i        Marker size
 * @uml.property  name="markerSize"
 */
	public void	setMarkerSize(int i) {
		markerSize = i;
	}
/**
 * Sets the marker style For example setMarkerStyle(Gc.MK_CIRCLE)
 * @param i        Marker style
 * @uml.property  name="markerStyle"
 */
	public void	setMarkerStyle(int i) {
		markerStyle = i;
	}
/**
 * Determines whether polygons and rectangles will be outlined with lineColor
 * @param outline  boolean
 * @uml.property  name="outlineFills"
 */
public void setOutlineFills(boolean outline) {
	outlineFills = outline;
}

public void setGradient(int grad) {
	if(!globals.java2Capable)
		return;
	try{
	fillStyle = FILL_GRADIENT;
	getHelper().gradientType = grad;
	gcHelper.gradient = null;
	}catch(Exception ignored){
		//no Java2D?
	}
}

public int getGradient(){
	if(!globals.java2Capable)
		return -1;
	try{
		return getHelper().gradientType;
	}
	catch(Exception noGrad){
		return -1;
	}
}

/**
 * Returns this Gc's helper class.  If none exists, one is created and returned.
 * Creation date: (1/22/2002 2:41:10 PM)
 * @return javachart.chart.GcHelper
 */
public GcHelper getHelper() {
	try{
	if(gcHelper==null){
		if(!globals.java2Capable)
				return null;
		gcHelper = new GcHelper(this);
	}
	}catch(Exception e){
		//no Java2d?
	}
	return gcHelper;
}


/**
 * Sets a secondary fill color on this Gc's Helper class.  This is used as a second color for Gradients or
 * Textures (pattern fills).
 * Creation date: (1/22/2002 3:02:48 PM)
 * @param c java.awt.Color
 */
public void setSecondaryFillColor(Color c) {
	if(globals.java2Capable){
		getHelper().secondaryFillColor = c;
		gcHelper.gradient = null;
		gcHelper.texture = null;
	}
}

public Color getSecondaryFillColor(){
	if(!globals.java2Capable){
		return fillColor;
	}
	return getHelper().secondaryFillColor;
}

/**
 * Installs a GcHelper if none exists, sets the fillStyle to FILL_TEXTURE, and sets the predefined pattern
 * on the GcHelper.
 * Creation date: (1/22/2002 2:35:06 PM)
 * @param grad int
 */
public void setTexture(int texture) {
	if(!globals.java2Capable)
		return;
	try{
	getHelper().textureType = texture;
	gcHelper.texture = null;
	fillStyle = FILL_TEXTURE;
	}catch(Exception ignored){
		//no Java2D?
	}
}

public int getTexture(){
	if(!globals.java2Capable)
		return FILL_TEXTURE;
	return getHelper().textureType;
}

/**
 * @param fillStyle  要设置的 fillStyle。
 * @uml.property  name="fillStyle"
 */
public void setFillStyle(int style){
	fillStyle = style;
}

/**
 * @return  返回 fillStyle。
 * @uml.property  name="fillStyle"
 */
public int getFillStyle(){
	return fillStyle;
}

/**
 * @param globals  要设置的 globals。
 * @uml.property  name="globals"
 */
public void setGlobals(Globals g){
	this.globals = g;
}

/**
 * @return  返回 globals。
 * @uml.property  name="globals"
 */
public Globals getGlobals(){
	return globals;
}

	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
}
