/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.io.Serializable;

/**
 * The Datum class is used as the basic chart data storage unit.   This class can accomodate several concurrent observations (such  as a date, high, low, and close for a financial chart) as well  as labels and graphical attributes.
 * @see  jatools.component.chart.chart.Dataset
 */

public class Datum implements Serializable
{
	//package vars
	public static final String DISCONTINUITY = "D";
	public static final double DEFAULT = Double.NEGATIVE_INFINITY;
	 String	label;
	Gc	gc;
	double	x = DEFAULT;
	double y = x ;
	double y2 = x ;
	double y3 = x ;
	Globals	globals;

/**
 * 	Constructor for HiLoClose charts.  Initializes 4 data elements: x, 
 *	hi, lo, and close.
 *@param	dataX	X value (possibly a date)
 *@param	hi	High price for a period
 *@param	lo	Low price for a period
 *@param	close	Closing price for a period
 *@param	g	this chart's Globals class
 */
	public Datum(	double  dataX,
	    	double 	hi,
	    	double 	lo,
	    	double 	close ,
		Globals	g) {

		globals = g;
		x = dataX;
		y = hi;
		y2 = lo;
		y3 = close;
		//gc = new Gc(globals);
        gc = new Gc(true, globals, false);
	}
/**
 *  	Constructs a Datum appropriate for use in Hi-Lo charts.  Assigns a 
 *	Gc based on this Datum's position within a Dataset.  Initializes
 *	X, Y and Z (or auxilary Y) values. 
 *@param        dataX	X value
 *@param        dataY	Y value
 *@param        dataZ	Z value
 *@param        element position within Dataset
 *@param	g	this chart's Globals class
 */
	public Datum(	double  dataX,
	    	double 	dataY,
	    	double 	dataZ,
	    	int	element,
		Globals	g) {

		globals = g;
		x = dataX;
		y = dataY;
		y2 = dataZ;
		//gc = new Gc(element, globals);
       gc = new Gc(true, globals, false);
	}
/**
 *  	Constructs a Datum appropriate for use in Hi-Lo charts.  Assigns a 
 *	Gc based on this Datum's position within a Dataset.  Initializes
 *	X, Y and Z (or auxilary Y) values.  Also initializes a label.
 *@param        dataX	X value
 *@param        dataY	Y value
 *@param        dataZ	Z value
 *@param        str     Label
 *@param        element position within Dataset
 *@param	g	this chart's Globals class
 */
	public Datum(	double  dataX,
	    	double 	dataY,
	    	double 	dataZ,
	    	String 	str,
	    	int	element,
		Globals	g) {

		globals = g;
		x = dataX;
		y = dataY;
		y2 = dataZ;
		//gc = new Gc(element, globals);
        gc = new Gc(true, globals, false);
		label = str;
	}
/**
 *      Datum with colors (Gc) initialized by dataset index number.
 *      Initializes X and Y values.
 *@param        dataX	X value
 *@param        dataY	Y value
 *@param        setNumber       Dataset index number
 *@param        g	This chart's Globals class
 */
	public Datum( 	double  dataX, 
	    	double  dataY,
	    	int     setNumber,
		Globals	g) {
		globals = g;
		x = dataX;
		y = dataY;
		//gc = new Gc(setNumber, globals);
                gc = new Gc(true, globals, false);
	}
/**
 *      Constructs a Datum with a Gc based on this Datum's place within
 *	a Dataset.  Also initializes a label and X and Y values.
 *@param        dataX	X value
 *@param        dataY	Y value
 *@param        str     Label
 *@param        element Element number
 */
	public Datum(	double  dataX,
	    	double 	dataY,
	    	String 	str,
	    	int	element,
		Globals	g) {

		globals = g;
		x = dataX;
		y = dataY;
		//gc = new Gc(element, globals);
        gc = new Gc(true, globals, false);
		label = str;
	}
/**
 *      Constructs Datum with unique color, initializes X, Y and label 
 *      values.
 *@param        dataX		X value
 *@param        dataY		Y value
 *@param        str     	Label
 *@param        individual      true if you want a unique color
 *@param        g		This chart's Globals class
 */
	public Datum(   double  dataX, 
				    double  dataY,
				    String  str,
				    boolean individual,
					Globals	g) {
		globals = g;
		x = dataX;
		y = dataY;
		label = str;
		gc = new Gc(individual, globals, true);
	}
/**
 *      Construct      Datum      with predictable color, initializes X, Y and
 * label values.
 *@param        whichPoint		implicit X value
 *@param        dataY			Y value
 *@param        str     		Label
  *@param        g				This chart's Globals class
 */
	public Datum(  int whichPoint,
					double  dataY,
					String  str,
					Globals	g) {
		globals = g;
		x = (double)whichPoint;
		y = dataY;
		label = str;
		gc = new Gc(whichPoint, globals);
	}
/**
 *      Constructs Datum with a default Gc, initializes X and Y values.
 *      
 *@param        dataX	X value
 *@param        dataY	Y value
 *@param        g	This chart's Globals class
 */
	public Datum(  double  dataX, 
	    	double  dataY,
		Globals	g) {

		globals = g;
		x = dataX;
		y = dataY;
		//gc = new Gc(globals);
        gc = new Gc(true, globals, false);
	}
/**
 *      Constructs Datum with unique color, initializes X and Y values.
 *@param        dataX		X value
 *@param        dataY		Y value
 *@param        individual      true if you want a unique color
 *@param        g               This chart's Globals class
 */
	public Datum(	double  dataX, 
	    	double  dataY,
	    	boolean individual,
		Globals	g) {

		globals = g;
		x = dataX;
		y = dataY;
		gc = new Gc(individual, globals, true);
	}
/**
 *      Constructs a Datum with a Gc based on this Datum's place within 
 *	a Dataset.  Also initializes a label and a Y value.
 *@param        dataY	Value
 *@param        str     Label
 *@param        element Element number
 */
	public Datum(	double  dataY,
	    	String 	str,
	    	int	element,
		Globals	g) {

		globals = g;
		y = dataY;
		//gc = new Gc(element, globals);
        gc = new Gc(true, globals, false);
		label = str;
	}
/**
 * Returns this Datum's Gc
 * @return        this Datum's Gc
 * @uml.property  name="gc"
 */
	public Gc	getGc() {
		return gc;
	}
	/**
	 * @return  返回 globals。
	 * @uml.property  name="globals"
	 */
	public Globals getGlobals(){
		return globals;
	}
/**
 * Returns this Datum's label
 * @return        Label
 * @uml.property  name="label"
 */
	public String	getLabel() {
		return label;
	}
/**
 *      Returns label, if it exists.  Otherwise returns the string value of Y.
 *@return       Label or Y
 */
	public String getString() {
		if(label!=null)
			return label;

		String inStr = Double.toString(y);
		int i = inStr.indexOf(".");
		if (i != -1) {//remove ".0" labels
			int length = inStr.length();
			if((i == length - 2)&&(inStr.charAt(length-1) == '0'))
				inStr =  inStr.substring(0,i);
		}
		return inStr;
	}
/**
 * Returns value of X.
 * @return        Value of X
 * @uml.property  name="x"
 */
	public double getX() {
		return x;
	}
/**
 * Returns value of Y.
 * @return        Value of Y
 * @uml.property  name="y"
 */
	public double getY() {
		return y;
	}
/**
 * Returns value of Y2.
 * @return        Value of Y2
 * @uml.property  name="y2"
 */
	public double getY2() {
		return y2;
	}
/**
 * Returns value of Y3.
 * @return        Value of Y3
 * @uml.property  name="y3"
 */
	public double getY3() {
		return y3;
	}
/**
 * Assigns a new Gc class to this Datum
 * @param g  	new Gc class
 * @uml.property  name="gc"
 */
	public void setGc(Gc g) {
		gc = g;
		gc.globals = globals;
	}
	/**
	 * @param globals  要设置的 globals。
	 * @uml.property  name="globals"
	 */
	public void setGlobals(Globals g){
		globals = g;
		gc.globals = g;
	}
/**
 * Sets this Datum's label to a new value.
 * @param s        Label
 * @uml.property  name="label"
 */
	public void setLabel(String s) {
		label = s;
	}
/**
 * Sets X to a new value.
 * @param d        New value of X
 * @uml.property  name="x"
 */
	public void setX(double d) {
		x = d;
	}
/**
 * Sets Y to a new value.
 * @param d        New value of Y
 * @uml.property  name="y"
 */
	public void setY(double d) {
		y = d;
	}
/**
 * Sets Y2 to a new value.
 * @param d        New value of Y2
 * @uml.property  name="y2"
 */
	public void setY2(double d) {
		y2 = d;
	}
/**
 * Sets Y3 to a new value.
 * @param d        New value of Y3
 * @uml.property  name="y3"
 */
	public void setY3(double d) {
		y3 = d;
	}
}