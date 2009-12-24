package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;



public class GradiantIcon extends AbstractColorIcon {
	
	Color masterColor = Color.WHITE;
	Color secondColor = Color.WHITE;
	int style = GcHelper.GRAD_LEFT_RIGHT;
	
	public GradiantIcon(int width, int height){
		super(width, height);
	}
	
	public GradiantIcon(Dimension dimension){
		super(dimension.width, dimension.height);
	}
	
	protected void setStyle(FillStyleInterface style){
		this.style = ((GradiantColor)style).style;
		this.masterColor = ((GradiantColor)style).masterColor;
		this.secondColor = ((GradiantColor)style).secondColor;
	}

	public void paintIcon(Component c, Graphics g1, int x, int y) {
		Graphics g = g1.create();
		if(masterColor != Gc.TRANSPARENT){
			switch(style){
			case GcHelper.GRAD_LEFT_RIGHT:
				doHorizontal(c, g, x, y);
				break;
			case GcHelper.GRAD_TOP_BOTTOM:
				doVertical(c, g, x, y);
				break;
			case GcHelper.GRAD_LEFT_RIGHT_MIRROR:
				doLeftRight(c, g, x, y);
				break;
			case GcHelper.GRAD_TOP_BOTTOM_MIRROR:
				doTopBottom(c, g, x, y);
				break;
			}
		}
		g.setColor(Color.black);
		g.drawRect(x, y, getIconWidth(), getIconHeight());		
		g.dispose();
	}
	
	private void doHorizontal(Component c, Graphics g, int x, int y){
		int x2 = x + width;
		int y2 = y + height;
		double mr = masterColor.getRed(), mg = masterColor.getGreen(), mb = masterColor.getBlue();
		double sr = secondColor.getRed(), sg = secondColor.getGreen(), sb = secondColor.getBlue();
		double rstep = (mr - sr) * 1.0 / width, gstep = (mg - sg) * 1.0 / width, bstep = (mb - sb) * 1.0 / width;
		for(int i = x; i < x2; i++){
			g.setColor(new Color((int)mr, (int)mg, (int)mb));
			g.drawLine(i, y, i, y2);
			mr = mr - rstep;mg = mg - gstep;mb = mb - bstep;			
		}	
	}
	
	private void doVertical(Component c, Graphics g, int x, int y){
		int x2 = x + width;
		int y2 = y + height;
		double mr = masterColor.getRed(), mg = masterColor.getGreen(), mb = masterColor.getBlue();
		double sr = secondColor.getRed(), sg = secondColor.getGreen(), sb = secondColor.getBlue();
		double rstep = (mr - sr) / height, gstep = (mg - sg) / height, bstep = (mb - sb) / height;
		for(int i = y; i < y2; i++){
			g.setColor(new Color((int)mr, (int)mg, (int)mb));
			g.drawLine(x, i, x2, i);
			mr -= rstep; mg -= gstep; mb -= bstep;			
		}
	}
	
	private void doLeftRight(Component c, Graphics g, int x, int y){
		int x2 = x + width;
		int y2 = y + height;
		double mr = masterColor.getRed(), mg = masterColor.getGreen(), mb = masterColor.getBlue();
		double sr = secondColor.getRed(), sg = secondColor.getGreen(), sb = secondColor.getBlue();
		double rstep = (mr - sr) * 2.0 / width, gstep = (mg - sg) * 2.0 / width, bstep = (mb - sb) * 2.0 / width;
		for(int i = x; i < x2; i++){
			g.setColor(new Color((int)mr, (int)mg, (int)mb));
			g.drawLine(i, y, i, y2);
			if(i < ((x + x2) / 2)){
				mr -= rstep; mg -= gstep; mb -= bstep;
			}else{
				mr += rstep; mg += gstep; mb += bstep;
			}
		}
	}
	
	private void doTopBottom(Component c, Graphics g, int x, int y){
		int x2 = x + width;
		int y2 = y + height;
		double mr = masterColor.getRed(), mg = masterColor.getGreen(), mb = masterColor.getBlue();
		double sr = secondColor.getRed(), sg = secondColor.getGreen(), sb = secondColor.getBlue();
		double rstep = (mr - sr) * 2.0 / height, gstep = (mg - sg) * 2.0 / height, bstep = (mb - sb) * 2.0 / height;
		for(int i = y; i < y2; i++){
			g.setColor(new Color((int)mr, (int)mg, (int)mb));
			g.drawLine(x, i, x2, i);
			if(i < ((y + y2) / 2)){
				mr -= rstep; mg -= gstep; mb -= bstep;
			}else{
				mr += rstep; mg += gstep; mb += bstep;
			}
		}
	}
}
