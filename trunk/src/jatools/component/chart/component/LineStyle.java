package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;


public class LineStyle implements FillStyleInterface {

	Color color = Color.red;

	int lineStyle = Gc.LINE_SOLID;

	int lineWidth = 1;

	int markerSize = 10;

	int markerStyle = Gc.MK_NONE;

	int iconType = 0;

	public boolean full;

	public LineStyle(boolean full) {
		this.full = full;
	}

	public Component createLabel(Dimension d) {
		return new HeaderLabel(this, d);
	}

	public Icon createIcon(Dimension d) {
		AbstractColorIcon icon;
		if (iconType == 0) {
			icon = new LineIcon(d);
		} else {
			icon = new MarkerIcon(d);
		}
		icon.setStyle(this);
		return icon;
	}

	public int getType() {
		return -1;
	}

	public void setToGc(Gc gc) {
		gc.setLineColor(color);
		gc.setLineStyle(lineStyle);
		gc.setLineWidth(lineWidth);
		gc.setFillColor(color);
		gc.setMarkerSize(markerSize);
		gc.setMarkerStyle(markerStyle);
	}

	public void getFromGc(Gc gc) {
		color = gc.getLineColor();
		lineStyle = gc.getLineStyle();
		lineWidth = gc.getLineWidth();
		markerSize = gc.getMarkerSize();
		markerStyle = gc.getMarkerStyle();
	}

	class HeaderLabel extends JLabel {

		LineStyle lineStyle;

		int width;

		int height;

		public HeaderLabel(LineStyle style, Dimension d) {
			lineStyle = style;
			width = d.width;
			height = d.height;
		}

		protected void paintComponent(Graphics g) {
			FontMetrics fm = g.getFontMetrics();
			lineStyle.iconType = 0;
			Icon lineIcon = lineStyle.createIcon(new Dimension(ICON_WIDTH,
					ICON_HEIGHT));
			lineStyle.iconType = 1;
			Icon markerIcon = lineStyle.createIcon(new Dimension(ICON_WIDTH,
					ICON_HEIGHT));
			String llabel = null;
			switch (lineStyle.lineStyle) {
			case LINE_SOLID:
				llabel = "实线";
				break;
			case LINE_DASH:
				llabel = "虚线";
				break;
			case LINE_DOT:
				llabel = "点线";
				break;
			case LINE_DOT_DASH:
				llabel = "点划线";
				break;
			}
			String mlabel = null;
			switch (lineStyle.markerStyle) {
			case Gc.MK_NONE:
				mlabel = "无";
				break;
			case Gc.MK_SQUARE:
				mlabel = "方形";
				break;
			case Gc.MK_DIAMOND:
				mlabel = "菱形";
				break;
			case Gc.MK_TRIANGLE:
				mlabel = "三角形";
				break;
			case Gc.MK_CIRCLE:
				mlabel = "圆形";
				break;
			}

			lineIcon.paintIcon(this, g, 3, 3);
			g.setColor(Color.BLACK);
			g.drawString(llabel, ICON_WIDTH + 6, height / 2 + 3);
			if (full) {
				int fw = fm.stringWidth(llabel);
				markerIcon.paintIcon(this, g, ICON_WIDTH + 9 + fw, 3);
				g.setColor(Color.BLACK);
				g.drawString(mlabel, ICON_WIDTH * 2 + 12 + fw, height / 2 + 3);
			}
		}

	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getLineStyle() {
		return lineStyle;
	}

	public void setLineStyle(int lineStyle) {
		this.lineStyle = lineStyle;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int width) {
		this.lineWidth = width;
	}

	public int getMarkerSize() {
		return markerSize;
	}

	public void setMarkerSize(int markerSize) {
		this.markerSize = markerSize;
	}

	public int getMarkerStyle() {
		return markerStyle;
	}

	public void setMarkerStyle(int markerStyle) {
		this.markerStyle = markerStyle;
	}

	public int getIconType() {
		return iconType;
	}

	public void setIconType(int iconType) {
		this.iconType = iconType;
	}

}
