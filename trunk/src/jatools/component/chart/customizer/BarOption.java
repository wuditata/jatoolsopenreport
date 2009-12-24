/*
 * Created on 2005-11-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.component.AbstractComponent;
import jatools.component.chart.component.CheckComponent;
import jatools.component.chart.component.FontComponent;
import jatools.component.chart.component.RangeComponent;
import jatools.component.chart.component.StringComponent;
import jatools.component.chart.component.ZFont;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;


/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BarOption extends Dialog implements ActionListener {
	CheckComponent ckshowlabel, ckshowvalue,cksinglecolor;

	StringComponent scbaseline, scdatalabel;

	RangeComponent rcbarwidth;

	JPanel p;

	JLabel ldatasource;

	JButton bdatasource;

	JComboBox cbdatasource;

//	Bar bar;

//	private BarDatasetPanel datasetPanel;

//	private AbstractComponent dataFormat;

	public void initCustomizer() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		setBorder(CommonFinal.EMPTY_BORDER);
		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.insets = CommonFinal.INSETS;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;

		ckshowlabel = new CheckComponent("显示标签", false);
		ckshowlabel.addChangeListener(this);
		ckshowvalue = new CheckComponent("显示值", false);
		ckshowvalue.addChangeListener(this);
		cksinglecolor = new CheckComponent("单独的颜色", false);
		cksinglecolor.addChangeListener(this);
		
//		dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
//		dataFormat.addChangeListener(this);
		
		rcbarwidth = new RangeComponent("柱形宽度", 0);
		rcbarwidth.addChangeListener(this);
		scbaseline = new StringComponent("基线", null);
		scbaseline.addChangeListener(this);
	/*	bshowlabel = new MoreButton("字体");
		bshowlabel.setActionCommand("label font");
		bshowlabel.addActionListener(this);*/

//		datasetPanel = new BarDatasetPanel();
//		datasetPanel.setChart(javaChart);
//		datasetPanel.setObject(null);
//		datasetPanel.addChangeListener(this);

		// 1
		gbc.gridwidth = 1;
		gbc.weightx = 0;
	
		//ckshow(0)  box.glue(1)
		add(ckshowlabel, gbc);	
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);	
		
	/*	gbc.gridwidth = 1;
		add(bshowlabel, gbc);*/
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createVerticalStrut(2), gbc);

		// 2

		/*gbc.gridwidth = 1;
		gbc.weightx = 1;
		add(ckshowvalue, gbc);
		gbc.gridwidth = 1;
		add(Box.createGlue(), gbc);*/
//		gbc.weightx = 1;
//		gbc.gridwidth = 1;
//		add(ckshowvalue, gbc);		
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		add(cksinglecolor,gbc);
		

		// 4
//		gbc.weightx = 1;
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		add(dataFormat,gbc);

//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		add(rcbarwidth, gbc);
		/*gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);*/

		// 5
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		add(scbaseline, gbc);
		/*gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);*/

		// 6

		//gbc.gridwidth = 2;
//		gbc.weightx = 1;
//		add(datasetPanel, gbc);
//		gbc.weightx = 0;
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		add(Box.createGlue(), gbc);
//		gbc.weighty = 1;
//		add(Box.createGlue(), gbc);

//		bar = ((BarInterface) chart).getBar();
//		
//		if(chart instanceof DateStackBarChart || chart instanceof DateStackColumnChart || chart instanceof StackBarChart || chart instanceof StackColumnChart){
//			scbaseline.setVisible(false);
//		}
	}

	public void show() {
		setVals();
//		datasetPanel.setVals();
		super.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("label font")) {
			if (FontComponent.getDefault().showChooser(this)) {
				ZFont font = (ZFont) FontComponent.getDefault().getValue();
				Color color = font.getForeColor();
				Font ff = new Font(font.getFace(), font.getStyle(), font
						.getSize());
//				for (int i = 0; i < chart.getNumDatasets(); i++) {
//					chart.getDatasets()[i].setLabelColor(color);
//					chart.getDatasets()[i].setLabelFont(ff);
//				}
			}
		}
		if (parent != null) {
			parent.fireChange(null);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chart.panel.Dialog#getVals()
	 */
	public void getVals() {
//		bar.setLabelsOn(ckshowlabel.getValue());
//		bar.setUseValueLabels(ckshowvalue.getValue());
//		((BarInterface) chart).setIndividualColors(cksinglecolor.getValue());
//		bar.setClusterWidth(rcbarwidth.getValue());
//		if (scbaseline.getValue() == null || scbaseline.getValue().equals(""))
//			bar.setBaseline(0);
//		else
//			bar.setBaseline(Double.parseDouble((String) scbaseline.getValue()));
		
//		chart.getDataRepresentation().setPattern((String) dataFormat.getValue());
		
		Plot plot = javaChart.getChart().getPlot();
		if(plot instanceof CategoryPlot){
			CategoryItemRenderer renderer = ((CategoryPlot) plot).getRenderer();
			if(renderer instanceof LineAndShapeRenderer){
				((LineAndShapeRenderer)renderer).setBaseShapesVisible(ckshowlabel.getValue());//显示折线图上的数据标志
			}
			
			if(renderer instanceof BarRenderer3D){
				((BarRenderer3D)renderer).setItemLabelAnchorOffset(10D);// 设置柱形图上的文字偏离值 
			}
			if(renderer instanceof BarRenderer){
//				((BarRenderer) renderer).setMaximumBarWidth(0.02); 
//				((BarRenderer) renderer).setItemMargin(0.0);  
//				((BarRenderer) renderer).setMinimumBarLength(0.5);
				
				
			}
			
			renderer.setBaseItemLabelsVisible(ckshowlabel.getValue());
			
		}
		
		volidateEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chart.panel.Dialog#setVals()
	 */
	public void setVals() {
//		ckshowlabel.setValue(bar.getLabelsOn());
//		ckshowvalue.setValue(bar.getUseValueLabels());
//		
//		cksinglecolor.setValue(((BarInterface) chart).getIndividualColors());
//		rcbarwidth.setValue(bar.getClusterWidth());
//		scbaseline.setValue(String.valueOf(bar.getBaseline()));
//		
//		dataFormat.setValue(chart.getDataRepresentation().getPattern());
		
		Plot plot = javaChart.chart.getPlot();

		if (plot instanceof CategoryPlot) {
			CategoryItemRenderer renderer = ((CategoryPlot) plot).getRenderer();
			if(renderer instanceof LineAndShapeRenderer){
				ckshowlabel.setValue(((LineAndShapeRenderer)renderer).getBaseItemLabelsVisible().booleanValue());//显示折线图上的数据标志
			}
			
			if(renderer instanceof BarRenderer){
				
			}
			
			ckshowlabel.setValue(renderer.getBaseItemLabelsVisible().booleanValue());
		}
		
		volidateEnabled();
	}

	private void volidateEnabled(){
	    
	   
		/*bshowlabel.setEnabled(ckshowlabel.getValue());*/
		ckshowvalue.setEnabled(ckshowlabel.getValue());	
//		dataFormat.setEnabled(ckshowvalue.getValue());
//		 datasetPanel.validateEnabled();
//		if(chart.getNumDatasets() < 2){	    
//		    cksinglecolor.setEnabled(true);
//		}		
//		else{
//		    cksinglecolor.setEnabled(false);
//		}
	}
	
	public void setObject(Object object) {
//		bar = ((BarInterface) chart).getBar();
		initCustomizer();
	}
}
