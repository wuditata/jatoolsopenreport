package jatools.component.chart;

import jatools.component.chart.chart.Dataset;

import java.util.HashMap;



public class PanelLabelMap {
	private static HashMap labelMap = new HashMap();
	static{
		labelMap.put("0", "ÏßĞÎÍ¼");
		labelMap.put("1", "ÖùĞÎÍ¼");
		labelMap.put("2", "±ıÍ¼");
	}
	
	public static String getLabel(String type){
		return (String) labelMap.get(type);
	}
	
	
}
