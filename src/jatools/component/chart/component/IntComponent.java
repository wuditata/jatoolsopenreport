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




public class IntComponent extends JPanel implements javax.swing.event.ChangeListener {

	
	ChangeListener	parent;
	JLabel labelL;
	JLabel valueL;
	JSlider		slider;
	private String suffix;
	
	public IntComponent(){
		this("new", 0, 0, 10, "");
	}
	//gets/sets ranges from 0 to 1
  	public IntComponent (	String	label,
				int	start, int min, int max, String suffix){

  		if(suffix == null)
  			this.suffix = "";
  		else
  			this.suffix = suffix;
  		
  		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		labelL = new JLabel(label);
		labelL.setPreferredSize(CommonFinal.LABEL_SIZE);
		slider = new JSlider(JSlider.HORIZONTAL, min, max + 1, start);
		slider.setPreferredSize(CommonFinal.PREFERRED_SIZE);
		slider.addChangeListener(this);
		valueL = new JLabel(String.valueOf(start) + suffix);
		valueL.setHorizontalAlignment(SwingConstants.RIGHT);
		valueL.setPreferredSize(CommonFinal.SUFFIX_LABEL_SIZE);
		//valueL.setPreferredSize()
		
		add(labelL, gbc);
		gbc.weightx = 1;
		add(slider, gbc);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(valueL, gbc);
	}

	public int getValue(){
		return slider.getValue();
	}
	public void setValue(int d){
		slider.removeChangeListener(this);
		slider.setValue(d);
		valueL.setText(String.valueOf(d) + suffix);
		slider.addChangeListener(this);
	}
	public void addChangeListener(ChangeListener l){
		parent = l;
	}
	public void removeChangeListener(){
		parent = null;
	}
	public void stateChanged(ChangeEvent e) {
		valueL.setText(String.valueOf(slider.getValue()) + suffix);
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