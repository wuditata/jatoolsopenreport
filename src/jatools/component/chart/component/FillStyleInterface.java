package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;


public interface FillStyleInterface {
	public final static int FILL_SOLID = 0;
	public final static int FILL_GRADIENT = 1;
	public final static int FILL_TEXTURE = 2;
	public final static int FILL_IMAGE = 3;
	
	public final static int LINE_SOLID = -1;
	public final static int LINE_DASH = 0;
	public final static int LINE_DOT = 1;
	public final static int LINE_DOT_DASH = 2;
	
	public final static int MK_NONE = -1;
	public final static int MK_SQUARE = 0;
	public final static int MK_DIAMOND = 1;
	public final static int MK_CIRCLE = 2;
	public final static int MK_TRIANGLE = 3;
	
	public final static int ICON_WIDTH = 20;
	public final static int ICON_HEIGHT = 10;
	
	public Component createLabel(Dimension d);
	
	public Icon createIcon(Dimension d);
	
	public int getType();
	
	public void setToGc(Gc gc);
	
	public void getFromGc(Gc gc);
}
