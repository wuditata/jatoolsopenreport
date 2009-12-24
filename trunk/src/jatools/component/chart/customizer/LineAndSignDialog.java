/*
 * Created on 2005-11-25
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.Globals;
import jatools.component.chart.component.FillStyleChooser;
import jatools.component.chart.component.FillStyleInterface;
import jatools.component.chart.component.LineStyle;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;


/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LineAndSignDialog extends JDialog implements ChangeListener, ActionListener{

    ChangeListener parent;
    LineAndSignPanel lap;
    JButton ok, cancel;
	PreviewPanel view;
	Gc gc = new Gc(Color.WHITE, new Globals());
    LineStyle style;
    FillStyleChooser chooser;
    IconType iconType = new IconType();
   
    public LineAndSignDialog(Component frame, String title, FillStyleChooser chooser, FillStyleInterface init, boolean model) {
        super((JDialog) frame, title, true); 
        /*this.setLocationRelativeTo(frame);*/
        this.chooser = chooser;
		if(init == null){
			style = new LineStyle(true);
		}else{
			style = (LineStyle) init;
		}
		style.setToGc(gc);
		this.chooser.setStyle(style);
        GridBagLayout gbl = new GridBagLayout();        
        getContentPane().setLayout(gbl);        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.BOTH;   
        
        setSize(420, 220);
        
        lap = new LineAndSignPanel(iconType, ((LineStyle)style).full);
        lap.setObject(gc);
        lap.addChangeListener(this);
        
        gbc.weightx =1;
        gbc.weighty = 1;
       /* gbc.weightx =1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        getContentPane().add(tp,gbc);*/
        
        ok = new JButton("确定");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		cancel = new JButton("取消");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		
		gbc.weightx = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		getContentPane().add(lap, gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(gbl);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weighty = 0;
		buttonPanel.add(ok, gbc);
		buttonPanel.add(cancel, gbc);
		gbc.weighty = 1;
		buttonPanel.add(Box.createGlue(), gbc);
		
		getContentPane().add(buttonPanel, gbc);
		
		view = new PreviewPanel();
		getContentPane().add(Box.createGlue(), gbc);
		gbc.weighty = 0;
		getContentPane().add(view, gbc);

        
    }
    public void addChangeListener(ChangeListener l){
		parent = l;
	}
	public void fireChange(Object object) {
		if(style == null){
			style = new LineStyle(true);
		}else{
			style = new LineStyle(style.full);
		}
		style.setIconType(iconType.type);
		style.getFromGc(gc);
		view.setIcon(style.createIcon(CommonFinal.VIEW_ICON_SIZE));
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")){
			chooser.setStyle(style);
		}
		hide();
	}

}
