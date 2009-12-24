package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.component.ColorIcon;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class PreviewPanel extends JPanel {
	JLabel preview;
	Icon icon;
	public PreviewPanel(){
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.BOTH ;
        gbc.weightx=1;
        gbc.weighty = 1;
		setLayout(gbl);
		setBorder(new TitledBorder("‘§¿¿"));
		gbc.weightx = 0;
		gbc.weighty = 0;
		icon = new ColorIcon(CommonFinal.VIEW_ICON_SIZE);
		preview = new JLabel();
		preview.setIcon(icon);
		add(preview, gbc);
	}

	public void setIcon(Icon icon){
		preview.setIcon(icon);
		preview.repaint();
	}
}
