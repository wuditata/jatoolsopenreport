package jatools.designer.componenteditor;


import jatools.component.chart.Chart;
import jatools.data.reader.DatasetReader;
import jatools.designer.peer.ComponentPeer;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Properties;

import org.jfree.chart.editor.JChartEditor;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.4 $
 */
public class ChartEditor implements ComponentEditor {
    /*
     * (non-Javadoc)
     *
     * @see com.jatools.designer.layer.ZComponentEditor#show(com.jatools.designer.ZComponentPeer,
     *      javax.swing.undo.CompoundEdit)
     */
    /**
     * DOCUMENT ME!
     *
     * @param peer DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public void show(ComponentPeer peer) {
        Chart chart = (Chart) peer.getComponent();
        
        Properties props = (Properties) chart.getProperties();

        if (props != null) {
            props = (Properties) props.clone();
        }

        DatasetReader reader = chart.getReader();

        String label = chart.getLabelField();

        ArrayList data = (ArrayList) chart.getPlotData();

        if (data != null) {
            data = (ArrayList) data.clone();
        }


        
        
        JChartEditor editor = new JChartEditor((Frame)peer.getOwner().getTopLevelAncestor(),(Chart) peer.getComponent());
        
        editor.show();
        
        peer.getOwner().repaint();
//
//        if (editor.done) {
//            ChartSetEdit edit = new ChartSetEdit(peer, props, reader, label, data);
//
//            peer.getOwner().addEdit(edit);
//        } else {
//            chart.setProperties(props);
//            chart.setReader(reader);
//            chart.setLabelField(label);
//            chart.setPlotData(data);
//            chart.chart = null;

//            peer.getOwner().repaint();
//        }

        peer.setEditing(false);

        
    }
    
   
    
}
