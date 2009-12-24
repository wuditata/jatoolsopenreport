package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.customizer.ChangeListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CheckComponent extends JPanel implements ActionListener{
	JLabel l;
	JCheckBox box;
	ChangeListener parent;
	public CheckComponent(){
		this("", false);
	}
	public CheckComponent(String label, boolean yesno){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		l = new JLabel(label);
		l.setPreferredSize(CommonFinal.LABEL_SIZE);
		box = new JCheckBox();
		box.addActionListener(this);
		box.setSelected(yesno);
		gbc.weightx = 0;
		add(l, gbc);
		add(box, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);
	}

	public boolean getValue(){
		return box.isSelected();
	}
	public void setValue(boolean yesno){
		box.setSelected(yesno);
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
		l.setEnabled(enabled);
		box.setEnabled(enabled);
	}
}
