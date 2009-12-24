package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
//import jatools.component.chart.chart.PieChart;
import jatools.component.chart.component.CheckComponent;
import jatools.component.chart.component.IntComponent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;


public class ThreeDPanel extends Dialog {

	private CheckComponent isThreeD;

	private IntComponent xDepth;

	private IntComponent yDepth;
	
	

	public ThreeDPanel() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		//setBorder(new TitledBorder("3D"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		isThreeD = new CheckComponent("3D", false);
		isThreeD.addChangeListener(this);
		xDepth = new IntComponent("深度 x:", 15, 0, 30, "");
		xDepth.addChangeListener(this);
		yDepth = new IntComponent("     y:", 15, 0, 30, "");
		yDepth.addChangeListener(this);
		/*JLabel l = new JLabel("3D效果");
		JSeparator js = new JSeparator(JSeparator.HORIZONTAL);
		js.setPreferredSize(new Dimension(100,2));
		
		gbc.gridwidth = 1;
		add(l,gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		add(js,gbc);*/
		SepratorPanel sp = new SepratorPanel("3D效果");
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(sp,gbc);
		gbc.weightx = 0;
		add(isThreeD, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(xDepth, gbc);
		add(yDepth, gbc);
		gbc.weighty = 1;
		add(Box.createGlue(), gbc);

	}

	public void setObject(Object object) {
		// TODO 自动生成方法存根

	}

	public void getVals() {
//		chart.setThreeD(isThreeD.getValue());
//		chart.getGlobals().setXOffset(xDepth.getValue());
//		chart.getGlobals().setYOffset(yDepth.getValue());
		
		validateEnabled();
	}

	public void setVals() {
//		isThreeD.setValue(chart.getThreeD());
//		xDepth.setValue(chart.getGlobals().getXOffset());
//		yDepth.setValue(chart.getGlobals().getYOffset());

		validateEnabled();
	}
	
	private void validateEnabled(){
		xDepth.setEnabled(isThreeD.getValue());
		yDepth.setEnabled(isThreeD.getValue());
//		if (chart instanceof PieChart) {
//			xDepth.setEnabled(false);
//		}
	}

	public void setEnabled(boolean yesno) {
		super.setEnabled(yesno);
		isThreeD.setEnabled(yesno);
		xDepth.setEnabled(yesno);
		yDepth.setEnabled(yesno);
	}
}
