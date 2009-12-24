/*
 * Created on 2005-11-25
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTabbedPane;


/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LineAndSignPanel extends Dialog{

    LinePanel lp;
    SignPanel sp;
    JTabbedPane tp;
    Gc gc;    
    IconType iconType;
	private boolean full;
    public LineAndSignPanel(IconType iconType, boolean full){
    	this.iconType = iconType;
    	this.full = full;
    }
    
    
    public void setObject(Object object) {
		gc = (Gc) object;
		initializeCustomizer();
	}

    
    /* (non-Javadoc)
     * @see com.hufeng.customizer.Tabs#initializeCustomizer()
     */
    protected void initializeCustomizer() {        
        // XXX Auto-generated method stub
        GridBagLayout gbl = new GridBagLayout();        
        setLayout(gbl);        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.BOTH;   
        
        tp = new JTabbedPane();
       
        sp = new SignPanel(iconType);
        sp.setObject(gc);
        sp.addChangeListener(this);
        lp = new LinePanel(iconType);
        lp.setObject(gc);
        lp.addChangeListener(this);
        
        tp.add("线形",lp);
        if(full)
        tp.add("标记",sp);
        
        gbc.weightx = 1;
		gbc.weighty = 1;
        add(tp,gbc);	
        
    }

	public void getVals() {
		// TODO 自动生成方法存根
		
	}


	public void setVals() {
		// TODO 自动生成方法存根
		
	}
}
