/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.awt.FontMetrics;
import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;

/**
 * A DisplayList is stored with other global information to record  information about each drawn object's position and shape.  By  passing a point and a Vector into the contains() method, you can  receive a list of all the objects that include the point.  Since  one location can contain many objects, this isn't as intuitive as  it may appear.  For example, a single point may correspond to an  individual bar (Datum), a set of bars (Dataset, Bar), a Plotarea,  and a Background.   Since the information is returned in your Vector  class as an anonymous list of objects, you can use the "instanceof"  operator to determine the object you're interested in.   The Globals class access method setUseDisplayList  determines whether the display list should accumulate information  or not.   By default, the global display list is inactive.  Generally  each drawing class also contains an access  method setUseDisplayList, which lets you selectively eliminate some  classes from your list.  All drawing classes add information to the global display list by default. If active, the global displayList is cleared each time a chart is  redrawn.
 * @see jatools.component.chart.chart.Globals
 * @see  jatools.component.chart.chart._Chart
 */

public class DisplayList implements Serializable {

	Vector	primitiveList = new Vector();
	Vector	objectList = new Vector();
	Globals	globals;

/**
 * Creates a new DisplayList without assigning a particular chart's Globals class.
 */
public DisplayList() {
}
/**
 *	Constructs a new, empty DisplayList
 *@param	g	this chart's Globals class
 */
	public DisplayList(Globals g){
		globals = g;
	}
/**
 *	Adds a Pie slice to the DisplayList
 * @param	obj	Object creating this slice
 * @param	widthHeight	width and height of circle
 * @param	startAngle	starting degrees (0 at 3 o'clock)
 * @param	arcAngle	degrees this arc sweeps
 */
	public synchronized void addArc(Object	obj,
			Point	center,
			Point	widthHeight,
			int	startAngle,
			int	arcAngle){

		Point 	arcArray[];
		double	currentRadians, totalRadians;
		int	nPoints, xVal, yVal;
		double	increment = Math.PI / 50; //100 points in circ

		currentRadians = (double)(startAngle)/180.0*Math.PI;
		totalRadians = (double)(arcAngle)/180.0*Math.PI;
		nPoints = (int) (totalRadians / increment);
		//we want at least a triangle: two radians and center
		arcArray = new Point[nPoints+2];
		arcArray[nPoints+1] = center;
		//put the last point in
		xVal = center.x + (int)(Math.cos(currentRadians + totalRadians) * (widthHeight.x/2));
		yVal = center.y + (int)(Math.sin(currentRadians + totalRadians) * (widthHeight.y/2));
		arcArray[nPoints] = new Point(xVal, yVal);

		//put any points around the edge
		for(int i=0;i<nPoints;i++){
			xVal = center.x + (int)(Math.cos(currentRadians) * (widthHeight.x/2));
			yVal = center.y + (int)(Math.sin(currentRadians) * (widthHeight.y/2));
			arcArray[i] = new Point(xVal, yVal);
			currentRadians += increment;
		}
		primitiveList.addElement(arcArray);
		objectList.addElement(obj);
	}
/**
 *	Adds a vertical line to the DisplayList.  This is used for Stick and HLOC
 *	charts.
 *@param	obj	the object creating this line
 *@param	start	start point for this line
 *@param	end	end point for this line
 */
	public void addLine(Object obj,
			Point	start,
			Point	end){
		Point ll = new Point(start.x - 2, start.y - 2);
		Point ur = new Point(end.x + 2, end.y + 2);
		addRectangle(obj, ll, ur);
	}
/**
 *	Adds a polygon to the DisplayList
 *@param	obj	the object creating this polygon
 *@param	p	the list of points comprising the polygon
 */
	public synchronized void addPolygon(Object	obj,
			Point	p[]){ 	//for area charts
                Point[] temp = new Point[p.length];
                for(int i=0; i<p.length; i++)
                        temp[i] = p[i];
		primitiveList.addElement(temp);
		objectList.addElement(obj);
	}
/**
 *	Adds a polyline to the DisplayList
 *@param	obj	the object creating this polyline
 *@param	p	the list of points comprising the polyline
 */
	public synchronized void addPolyline(Object	obj,
			Point	p[]){ 	//for line charts
		Point polylist[] = new Point [p.length * 2];
		for(int i=0;i<p.length;i++){
			polylist[i] = p[i];
			//arbitrary 2 pixel shift up and down... has problems with acute angle picks
			polylist[i].translate(2, 2);
			polylist[polylist.length - i - 1] =
				new Point(p[i].x-4, p[i].y-4); 
		}
		primitiveList.addElement(polylist);
		objectList.addElement(obj);
	}
/**
 *      Adds a rectangle to the DisplayList
 *@param        obj	The object creating this rectangle
 *@param        ll      Lower-left point
 *@param        ur      Upper-right point
 *@return       None
 */
	public synchronized void addRectangle(Object	obj,
			Point		ll,
			Point		ur){
		Point	myRect[] = new Point[4];
		myRect[0] = new Point(ll.x, ll.y);
		myRect[1] = new Point(ll.x, ur.y);
		myRect[2] = new Point(ur.x, ur.y);
		myRect[3] = new Point(ur.x, ll.y);
		primitiveList.addElement(myRect);
		objectList.addElement(obj);
	}
/**
 *	Adds a text string to the DisplayList
 *@param        obj	The object creating this text string
 *@param        llx     Lower-left X coordinate
 *@param        lly     Lower-left Y coordinate
 *@param        s       This String
 *@param        fm	This String's FontMetrics
 *@return       None
 */
	public synchronized void addTextString(Object	obj,
			int		llx,
			int		lly,
			String		s,
			FontMetrics	fm){

		int strW = fm.stringWidth(s);
		int strH = fm.getHeight();

		Point	myRect[] = new Point[4];
		myRect[0] = new Point(llx, lly);
		myRect[1] = new Point(llx, lly + strH);
		myRect[2] = new Point(llx + strW, lly + strH);
		myRect[3] = new Point(llx + strW, lly);
		primitiveList.addElement(myRect);
		objectList.addElement(obj);
	}
/**
 *	Empties the current DisplayList
 */
	public synchronized void clear(){
		//should dispose of old vectors?
		primitiveList = new Vector();
		objectList = new Vector();
	}
/**
 *	Fills a Vector with objects located at the specified Point
 *@param	p		The Point to inquire
 *@param	yourVector	A user supplied Vector class
 */
	public synchronized boolean contains(Point	p,
				Vector yourVector){
		if(traverseList(p, yourVector))
			return true;
		else
			return false;
	}
	private boolean inPolygon(Point		pickpt,
				  Point		pts[]) {
		int npts;
		int ret=0;
		int iflip=0;
		int i,j;
		int xf, yf, xt, yt;
		long flip;
		npts = pts.length;
		for (i=0; i<npts; i++) {
			j = i+1;
			if (j == npts) j = 0;
			xf = pts[i].x - pickpt.x;
			yf = pts[i].y - pickpt.y;
			xt = pts[j].x - pickpt.x;
			yt = pts[j].y - pickpt.y;
			if ((xf <= 0 && xt <= 0) || 
				(yf <= 0 && yt <= 0) || 
				(xf > 0 && xt > 0)) continue; 
			if ((flip = (xf * yt) - (yf * xt))==0) continue;
			if (((flip)>0) != ((xf-xt)>0)) continue;
			ret++;
			if (flip < 0) iflip--;
			if (flip > 0) iflip++;
			}
	ret &= 1;
	if (ret == 0 && iflip != 0) ret = 1;
	if (ret == 1) 
		return true;
	else 	
		return false;
	}
	private boolean traverseList(Point	p,
					Vector userVector){
		Point	myPoints[];
		Point	pickPoint;
		int 	i;
		boolean foundOne = false;

		pickPoint = new Point(p.x, globals.maxY - p.y);
		for(i=0;i<primitiveList.size();i++){
			myPoints = (Point [])primitiveList.elementAt(i);
			if(inPolygon(pickPoint, myPoints)) {
				userVector.addElement(objectList.elementAt(i));
				foundOne = true;
			}
		}
		return foundOne;
	}
}