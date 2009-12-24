package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.SingleColor;
import jatools.component.chart.component.TextureColor;
import jatools.component.chart.component.TextureIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JToggleButton;



public class CrossLinePanel extends Dialog implements ActionListener {
	ColorComponent bmaster, bsecond;

	Gc gc;
	
	JToggleButton[] buttons;
	TextureIcon[] icons;
	TextureIcon icon;
	public CrossLinePanel(){

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
		buttons = new JToggleButton[5];
		icons = new TextureIcon[5];
		TextureColor text = new TextureColor();
		text.setMasterColor(new Color(153, 153, 255));
		text.setSecondColor(Color.white);
		for(int i = 0; i < 5; i++){
			text.setStyle(i);
			icons[i] = (TextureIcon) text.createIcon(new Dimension(CommonFinal.STYLE_BUTTON_SIZE));
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

	public void show() {
		gc.setFillStyle(Gc.FILL_TEXTURE);
		setVals();
		super.show();
		fireChange(null);
	}
	
	public void setObject(Object object) {
		gc = (Gc) object;
	}

	public void getVals() {
		gc.setFillColor(((SingleColor)bmaster.getValue()).getColor());
		gc.setSecondaryFillColor(((SingleColor)bsecond.getValue()).getColor());
		if (buttons[0].isSelected()) {
			gc.setTexture(GcHelper.TEXTURE_H_STRIPE);
		} else if (buttons[1].isSelected()) {
			gc.setTexture(GcHelper.TEXTURE_V_STRIPE);
		} else if (buttons[2].isSelected()) {
			gc.setTexture(GcHelper.TEXTURE_DOWN_STRIPE);
		} else if (buttons[3].isSelected()) {
			gc.setTexture(GcHelper.TEXTURE_UP_STRIPE);
		}else if(buttons[4].isSelected()){
			gc.setTexture(GcHelper.TEXTURE_CROSS_STRIPE);
		}
	}

	public void setVals() {
		bmaster.setValue(new SingleColor(gc.getFillColor()));
		bsecond.setValue(new SingleColor(gc.getSecondaryFillColor()));
		if (gc.getTexture() == GcHelper.TEXTURE_H_STRIPE) {
			buttons[0].setSelected(true);
		} else if (gc.getTexture() == GcHelper.TEXTURE_V_STRIPE) {
			buttons[1].setSelected(true);
		} else if (gc.getTexture() == GcHelper.TEXTURE_DOWN_STRIPE) {
			buttons[2].setSelected(true);
		} else if (gc.getTexture() == GcHelper.TEXTURE_UP_STRIPE) {
			buttons[3].setSelected(true);
		} else if(gc.getTexture() == GcHelper.TEXTURE_CROSS_STRIPE){
			buttons[4].setSelected(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		fireChange(null);
	}
}
