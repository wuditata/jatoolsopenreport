package jatools.designer.action;

import jatools.component.Image;
import jatools.component.chart.Chart;
import jatools.designer.App;
import jatools.designer.Main;
import jatools.designer.property.editor.ImageSrcPropertyEditor;
import jatools.engine.imgloader.ImageLoader;

import java.awt.event.ActionEvent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class NewChartAction extends ReportAction {
    /**
     * Creates a new ZInsertImageAction object.
     *
     * @param owner DOCUMENT ME!
     */
    public NewChartAction() {
        super("Í³¼ÆÍ¼", getIcon("/jatools/icons/chart.gif"), getIcon("/jatools/icons/chart2.gif")); // //$NON-NLS-2$
        putValue(CLASS,Chart.class );
       }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {

    	Chart image = new Chart();
    	
          
            image.setWidth(270);
            image.setHeight(200);
            

            Main.getInstance().getActiveEditor().getReportPanel().setBaby(image);
        
    }
}
