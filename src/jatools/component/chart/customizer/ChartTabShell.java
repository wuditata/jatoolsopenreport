package jatools.component.chart.customizer;

import jatools.component.chart.PanelLabelMap;

import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;



public class ChartTabShell extends Tabs {
	private RegularPanel ip;

	private AxisPanel xaxis;

	private AxisPanel yaxis;

	private LegendPanel lp;

	private BarOption bo;

	protected void initTabbed() {
		ip = new RegularPanel();
		ip.setChart(javachart);
		ip.setObject(null);
		ip.addChangeListener(this);
		tp.add(ip, "常规");
		
		Plot plot = javachart.chart.getPlot();
		
		if(!(plot instanceof PiePlot)){
			xaxis = new AxisPanel();
			yaxis = new AxisPanel();
			xaxis.setChart(javachart);
			xaxis.setObject("xaxis");
//			xaxis.setObject(chart.getXAxis());
			yaxis.setChart(javachart);
//			yaxis.setObject(chart.getYAxis());
			yaxis.setObject("yaxis");
			xaxis.addChangeListener(this);
			yaxis.addChangeListener(this);
			tp.add(xaxis, "X轴");
			tp.add(yaxis, "Y轴");
		}
		
		
		
		lp = new LegendPanel();
		bo = new BarOption();
		
		lp.setChart(javachart);
		lp.setObject(null);
		
		bo.setChart(javachart);
		bo.setObject(null);
		
		lp.addChangeListener(this);
		bo.addChangeListener(this);
		
		tp.add(lp, "图例");
//		System.out.println(javachart.getProperties().getProperty("chartType"));
		tp.add(bo, PanelLabelMap.getLabel(javachart.getProperties().getProperty("chartType")) + "选项");
	}

	protected void refershTabContent() {
		tp.remove(bo);
		bo = new BarOption();
		bo.setChart(javachart);
		bo.setObject(null);
		bo.addChangeListener(this);
		tp.add(bo, PanelLabelMap.getLabel(javachart.getProperties().getProperty("chartType")) + "选项");
	}

}
