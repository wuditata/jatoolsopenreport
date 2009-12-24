/*
 * Created on 2005-12-1
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SepratorPanel extends JPanel{
    /**
     * 
     */
    public SepratorPanel(String s) {
        
        GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel l = new JLabel(s);       
		JSeparator js = new JSeparator(JSeparator.HORIZONTAL);
		js.setPreferredSize(new Dimension(100,2));
		
		gbc.gridwidth = 1;
		add(l,gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		add(js,gbc);
        
    }

    public static void main(String[] args) {
    }
}
