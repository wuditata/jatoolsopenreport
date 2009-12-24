package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.PlotData;
import jatools.component.chart.chart.Dataset;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.FillStyleChooser;
import jatools.component.chart.component.FillStyleFactory;
import jatools.component.chart.component.FillStyleInterface;
import jatools.component.chart.component.FontComponent;
import jatools.component.chart.component.MoreButton;
import jatools.component.chart.component.StringComponent;
import jatools.component.chart.component.ZFont;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;


public class BarDatasetPanel extends Dialog implements ActionListener, ItemListener {
	
	private JLabel ldatasource;
	private MoreButton bdatasource;
	protected StringComponent scdatalabel;
	protected JComboBox cbdatasource;
	private ColorComponent datasetColor;
	private ColorComponent datasetOutline;
	private JButton bshowlabel;
	Dataset dataset;
	
	public void initCustomizer(){
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);		
		//setBorder(CommonFinal.EMPTY_BORDER);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;		
			
		
		//setBorder(new TitledBorder("数据列"));		
		bshowlabel = new MoreButton("字体");
		bshowlabel.setActionCommand("label font");
		bshowlabel.addActionListener(this);
		
		
		
		ldatasource = new JLabel("数据列");
		ldatasource.setPreferredSize(CommonFinal.LABEL_SIZE);
		bdatasource = new MoreButton("填充样式");
		bdatasource.setActionCommand("dataset fillstyle");
		bdatasource.addActionListener(this);
		scdatalabel = new StringComponent("标签", null);
		scdatalabel.addChangeListener(this);
		cbdatasource = new JComboBox();
//		for (int i = 0; i < javaChart.getPlotData().size(); i++) {
//			cbdatasource.addItem(((PlotData) javaChart.getPlotData().get(i)).getField());
//		}
		cbdatasource.addItemListener(this);
		datasetColor = new ColorComponent("颜色", null);
		datasetColor.addChangeListener(this);
		datasetOutline = new ColorComponent("轮廓线", null);
		datasetOutline.addChangeListener(this);
		
		// p 1
		
		
		SepratorPanel sp = new SepratorPanel("数据列设置");
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(sp,gbc);
		gbc.weightx = 0;
		
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		add(ldatasource, gbc);
		//gbc.weightx = 1;
		//gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(cbdatasource, gbc);
		add(bshowlabel,gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(),gbc);
		/*gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);*/
		
		gbc.weightx = 1;
		gbc.gridwidth = 2;
		add(datasetColor, gbc);
//		gbc.weightx = 0;
//		gbc.gridwidth = 1;
//		add(bdatasource, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(),gbc);
		
		gbc.weightx = 1;
		gbc.gridwidth = 2;
		add(datasetOutline, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(),gbc);
		/*gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);*/
		
//		add(scdatalabel, gbc);
//		gbc.weighty = 1;
//		add(Box.createGlue(), gbc);
		
//		dataset.getGc().setOutlineFills(true);
//		if(chart instanceof AreaInterface || chart instanceof CandlestickChart || chart instanceof HiLoCloseChart){
//			scdatalabel.setVisible(false);
//		}
	}
	
	
	public void getVals() {
//		datasetColor.getValue().setToGc(dataset.getGc());
//		dataset.getGc().setLineColor(datasetOutline.getColor());
//		int i = dataset.getData().size();
//		String[] labels = new String[i];
//		if(scdatalabel.getValue() != null){
//			if(!scdatalabel.getValue().equals("")){
//				JavachartUtil.addArray(labels, ((String) scdatalabel.getValue()).split(","));
//			}
//			dataset.setLabels(labels);
//			dataset.replaceLabels(labels);
//		}
		
	}

	public void setVals() {
//		String[] s = new String[dataset.getData().size()];
//		for(int i = 0; i < s.length; i++){
//			s[i] = dataset.getDataElementAt(i).getLabel();
//		}
//		scdatalabel.setValue(JavachartUtil.plus(s, ","));
//		datasetColor.setValue(FillStyleFactory.createFillStyle(dataset.getGc()));
//		datasetOutline.setValue(dataset.getGc().getLineColor());
		
		
	}
	
	public void validateEnabled(){
//		bshowlabel.setEnabled(chart.getDataRepresentation().getLabelsOn());
	}
	
	public void actionPerformed(ActionEvent e) {
	   
		if (e.getActionCommand().equals("dataset fillstyle")) {
			Component f = getParent();
			while(!(f instanceof JDialog)){
				f = f.getParent();
			}
			FillStyleInterface style = FillStyleChooser.showDialog(f,
					"填充样式", FillStyleFactory.createFillStyle(dataset.getGc()), 0);
			datasetColor.setValue(style);
			style.setToGc(dataset.getGc());
		}
		else if (e.getActionCommand().equals("label font")) {
			if (FontComponent.getDefault().showChooser(this)) {
				ZFont font = (ZFont) FontComponent.getDefault().getValue();
				Color color = font.getForeColor();
				Font ff = new Font(font.getFace(), font.getStyle(), font
						.getSize());
//				dataset.setLabelColor(color);
//				dataset.setLabelFont(ff);
				/*for (int i = 0; i < chart.getNumDatasets(); i++) {
					chart.getDatasets()[i].setLabelColor(color);
					chart.getDatasets()[i].setLabelFont(ff);
				}*/
			}
		}
		if(parent != null){
			parent.fireChange(null);
		}
	}

	public void itemStateChanged(ItemEvent e) {
//		dataset = chart.getDataset((String) cbdatasource.getSelectedItem());
//		dataset.getGc().setOutlineFills(true);
		setVals();
	}

	public void setObject(Object object) {
//		dataset = (Dataset) object;
		initCustomizer();
	}

}
