package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;


public class DateFormatComponent extends AbstractComponent implements ActionListener {

	private JLabel dateLabel;
	private JComboBox dateFormat;

	public DateFormatComponent(String title){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		dateLabel = new JLabel(title);
		dateLabel.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
		dateFormat = new JComboBox();
		dateFormat.addItem("自定义...");
		dateFormat.addItem("yy-MM-dd");
		dateFormat.addItem("yy-MM-dd HH-mm-ss");
		dateFormat.addItem("yy年MM月dd日");
		dateFormat.addItem("yy年MM月dd日 HH时mm分ss秒");

		dateFormat.setEditable(true);
		dateFormat.setActionCommand("date format");
		dateFormat.getEditor().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(parent != null)
					parent.fireChange(e);
			}
			
		});
		dateFormat.addActionListener(this);
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		add(dateLabel, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(dateFormat, gbc);
	}
	
	public void setValue(Object object) {
		dateFormat.getEditor().setItem(object);
	}

	public Object getValue() {
		return dateFormat.getEditor().getItem();
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("date format")){
			if(dateFormat.getSelectedItem().equals("自定义...")){
				//dateFormat.getEditor().selectAll();
			}else{
				if(parent != null)
					parent.fireChange(e);
			}
		}
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		dateLabel.setEnabled(enabled);
		dateFormat.setEditable(enabled);
	}

	
}
