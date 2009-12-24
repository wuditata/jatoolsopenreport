package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;


public class DecimalFormatComponent extends AbstractComponent implements ActionListener {

	private JLabel label;
	private JComboBox dataFormat;
	
	public DecimalFormatComponent(String title){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		label = new JLabel(title);
		label.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
		dataFormat = new JComboBox(new String[]{
				"自定义...",
				"#0", // //
				"#0.0", // //
				"#0.00", // //
				"#0.000", // //
				"#0.0000", // //
				"#,##0", // //
				"#,##0.0", // //
				"#,##0.00", // //
				"#,##0.000", // //
				"#,##0.0000", // //
				//null, //货币
				"￥0", // //
				"￥0.0", // //
				"￥0.00", // //
				"￥0.000", // //
				"￥0.0000", // //
				"￥#,##0", // //
				"￥#,##0.0", // //
				"￥#,##0.00", // //
				"￥#,##0.000", // //
				"￥#,##0.0000", // //
				//null, //百分比
				"0%", // //
				"0.0%", // //
				"0.00%", // //
				"0.000%", // //
				"0.0000%", // //
				//null, // 科学计数法
				"0.##E0", "0.00E0"
		});
		/*dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 0));
		dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 1));
		dataFormat.addItem(new DataFormat(NumberFormat.getNumberInstance(), 2));
		
		dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 0));
		dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 1));
		dataFormat.addItem(new DataFormat(NumberFormat.getPercentInstance(), 2));
		
		dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 0));
		dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 1));
		dataFormat.addItem(new DataFormat(NumberFormat.getCurrencyInstance(), 2));*/
		dataFormat.setActionCommand("num format");
		dataFormat.addActionListener(this);
		dataFormat.setEditable(true);
		dataFormat.getEditor().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(parent != null)
					parent.fireChange(e);
			}
			
		});
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		add(label, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(dataFormat, gbc);
	}
	
	public void setValue(Object object) {
		dataFormat.getEditor().setItem(object);
	}

	public Object getValue() {
		return dataFormat.getEditor().getItem();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("num format")){
			if(dataFormat.getSelectedItem().equals("自定义...")){
				//dataFormat.getEditor().selectAll();
			}else{
				if(parent != null)
					parent.fireChange(e);
			}
		}
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		label.setEnabled(enabled);
		dataFormat.setEnabled(enabled);
	}

}
