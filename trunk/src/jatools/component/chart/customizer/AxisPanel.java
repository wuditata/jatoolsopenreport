/*
 * Created on 2009-11-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.applet.ChartUtil;
import jatools.component.chart.component.AbstractComponent;
import jatools.component.chart.component.CheckComponent;
import jatools.component.chart.component.ColorComponent;
import jatools.component.chart.component.FillStyleChooser;
import jatools.component.chart.component.FillStyleInterface;
import jatools.component.chart.component.FontComponent;
import jatools.component.chart.component.FormatComponent;
import jatools.component.chart.component.IntComponent;
import jatools.component.chart.component.LineStyle;
import jatools.component.chart.component.MoreButton;
import jatools.component.chart.component.StringComponent;
import jatools.component.chart.component.ZFont;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AxisPanel extends Dialog implements ActionListener, ItemListener {
	StringComponent scaxistitle;

	CheckComponent ckshowlabel;

	CheckComponent ckshowgrid;

	ColorComponent ccaxis;

	ColorComponent ccgrid;

	ColorComponent ccscale;

	JButton faxistitle;

	JComboBox caxisposition;

	JPanel p;

	JLabel axisposition;

	//    AxisInterface axis;
	private MoreButton faxislabel;

	int topBottomOffset;

	private IntComponent axisAngle;

	private MoreButton bscale;

	private CheckComponent rotator;

	private AbstractComponent dataFormat;

	private JPanel p3;

	private MoreButton blinestyle;

	ZFont faxistitleFont = new ZFont("Dialog", 0, 12, Color.black, null);

	ZFont faxislabelFont = new ZFont("Dialog", 0, 12, Color.black, null);

	String axis;

	//private MoreButton blinestyle;
	/**
	 * DOCUMENT ME!
	 */
	public void initCustomizer() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		setBorder(CommonFinal.EMPTY_BORDER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		scaxistitle = new StringComponent("轴标题", null);
		scaxistitle.addChangeListener(this);

		ckshowlabel = new CheckComponent("显示标签", false);
		ckshowlabel.addChangeListener(this);
		ckshowgrid = new CheckComponent("显示网格线", false);
		ckshowgrid.addChangeListener(this);

		ccaxis = new ColorComponent("轴线颜色", null);
		ccaxis.addChangeListener(this);
		ccgrid = new ColorComponent("网格线颜色", null);
		ccgrid.addChangeListener(this);
		ccscale = new ColorComponent("主刻度颜色", null);
		ccscale.addChangeListener(this);
		axisAngle = new IntComponent("标签角度", 0, -90, 89, "度");
		axisAngle.addChangeListener(this);

		faxistitle = new MoreButton("字体");
		faxistitle.setActionCommand("title font");
		faxistitle.addActionListener(this);

		faxislabel = new MoreButton("字体");
		faxislabel.setActionCommand("label font");
		faxislabel.addActionListener(this);

		rotator = new CheckComponent("标题反转", false);
		rotator.addChangeListener(this);

		//        if (axis instanceof DateAxis) {
		//            dataFormat = FormatComponent.getDateFormatInstance("日期格式");
		//        } else {
		dataFormat = FormatComponent.getDecimalFormatInstance("数据格式");
		//        }

		dataFormat.addChangeListener(this);

		bscale = new MoreButton("轴缩放");
		bscale.setActionCommand("axis scale");
		bscale.addActionListener(this);

		/*bformat = new MoreButton("数据样式");
		 bformat.setActionCommand("axis format");
		 bformat.addActionListener(this);*/
		blinestyle = new MoreButton("线形样式");
		blinestyle.setActionCommand("line style");
		blinestyle.addActionListener(this);

		caxisposition = new JComboBox();

		Plot plot = javaChart.chart.getPlot();
		if (plot instanceof CategoryPlot) {
			CategoryPlot categoryPlot = (CategoryPlot) plot;
			if (axis.equals("xaxis")) {
				if ((categoryPlot.getDomainAxisLocation() == AxisLocation.BOTTOM_OR_LEFT)) {
					caxisposition.addItem("底部");
					caxisposition.addItem("顶部");
				} else {
					caxisposition.addItem("顶部");
					caxisposition.addItem("底部");
				}
			} else if (axis.equals("yaxis")) {
				if ((categoryPlot.getRangeAxisLocation() == AxisLocation.TOP_OR_LEFT)) {
					caxisposition.addItem("左边");
					caxisposition.addItem("右边");
				} else {
					caxisposition.addItem("右边");
					caxisposition.addItem("左边");
				}
			}

		}

		caxisposition.addItemListener(this);

		p = new JPanel();
		// p2 = new JPanel();
		p3 = new JPanel();

		// 1
		// add(laxistitle,gbc);
		gbc.weightx = 1;
		// gbc.gridwidth =2;
		add(scaxistitle, gbc);
		gbc.weightx = 0.0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(faxistitle, gbc);

		gbc.weightx = 1;
		gbc.gridwidth = 1;
		add(rotator, gbc);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(bscale, gbc);

		// 2
		gbc.weightx = 1;
		gbc.gridwidth = 1;
		add(ckshowlabel, gbc);
		gbc.weightx = 0.0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(faxislabel, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		add(axisAngle, gbc);

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		add(dataFormat, gbc);

		// 3
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(ccaxis, gbc);

		gbc.weightx = 1;
		add(ccscale, gbc);

		// 6
		axisposition = new JLabel("轴位置");
		axisposition.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
		p.setLayout(new GridBagLayout());
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		p.add(axisposition, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		p.add(caxisposition, gbc);

		add(p, gbc);

		add(ckshowgrid, gbc);

		gbc.weightx = 1;
		gbc.gridwidth = 1;
		add(ccgrid, gbc);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		//        add(blinestyle, gbc);

		/*gbc.gridwidth = GridBagConstraints.REMAINDER;
		 gbc.weightx = 0;
		 add(blinestyle, gbc);*/
		gbc.weighty = 1;
		add(Box.createGlue(), gbc);

		if (axis.equals("xaxis")) {
			dataFormat.setEnabled(false);
		}

		rotator.setEnabled(false);

	}

	/**
	 * DOCUMENT ME!
	 */
	public void show() {
		setVals();
		super.show();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("axis scale")) {
			Component c = getParent();

			while (!(c instanceof JDialog)) {
				c = c.getParent();
			}

			//            AxisScaleDialog.showDialog(c, "轴缩放设置", axis, this);
		} else if (e.getActionCommand().equals("line style")) {
			Component c = getParent();

			while (!(c instanceof JDialog)) {
				c = c.getParent();
			}

			FillStyleInterface style = new LineStyle(false);
			style = FillStyleChooser.showDialog(c, "线形样式", style, 1);
			ccgrid.setValue(style);
		} else if (e.getActionCommand().equals("title font")
				|| e.getActionCommand().equals("label font")) {
			if (FontComponent.getDefault().showChooser(this)) {

				//
				if (e.getActionCommand().equals("title font")) {
					faxistitleFont = (ZFont) FontComponent.getDefault().getValue();
				} else if (e.getActionCommand().equals("label font")) {
					faxislabelFont = (ZFont) FontComponent.getDefault().getValue();
				}

			}
		}

		fireChange(null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see chart.panel.Dialog#setObject(java.lang.Object)
	 */
	/**
	 * DOCUMENT ME!
	 *
	 * @param object DOCUMENT ME!
	 */
	public void setObject(Object object) {
		axis = (String) object;
		initCustomizer();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see chart.panel.Dialog#getVals()
	 */
	/**
	 * DOCUMENT ME!
	 */
	public void getVals() {

		Plot plot = javaChart.getChart().getPlot();
		if (plot instanceof CategoryPlot) {
			CategoryPlot categoryPlot = (CategoryPlot) plot;

			if (axis.equals("xaxis")) {
				String si = caxisposition.getSelectedItem().toString();
				if (si != null) {
					if (si.equals("底部"))
						categoryPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
					else
						categoryPlot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);
				}

				categoryPlot.setDomainGridlinesVisible(ckshowgrid.getValue());
				if (ccgrid.getColor() != null)
					categoryPlot.setDomainGridlinePaint(ccgrid.getColor());
				else
					categoryPlot.setDomainGridlinePaint(Color.BLACK);

				CategoryAxis domainAxis = categoryPlot.getDomainAxis();
				domainAxis.setLabel((String) scaxistitle.getValue());
				domainAxis.setLabelFont(new Font(faxistitleFont.getFace(), faxistitleFont
						.getStyle(), faxistitleFont.getSize()));
				domainAxis.setLabelPaint(faxistitleFont.getForeColor());
				domainAxis.setTickLabelsVisible(ckshowlabel.getValue());

				domainAxis.setTickLabelFont(new Font(faxislabelFont.getFace(), faxislabelFont
						.getStyle(), faxislabelFont.getSize()));
				domainAxis.setTickLabelPaint(faxislabelFont.getForeColor());

				if (axisAngle.getValue() > 0) {
					if (axisAngle.getValue() == 90)
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
					else
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions
								.createUpRotationLabelPositions(axisAngle.getValue()
										* java.lang.Math.PI / 180.0));
				} else if (axisAngle.getValue() < 0) {
					if (axisAngle.getValue() == -90)
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
					else
						domainAxis.setCategoryLabelPositions(ChartUtil
								.createRightRotationLabelPositions(axisAngle.getValue()
										* java.lang.Math.PI / 180.0));
				} else {
					domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
				}

				domainAxis.setAxisLinePaint(ccaxis.getColor());
				domainAxis.setTickMarkPaint(ccscale.getColor());

			} else if (axis.equals("yaxis")) {
				String si = caxisposition.getSelectedItem().toString();
				if (si != null) {
					if (si.equals("左边"))
						categoryPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
					else
						categoryPlot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
				}

				categoryPlot.setRangeGridlinesVisible(ckshowgrid.getValue());
				if (ccgrid.getColor() != null)
					categoryPlot.setRangeGridlinePaint(ccgrid.getColor());
				else
					categoryPlot.setRangeGridlinePaint(Color.BLACK);

				NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
				rangeAxis.setLabel((String) scaxistitle.getValue());
				rangeAxis.setLabelFont(new Font(faxistitleFont.getFace(),
						faxistitleFont.getStyle(), faxistitleFont.getSize()));
				rangeAxis.setLabelPaint(faxistitleFont.getForeColor());
				rangeAxis.setTickLabelsVisible(ckshowlabel.getValue());

				rangeAxis.setTickLabelFont(new Font(faxislabelFont.getFace(), faxislabelFont
						.getStyle(), faxislabelFont.getSize()));
				rangeAxis.setTickLabelPaint(faxislabelFont.getForeColor());
				rangeAxis.setAxisLinePaint(ccaxis.getColor());
				rangeAxis.setTickMarkPaint(ccscale.getColor());
				if (!"自定义...".equals((String) dataFormat.getValue()))
					rangeAxis.setNumberFormatOverride(new DecimalFormat((String) dataFormat
							.getValue()));

			}

		}

		volidateEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chart.panel.Dialog#setVals()
	 */
	/**
	 * DOCUMENT ME!
	 */
	public void setVals() {

		Plot plot = javaChart.chart.getPlot();

		if (plot instanceof CategoryPlot) {
			CategoryPlot categoryPlot = (CategoryPlot) plot;
			if (axis.equals("xaxis")) {
				ckshowgrid.setValue(categoryPlot.isDomainGridlinesVisible());
				ccgrid.setValue((Color) categoryPlot.getDomainGridlinePaint());

				CategoryAxis domainAxis = categoryPlot.getDomainAxis();
				scaxistitle.setValue(domainAxis.getLabel());
				faxistitleFont.setFace(domainAxis.getLabelFont().getFamily());
				faxistitleFont.setStyle(domainAxis.getLabelFont().getStyle());
				faxistitleFont.setSize(domainAxis.getLabelFont().getSize());
				faxistitleFont.setForeColor((Color) domainAxis.getLabelPaint());

				ckshowlabel.setValue(domainAxis.isTickLabelsVisible());
				faxislabelFont.setFace(domainAxis.getTickLabelFont().getFamily());
				faxislabelFont.setStyle(domainAxis.getTickLabelFont().getStyle());
				faxislabelFont.setSize(domainAxis.getTickLabelFont().getSize());
				faxislabelFont.setForeColor((Color) domainAxis.getTickLabelPaint());

				ccaxis.setValue((Color) domainAxis.getAxisLinePaint());
				ccscale.setValue((Color) domainAxis.getTickMarkPaint());
			} else if (axis.equals("yaxis")) {
				ckshowgrid.setValue(categoryPlot.isRangeGridlinesVisible());
				ccgrid.setValue((Color) categoryPlot.getRangeGridlinePaint());

				NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();

				scaxistitle.setValue(rangeAxis.getLabel());
				faxistitleFont.setFace(rangeAxis.getLabelFont().getFamily());
				faxistitleFont.setStyle(rangeAxis.getLabelFont().getStyle());
				faxistitleFont.setSize(rangeAxis.getLabelFont().getSize());
				faxistitleFont.setForeColor((Color) rangeAxis.getLabelPaint());

				ckshowlabel.setValue(rangeAxis.isTickLabelsVisible());
				faxislabelFont.setFace(rangeAxis.getTickLabelFont().getFamily());
				faxislabelFont.setStyle(rangeAxis.getTickLabelFont().getStyle());
				faxislabelFont.setSize(rangeAxis.getTickLabelFont().getSize());
				faxislabelFont.setForeColor((Color) rangeAxis.getTickLabelPaint());

				ccaxis.setValue((Color) rangeAxis.getAxisLinePaint());
				ccscale.setValue((Color) rangeAxis.getTickMarkPaint());
				if (rangeAxis.getNumberFormatOverride() != null)
					dataFormat.setValue(((DecimalFormat) rangeAxis.getNumberFormatOverride())
							.toPattern());

			}

		}

		volidateEnabled();
	}

	private void volidateEnabled() {
		axisAngle.setEnabled(ckshowlabel.getValue());
		faxislabel.setEnabled(ckshowlabel.getValue());
		ccgrid.setEnabled(ckshowgrid.getValue());
		blinestyle.setEnabled(ckshowgrid.getValue());

		if (scaxistitle.getValue() == null || ((String) scaxistitle.getValue()).equals("")) {
			faxistitle.setEnabled(false);
			rotator.setEnabled(false);
		} else {
			faxistitle.setEnabled(true);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		AxisPanel ip = new AxisPanel();
		f.getContentPane().add(ip);
		f.setSize(600, 500);
		f.show();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void itemStateChanged(ItemEvent e) {
		fireChange(null);
	}

}
