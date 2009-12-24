package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.ColorIcon;
import jatools.component.chart.component.FillStyleFactory;
import jatools.component.chart.component.FillStyleInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JLabel;


public class SingleColorPanel extends Dialog {
	
	ColorComponent single;
	Gc gc;
	ColorIcon icon;
	JLabel preview;
	public SingleColorPanel(){
		GridBagLayout gbl = new GridBagLayout();        
        setLayout(gbl);
        setBorder(CommonFinal.EMPTY_BORDER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        single = new ColorComponent("ÑÕÉ«", null);
        single.addChangeListener(this);
        
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(single, gbc);
        gbc.weighty = 1;
        add(Box.createGlue(), gbc);
	}

	public void show(){
		gc.setFillStyle(Gc.FILL_SOLID);
		setVals();
		super.show();
		fireChange(null);
	}
	
	public void setObject(Object object) {
		gc = (Gc) object;
	}
	
	public void getVals() {
		((FillStyleInterface) single.getValue()).setToGc(gc);
	}

	public void setVals() {
		single.setValue(FillStyleFactory.createFillStyle(gc));
	}

}
