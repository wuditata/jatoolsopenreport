package jatools.component.painter;

import jatools.component.Component;
import jatools.component.chart.Chart;
import jatools.data.reader.AbstractDatasetReader;
import jatools.dataset.Dataset;
import jatools.dataset.DatasetException;

import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ChartPainter extends SimplePainter {

	/**
	 * DOCUMENT ME!
	 * 
	 * @param g2
	 *            DOCUMENT ME!
	 */
	public void paintComponent(Graphics2D g2, Component c) {
		Chart chart = (Chart) c;
		        
		
		
		JFreeChart chartDraw = chart.getChart();
		
		Graphics2D gcopy = (Graphics2D) g2.create();
		
		chartDraw.draw(gcopy, c.getBounds());
	
		gcopy.dispose();

		// ds -> PieDataset -> g2 -> pie

	}

}
