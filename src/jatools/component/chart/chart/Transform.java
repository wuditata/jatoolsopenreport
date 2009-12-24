/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.awt.Point;
import java.io.Serializable;
import java.util.Date;

/**
 *	Provides a simple 2-D transformation.  Default chart graphs use 
 *	at least 2 Transforms: 1 that transforms data into the plotting 
 *	area, and a second that transforms the relative coordinates of 
 *	legends, axis locations, etc. (which range from 0 to 1.0) into the 
 *	real pixel coordinates of the screen.  While Transforms are fairly 
 *	simple in this context, note that all data representations (bars, 
 *	lines, etc.) are drawn through a data Transform.  By creating a 
 *	subclass of Transform, you can easily build data representations 
 *	that draw in non-linear spaces, such as logarithms, natural logs, 
 *	even discontinuous spaces.  For a complete representation, of 
 *	course, you would also need to create a descriptive axis system, 
 *	probably a subclass of Axis.
 *@see	jatools.component.chart.chart.Plotarea
 *@see	jatools.component.chart.chart.Area
 *@see	jatools.component.chart.chart.Bar
 *@see	jatools.component.chart.chart.Line
 */

public class Transform implements Serializable {

	//used for transforming graphics going into the plotting area

	    //internals
	private         double          scaleX;
	private         double          scaleY;
	private         double          shiftX;
	private         double          shiftY;
	private		 double 		  dateShiftX;
	private 		 double			  dateScaleX;

	private         double          logScaleX;
	private         double          logScaleY;
	private         double          logShiftX;
	private         double          logShiftY;

	protected		boolean			logXScaling = false;
	protected		boolean			logYScaling = false;

	final static double LOG_10_E = 0.43429420;
	final static int MAX_VAL = 8192;	
	final static int MIN_VAL = -8192;
	


/**
 *	Creates a transform that will scale numbers from a double precision
 *	arena to an integer space.
 *
 * @param	dllX	lower left X
 * @param	dllY	lower left Y
 * @param	durX	upper right X
 * @param	durY	upper right Y
 * @param	illX	lower left X
 * @param	illY	lower left Y
 * @param	iurX	upper right X
 * @param	iurY	upper right Y
 */
	public Transform(      double  dllX, //double precision window
	    double  dllY, 
	    double  durX,
	    double  durY,
	    int     illX, //integer precision window
	    int     illY,
	    int     iurX, 
	    int     iurY) {

		double  logdllX = log10(dllX);
	    double  logdllY = log10(dllY); 
	    double  logdurX = log10(durX);
	    double  logdurY = log10(durY);

		scaleX = (double)(iurX - illX) / (durX - dllX);
		scaleY = (double)(iurY - illY) / (durY - dllY);

		shiftX = (double) illX - (scaleX * dllX);
		shiftY = (double) illY - (scaleY * dllY);

		//now log values
		logScaleX = (double)(iurX - illX) / (logdurX - logdllX);
		logScaleY = (double)(iurY - illY) / (logdurY - logdllY);

		logShiftX = (double) illX - (logScaleX * logdllX);
		logShiftY = (double) illY - (logScaleY * logdllY);
	}
/**
 *	Creates a transform that will scale numbers from a double precision
 *	arena to an integer space.
 *
 * @param	dllX	lower left X
 * @param	dllY	lower left Y
 * @param	durX	upper right X
 * @param	durY	upper right Y
 * @param	ll	integer lower left
 * @param	ur	integer upper right
 */
	public Transform(      double  dllX, //double precision window
	    double  dllY, 
	    double  durX,
	    double  durY,                                  
	    Point   ll, //integer precision window
	    Point   ur ) {

		double  logdllX = log10(dllX);
	    double  logdllY = log10(dllY); 
	    double  logdurX = log10(durX);
	    double  logdurY = log10(durY);

	    //System.out.println(durX - dllX);
	    //System.out.println(ur.x - ll.x);
	   // System.out.println(ur.x + " " + ll.y);
	    //System.out.println(durX + " " + dllX);
		scaleX = (double) (ur.x - ll.x) / (durX - dllX);
//		System.out.println(scaleX);
		scaleY = (double) (ur.y - ll.y) / (durY - dllY);
		
		shiftX = (double) ll.x - (scaleX * dllX);
		/*if(shiftX < 0){
		shiftX = ll.x;
		}*/
		//System.out.println(shiftX);
		
		shiftY = (double) ll.y - (scaleY * dllY);

		//now log values
		logScaleX = (double) (ur.x - ll.x) / (logdurX - logdllX);
		logScaleY = (double) (ur.y - ll.y) / (logdurY - logdllY);

		logShiftX = (double) ll.x - (logScaleX * logdllX);
		logShiftY = (double) ll.y - (logScaleY * logdllY);
	}
	static double log10(double inVal){
		return(Math.log(inVal) * LOG_10_E);
	}
/**
 *	Returns a transformed integerized Point from double precision X and Y
 *	coordinates
 */
	public Point   point(double    x,
	    double   y ) {
		int iX, iY;

		if(!logXScaling)
		{
	
			//added some overflow logic to accomodate scrolling of large data series 5.30.2000
			//System.out.println(x);
//			System.out.println(scaleX);
//			System.out.println(shiftX);
			double dX = (x * scaleX) + shiftX;
			/*System.out.println(x);
			
			System.out.println(shiftX + " " +dX);*/
//			System.out.println(dX);
			if(dX>MAX_VAL)
				iX = MAX_VAL;
			else if (dX < MIN_VAL)
				iX = MIN_VAL;
			else iX = (int)dX;
			//original code follows
			//iX = (int)(Math.min(((x * scaleX) + shiftX), Short.MAX_VALUE));
		}
		else{
			iX = (int)((log10(x) * logScaleX) + logShiftX);
		}
		
		if(!logYScaling)
		{
			double dY = (y * scaleY) + shiftY;
			if(dY>MAX_VAL)
				iY = MAX_VAL;
			else if (dY < MIN_VAL)
				iY = MIN_VAL;
			else iY = (int)dY;
			//iY = (int)((y * scaleY) + shiftY);
		}
		else
			iY = (int)((log10(y) * logScaleY) + logShiftY);

		return new Point(iX, iY);
	}
/**
 *	Transforms an array of double precision X and Y values into an array
 *	of integerized Point classes
 */
	public Point[]	pointList(	double	x[],
	    double	y[]) {
		int		i;
		Point	pts[];

		pts = new Point[x.length];
		for(i=0;i<x.length;i++) {
			pts[i] = point(x[i], y[i]);
		}
		return pts;
	}
	public String   toString() {
		return getClass().getName() + "[" + 
		    " shiftX " + shiftX +
		    " shiftY " + shiftY +
		    " scaleX " + scaleX +
		    " scaleY " + scaleY +
		    " ]";
	}
/**
 *	Transforms an integer X value into a double precision X value.  This
 *	method is used to translate the X component of a pixel coordinate into
 *	data or percentage space
 */
	public double	xValue(int ix){
		double x;
		x = (double) ix;
		if(!logXScaling) {
			x = (x - shiftX)/scaleX;
			return x;
		}
		else {
			x = (x - logShiftX)/logScaleX;
			return log10(x);
		}
	}
/**
 *	Transforms an integer Y value into a double precision Y value.  This
 *	method is used to translate the Y component of a pixel coordinate into
 *	data or percentage space
 */
	public double	yValue(int iy){
		double y;

		y = (double) iy;
		if(!logYScaling) {
			y = (y - shiftY)/scaleY;
			return y;
		}
		else {
			y = (y - logShiftY)/logScaleY;
			return log10(y);
		}
	}
	
	public static void main(String[] args) {
		long end = new Date(2005-1900,1-1,15).getTime() ;
		long start = new Date(2005-1900,1-1,1).getTime();
		long now =new Date(2005-1900,1-1,3).getTime();
		
		double x =1.0 *( now - start) / (end - start);
		
		System.out.println(x);
		System.out.println("start = " + start);
		System.out.println("end = " + end);
		System.out.println("now = " + now);
		System.out.println("now - start = " + (now - start));
		
	}
}
