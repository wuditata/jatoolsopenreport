package jatools.component.chart.component;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.customizer.ChangeListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class StringComponent extends JPanel implements KeyListener {
	JLabel l;
	JTextField t;
	ChangeListener parent;
	
	public StringComponent(){
		this("", null);
	}
	
	public StringComponent(String label, String text){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		l = new JLabel(label);
		l.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
		t = new JTextField(text);
		t.setPreferredSize(CommonFinal.PREFERRED_SIZE);
		t.addKeyListener(this);
		add(l, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(t, gbc);
	}

	public void setValue(Object object) {
		t.setText((String) object);
	}

	public Object getValue() {
		return t.getText();
	}

	public void addChangeListener(ChangeListener l){
		parent = l;
	}
	public void removeChangeListener(){
		parent = null;
	}
	
	public void setEnabled(boolean yesno){
		super.setEnabled(yesno);
		l.setEnabled(yesno);
		t.setEnabled(yesno);
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if(parent != null)
			parent.fireChange(null);
	}

	public void keyTyped(KeyEvent e) {

	}
}
