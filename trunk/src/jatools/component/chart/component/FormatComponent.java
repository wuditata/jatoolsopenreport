package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.customizer.ChangeListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FormatComponent extends JPanel implements ActionListener {

	private JLabel label;
	private JComboBox dataFormat;
	private ChangeListener parent;
	public FormatComponent(String title){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		label = new JLabel(title);
		label.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
		dataFormat = new JComboBox();
		dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 0));
		dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 1));
		dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 2));
		
		dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 0));
		dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 1));
		dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 2));
		
		dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 0));
		dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 1));
		dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 2));
		dataFormat.setActionCommand("num format");
		dataFormat.addActionListener(this);
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		add(label, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(dataFormat, gbc);

	}
	
	public static AbstractComponent getDecimalFormatInstance(String title){
		return new DecimalFormatComponent(title);
	}

	public static AbstractComponent getDateFormatInstance(String title){
		return new DateFormatComponent(title);
	}
	
	
	public DataFormat getValue(){
		return (DataFormat) dataFormat.getSelectedItem();
	}
	public void setValue(DataFormat format){
		dataFormat.setSelectedItem(format);
	}
	public void actionPerformed(ActionEvent e) {
		if(parent != null)
			parent.fireChange(null);
	}
	public void addChangeListener(ChangeListener l){
		parent = l;
	}
	public void removeChangeListener(){
		parent = null;
	}
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		label.setEnabled(enabled);
		dataFormat.setEnabled(enabled);
	}


}
