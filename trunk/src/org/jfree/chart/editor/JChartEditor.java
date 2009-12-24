/*
 * 创建日期 2009-11-14
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package org.jfree.chart.editor;

import jatools.component.chart.Chart;
import jatools.component.chart.ChartFactory;
import jatools.component.chart.CommonFinal;
import jatools.component.chart.applet.ChartUtil;
import jatools.component.chart.component.MoreButton;
import jatools.component.chart.component.ReportComboBox;
import jatools.component.chart.customizer.ChangeListener;
import jatools.component.chart.customizer.ChartTabShell;
import jatools.component.chart.customizer.Tabs;
import jatools.util.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;

import jatools.designer.Main;

/**
 * @author litterbin TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class JChartEditor extends JDialog implements ChangeListener, ActionListener {
	private Chart javaChart;

	Tabs tab;

	ChangeListener pcl;

	Vector support = new Vector();

	JButton okButton;

	/**
	 * DOCUMENT ME!
	 */
	public boolean done;

	// private JComboBox ctype;
	private ReportComboBox typeBox;

	/**
	 * Creates a new ChartEditor object.
	 */
	public JChartEditor() {
	}

	/**
	 * Creates a new ChartEditor object.
	 *
	 * @param f DOCUMENT ME!
	 * @param javaChart DOCUMENT ME!
	 */
	public JChartEditor(Frame f, Chart javaChart) {
		super(f, "图表属性", true);
		this.javaChart = javaChart;
		// this.setTitle("图表属性");
		getContentPane().setLayout(new BorderLayout(2, 2));

		// getContentPane().setLayout(new GridBagLayout());
		JPanel panel = new JPanel();

		/*
		 * GridBagLayout gbl = new GridBagLayout(); panel.setLayout(gbl);
		 */
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		okButton = new MoreButton("确定");
		okButton.setActionCommand("ok");
		okButton.addActionListener(this);

		panel.setLayout(new GridBagLayout());

		JLabel ltype = new JLabel("图表类型");
		ltype.setPreferredSize(CommonFinal.LABEL_SIZE);

		typeBox = new ReportComboBox();
		typeBox.setType((String) javaChart.getProperties().get("chartType"));
		typeBox.addActionListener(this);

		gbc.gridwidth = 1;
		gbc.weightx = 0;
		panel.add(ltype, gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		// ****************
		// panel.add(ctype, gbc);
		panel.add(typeBox, gbc);

		String type1 = (String) javaChart.getProperties().get("chartType");

		if (type1 == null) {
			type1 = "1";
		}

		tab = new ChartTabShell();
		//        tab = CustomizerFactory.createInstance(javaChart.chart.getClass());
		tab.setJavaChart(javaChart, Integer.parseInt(type1));
		// tab.initDataPanel(javaChart);
		tab.addChangeListener(this);

		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p.add(okButton);

		MoreButton cancelButton = new MoreButton("取消");
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		p.add(cancelButton);

		/*
		 * gbc.weightx = 0; gbc.gridwidth = 1; panel.add(ltype, gbc);
		 * gbc.weightx = 1; gbc.gridwidth = GridBagConstraints.REMAINDER;
		 * panel.add(ctype, gbc); gbc.gridwidth = GridBagConstraints.REMAINDER;
		 * panel.add(Box.createGlue(), gbc);
		 *
		 * gbc.weightx = 1; gbc.gridwidth = GridBagConstraints.REMAINDER;
		 * panel.add(tab, gbc);
		 *
		 * gbc.weightx = 1; gbc.gridwidth = 1; panel.add(Box.createGlue(), gbc);
		 * gbc.weightx = 1; gbc.gridwidth = 1; panel.add(Box.createGlue(), gbc);
		 * gbc.weightx = 0; gbc.gridwidth = GridBagConstraints.REMAINDER;
		 * panel.add(okButton, gbc);
		 *
		 * getContentPane().add(panel);
		 */
		getContentPane().add(panel, BorderLayout.NORTH);
		getContentPane().add(tab, BorderLayout.CENTER);
		getContentPane().add(p, BorderLayout.SOUTH);
		setSize(400, 470);
		this.setLocationRelativeTo(f);
	}

	/**
	 * @return 返回 javaChart。
	 * @uml.property name="javaChart"
	 */
	public Chart getJavaChart() {
		return javaChart;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param object DOCUMENT ME!
	 */
	public void fireChange(Object object) {
		if (support.size() > 0) {
			for (int i = 0; i < support.size(); i++) {
				ChangeListener l = ((ChangeListener) support.elementAt(i));
				l.fireChange(null);
			}
		}

		Plot plot = null;
		Map map = new Map();
		map.put("chartType", javaChart.getProperties().get("chartType"));

		if (javaChart.chart != null) {
			plot = javaChart.chart.getPlot();
			map.put("background", ChartUtil.toString((Color) javaChart.chart.getBackgroundPaint()));
			map.put("backgroundOutline", ChartUtil.toString((Color) javaChart.chart
					.getBorderPaint()));

			LegendTitle legend = javaChart.chart.getLegend();

			map.put("legendPosition", legend.getPosition().toString());

			map.put("legendbackground", ChartUtil.toString((Color) legend.getBackgroundPaint()));

			map.put("legendVisible", Boolean.toString(legend.isVisible()));
			map.put("legendItemFont", ChartUtil.toString(legend.getItemFont()));
			map.put("legendItemColor", ChartUtil.toString((Color) legend.getItemPaint()));

			if (!(plot instanceof PiePlot)) {
				CategoryPlot categoryPlot = (CategoryPlot) plot;
				map.put("plotarea", ChartUtil.toString((Color) categoryPlot.getBackgroundPaint()));
				map.put("plotareaOutline", ChartUtil.toString((Color) categoryPlot
						.getOutlinePaint()));

			}

			if (plot instanceof CategoryPlot) {
				CategoryPlot categoryPlot = (CategoryPlot) plot;

				if (categoryPlot.getDomainAxisLocation() == AxisLocation.BOTTOM_OR_LEFT) {
					map.put("axisLocation", "bottom");
				} else {
					map.put("axisLocation", "top");
				}

				if (categoryPlot.getRangeAxisLocation() == AxisLocation.TOP_OR_LEFT) {
					map.put("rangeAxisLocation", "left");
				} else {
					map.put("rangeAxisLocation", "right");
				}

				map.put("domainGridlinesVisible", Boolean.toString(categoryPlot
						.isDomainGridlinesVisible()));
				map.put("domainGridlineColor", ChartUtil.toString((Color) categoryPlot
						.getDomainGridlinePaint()));

				map.put("rangeGridlinesVisible", Boolean.toString(categoryPlot
						.isRangeGridlinesVisible()));
				map.put("rangeGridlineColor", ChartUtil.toString((Color) categoryPlot
						.getRangeGridlinePaint()));

				CategoryAxis domainAxis = categoryPlot.getDomainAxis();

				//        		System.out.println(domainAxis.getCategoryLabelPositions().getLabelPosition(RectangleEdge.TOP).getAngle());
				map.put("axisAngle", String.valueOf(domainAxis.getCategoryLabelPositions()
						.getLabelPosition(RectangleEdge.BOTTOM).getAngle()));

				map.put("axisLabel", domainAxis.getLabel());
				map.put("axisLabelFont", ChartUtil.toString(domainAxis.getLabelFont()));
				map.put("axisLabelColor", ChartUtil.toString((Color) domainAxis.getLabelPaint()));

				map
						.put("axisTickLabelsVisible", Boolean.toString(domainAxis
								.isTickLabelsVisible()));
				map.put("axisTickLabelsFont", ChartUtil.toString(domainAxis.getTickLabelFont()));
				map.put("axisTickLabelsColor", ChartUtil.toString((Color) domainAxis
						.getTickLabelPaint()));

				map.put("axisLineColor", ChartUtil.toString((Color) domainAxis.getAxisLinePaint()));
				map.put("axisTickMarkColor", ChartUtil.toString((Color) domainAxis
						.getTickMarkPaint()));

				NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
				map.put("rangeAxisLabel", rangeAxis.getLabel());
				map.put("rangeAxisLabelFont", ChartUtil.toString(rangeAxis.getLabelFont()));
				map.put("rangeAxisLabelColor", ChartUtil
						.toString((Color) rangeAxis.getLabelPaint()));

				map.put("rangeAxisTickLabelsVisible", Boolean.toString(rangeAxis
						.isTickLabelsVisible()));
				map
						.put("rangeAxisTickLabelsFont", ChartUtil.toString(rangeAxis
								.getTickLabelFont()));
				map.put("rangeAxisTickLabelsColor", ChartUtil.toString((Color) rangeAxis
						.getTickLabelPaint()));

				map.put("rangeAxisLineColor", ChartUtil.toString((Color) rangeAxis
						.getAxisLinePaint()));
				map.put("rangeAxisTickMarkColor", ChartUtil.toString((Color) rangeAxis
						.getTickMarkPaint()));

				if ((DecimalFormat) rangeAxis.getNumberFormatOverride() != null)
					map.put("numberFormat", ((DecimalFormat) rangeAxis.getNumberFormatOverride())
							.toPattern());

				CategoryItemRenderer renderer = categoryPlot.getRenderer();

				if (renderer instanceof LineAndShapeRenderer) {
					map.put("ItemLabelsVisible", ((LineAndShapeRenderer) renderer)
							.getBaseItemLabelsVisible()+"");
				}

				if (renderer instanceof BarRenderer) {
					map.put("ItemLabelsVisible", renderer
							.getBaseItemLabelsVisible()+"");

				}

			}

			if (plot instanceof PiePlot) {

			}

			if (javaChart.chart.getTitle() != null) {
				map.put("titleString", javaChart.chart.getTitle().getText());
				map.put("titleFont", ChartUtil.toString(javaChart.chart.getTitle().getFont()));
				map.put("titleColor", ChartUtil.toString((Color) javaChart.chart.getTitle()
						.getPaint()));
			}

			javaChart.setProperties(javaChart.asProperties(map));

		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main.getInstance().getActiveEditor().repaint();
			}
		});

		// App.getActiveEditor().repaint();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param p DOCUMENT ME!
	 */
	public void addChangeListener(ChangeListener p) {
		support.addElement(p);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public void removeChangeListener(ChangeListener l) {
		support.removeElement(l);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			hide();
			done = true;
		} else if (e.getActionCommand().equals("cancel")) {
			hide();
		} else {
			transferChartType();
		}
	}

	private void transferChartType() {

		this.javaChart.getProperties().put("chartType", typeBox.getType());

		JFreeChart tmpChart = ChartFactory.createInstance(this.javaChart, null);

		boolean canTrans = false;

		if (javaChart.getPlotData() != null) {
			if (tmpChart.getPlot() instanceof CategoryPlot) {
				canTrans = true;
			} else if (tmpChart.getPlot() instanceof XYPlot) {
				canTrans = true;
			} else if (tmpChart.getPlot() instanceof PiePlot) {
				canTrans = true;
			}
		}

		if (canTrans) {

			this.javaChart.clearProperties2();

			javaChart.setReader(javaChart.getReader());
			javaChart.setLabelField(javaChart.getLabelField());
			javaChart.setPlotData(javaChart.getPlotData());
			javaChart.chart = tmpChart;
		} else {

			this.javaChart.clearProperties();

			javaChart.setReader(null);
			javaChart.setLabelField(null);
			javaChart.setPlotData(null);

			javaChart.chart = tmpChart;
		}

		Tabs tmptab = new ChartTabShell();
		tmptab.setJavaChart(javaChart, Integer.parseInt(typeBox.getType()));
		tmptab.addChangeListener(this);

		getContentPane().remove(tab);
		getContentPane().add(tmptab, BorderLayout.CENTER);

		tab = tmptab;
		getContentPane().validate();
		fireChange(null);
	}
}
