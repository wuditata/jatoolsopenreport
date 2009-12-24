package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class LineIcon extends AbstractColorIcon {
	
	static float dashArray[] = { 5.0f };

	static float dotArray[] = { 2.0f };

	static float dotDashArray[] = { 2.0f, 2.0f, 5.0f, 2.0f };

	float lineStyles[][] = { dashArray, dotArray, dotDashArray };
	
	Color color = Color.RED;
	int lineStyle = Gc.LINE_SOLID;
	int lineWidth = 1;

	public LineIcon(int width, int height){
		super(width, height);
	}
	
	public LineIcon(Dimension d){
		super(d.width, d.height);
	}
	
	protected void setStyle(FillStyleInterface style) {
		color = ((LineStyle)style).color;
		lineStyle = ((LineStyle)style).lineStyle;
		lineWidth = ((LineStyle)style).lineWidth;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Stroke stroke;
		Graphics2D g2d = (Graphics2D) g.create();
		if(color == null) return;
		if(color.equals(Color.WHITE)){
			g2d.setColor(Color.DARK_GRAY);
		}else{
			g2d.setColor(Color.WHITE);
		}
		g2d.fillRect(x, y, width, height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y, width, height);
		g2d.setColor(color);
			try {
				if (lineStyle != Gc.LINE_SOLID) {
					stroke = new BasicStroke((float) lineWidth,
							BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
							10.0f, lineStyles[lineStyle], 0.0f);
				} else
					stroke = new BasicStroke((float) lineWidth);
			} catch (Exception e) { // no line style?
				stroke = new BasicStroke((float) lineWidth);
			}

		g2d.setStroke(stroke);
		g2d.drawLine(x + 2, y + height / 2, x + width - 3, y + height / 2);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
		JLabel label = new JLabel();
		label.setIcon(new LineIcon(100, 100));
		frame.getContentPane().add(label);
		frame.setVisible(true);
	}

}
