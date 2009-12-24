/*
 * Created on 2005-11-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
//import jatools.component.chart.chart.BubbleChart;
//import jatools.component.chart.chart.CandlestickChart;
//import jatools.component.chart.chart.GanttChart;
//import jatools.component.chart.chart.HiLoCloseChart;
//import jatools.component.chart.chart.PieChart;
//import jatools.component.chart.chart.PolarChart;
//import jatools.component.chart.chart.RegressChart;
//import jatools.component.chart.chart.SectorMapChart;
//import jatools.component.chart.chart.SpeedoChart;
import jatools.component.chart.applet.ChartUtil;
import jatools.component.chart.component.CheckComponent;
import jatools.component.chart.component.ColorComponent;
//import jatools.component.chart.component.FillStyleChooser;
//import jatools.component.chart.component.FillStyleFactory;
//import jatools.component.chart.component.FillStyleInterface;
//import jatools.component.chart.component.FontComponent;
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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.jfree.chart.title.TextTitle;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RegularPanel extends Dialog implements ActionListener{
    ColorComponent background, plotarea, backgroundOutline, plotareaOutline;
    StringComponent title;
    CheckComponent cc;
    ColorStyleDialog bcolorStyle, pcolorStyle;
    ZFont titleFont = new ZFont("Dialog", 0, 12, Color.black, null);
	private ThreeDPanel threeD;
	private JButton btitle;
	private CheckComponent antialiasing;
	
	JButton bbg, bhtbg, bMargin;
 
    public void initCustomizer(){
        GridBagLayout gbl = new GridBagLayout();        
        setLayout(gbl);
        setBorder(CommonFinal.EMPTY_BORDER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        background = new ColorComponent("背景", null);
        background.addChangeListener(this);
        backgroundOutline = new ColorComponent("轮廓线", null);
        backgroundOutline.addChangeListener(this);
        plotarea = new ColorComponent("绘图区", null);
        plotarea.addChangeListener(this);
        plotareaOutline = new ColorComponent("绘图区轮廓线", null);
        plotareaOutline.addChangeListener(this);
        title = new StringComponent("标题", null);
        title.addChangeListener(this);

        threeD = new ThreeDPanel();
//        threeD.setChart(chart);
        threeD.addChangeListener(this);
        
        bbg = new MoreButton("填充样式");
        bbg.setActionCommand("background fillstyle");
        bbg.addActionListener(this);
        bhtbg = new MoreButton("填充样式");
        bhtbg.setActionCommand("plotarea fillstyle");
        bhtbg.addActionListener(this);
        btitle = new MoreButton("字体");
		btitle.setActionCommand("title font");
        btitle.addActionListener(this);
        bMargin = new MoreButton("绘图区边距");
        bMargin.setActionCommand("plotarea margin");
        bMargin.addActionListener(this);
        
        antialiasing = new CheckComponent("平滑边缘", true);
        antialiasing.addChangeListener(this);
  
        
        
        //第1行   

        gbc.weightx = 1;
        add(background, gbc);
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER ;        
//        add(bbg, gbc);
        add(Box.createGlue(),gbc);
        //2
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        add(backgroundOutline, gbc);
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(Box.createGlue(),gbc);  
        //3
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        add(plotarea, gbc);
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        add(bhtbg, gbc);
        add(Box.createGlue(),gbc);
        
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        add(plotareaOutline, gbc);
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        add(bMargin, gbc); 
        add(Box.createGlue(),gbc);
        
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        add(title, gbc);
        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(btitle, gbc); 
        
//        add(antialiasing, gbc);
        
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
//        add(threeD, gbc);

        gbc.weighty = 1;
        add(Box.createGlue(),gbc);
        
        
        antialiasing.setEnabled(false);
        threeD.setEnabled(false);
        
        
//        if(chart instanceof PieChart){
//        	plotarea.setEnabled(false);
//        	plotareaOutline.setEnabled(false);
//        	bhtbg.setEnabled(false);
//        	bMargin.setEnabled(false);
//        }
//        if(chart instanceof SectorMapChart){
//        	plotarea.setEnabled(false);
//        	plotareaOutline.setEnabled(false);
//        	bhtbg.setEnabled(false);
//        	bMargin.setEnabled(false);
//        	chart.setThreeD(false);
//        	threeD.setEnabled(false);
//        }
//        
//        if(chart instanceof CandlestickChart || chart instanceof HiLoCloseChart || chart instanceof BubbleChart || chart instanceof RegressChart || chart instanceof SpeedoChart || chart instanceof PolarChart || chart instanceof GanttChart){
//        	chart.setThreeD(false);
//        	threeD.setEnabled(false);
//        }       
    }
    
    public void show(){
    	setVals();
//    	threeD.setVals();
    	super.show();
    }

	public void actionPerformed(ActionEvent e) {
		Component f = getParent();
		while (!(f instanceof JDialog))
			f = f.getParent();
//		if(e.getActionCommand().equals("background fillstyle")){
//			FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", FillStyleFactory.createFillStyle(chart.getBackground().getGc()), 0);
//			background.setValue(style);
//		}else if(e.getActionCommand().equals("plotarea fillstyle")){
//			FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式", FillStyleFactory.createFillStyle(chart.getPlotarea().getGc()), 0);
//			plotarea.setValue(style);
//		}
//		else if(e.getActionCommand().equals("title font")){
//			if(FontComponent.getDefault().showChooser(this)){
//				titleFont = (ZFont) FontComponent.getDefault().getValue();
//			}
//		}else if(e.getActionCommand().equals("plotarea margin")){
//			PlotareaMargin.showDialog(f, "绘图区边距设置", chart.getPlotarea(), this);
//		}
		
		if(e.getActionCommand().equals("title font")){
			if(FontComponent.getDefault().showChooser(this)){
				titleFont = (ZFont) FontComponent.getDefault().getValue();
			}
		}
		
		fireChange(null);
	}

	public void getVals() {
		
//		chart.setAntialiasing(antialiasing.getValue());
//		
//		FillStyleInterface style = (FillStyleInterface) background.getValue();
//		style.setToGc(chart.getBackground().getGc());
//		chart.getBackground().getGc().setLineColor(backgroundOutline.getColor());
//		style = (FillStyleInterface) plotarea.getValue();
//		style.setToGc(chart.getPlotarea().getGc());
//		chart.getPlotarea().getGc().setLineColor(plotareaOutline.getColor());
//		chart.getBackground().setTitleString((String) title.getValue());
//		chart.getBackground().setTitleFont(new Font(titleFont.getFace(), titleFont.getStyle(), titleFont.getSize()));
//		chart.getBackground().setTitleColor(titleFont.getForeColor());
		
		javaChart.getChart().setBackgroundPaint(background.getColor());
		javaChart.getChart().setBorderPaint(backgroundOutline.getColor());
		javaChart.getChart().setBorderVisible(true);
		if(plotarea.isValid())
			javaChart.getChart().getPlot().setBackgroundPaint(plotarea.getColor());
		
		if(plotareaOutline.isValid())
			javaChart.getChart().getPlot().setOutlinePaint(plotareaOutline.getColor());
		
		javaChart.getChart().setTitle((String) title.getValue());
		javaChart.getChart().getTitle().setFont(new Font(titleFont.getFace(), titleFont.getStyle(), titleFont.getSize()));
		javaChart.getChart().getTitle().setPaint(titleFont.getForeColor());
		
//		System.out.println(ChartUtil.toString(background.getColor()));
		
		volidateEnabled();
	}

	public void setVals() {
		init();
		
//		antialiasing.setValue(chart.isAntialiasing());
//		
//		background.setValue(FillStyleFactory.createFillStyle(chart.getBackground().getGc()));
//		backgroundOutline.setValue(chart.getBackground().getGc().getLineColor());
//		plotarea.setValue(FillStyleFactory.createFillStyle(chart.getPlotarea().getGc()));
//		plotareaOutline.setValue(chart.getPlotarea().getGc().getLineColor());
//		title.setValue(chart.getBackground().getTitleString());
//		titleFont.setFace(chart.getBackground().getTitleFont().getFamily());
//		titleFont.setStyle(chart.getBackground().getTitleFont().getStyle());
//		titleFont.setSize(chart.getBackground().getTitleFont().getSize());
//		titleFont.setForeColor(chart.getBackground().getTitleColor());
		
		background.setValue((Color)javaChart.chart.getBackgroundPaint());
		backgroundOutline.setValue((Color)javaChart.chart.getBorderPaint());
		if(plotarea.isValid())
			plotarea.setValue((Color)javaChart.chart.getPlot().getBackgroundPaint());
		if(plotareaOutline.isValid())
			plotareaOutline.setValue((Color)javaChart.chart.getPlot().getOutlinePaint());
		if(javaChart.chart.getTitle() != null){
			title.setValue(javaChart.chart.getTitle().getText());
			titleFont.setFace(javaChart.chart.getTitle().getFont().getFamily());
			titleFont.setStyle(javaChart.chart.getTitle().getFont().getStyle());
			titleFont.setSize(javaChart.chart.getTitle().getFont().getSize());
			titleFont.setForeColor((Color)javaChart.chart.getTitle().getPaint());
			
		}
		volidateEnabled();
	}
	
	private void volidateEnabled(){
//		bbg.setVisible(false);
//		bhtbg.setVisible(false);
//		bMargin.setVisible(false);
		if(javaChart.chart.getTitle()== null){
			btitle.setEnabled(false);
		}else{
			if(javaChart.chart.getTitle().getText()== null || javaChart.chart.getTitle().getText().equals("")){
				btitle.setEnabled(false);
			}
			else{
				btitle.setEnabled(true);
			}
		}
	}
	
	private void init(){
//		chart.getBackground().getGc().setOutlineFills(true);
//		chart.getPlotarea().getGc().setOutlineFills(true);
	}

	public void setObject(Object object) {
		initCustomizer();
	}
}
