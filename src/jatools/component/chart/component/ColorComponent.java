package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.customizer.ChangeListener;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class ColorComponent extends JPanel implements ActionListener {
	
	JLabel label ;
	ZColorComboBox box;
	ChangeListener parent;
	SingleColor single = new SingleColor(Color.WHITE);
	LineStyle line = new LineStyle(true);
	
	public ColorComponent(){
		this("", null);
	}
	
	public ColorComponent(String label, Color color){
		single.setColor(color);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.BOTH;
		this.label = new JLabel(label);
		this.label.setPreferredSize(CommonFinal.LABEL_SIZE);
		box = new ZColorComboBox();
		setValue(single);
		box.addActionListener(this);
		add(this.label);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(box, gbc);
		
	}
	
	public FillStyleInterface getValue(){
		return box.getFillStyle();
	}
	
	public void setValue(FillStyleInterface object){
		box.setFillStyle(object);
	}
	
	public void setValue(Color color){
		if(box.getFillStyle() instanceof LineStyle){
			line = (LineStyle) box.getFillStyle();
			line.setColor(color);
			box.setFillStyle(line);
		}else{
			single.setColor(color);
			box.setFillStyle(single);
		}
	}
	
	public Color getColor(){
		if(box.getFillStyle() instanceof LineStyle){
			return line.getColor();
		}else{
			return single.getColor();
		}
	}
		
	public void addChangeListener(ChangeListener l){
		parent = l;
	}
	public void removeChangeListener(){
		parent = null;
	}

	public void actionPerformed(ActionEvent e) {
		if(box.getFillStyle() instanceof LineStyle){
			line = new LineStyle(((LineStyle)box.getFillStyle()).full);
			line.setColor(box.getColor());
			box.setFillStyle(line);
		}else{
			single.setColor(box.getColor());
			box.setFillStyle(single);
		}
		if(parent != null)
			parent.fireChange(null);
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		label.setEnabled(enabled);
		box.setEnabled(enabled);
	}
	
	
}
