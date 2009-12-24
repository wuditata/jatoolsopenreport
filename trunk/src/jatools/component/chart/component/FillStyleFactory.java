package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;



public class FillStyleFactory {
	public static FillStyleInterface createFillStyle(Gc gc){
		FillStyleInterface style = null;
		if(gc.getFillStyle() == Gc.FILL_SOLID){
			style = new SingleColor(Color.WHITE);
		}else  if(gc.getFillStyle() == Gc.FILL_GRADIENT){
			style = new GradiantColor();
		}else if(gc.getFillStyle() == Gc.FILL_TEXTURE){
			style = new TextureColor();
		}
		style.getFromGc(gc);
		return style;
	}
}
