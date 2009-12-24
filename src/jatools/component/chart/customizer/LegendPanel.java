/*
 * Created on 2009-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Legend;
import jatools.component.chart.component.CheckComponent;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.FillStyleChooser;
import jatools.component.chart.component.FillStyleFactory;
import jatools.component.chart.component.FillStyleInterface;
import jatools.component.chart.component.FontComponent;
import jatools.component.chart.component.MoreButton;
import jatools.component.chart.component.RangeComponent;
import jatools.component.chart.component.ZFont;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LegendPanel extends Dialog implements ActionListener {
	PropertyChangeListener myParent;

	CheckComponent ckshowlegend;

	CheckComponent backgroundVis;

	ColorComponent ccbackground;

	RangeComponent xposition, yposition;

	RangeComponent iclegendheight;

	RangeComponent iclegendwidth;

	JLabel ldirection;

	JPanel p;

	LegendTitle legend;

	JRadioButton rvertical, rhorizontal, rleft, rright;

	private JButton mlegendbackground;

	private JButton ftext;

	private ColorComponent ccoutline;

	/**
	 * 
	 */
	public LegendPanel() {

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		setBorder(CommonFinal.EMPTY_BORDER);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		iclegendheight = new RangeComponent("图标高度", 0);
		iclegendheight.addChangeListener(this);
		iclegendwidth = new RangeComponent("图标宽度", 0);
		iclegendwidth.addChangeListener(this);

		p = new JPanel();
		// p.setBorder(new TitledBorder("图例布局"));
		p.setLayout(gbl);

		ldirection = new JLabel("方向");
		ldirection.setPreferredSize(CommonFinal.LABEL_SIZE);

		ckshowlegend = new CheckComponent("显示图例", false);
		ckshowlegend.addChangeListener(this);

		backgroundVis = new CheckComponent("显示背景", false);
		backgroundVis.addChangeListener(this);
		ccbackground = new ColorComponent("背景颜色", null);
		ccbackground.addChangeListener(this);
		ccoutline = new ColorComponent("轮廓线", null);
		ccoutline.addChangeListener(this);

		ftext = new MoreButton("字体");
		ftext.setActionCommand("legend font");
		ftext.addActionListener(this);
		mlegendbackground = new MoreButton("填充样式");
		mlegendbackground.setActionCommand("legend fillstyle");
		mlegendbackground.addActionListener(this);

		// rvertical = new JRadioButton("垂直");
		// rvertical.setHorizontalTextPosition(SwingConstants.LEFT);
		// rvertical.addActionListener(this);
		// rhorizontal = new JRadioButton("水平");
		// rhorizontal.addActionListener(this);
		// rhorizontal.setHorizontalTextPosition(SwingConstants.LEFT);

		rvertical = new JRadioButton("底部");
		rvertical.setHorizontalTextPosition(SwingConstants.LEFT);
		rvertical.addActionListener(this);
		rhorizontal = new JRadioButton("顶部");
		rhorizontal.addActionListener(this);
		rhorizontal.setHorizontalTextPosition(SwingConstants.LEFT);

		rleft = new JRadioButton("左边");
		rleft.setHorizontalTextPosition(SwingConstants.LEFT);
		rleft.addActionListener(this);
		rright = new JRadioButton("右边");
		rright.addActionListener(this);
		rright.setHorizontalTextPosition(SwingConstants.LEFT);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rvertical);
		bg.add(rhorizontal);
		bg.add(rleft);
		bg.add(rright);

		xposition = new RangeComponent("位置   X:", 0.0);
		xposition.addChangeListener(this);
		yposition = new RangeComponent("       Y:", 0.0);
		yposition.addChangeListener(this);

		/*
		 * JLabel l = new JLabel("图例布局"); JSeparator js = new
		 * JSeparator(JSeparator.HORIZONTAL); js.setPreferredSize(new
		 * Dimension(100,2));
		 */

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		add(ckshowlegend, gbc);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(ftext, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 1;
		add(backgroundVis, gbc);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(Box.createGlue(), gbc);
		// 2
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		add(ccbackground, gbc);
		gbc.weightx = 0.0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		// add(mlegendbackground, gbc);
		add(Box.createGlue(), gbc);

		// gbc.gridwidth = 1;
		// gbc.weightx = 1.0;
		// add(ccoutline, gbc);
		// gbc.weightx = 0.0;
		// gbc.gridwidth = GridBagConstraints.REMAINDER;
		// add(Box.createGlue(), gbc);

		// 3
		// p 1

		SepratorPanel sp = new SepratorPanel("图例布局");
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(sp, gbc);

		gbc.weightx = 1;
		// gbc.gridwidth = 1;
		p.add(ldirection, gbc);
		p.add(rvertical, gbc);
		// gbc.gridwidth = GridBagConstraints.REMAINDER;
		p.add(rhorizontal, gbc);
		// gbc.weightx = 1;

		p.add(rleft, gbc);
		p.add(rright, gbc);

		// add(Box.createGlue(), gbc);

		// p 2

		// p.add(iclegendheight, gbc);
		// p 3
		// p.add(iclegendwidth, gbc);
		// p 4
		// p.add(xposition, gbc);
		// p 5
		// p.add(yposition, gbc);

		add(p, gbc);

		gbc.weighty = 1;
		add(Box.createGlue(), gbc);

	}

	public void show() {
		setVals();
		super.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Component f = getParent();
		while (!(f instanceof JDialog))
			f = f.getParent();
		if (e.getActionCommand().equals("legend fillstyle")) {
			// FillStyleInterface style = FillStyleChooser.showDialog(f, "填充样式",
			// FillStyleFactory.createFillStyle(legend.getBackgroundGc()),
			// 0);
			// ccbackground.setValue(style);
		} else if (e.getActionCommand().equals("legend font")) {
			if (FontComponent.getDefault().showChooser(this)) {
				ZFont font = (ZFont) FontComponent.getDefault().getValue();
				Color color = font.getForeColor();
				Font ff = new Font(font.getFace(), font.getStyle(), font.getSize());
				javaChart.getChart().getLegend().setItemFont(ff);
				javaChart.getChart().getLegend().setItemPaint(color);
			}
		}

		fireChange(null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hufeng.customizer.Dialog#setObject(java.lang.Object)
	 */
	public void setObject(Object object) {
		// legend = (LegendTitle) object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hufeng.customizer.Dialog#getVals()
	 */
	public void getVals() {
		javaChart.getChart().getLegend().setVisible(ckshowlegend.getValue());

		javaChart.getChart().getLegend().setBackgroundPaint(ccbackground.getColor());

		if (rvertical.isSelected()) {
			javaChart.getChart().getLegend().setPosition(RectangleEdge.BOTTOM);
		} else if (rhorizontal.isSelected()) {
			javaChart.getChart().getLegend().setPosition(RectangleEdge.TOP);
		} else if (rleft.isSelected()) {
			javaChart.getChart().getLegend().setPosition(RectangleEdge.LEFT);
		} else if (rright.isSelected()) {
			javaChart.getChart().getLegend().setPosition(RectangleEdge.RIGHT);
		}

		volidateEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hufeng.customizer.Dialog#setVals()
	 */
	public void setVals() {

		if (javaChart.getChart().getLegend().getPosition() == RectangleEdge.BOTTOM) {
			rvertical.setSelected(true);
		} else if (javaChart.getChart().getLegend().getPosition() == RectangleEdge.TOP) {
			rhorizontal.setSelected(true);
		} else if (javaChart.getChart().getLegend().getPosition() == RectangleEdge.LEFT) {
			rleft.setSelected(true);
		} else if (javaChart.getChart().getLegend().getPosition() == RectangleEdge.RIGHT) {
			rright.setSelected(true);
		}

		ckshowlegend.setValue(javaChart.getChart().getLegend().isVisible());
		ccbackground.setValue((Color) javaChart.getChart().getLegend().getBackgroundPaint());

		volidateEnabled();
	}

	private void volidateEnabled() {
		if (ckshowlegend.getValue()) {
			ccbackground.setEnabled(backgroundVis.getValue());
		} else {
			ccbackground.setEnabled(false);
		}
		ftext.setEnabled(ckshowlegend.getValue());
		backgroundVis.setEnabled(ckshowlegend.getValue());
		ldirection.setEnabled(ckshowlegend.getValue());
		rvertical.setEnabled(ckshowlegend.getValue());
		rhorizontal.setEnabled(ckshowlegend.getValue());
		rleft.setEnabled(ckshowlegend.getValue());
		rright.setEnabled(ckshowlegend.getValue());

		xposition.setEnabled(ckshowlegend.getValue());
		yposition.setEnabled(ckshowlegend.getValue());
		iclegendheight.setEnabled(ckshowlegend.getValue());
		iclegendwidth.setEnabled(ckshowlegend.getValue());
	}
}
