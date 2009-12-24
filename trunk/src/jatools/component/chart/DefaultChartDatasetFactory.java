package jatools.component.chart;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

public class DefaultChartDatasetFactory {
	public static Dataset getDefualeDataSet(String type){
		if(type.equals("0") || type.equals("1")){
			DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
			categoryDataset.addValue(20, "item1", "label1");
			categoryDataset.addValue(70, "item2", "label1");
			categoryDataset.addValue(40, "item3", "label1");
			categoryDataset.addValue(30, "item1", "label2");
			categoryDataset.addValue(60, "item2", "label2");
			categoryDataset.addValue(50, "item3", "label2");
			categoryDataset.addValue(40, "item1", "label3");
			categoryDataset.addValue(70, "item2", "label3");
			categoryDataset.addValue(50, "item3", "label3");
			categoryDataset.addValue(90, "item1", "label4");
			categoryDataset.addValue(80, "item2", "label4");
			categoryDataset.addValue(100, "item3", "label4");
			return categoryDataset;
		}
		else if(type.equals("2")){
			DefaultPieDataset pieDataset = new DefaultPieDataset();
			pieDataset.setValue("item1", 40);
			pieDataset.setValue("item2", 50);
			pieDataset.setValue("item3", 20);
			pieDataset.setValue("item4", 100);
			return pieDataset;
		}
		return null;
	}
}
