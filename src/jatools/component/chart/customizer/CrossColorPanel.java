package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.GradiantColor;
import jatools.component.chart.component.GradiantIcon;
import jatools.component.chart.component.SingleColor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JToggleButton;


public class CrossColorPanel extends Dialog implements ActionListener {
	ColorComponent bmaster, bsecond;

	Gc gc;
	
	JToggleButton[] buttons;
	GradiantIcon[] icons;
	GradiantIcon icon;

	public void show() {
		gc.setFillStyle(Gc.FILL_GRADIENT);
		setVals();
		super.show();
		fireChange(null);
	}

	public void setObject(Object object) {
		gc = (Gc) object;
		initCustomizer();
	}
	
	public void getVals() {
		gc.setFillColor(((SingleColor)bmaster.getValue()).getColor());
		gc.setSecondaryFillColor(((SingleColor)bsecond.getValue()).getColor());
		if (buttons[0].isSelected()) {
			gc.setGradient(GcHelper.GRAD_LEFT_RIGHT);
		} else if (buttons[1].isSelected()) {
			gc.setGradient(GcHelper.GRAD_TOP_BOTTOM);
		} else if (buttons[2].isSelected()) {
			gc.setGradient(GcHelper.GRAD_TOP_BOTTOM_MIRROR);
		} else if (buttons[3].isSelected()) {
			gc.setGradient(GcHelper.GRAD_LEFT_RIGHT_MIRROR);
		}
	}

	public void setVals() {
		bmaster.setValue(new SingleColor(gc.getFillColor()));
		bsecond.setValue(new SingleColor(gc.getSecondaryFillColor()));
		if (gc.getGradient() == GcHelper.GRAD_LEFT_RIGHT) {
			buttons[0].setSelected(true);
		} else if (gc.getGradient() == GcHelper.GRAD_TOP_BOTTOM) {
			buttons[1].setSelected(true);
		} else if (gc.getGradient() == GcHelper.GRAD_TOP_BOTTOM_MIRROR) {
			buttons[2].setSelected(true);
		} else if (gc.getGradient() == GcHelper.GRAD_LEFT_RIGHT_MIRROR) {
			buttons[3].setSelected(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		fireChange(null);
	}

	protected void initCustomizer() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		setBorder(CommonFinal.EMPTY_BORDER);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.BOTH;

		bmaster = new ColorComponent("主要颜色", null);
		bmaster.addChangeListener(this);
		bsecond = new ColorComponent("次要颜色", null);
		bsecond.addChangeListener(this);

		JLabel lmode = new JLabel("样式");
		ButtonGroup group = new ButtonGroup();
		Box box = Box.createHorizontalBox();
		buttons = new JToggleButton[4];
		icons = new GradiantIcon[4];
		GradiantColor grad = new GradiantColor();
		grad.setMasterColor(new Color(153, 153, 255));
		grad.setSecondColor(Color.white);
		for(int i = 0; i < 4; i++){
			grad.setStyle(3 - i);
			icons[i] = (GradiantIcon) grad.createIcon(CommonFinal.STYLE_BUTTON_SIZE);
			buttons[i] = new JToggleButton(icons[i]);
			buttons[i].setMargin(new Insets(0, 0, 0, 0));
			buttons[i].addActionListener(this);
			group.add(buttons[i]);
			box.add(Box.createHorizontalStrut(5));
			box.add(buttons[i]);
		}

		box.add(Box.createHorizontalStrut(5));

		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(bmaster, gbc);

		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(bsecond, gbc);

		gbc.gridwidth = 1;
		add(lmode, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(box, gbc);
		
		gbc.weighty = 1;
		add(Box.createGlue(), gbc);
	}

}
