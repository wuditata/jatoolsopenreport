package jatools.component.chart.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

public class MoreButton extends JButton
    {
    	public MoreButton()
    	{
    		
    		this(">>");
    		
    		
    		
    		
    	}
    	
    	public MoreButton(String caption)
    	{
    		super(caption);
    		this.setPreferredSize( new Dimension(70,25));
    		this.setFont(new Font("Dialog",0,12));
    		this.setMaximumSize(getPreferredSize());
    		this.setMinimumSize(getPreferredSize());
    		this.setIconTextGap(0);
    		this.setMargin( new Insets(0,0,0,0));
    	}
    }


