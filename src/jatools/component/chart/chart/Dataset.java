/*
 * Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Visual Engineering, Inc.
 * Use is subject to license terms.
 */
package jatools.component.chart.chart;

import jatools.component.chart.Tip;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * A collection of Datum classes, along with a set of graphical attributes (Gc)
 * and labelling information. In addition, Dataset provides methods for
 * determining certain metrics about the overall collection. These methods are
 * used by some of the graphical component classes (e.g. Pie percentages) as
 * well as the Axis classes (max, min, etc.) Datasets are always initialized
 * with a Gc, a dataset label color (black), and an initial label font
 * (TimesRoman, 12)
 * 
 * @see jatools.component.chart.chart.Datum
 * @see jatools.component.chart.chart._Chart
 */

public class Dataset implements Serializable, Cloneable {

	// package vars
	Vector data = new Vector();

	String setName;

	Gc gc;

	Color labelColor = Color.black;

	Font labelFont = Gc.defaultFont;

	Globals globals;

	String[] labels;

	private Tip[] tips;

	/**
	 * Constructs an unassigned Dataset class. This is primarily useful for
	 * chart data feed beans, as all Chart datasets must include the Chart's
	 * Globals class
	 */
	public Dataset() {
		this("new", null, null, new Globals());
	}

	public boolean hasTip() {
		if (tips != null) {

			for (int i = 0; i < tips.length; i++) {
				if (tips[i] != null) {
					return true;
				}
			}
		}

		return false;
	}

	/***************************************************************************
	 * Constructor for HiLoClose charts. Initializes everything but labels: x,
	 * y, y2, and y3 values. Sets an initial Gc determined by the set number.
	 * 
	 * @param name
	 *            Dataset name
	 * @param xarr
	 *            Array of X values
	 * @param yarr
	 *            Array of Y values
	 * @param y2arr
	 *            Array of Y2 values
	 * @param y3arr
	 *            Array of Y3 values
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            this chart's Globals class
	 */
	public Dataset(String name, double xarr[], double yarr[], double y2arr[], double y3arr[],
			int setNumber, Globals g) {

		init(name, setNumber, g);
		for (int i = 0; i < yarr.length; i++) {
			data.addElement(new Datum(xarr[i], yarr[i], y2arr[i], y3arr[i], g));
		}
	}

	/**
	 * Constructor for HiLo charts. Initializes everything: x, y, y2, and label
	 * values. Sets an initial Gc determined by the set number. Builds an
	 * incomplete Dataset if one of the arrays is incomplete (Data truncated to
	 * last complete value)
	 * 
	 * @param name
	 *            Dataset name
	 * @param xarr
	 *            Array of X values
	 * @param yarr
	 *            Array of Y values
	 * @param y2arr
	 *            Array of Y2 values
	 * @param label
	 *            Label values
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            This chart's Globals class
	 */
	public Dataset(String name, double xarr[], double yarr[], double y2arr[], String label[],
			int setNumber, Globals g) {

		init(name, setNumber, g);

		for (int i = 0; i < yarr.length; i++) {
			try {
				data.addElement(new Datum(xarr[i], yarr[i], y2arr[i], label[i], setNumber, g));
			} catch (ArrayIndexOutOfBoundsException e) {
				return;
				// incomplete data
			}
		}

		labels = label;
	}

	/**
	 * Constructor for HiLo charts. Initializes everything but labels: x, y, and
	 * y2 values. Sets an initial Gc determined by the set number.
	 * 
	 * @param name
	 *            Dataset name
	 * @param xarr
	 *            Array of X values
	 * @param yarr
	 *            Array of Y values
	 * @param y2arr
	 *            Array of Y2 values
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            this chart's Globals class
	 */
	public Dataset(String name, double xarr[], double yarr[], double y2arr[], int setNumber,
			Globals g) {

		init(name, setNumber, g);
		for (int i = 0; i < yarr.length; i++) {
			data.addElement(new Datum(xarr[i], yarr[i], y2arr[i], setNumber, g));
		}
	}

	/**
	 * Constructor for label charts that use both X and Y. Sets initial Gc
	 * determined by set number. Also initializes labels for each data point.
	 * 
	 * @param name
	 *            Dataset name
	 * @param xarr
	 *            Array of X values
	 * @param yarr
	 *            Array of Y values
	 * @param label
	 *            Label values
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            This chart's Globals class
	 */
	public Dataset(String name, double xarr[], double yarr[], String label[], int setNumber,
			Globals g) {

		init(name, setNumber, g);
		for (int i = 0; i < yarr.length; i++) {
			data.addElement(new Datum(xarr[i], yarr[i], label[i], setNumber, g));
		}

		labels = label;
	}

	/**
	 * Constructor for charts that use X and Y values, but have no labels. Sets
	 * initial Gc determined by set number.
	 * 
	 * @param name
	 *            Dataset name
	 * @param xarr
	 *            Array of X values
	 * @param yarr
	 *            Array of Y values
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            This chart's Globals class
	 */
	public Dataset(String name, double xarr[], double yarr[], int setNumber, Globals g) {

		init(name, setNumber, g);
		for (int i = 0; i < xarr.length; i++) {
			data.addElement(new Datum(xarr[i], yarr[i], setNumber, g));
		}
	}

	/**
	 * Constructor for nonlabelled charts that use both X and Y observations.
	 * Sets a default initial Gc.
	 * 
	 * @param name
	 *            Dataset name
	 * @param xarr
	 *            Array of X values
	 * @param yarr
	 *            Array of Y values
	 * @param g
	 *            This chart's Globals class
	 */
	public Dataset(String name, double xarr[], double yarr[], Globals g) {

		init(name, -1, g);
		if ((xarr == null) || (yarr == null))
			return;
		for (int i = 0; i < xarr.length; i++) {
			data.addElement(new Datum(xarr[i], yarr[i], g));
		}

	}

	/**
	 * Constructor for label charts that use 1 data element. Sets initial Gc
	 * determined by set number. Also initializes the label.
	 * 
	 * @param name
	 *            Dataset name
	 * @param yarr
	 *            Array of chart data
	 * @param label
	 *            Label values
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            This chart's Globals class
	 */
	public Dataset(String name, double yarr[], String label[], int setNumber, Globals g) {

		init(name, setNumber, g);
		for (int i = 0; i < yarr.length; i++) {
			try {
				data.addElement(new Datum((double) i, yarr[i], label[i], setNumber, g));
			} catch (ArrayIndexOutOfBoundsException e) {
				data.addElement(new Datum((double) i, yarr[i], "", setNumber, g));
			}
		}

		labels = label;
	}

	/**
	 * Constructor for labelled charts that use only Y values. Sets the default
	 * initial Gc.
	 * 
	 * @param name
	 *            Dataset name
	 * @param yarr
	 *            Array of chart data
	 * @param label
	 *            Label values
	 * @param individual
	 *            Use unique color for each data element's Gc
	 */
	public Dataset(String name, double yarr[], String label[], boolean individual, Globals g) {

		init(name, 1, g); // uses a unique Gc for Datum
		for (int i = 0; i < yarr.length; i++) {
			try {
				data.addElement(new Datum(i, yarr[i], label[i], g));
			} catch (ArrayIndexOutOfBoundsException e) {
				data.addElement(new Datum(i, yarr[i], null, g));
			}
		}

		labels = label;
	}

	/**
	 * Constructor for charts that use only 1 data element (Y values). For
	 * example, this is appropriate for Pie charts. Sets an initial Gc
	 * determined by set number.
	 * 
	 * @param name
	 *            Dataset name
	 * @param yarr
	 *            Array of chart data
	 * @param setNumber
	 *            This Dataset's position in an array
	 * @param g
	 *            This chart's Globals class
	 */
	public Dataset(String name, double yarr[], int setNumber, Globals g) {

		init(name, setNumber, g);
		for (int i = 0; i < yarr.length; i++) {
			data.addElement(new Datum(i, yarr[i], null, setNumber, g));
		}
	}

	/**
	 * Constructor for nonlabelled charts that use only Y values. Sets the
	 * default initial Gc.
	 * 
	 * @param name
	 *            Dataset name
	 * @param yarr
	 *            Array of chart data
	 * @param individual
	 *            Use unique or random color for each data element
	 * @param g
	 *            this chart's Globals class
	 */
	public Dataset(String name, double yarr[], boolean individual, Globals g) {

		init(name, 1, g);
		for (int i = 0; i < yarr.length; i++) {
			data.addElement(new Datum((double) i, yarr[i], individual, g));
		}
	}

	public Dataset(String s, double[] x, double[] y, String[] labels2, int numberOfDatasets,
			Globals globals2, Tip[] tips) {
		this(s, x, y, labels2, numberOfDatasets, globals2);
		this.tips = tips;
	}

	/**
	 * Installs a Datum class into this Dataset. Also assigns this Dataset's
	 * Globals class to this Datum.
	 * 
	 * @param d
	 *            javachart.chart.Datum
	 */
	public void addDatum(Datum d) {
		data.addElement(d);
		d.setGlobals(globals);
	}

	/**
	 * Adds an observation to this Dataset. Creates a new Datum class containing
	 * the X, Y, and label values specified. Adds that class to the Dataset's
	 * data Vector.
	 * 
	 * @param x
	 *            double
	 * @param y
	 *            double
	 * @param l
	 *            java.lang.String
	 */
	public void addPoint(double x, double y, String l) {
		addDatum(new Datum(x, y, l, data.size(), globals));
	}

	/**
	 * Method
	 * 
	 * @return java.lang.Object
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * Prints out all values of X and Y.
	 */
	public void dump() {
		int i;

		for (i = 0; i < data.size(); i++) {
			System.out.println("X " + ((Datum) data.elementAt(i)).x + " Y "
					+ ((Datum) data.elementAt(i)).y + " Y2 " + ((Datum) data.elementAt(i)).y2);
		}
	}

	/**
	 * Returns the current Datum classes.
	 * 
	 * @return Vector containing Datum classes
	 * @uml.property name="data"
	 */
	public Vector getData() {
		return data;
	}

	/**
	 * returns a specified Datum
	 * 
	 * @return Vector containing this Dataset's Datum classes
	 */
	public Datum getDataElementAt(int where) {
		return (Datum) data.elementAt(where);
	}

	/**
	 * Returns this Dataset's Gc class
	 * 
	 * @return Graphic context
	 * @uml.property name="gc"
	 */
	public Gc getGc() {
		return gc;
	}

	/**
	 * Returns this Dataset's Globals class, which is presumably the same as the
	 * overall chart's Globals class.
	 * 
	 * @uml.property name="globals"
	 */
	public Globals getGlobals() {
		return globals;
	}

	/**
	 * Returns the current labelColor.
	 * 
	 * @return Current labelColor
	 * @uml.property name="labelColor"
	 */
	public Color getLabelColor() {
		return labelColor;
	}

	/**
	 * Returns the current labelFont.
	 * 
	 * @return Current labelFont
	 * @uml.property name="labelFont"
	 */
	public Font getLabelFont() {
		return labelFont;
	}

	/**
	 * Returns the name of current Dataset.
	 * 
	 * @return Dataset name
	 */
	public String getName() {
		return setName;
	}

	/**
	 * Returns all values of X.
	 * 
	 * @return Array of X values
	 */
	public synchronized double[] getXValues() {
		int i;
		double arr[];

		arr = new double[data.size()];
		for (i = 0; i < data.size(); i++)
			arr[i] = ((Datum) data.elementAt(i)).x;
		return arr;
	}

	/**
	 * Returns all values of Y2.
	 * 
	 * @return Array of Y2 values
	 */
	public synchronized double[] getY2Values() {
		int i;
		double arr[];

		arr = new double[data.size()];
		for (i = 0; i < data.size(); i++)
			arr[i] = ((Datum) data.elementAt(i)).y2;
		return arr;
	}

	/**
	 * Returns all values of Y3.
	 * 
	 * @return Array of Y3 values
	 */
	public synchronized double[] getY3Values() {
		int i;
		double arr[];

		arr = new double[data.size()];
		for (i = 0; i < data.size(); i++)
			arr[i] = ((Datum) data.elementAt(i)).y3;
		return arr;
	}

	/**
	 * Returns all values of Y.
	 * 
	 * @return Array of Y values
	 */
	public synchronized double[] getYValues() {
		int i;
		double arr[];

		arr = new double[data.size()];
		for (i = 0; i < data.size(); i++)
			arr[i] = ((Datum) data.elementAt(i)).y;
		return arr;
	}

	private void init(String name, int setNumber, Globals g) {
		setName = name;
		globals = g;
		if (setNumber == -1)
			gc = new Gc(g);
		else
			gc = new Gc(setNumber, g);
	}

	private double max(double in, double cmp) {
		if (cmp == Datum.DEFAULT)
			return in;
		else
			return Math.max(in, cmp);
	}

	/**
	 * Returns the maximum value of X in this dataset.
	 * 
	 * @return Maximum value of X
	 */
	public synchronized double maxX() {
		int i, count;
		double max;

		count = data.size();
		if (count == 0)
			return 0.0;
		max = ((Datum) data.elementAt(0)).x;
		for (i = 1; i < count; i++)
			max = max(max, ((Datum) data.elementAt(i)).x);
		return max;
	}

	/**
	 * Returns the maximum value of Y in this dataset.
	 * 
	 * @return Maximum value of Y
	 */
	public synchronized double maxY() {
		int i, count;
		double max;

		count = data.size();
		if (count == 0)
			return 0.0;
		max = ((Datum) data.elementAt(0)).y;
		for (i = 1; i < count; i++)
			max = max(max, ((Datum) data.elementAt(i)).y);
		return max;
	}

	/**
	 * Returns the maximum value of Y2 in this dataset.
	 * 
	 * @return Maximum value of Y2
	 */
	public synchronized double maxY2() {
		int i, count;
		double max;

		count = data.size();
		if (count == 0)
			return 0.0;
		max = ((Datum) data.elementAt(0)).y2;
		for (i = 1; i < count; i++)
			max = max(max, ((Datum) data.elementAt(i)).y2);
		return max;
	}

	/**
	 * Returns the maximum value of Y3 in this dataset.
	 * 
	 * @return Maximum value of Y3
	 */
	public synchronized double maxY3() {
		int i, count;
		double max;

		count = data.size();
		if (count == 0)
			return 0.0;
		max = ((Datum) data.elementAt(0)).y3;
		for (i = 1; i < count; i++)
			max = max(max, ((Datum) data.elementAt(i)).y3);
		return max;
	}

	// utility methods to discover real mins among un-set y2, y3, etc.
	private double min(double in, double cmp) {
		if (cmp == Datum.DEFAULT) // default value
			return in;
		else if (in == Datum.DEFAULT)
			return cmp; // the real minimum - cmp is non-default, in is default
		else
			return Math.min(in, cmp);
	}

	/**
	 * Returns the minimum value of X in this dataset.
	 * 
	 * @return Minimum value of X
	 */
	public synchronized double minX() {
		int i, count;
		double min;

		min = ((Datum) data.elementAt(0)).x;
		count = data.size();
		for (i = 1; i < count; i++) {
			System.out.println(jatools.formatter.DateFormat.format(new Date(
					(long) ((Datum) data.elementAt(i)).x), "yyyy-MM-dd hh-mm-ss"));
			min = min(min, ((Datum) data.elementAt(i)).x);
		}
		return min;
	}

	/**
	 * Returns the minimum value of Y in this dataset.
	 * 
	 * @return Minimum value of Y
	 */
	public synchronized double minY() {
		int i, count;
		double min;

		min = ((Datum) data.elementAt(0)).y;
		count = data.size();
		for (i = 1; i < count; i++)
			min = min(min, ((Datum) data.elementAt(i)).y);
		return min;
	}

	/**
	 * Returns the minimum value of Y2 in this dataset.
	 * 
	 * @return Minimum value of Y2
	 */
	public synchronized double minY2() {
		int i, count;
		double min;

		min = ((Datum) data.elementAt(0)).y2;
		count = data.size();
		for (i = 1; i < count; i++)
			min = min(min, ((Datum) data.elementAt(i)).y2);
		return min;
	}

	/**
	 * Returns the minimum value of Y3 in this dataset.
	 * 
	 * @return Minimum value of Y3
	 */
	public synchronized double minY3() {
		int i, count;
		double min;

		min = ((Datum) data.elementAt(0)).y3;
		count = data.size();
		for (i = 1; i < count; i++)
			min = min(min, ((Datum) data.elementAt(i)).y3);
		return min;
	}

	/**
	 * Replaces labels in your dataset.
	 * 
	 * @param xarr
	 *            Array of new X values
	 */
	public synchronized void replaceLabels(String labels[]) {
		int i, count;
		count = Math.min(data.size(), labels.length);

		for (i = 0; i < count; i++) {
			if (labels[i] != null)
				((Datum) data.elementAt(i)).label = labels[i];
		}
	}

	/**
	 * Replaces X values in your dataset. If the X value array is larger than
	 * the current size of this Dataset, the Dataset is extended to accomodate
	 * the additional entries. If the X value array is smaller than the size of
	 * the data Vector, the Vector is trimmed to the size of the X value array.
	 * If the new array is smaller than the current array, the extra entries at
	 * the end of the current array are not changed.
	 * 
	 * @param xarr
	 *            Array of new X values
	 */
	public synchronized void replaceXData(double xarr[]) {
		int i, count;

		count = data.size();
		if (xarr.length > count) {
			for (i = 0; i < count; i++) {
				((Datum) data.elementAt(i)).x = xarr[i];
			}
			for (i = count; i < xarr.length; i++) {
				data.addElement(new Datum(xarr[i], 0., globals));
			}
		} else {
			while (data.size() > xarr.length) {
				data.removeElementAt(data.size() - 1);
			}
			for (i = 0; i < xarr.length; i++) {
				((Datum) data.elementAt(i)).x = xarr[i];
			}
		}
	}

	/**
	 * Replaces Y2 values in your dataset. If the Y value array is larger than
	 * the current size of this Dataset, the Dataset is extended to accomodate
	 * the additional entries. If the new array is smaller than the current
	 * array, the extra entries at the end of the current array are not changed.
	 * 
	 * @param yarr
	 *            Array of new Y2 values
	 */
	public synchronized void replaceY2Data(double yarr[]) {
		int i, count;

		count = data.size();
		if (yarr.length > count) {
			for (i = 0; i < count; i++) {
				((Datum) data.elementAt(i)).y2 = yarr[i];
			}
			for (i = count; i < yarr.length; i++)
				data.addElement(new Datum(0.0, 0.0, yarr[i], 0.0, globals));
		} else
			while (data.size() > yarr.length) {
				data.removeElementAt(data.size() - 1);
			}
		for (i = 0; i < yarr.length; i++) {
			((Datum) data.elementAt(i)).y2 = yarr[i];
		}
	}

	/**
	 * Replaces Y3 values in your dataset. If the Y value array is larger than
	 * the current size of this Dataset, the Dataset is extended to accomodate
	 * the additional entries. If the new array is smaller than the current
	 * array, the extra entries at the end of the current array are not changed.
	 * 
	 * @param yarr
	 *            Array of new Y3 values
	 */
	public synchronized void replaceY3Data(double yarr[]) {
		int i, count;
		count = data.size();
		if (yarr.length > count) {
			for (i = 0; i < count; i++) {
				((Datum) data.elementAt(i)).y3 = yarr[i];
			}
			for (i = count; i < yarr.length; i++)
				data.addElement(new Datum(0., 0., 0., yarr[i], globals));
		} else
			while (data.size() > yarr.length) {
				data.removeElementAt(data.size() - 1);
			}
		for (i = 0; i < yarr.length; i++) {
			((Datum) data.elementAt(i)).y3 = yarr[i];
		}
	}

	/**
	 * Replaces Y values in your dataset. If the Y value array is larger than
	 * the current size of this Dataset, the Dataset is extended to accomodate
	 * the additional entries. If the new array is smaller than the current
	 * array, the extra entries at the end of the current array are not changed.
	 * 
	 * @param yarr
	 *            Array of new Y values
	 */
	public synchronized void replaceYData(double yarr[]) {
		int i, count;

		count = data.size();
		if (yarr.length > count) {
			for (i = 0; i < count; i++) {
				((Datum) data.elementAt(i)).y = yarr[i];
			}
			for (i = count; i < yarr.length; i++) {
				data.addElement(new Datum(0., yarr[i], 0., 0., globals));
			}
		} else
			while (data.size() > yarr.length) {
				data.removeElementAt(data.size() - 1);
			}
		for (i = 0; i < yarr.length; i++) {
			((Datum) data.elementAt(i)).y = yarr[i];
		}
	}

	/**
	 * Installs a new Vector of data classes
	 * 
	 * @param d
	 *            data vector
	 * @uml.property name="data"
	 */
	public void setData(Vector d) {
		data = d;
		for (int i = 0; i < d.size(); i++) {
			((Datum) d.elementAt(i)).setGlobals(globals);
		}
	}

	/**
	 * Installs a new Gc class for this Dataset
	 * 
	 * @param g
	 *            New graphic context
	 * @uml.property name="gc"
	 */
	public void setGc(Gc g) {
		gc = g;
		g.globals = globals;
	}

	/**
	 * sets this Dataset's Globals class, which is presumably the same as the
	 * overall chart's Globals class. Also installs this Globals class into all
	 * Datum classes contained within this Dataset.
	 * 
	 * @uml.property name="globals"
	 */
	public void setGlobals(Globals g) {
		globals = g;
		for (int i = 0; i < data.size(); i++) {
			Datum d = getDataElementAt(i);
			d.setGlobals(g);
		}
		gc.globals = g;
	}

	/**
	 * Sets a new labelColor.
	 * 
	 * @param New
	 *            labelColor
	 * @uml.property name="labelColor"
	 */
	public void setLabelColor(Color c) {
		labelColor = c;
	}

	/**
	 * Sets a new labelFont.
	 * 
	 * @param New
	 *            labelFont
	 * @uml.property name="labelFont"
	 */
	public void setLabelFont(Font f) {
		labelFont = f;
	}

	/**
	 * Rename the current Dataset.
	 * 
	 * @param s
	 *            New name for Dataset
	 */
	public void setName(String s) {
		setName = s;
	}

	/**
	 * @return 返回 labels。
	 * @uml.property name="labels"
	 */
	public String[] getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            要设置的 labels。
	 * @uml.property name="labels"
	 */
	public void setLabels(String[] l) {
		labels = l;
	}

	public Tip[] getTips() {
		return tips;
	}
}