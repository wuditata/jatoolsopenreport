package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;


public class SingleColor implements FillStyleInterface {

	Color color;
	
	private String label = "´¿É«";
	
	public SingleColor(Color color){
		this.color = color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Component createLabel(Dimension d) {
		return new HeaderLabel(createIcon(new Dimension(ICON_WIDTH, ICON_HEIGHT)), label, d);
	}
	
	public Icon createIcon(Dimension d){
		ColorIcon icon = new ColorIcon(d);
		icon.setStyle(this);
		return icon;
	}
	
	public void setToGc(Gc gc) {
		gc.setFillStyle(Gc.FILL_SOLID);
		gc.setFillColor(color);
	}

	public void getFromGc(Gc gc) {
		color = gc.getFillColor();
	}

	public int getType() {
		return FILL_SOLID;
	}

}
