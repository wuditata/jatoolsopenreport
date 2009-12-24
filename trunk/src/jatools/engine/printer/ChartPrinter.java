package jatools.engine.printer;

import jatools.component.ImageStyle;
import jatools.component.chart.Chart;
import jatools.component.chart.ChartFactory;
import jatools.engine.PrintConstants;
import jatools.engine.imgloader.ImageLoader;
import jatools.engine.script.Script;
import jatools.util.Util;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ChartPrinter extends ImagePrinter {
    protected ImageStyle getImageStyle(Script script) {
        Chart chart = (Chart) this.getComponent();

        ImageStyle lastcss = chart.getImageCSS();

        chart.getProperties().put("width", chart.getWidth() + "");
        chart.getProperties().put("height", chart.getHeight() + "");

//        Bean bean = ChartFactory.createBeanInstance(chart, script);
        
       
        
        JFreeChart jfc = ChartFactory.createInstance(chart,script);
        
        ImageLoader loader = (ImageLoader) script.get(PrintConstants.IMAGE_LOADER);

        if (loader.isAcceptableExportFormat(lastcss.getExportImageFormat())) {

//        	java.awt.Image image = 
//                BufferedImage image = Util.asBufferedImage(jfc.getImage());
                
                BufferedImage image = draw(jfc,chart.getWidth(),chart.getHeight());
                	
                lastcss.setAwtObject(image);
        } else if (loader.isAcceptableExportFormat(ImageStyle.AWT)) {
            try {
              
//                BufferedImage image = Util.asBufferedImage(jfc.getImage());
                
                BufferedImage image = draw(jfc,chart.getWidth(),chart.getHeight());
                
                lastcss.setAwtObject(image);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lastcss;
    }
    
    protected BufferedImage draw(JFreeChart chart, int width, int height)
    {
	    BufferedImage img =
	    new BufferedImage(width , height,
	    BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = img.createGraphics();
	    chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
	    g2.dispose();
	    return img;
    } 
    
}
