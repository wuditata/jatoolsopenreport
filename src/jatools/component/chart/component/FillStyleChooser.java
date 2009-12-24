package jatools.component.chart.component;

import jatools.component.chart.customizer.ColorStyleDialog;
import jatools.component.chart.customizer.LineAndSignDialog;

import java.awt.Component;

import javax.swing.JDialog;


public class FillStyleChooser {
	FillStyleInterface style;

	public FillStyleInterface getStyle() {
		return style;
	}

	public void setStyle(FillStyleInterface style) {
		this.style = style;
	}

	public static FillStyleInterface showDialog(Component c, String title,
			FillStyleInterface init, int type) {
		FillStyleChooser chooser = new FillStyleChooser();
		JDialog dialog = null;
		if (type == 0) {
			dialog = new ColorStyleDialog((JDialog) c, title, chooser, init,
					true);
		} else if (type == 1) {
			dialog = new LineAndSignDialog(c, title, chooser, init, true);
		}
		dialog.setLocationRelativeTo(c);
		dialog.show();
		return chooser.getStyle();
	}
}
