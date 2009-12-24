/*
 * Created on 2005-11-25
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.IntComponent;
import jatools.component.chart.component.LineStyle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;



/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LinePanel extends Dialog implements ActionListener{

    ColorComponent cclineforecolor;
    JLabel llinestyle;   
    JToggleButton[] buttons;
    ButtonGroup bg;
    IntComponent rclinewidth;
    Gc gc;
    IconType iconType;
    public LinePanel(IconType iconType) {
        this.iconType = iconType;
        GridBagLayout gbl = new GridBagLayout();        
        setLayout(gbl);
        setBorder(CommonFinal.EMPTY_BORDER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        cclineforecolor = new ColorComponent("前景色",null);
        cclineforecolor.addChangeListener(this);
        
        llinestyle = new JLabel("样式");
        llinestyle.setPreferredSize(CommonFinal.LABEL_SIZE);      
        
        bg = new ButtonGroup();       
        
        Box box = Box.createHorizontalBox();
		buttons = new JToggleButton[4];
        Icon[] icons = new Icon[4];
		LineStyle ls = new LineStyle(true);
		ls.setIconType(0);
		for(int i = 0; i < 4; i++){
			ls.setLineStyle(i - 1);
			icons[i] = ls.createIcon(CommonFinal.STYLE_BUTTON_SIZE);
			buttons[i] = new JToggleButton(icons[i]);
			buttons[i].setMargin(new Insets(0, 0, 0, 0));
			buttons[i].addActionListener(this);
			bg.add(buttons[i]);
			box.add(Box.createHorizontalStrut(5));
			box.add(buttons[i]);
		}
        
		box.add(Box.createHorizontalStrut(5));   
        
        rclinewidth = new IntComponent("线宽",0,0,9, "像素");
        rclinewidth.addChangeListener(this);
        
        //1
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        add(cclineforecolor,gbc);
        
        //2
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        add(llinestyle,gbc);
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(box,gbc);
       // add(Box.createGlue(), gbc);      
        
        //3
        gbc.weightx = 1;
        add(rclinewidth,gbc);
        

		gbc.weighty = 1;
		add(Box.createGlue(), gbc);


    }
    
    public void show(){
    	setVals();
    	iconType.type = 0;
    	super.show();
    	fireChange(null);
    }
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        LinePanel ip = new LinePanel(null);
		f.getContentPane().add(ip);
		f.setSize(400, 500);
		f.show();
        
    }
    /* (non-Javadoc)
     * @see com.hufeng.customizer.Dialog#setObject(java.lang.Object)
     */
    public void setObject(Object object) {
        gc = (Gc) object;
    }
    public void getVals() {
    	gc.setLineColor(cclineforecolor.getColor());
    	//System.out.println(cclineforecolor.getColor());
        gc.setLineWidth(rclinewidth.getValue());
        if (buttons[0].isSelected()) {
			gc.setLineStyle(Gc.LINE_SOLID);
		} else if (buttons[1].isSelected()) {
			gc.setLineStyle(Gc.LINE_DASH);
		} else if (buttons[2].isSelected()) {
			gc.setLineStyle(Gc.LINE_DOT);
		} else if (buttons[3].isSelected()) {
			gc.setLineStyle(Gc.LINE_DOT_DASH);
		}
    }
    /* (non-Javadoc)
     * @see com.hufeng.customizer.Dialog#setVals()
     */
    public void setVals() {
    	cclineforecolor.setValue(gc.getLineColor());
    	rclinewidth.setValue(gc.getLineWidth());
    	if (gc.getLineStyle() == Gc.LINE_SOLID) {
			buttons[0].setSelected(true);
		} else if (gc.getLineStyle() == Gc.LINE_DASH) {
			buttons[1].setSelected(true);
		} else if (gc.getLineStyle() == Gc.LINE_DOT) {
			buttons[2].setSelected(true);
		} else if (gc.getLineStyle() == Gc.LINE_DOT_DASH) {
			buttons[3].setSelected(true);
		}
    }
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        fireChange(null);
    }
}
