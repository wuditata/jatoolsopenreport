/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.customizer.ChangeListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;




public class RangeComponent extends JPanel implements javax.swing.event.ChangeListener {

	ChangeListener	parent;
	JLabel labelL;
	
	JLabel valueL;
	JSlider		slider;
	
	public RangeComponent(){
		this("", 0);
	}
	//gets/sets ranges from 0 to 1
  	public RangeComponent (	String	label,
				double	start){

  		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.BOTH;
		labelL = new JLabel(label);
		labelL.setPreferredSize(CommonFinal.LABEL_SIZE);
		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(start*100.));
		slider.setPreferredSize(CommonFinal.PREFERRED_SIZE);
		slider.addChangeListener(this);
		valueL = new JLabel("blank");
		valueL.setPreferredSize(CommonFinal.SUFFIX_LABEL_SIZE);
		valueL.setHorizontalAlignment(SwingConstants.RIGHT);
		setLabelText((int)(start*100.));
		
		add(labelL, gbc);
		gbc.weightx = 1;
		add(slider, gbc);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(valueL, gbc);
	}

	public double getValue(){
		return (double)(slider.getValue())/100.;
	}
	private void setLabelText(int i) {
		/*if(i==100)
			valueL.setText("1.0");
		else if (i < 10)
			valueL.setText("0.0" + String.valueOf(i));
		else
			valueL.setText("0." + String.valueOf(i));*/
		valueL.setText(String.valueOf(i) + "%");
	}
	public void setValue(double d){
		slider.removeChangeListener(this);
		slider.setValue((int) (d*100));
		slider.addChangeListener(this);
		setLabelText(slider.getValue());
	}
	public void addChangeListener(ChangeListener l){
		parent = l;
	}
	public void removeChangeListener(){
		parent = null;
	}
	public void stateChanged(ChangeEvent e) {
		setLabelText(slider.getValue());
		if(parent!=null)
			parent.fireChange(null);
	}
	
	public void setEnabled(boolean yesno){
		super.setEnabled(yesno);
		labelL.setEnabled(yesno);
		valueL.setEnabled(yesno);
		slider.setEnabled(yesno);
	}
}