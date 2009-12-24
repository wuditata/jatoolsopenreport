package jatools.component.chart;

import jatools.data.reader.DatasetReader;
import jatools.dataset.Dataset;
import jatools.engine.script.ReportContext;
import jatools.engine.script.Script;

import java.util.ArrayList;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartDatasetFactory {

	private static org.jfree.data.general.Dataset dataset = null;
	private static DefaultCategoryDataset categoryDataset = null;
	private static DefaultPieDataset pieDataset = null;
	
	public static org.jfree.data.general.Dataset getDataSet(Script script,Chart javaChart,String type) {
		
		DatasetReader reader = javaChart.getReader();
		String labelField = javaChart.getLabelField();
		ArrayList plotData = javaChart.getPlotData();
		
		
		
		if (reader == null || labelField == null || plotData == null || plotData.isEmpty())
			return null;

//		Plot plot = javaChart.chart.getPlot();
//		
//		if (plot instanceof CategoryPlot) {
//			dataset = new DefaultCategoryDataset();
//		}
//		else if (plot instanceof XYPlot) {
//			dataset = new DefaultCategoryDataset();
//        }
//		else if (plot instanceof PiePlot) {
//			dataset = new DefaultPieDataset();
//        }
		
		
		if(type.equals("0") || type.equals("1")){
				dataset = new DefaultCategoryDataset();
			
		}
		else if(type.equals("2")){
			dataset = new DefaultPieDataset();//pieDataset;
			
		}
		
		Dataset set = null;

		try {
			set = reader.read(script, -1);
		} catch (Exception e1) {
			e1.printStackTrace();

			return null;
		}

		for (int i = 0; i < set.getRowCount(); i++) {

			for (int j = 0; j < plotData.size(); j++) {
				String label = set.getValueAt(i, labelField) + "";
				String legendField = ((PlotData) plotData.get(j)).getField();
				double val = 0.0;
				Number n = (Number) set.getValueAt(i, ((PlotData) plotData.get(j)).getField());
				if (n != null)
					val = n.doubleValue();
//				categorydataset1.addValue(val, "" + j, new LabelValue(label, i));
				addValue(dataset, val, legendField, new LabelValue(label, i));
//				System.out.println(set.get);
				
			}

		}
//		return pieDataset;
		return dataset;
	}

	private static void addValue(org.jfree.data.general.Dataset dataset,double val, Comparable field, Comparable label) {
		if(dataset instanceof DefaultCategoryDataset){
			((DefaultCategoryDataset)dataset).addValue(val, field, label);
			
//			categoryDataset.addValue(val, field, label);
		}
		
		else if(dataset instanceof DefaultPieDataset){
			if(label != null)
				label = label.toString();
			((DefaultPieDataset)dataset).setValue(label, val);
//			pieDataset.setValue(label, val);
		}
	}
	
	public static void setDataset(Chart javaChart,org.jfree.data.general.Dataset dataset){
		Plot plot = javaChart.chart.getPlot();
		if (plot instanceof CategoryPlot) {
			((CategoryPlot)plot).setDataset((CategoryDataset) dataset);
		}
	}

}
