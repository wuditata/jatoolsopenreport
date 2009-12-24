/*
 * Created on 2004-1-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart;

import jatools.engine.script.ReportContext;
import jatools.engine.script.Script;

import java.util.HashMap;
import java.util.Properties;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author java
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChartFactory {
	private static Properties props = new Properties();

	static java.util.Map chartCache = new HashMap();

	static {
		chartCache.put("0", "LineChart");
		chartCache.put("1", "BarChart");
		chartCache.put("2", "PieChart");
//		chartCache.put("3", "PieChart");
//		chartCache.put("4", "PieChart3D");
//		chartCache.put("5", "LineChart");
//		chartCache.put("6", "LineChart3D");
//		chartCache.put("7", "XYLineChart");

	}


	/**
	 * DOCUMENT ME!
	 * 
	 * @param props
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static JFreeChart createInstance(Chart javaChart,Script script) {
		props = javaChart.getProperties();
		String chartType = props.getProperty("chartType");
		String type = "1";

		if (chartType != null) {
			type = chartType;
		} else {
			props.setProperty("chartType", type);
		}
		// System.out.println(type);

		return createInstance(javaChart,type,script);
	}

	public static JFreeChart createInstance(Chart javaChart,String type,Script script) {
		if(script == null)
			try {
				script = new ReportContext(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		JFreeChart chart = null;

		String chartTypeMap = (String) chartCache.get(type);

		Dataset dataset = ChartDatasetFactory.getDataSet(script,javaChart,type);
		if(dataset == null){
			dataset = DefaultChartDatasetFactory.getDefualeDataSet(type);
		}

		if (chartTypeMap.equals("BarChart")) {
			chart = org.jfree.chart.ChartFactory.createBarChart(null,// 图表标题
					null,// X轴的显示标签
					null,// Y轴的显示标签
					(CategoryDataset) dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直
					true,// 是否显示图例(对于简单的柱状图必须是false)
					false, false);// 是否生成URL链接
		} else if (chartTypeMap.equals("BarChart3D")) {

			chart = org.jfree.chart.ChartFactory.createBarChart3D(null,// 图表标题
					null,// X轴的显示标签
					null,// Y轴的显示标签
					(CategoryDataset) dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直
					true,// 是否显示图例(对于简单的柱状图必须是false)
					false,// 是否启用提示
					false);// 是否生成URL链接
		} else if (chartTypeMap.equals("PieChart")) {

			chart = org.jfree.chart.ChartFactory.createPieChart(null,
					(DefaultPieDataset) dataset, true, false, false);
		} else if (chartTypeMap.equals("PieChart3D")) {

			chart = org.jfree.chart.ChartFactory.createPieChart3D(null,
					(DefaultPieDataset) dataset, true, false, false);
		} else if (chartTypeMap.equals("LineChart")) {

			chart = org.jfree.chart.ChartFactory.createLineChart(null, null, null,
					(CategoryDataset) dataset, PlotOrientation.VERTICAL, true, false, false);
		} else if (chartTypeMap.equals("LineChart3D")) {

			chart = org.jfree.chart.ChartFactory.createLineChart3D(null, null, null,
					(CategoryDataset) dataset, PlotOrientation.VERTICAL, true, false, false);
		} else if (chartTypeMap.equals("XYLineChart")) {

			chart = org.jfree.chart.ChartFactory.createXYLineChart(null, null, null,
					(XYDataset) dataset, PlotOrientation.VERTICAL, true, false, false);
		} else {
			chart = org.jfree.chart.ChartFactory.createBarChart(null,// 图表标题
					null,// X轴的显示标签
					null,// Y轴的显示标签
					(CategoryDataset) dataset, // 数据集
					PlotOrientation.VERTICAL, // 图表方向：水平、垂直
					true,// 是否显示图例(对于简单的柱状图必须是false)
					false, false);// 是否生成URL链接
		}

		ChartStyle.setChartStyle(chart, props);

		return chart;
	}
	


}
