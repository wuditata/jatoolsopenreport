package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;



public class MarkerIcon extends AbstractColorIcon {

	int size = 10;

	int style = Gc.MK_DIAMOND;
	
	Color color = Color.red;

	public MarkerIcon(int width, int height) {
		super(width, height);
	}

	public MarkerIcon(Dimension d) {
		super(d.width, d.height);
	}

	protected void setStyle(FillStyleInterface style) {
		size = ((LineStyle) style).markerSize;
		this.style = ((LineStyle) style).markerStyle;
		this.color = ((LineStyle)style).color;
	}

	public void paintIcon(Component c, Graphics g1, int x, int y) {
		
		Graphics g = g1.create();
		if(color == null)
			return;
		if(color.equals(Color.WHITE)){
			g.setColor(Color.DARK_GRAY);
		}else{
			g.setColor(Color.WHITE);
		}
		g.fillRect(x, y, width, height);
		g.setColor(color);
		int xc = x + width / 2;
		int yc = y + height / 2;
		int halfSize = height / 4;
		switch (style) {
		case Gc.MK_SQUARE: {
			g.fillRect(xc - halfSize, yc - halfSize, height / 2, height / 2);
			break;
		}
		case Gc.MK_DIAMOND: {
			int xArr[] = new int[4];
			int yArr[] = new int[4];
			xArr[0] = xc - halfSize;
			yArr[0] = yc;
			xArr[1] = xc;
			yArr[1] = yc + halfSize;
			xArr[2] = xc + halfSize;
			yArr[2] = yc;
			xArr[3] = xc;
			yArr[3] = yc - halfSize;
			g.fillPolygon(xArr, yArr, 4);
			break;
		}
		case Gc.MK_CIRCLE: {
			g.fillOval(xc - halfSize, yc - halfSize, height / 2, height / 2);
			break;
		}
		case Gc.MK_TRIANGLE: {
			int xArr[] = new int[3];
			int yArr[] = new int[3];
			xArr[0] = xc - halfSize;
			yArr[0] = yc + halfSize;
			xArr[1] = xc;
			yArr[1] = yc - halfSize;
			xArr[2] = xc + halfSize;
			yArr[2] = yc + halfSize;
			g.fillPolygon(xArr, yArr, 3);
		}
		}
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
}