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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;



/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SignPanel extends Dialog implements ActionListener{

    ColorComponent ccsignforecolor;
    JLabel lsignstyle;
    JToggleButton[] buttons;
    IntComponent rcsignwidth;
    ButtonGroup bg;
    JButton bfillstyle,bpicture;
    JLabel lpicture;
    Gc gc;
    IconType iconType;
    public SignPanel(IconType iconType) {
        this.iconType = iconType;
        GridBagLayout gbl = new GridBagLayout();        
        setLayout(gbl);
        setBorder(CommonFinal.EMPTY_BORDER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        ccsignforecolor = new ColorComponent("前景色",null);
        ccsignforecolor.addChangeListener(this);
        
        lsignstyle = new JLabel("样式");
        lsignstyle.setPreferredSize(CommonFinal.LABEL_SIZE);
        
        bg = new ButtonGroup();
        
        Box box = Box.createHorizontalBox();
		buttons = new JToggleButton[5];
		Icon[] icons = new Icon[5];
		LineStyle line = new LineStyle(true);
		line.setIconType(1);
		for(int i = 0; i < 5; i++){
			line.setMarkerStyle(i - 1);
			line.setMarkerSize(CommonFinal.STYLE_BUTTON_SIZE.width / 2);
			icons[i] = line.createIcon(CommonFinal.STYLE_BUTTON_SIZE);
			buttons[i] = new JToggleButton(icons[i]);
			buttons[i].setMargin(new Insets(0, 0, 0, 0));
			buttons[i].addActionListener(this);
			bg.add(buttons[i]);
			box.add(Box.createHorizontalStrut(1));
			box.add(buttons[i]);
		}
		box.add(Box.createHorizontalStrut(5));    
        
        
        rcsignwidth = new IntComponent("大小",0,0,29, "像素");
        rcsignwidth.addChangeListener(this);
        
        //1
        gbc.weightx = 1; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(rcsignwidth, gbc);
        //2
        
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        add(lsignstyle,gbc);
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(box, gbc);  
        
        //3
        //add(rcsignwidth,gbc);
        
        
		gbc.weighty = 1;
		add(Box.createGlue(), gbc);


    }
    
    public void show(){
    	setVals();
    	iconType.type = 1;
    	super.show();
    	fireChange(null);
    }
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        SignPanel ip = new SignPanel(null);
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
    /* (non-Javadoc)
     * @see com.hufeng.customizer.Dialog#getVals()
     */
    public void getVals() {
        gc.setMarkerSize(rcsignwidth.getValue());
        if (buttons[0].isSelected()) {
			gc.setMarkerStyle(Gc.MK_NONE);
		} else if (buttons[1].isSelected()) {
			gc.setMarkerStyle(Gc.MK_SQUARE);
		} else if (buttons[2].isSelected()) {
			gc.setMarkerStyle(Gc.MK_DIAMOND);
		} else if (buttons[3].isSelected()) {
			gc.setMarkerStyle(Gc.MK_CIRCLE);
		}else if (buttons[4].isSelected()) {
			gc.setMarkerStyle(Gc.MK_TRIANGLE);
		}
    }
    /* (non-Javadoc)
     * @see com.hufeng.customizer.Dialog#setVals()
     */
    public void setVals() {
    	rcsignwidth.setValue(gc.getMarkerSize());
    	if (gc.getMarkerStyle() == Gc.MK_NONE) {
			buttons[0].setSelected(true);
		} else if (gc.getMarkerStyle() == Gc.MK_SQUARE) {
			buttons[1].setSelected(true);
		} else if (gc.getMarkerStyle() == Gc.MK_DIAMOND) {
			buttons[2].setSelected(true);
		} else if (gc.getMarkerStyle() == Gc.MK_CIRCLE) {
			buttons[3].setSelected(true);
		}else if (gc.getMarkerStyle() == Gc.MK_TRIANGLE) {
			buttons[4].setSelected(true);
		}
    }
	public void actionPerformed(ActionEvent e) {
		fireChange(null);
	}
}
