package jatools.component.chart.customizer;


import jatools.component.chart.Chart;

import javax.swing.JPanel;



public abstract class Dialog extends JPanel implements ChangeListener {

	protected Chart javaChart;
	protected ChangeListener parent;
	
	public void setChart(Chart javaChart){
		this.javaChart = javaChart;
	}
    
	public void setObject(Object object){
		
	}
	
	public void addChangeListener(ChangeListener l) {
		parent = l;
	}

	public void fireChange(Object object){
		getVals();
		if(parent!=null)
			parent.fireChange(object);
	}
	public void removeChangeListener(ChangeListener l) {
		parent = null;
	}
	
	public abstract void getVals();
	public abstract void setVals();

}
