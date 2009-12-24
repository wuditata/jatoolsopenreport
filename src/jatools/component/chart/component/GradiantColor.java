package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;


public class GradiantColor implements FillStyleInterface {
	
	int style;
	Color masterColor;
	Color secondColor;
	
	private String label = "½¥±äÉ«";
	
	public Color getMasterColor() {
		return masterColor;
	}

	public void setMasterColor(Color masterColor) {
		this.masterColor = masterColor;
	}

	public Color getSecondColor() {
		return secondColor;
	}

	public void setSecondColor(Color secondColor) {
		this.secondColor = secondColor;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public Component createLabel(Dimension d) {
		return new HeaderLabel(createIcon(new Dimension(ICON_WIDTH, ICON_HEIGHT)), label, d);
	}

	public Icon createIcon(Dimension d){
		GradiantIcon icon = new GradiantIcon(d);
		icon.setStyle(this);
		return icon;
	}
	
	public void setToGc(Gc gc) {
		gc.setFillStyle(Gc.FILL_GRADIENT);
		gc.setGradient(style);
		gc.setFillColor(masterColor);
		gc.setSecondaryFillColor(secondColor);
	}

	public void getFromGc(Gc gc) {
		style = gc.getGradient();
		masterColor = gc.getFillColor();
		secondColor = gc.getSecondaryFillColor();
	}

	public int getType() {
		return FILL_GRADIENT;
	}

}
