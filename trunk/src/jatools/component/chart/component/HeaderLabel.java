package jatools.component.chart.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

public class HeaderLabel extends JLabel {
	Icon icon;
	String label;
	int width, height;
	public HeaderLabel(Icon icon, String label, Dimension d){
		this.icon = icon;
		this.label = label;
		width = d.width;
		height = d.height;
	}
	
	public void paintComponent(Graphics g1){
		Graphics g = g1.create();
		icon.paintIcon(this, g, 3, 3);
		g.setColor(Color.BLACK);
		g.drawString(label, icon.getIconWidth() + 6, height / 2 + 3);
	}
}
