package jatools.component.chart;

import jatools.component.chart.applet.ChartUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.Properties;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

public class ChartStyle {
	public ChartStyle(){
		
	}
	public static void setChartStyle(JFreeChart chart,Properties props){
		//标题
		String titleString = props.getProperty("titleString");
		String titleFont = props.getProperty("titleFont");
		String titleColor = props.getProperty("titleColor");
		
		String background = props.getProperty("background");//背景颜色
		String backgroundOutline = props.getProperty("backgroundOutline");//边界线
		String plotarea = props.getProperty("plotarea");//绘图区颜色
		String plotareaOutline = props.getProperty("plotareaOutline");//绘图区线条颜色
		
		//图例
		String legendbackground = props.getProperty("legendbackground");
		String legendVisible = props.getProperty("legendVisible");
		String legendItemFont = props.getProperty("legendItemFont");
		String legendItemColor = props.getProperty("legendItemColor");
		String legendPosition = props.getProperty("legendPosition");
		
		String axisLabel = props.getProperty("axisLabel");
		String axisLabelFont = props.getProperty("axisLabelFont");
		String axisLabelColor = props.getProperty("axisLabelColor");
		
		String rangeAxisLabel = props.getProperty("rangeAxisLabel");
		String rangeAxisLabelFont = props.getProperty("rangeAxisLabelFont");
		String rangeAxisLabelColor = props.getProperty("rangeAxisLabelColor");
		
		String axisTickLabelsVisible = props.getProperty("axisTickLabelsVisible");
		String axisTickLabelsFont = props.getProperty("axisTickLabelsFont");
		String axisTickLabelsColor = props.getProperty("axisTickLabelsColor");
		String axisLineColor = props.getProperty("axisLineColor");
		String axisTickMarkColor = props.getProperty("axisTickMarkColor");
		
		String rangeAxisTickLabelsVisible = props.getProperty("rangeAxisTickLabelsVisible");
		String rangeAxisTickLabelsFont = props.getProperty("rangeAxisTickLabelsFont");
		String rangeAxisTickLabelsColor = props.getProperty("rangeAxisTickLabelsColor");
		String rangeAxisLineColor = props.getProperty("rangeAxisLineColor");
		String rangeAxisTickMarkColor = props.getProperty("rangeAxisTickMarkColor");
		
		String axisLocation = props.getProperty("axisLocation");
		String rangeAxisLocation = props.getProperty("rangeAxisLocation");
		
		String domainGridlinesVisible = props.getProperty("domainGridlinesVisible");
		String domainGridlineColor = props.getProperty("domainGridlineColor");
		
		String rangeGridlinesVisible = props.getProperty("rangeGridlinesVisible");
		String rangeGridlineColor = props.getProperty("rangeGridlineColor");
		
		String ItemLabelsVisible = props.getProperty("ItemLabelsVisible");
		
		
		if(background != null){
			chart.setBackgroundPaint(ChartUtil.getColor(background));
		}
		else{
			chart.setBackgroundPaint(Color.WHITE);
		}
		
		if(backgroundOutline != null){
			chart.setBorderPaint(ChartUtil.getColor(backgroundOutline));//边界线条颜色
//			chart.setBorderStroke(new BasicStroke(10));//边界线条笔触
			chart.setBorderVisible(true);// 边界线条是否可见
		}

		//图片标题
		if(titleString !=null){
			chart.setTitle(titleString);
			chart.getTitle().setFont(ChartUtil.getFont(titleFont));
			chart.getTitle().setPaint(ChartUtil.getColor(titleColor));
		}
		
		//图例
		LegendTitle legend = (LegendTitle) chart.getLegend();
		if(legendbackground != null)
			legend.setBackgroundPaint(ChartUtil.getColor(legendbackground));
		if(legendVisible !=null){
			legend.setVisible(Boolean.parseBoolean(legendVisible));
			legend.setItemFont(ChartUtil.getFont(legendItemFont));
			legend.setItemPaint(ChartUtil.getColor(legendItemColor));
		}
		if(legendPosition != null){
			if(legendPosition.equals("RectangleEdge.BOTTOM"))
				legend.setPosition(RectangleEdge.BOTTOM);
			else if(legendPosition.equals("RectangleEdge.TOP"))
				legend.setPosition(RectangleEdge.TOP);
			else if(legendPosition.equals("RectangleEdge.LEFT"))
				legend.setPosition(RectangleEdge.LEFT);
			else if(legendPosition.equals("RectangleEdge.RIGHT"))
				legend.setPosition(RectangleEdge.RIGHT);
		}
		
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		Plot plot = chart.getPlot();
		plot.setNoDataMessage("无数据显示");

		if (plot instanceof CategoryPlot) {
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();// 图表层
			plot.setBackgroundAlpha(0.6f);
			plot.setForegroundAlpha(0.9F);// 设置透明度
			
			if(plotarea != null){
				plot.setBackgroundPaint(ChartUtil.getColor(plotarea));//绘图区颜色
			}
			
			if(domainGridlinesVisible != null)
				categoryPlot.setDomainGridlinesVisible(Boolean.parseBoolean(domainGridlinesVisible));//设置是否显示垂直网格线
			if(domainGridlineColor != null)
				categoryPlot.setDomainGridlinePaint(ChartUtil.getColor(domainGridlineColor));//设置垂直网格线颜色
			
			if(rangeGridlinesVisible != null)
				categoryPlot.setRangeGridlinesVisible(Boolean.parseBoolean(rangeGridlinesVisible));//设置是否显示水平网格线
			else
				categoryPlot.setRangeGridlinesVisible(false);
			if(rangeGridlineColor != null)
				categoryPlot.setRangeGridlinePaint(ChartUtil.getColor(rangeGridlineColor));//设置水平网格线颜色
			
			
			if(plotareaOutline != null){
				categoryPlot.setOutlinePaint(ChartUtil.getColor(plotareaOutline));//图示边界线条颜色
//			categryPlot.setOutlineStroke(new BasicStroke(10));//   图示边界线条笔触
				categoryPlot.setOutlineVisible(true);
			}
			//设置纵横坐标的显示位置
			if(axisLocation != null){
				if(axisLocation.equals("bottom"))
					categoryPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);//横坐标在底部
				else
					categoryPlot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);//横坐标在顶部
			}
			
			if(rangeAxisLocation != null){
				if(rangeAxisLocation.equals("left"))
					categoryPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);//纵坐标在左边
				else
					categoryPlot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);//纵坐标在右边
			}
			
			// x轴设置
			CategoryAxis domainAxis = categoryPlot.getDomainAxis();
			if(axisLabel != null){
				domainAxis.setLabel(axisLabel);
				domainAxis.setLabelFont(ChartUtil.getFont(axisLabelFont));//轴标题字体
				domainAxis.setLabelPaint(ChartUtil.getColor(axisLabelColor));//轴标题颜色
			}
			
			if(axisTickLabelsVisible != null){
				domainAxis.setTickLabelsVisible(Boolean.parseBoolean(axisTickLabelsVisible));
			}
			
			if(axisTickLabelsFont != null){
				domainAxis.setTickLabelFont(ChartUtil.getFont(axisTickLabelsFont));//轴标尺值字体
			}
			if(axisTickLabelsColor != null){
				domainAxis.setTickLabelPaint(ChartUtil.getColor(axisTickLabelsColor));
			}
			
			if(axisLineColor != null){
				domainAxis.setAxisLinePaint(ChartUtil.getColor(axisLineColor));//轴线颜色
			}
			
			if(axisTickMarkColor != null){
				domainAxis.setTickMarkPaint(ChartUtil.getColor(axisTickMarkColor));//轴标尺颜色
			}
			
			String axisAngle = props.getProperty("axisAngle");
			
			if(axisAngle != null){
				double d = Double.parseDouble(axisAngle);
				if(d > 0.0){
					if(d == 1.5707963267948966)
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
					else
						domainAxis.setCategoryLabelPositions(ChartUtil
								.createRightRotationLabelPositions(-d));
				}
				if(d < -0.0){
					if(d == -1.5707963267948966)
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
					else
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions
								.createUpRotationLabelPositions(-d));
				}
				if(d == 0.0){
					domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
				}
				
			}
			
			//y轴设置
			NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
			
			if(rangeAxisLabel != null){
				rangeAxis.setLabel(rangeAxisLabel);
				rangeAxis.setLabelPaint(ChartUtil.getColor(rangeAxisLabelColor));
				rangeAxis.setLabelFont(ChartUtil.getFont(rangeAxisLabelFont));
			}
			
			if(rangeAxisTickLabelsVisible != null){
				rangeAxis.setTickLabelsVisible(Boolean.parseBoolean(rangeAxisTickLabelsVisible));
			}
			
			if(rangeAxisTickLabelsFont != null){
				rangeAxis.setTickLabelFont(ChartUtil.getFont(rangeAxisTickLabelsFont));//轴标尺值字体
			}
			if(rangeAxisTickLabelsColor != null){
				rangeAxis.setTickLabelPaint(ChartUtil.getColor(rangeAxisTickLabelsColor));
			}
			
			if(rangeAxisLineColor != null){
				rangeAxis.setAxisLinePaint(ChartUtil.getColor(rangeAxisLineColor));//轴线颜色
			}
			
			if(rangeAxisTickMarkColor != null){
				rangeAxis.setTickMarkPaint(ChartUtil.getColor(rangeAxisTickMarkColor));//轴标尺颜色
			}
			
			String numberFormat = props.getProperty("numberFormat");
			if(numberFormat != null)
				rangeAxis.setNumberFormatOverride(new DecimalFormat(numberFormat));
			
			// VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭
			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			
            // 设置item(bar)标签值的位置是在上面还是在bar内
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
			if(renderer instanceof LineAndShapeRenderer){
				if(ItemLabelsVisible != null)
					((LineAndShapeRenderer)renderer).setBaseShapesVisible(Boolean.parseBoolean(ItemLabelsVisible));//显示折线图上的数据标志
			}
//			if(renderer instanceof BarRenderer3D){
//				((BarRenderer3D)renderer).setItemLabelAnchorOffset(10D);// 设置柱形图上的文字偏离值 
//			}
			if(renderer instanceof BarRenderer){
//				((BarRenderer) renderer).setMaximumBarWidth(0.02); 
//				((BarRenderer) renderer).setItemMargin(0.0);  
//				((BarRenderer) renderer).setMinimumBarLength(0.5);
				
			}
			
			if(ItemLabelsVisible != null)
				renderer.setBaseItemLabelsVisible(Boolean.parseBoolean(ItemLabelsVisible));//显示每个柱子上的数据
			
			renderer.setBaseItemLabelFont(new Font("宋体", Font.PLAIN, 12));
		}

		if (plot instanceof PiePlot) {
			// 得到3D饼图的plot对象
			PiePlot piePlot = (PiePlot) chart.getPlot();
			plot.setBackgroundAlpha(0.6f);
			piePlot.setStartAngle(290);// 设置旋转角度
			piePlot.setDirection(Rotation.CLOCKWISE);// 设置旋转方向:顺时针方向
			piePlot.setForegroundAlpha(0.5f);// 设置透明度
			piePlot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));
			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
            "{0}:{1}({2})"));//名称、值、百分比

		}
		
	}
}
