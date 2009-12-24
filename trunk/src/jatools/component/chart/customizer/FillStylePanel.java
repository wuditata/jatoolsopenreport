package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTabbedPane;



public class FillStylePanel extends Dialog {

	Gc gc;

	SingleColorPanel scp;

	CrossColorPanel ccp;

	CrossLinePanel clp;

	ImageSelectPanel isp;

	JTabbedPane tp;

	public void setObject(Object object) {
		gc = (Gc) object;
		initializeCustomizer();
	}

	protected void initializeCustomizer() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		tp = new JTabbedPane();
		scp = new SingleColorPanel();
		scp.setObject(gc);
		scp.addChangeListener(this);
		ccp = new CrossColorPanel();
		ccp.setObject(gc);
		ccp.addChangeListener(this);
		clp = new CrossLinePanel();
		clp.setObject(gc);
		clp.addChangeListener(this);
		isp = new ImageSelectPanel();
		isp.setObject(gc);
		isp.addChangeListener(this);
		
		tp.add("纯色", scp);
		tp.add("渐变色", ccp);
		tp.add("线纹", clp);
		tp.add("图片", isp);

		add(tp, gbc);

		if (gc.getFillStyle() == Gc.FILL_SOLID) {
			tp.setSelectedComponent(scp);
		} else if (gc.getFillStyle() == Gc.FILL_GRADIENT) {
			tp.setSelectedComponent(ccp);
		} else if (gc.getFillStyle() == Gc.FILL_TEXTURE) {
			if(gc.getImage() != null && gc.getTexture() == GcHelper.TEXTURE_IMAGE){
				tp.setSelectedComponent(isp);
			}else{
				tp.setSelectedComponent(clp);
			}
		}
	}


	public void getVals() {
		// TODO 自动生成方法存根
		
	}

	public void setVals() {
		// TODO 自动生成方法存根
		
	}

}
