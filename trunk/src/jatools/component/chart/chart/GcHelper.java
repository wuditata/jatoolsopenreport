/*
 * Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Visual Engineering, Inc.
 * Use is subject to license terms.
 */
package jatools.component.chart.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ReplicateScaleFilter;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

/**
 * An extended Gc class that implements some Java2d features, such as linestyle.
 */
public class GcHelper {

	// line styles
	static float dashArray[] = { 5.0f };

	static float dotArray[] = { 2.0f };

	static float dotDashArray[] = { 2.0f, 2.0f, 5.0f, 2.0f };

	float lineStyles[][] = { dashArray, dotArray, dotDashArray };

	// gradient and pattern info
	Color secondaryFillColor = Color.white;

	GradientPaint gradient;

	GradientPaint userGradient = null;

	int gradientWidth = -1; // we need to adjust this on the fly to build a new

	// gradient when bars are a different width or height.

	// some predefined gradients
	public final static int GRAD_LEFT_RIGHT_MIRROR = 0;

	public final static int GRAD_TOP_BOTTOM_MIRROR = 1;

	public final static int GRAD_TOP_BOTTOM = 2;

	public final static int GRAD_LEFT_RIGHT = 3;

	protected int gradientType = 2;

	TexturePaint texture;

	TexturePaint userTexture;

	Stroke stroke;

	Stroke userStroke;

	// some predefined textures
	public final static int TEXTURE_IMAGE = -1;

	public final static int TEXTURE_H_STRIPE = 0;

	public final static int TEXTURE_V_STRIPE = 1;

	public final static int TEXTURE_DOWN_STRIPE = 2;

	public final static int TEXTURE_UP_STRIPE = 3;

	public final static int TEXTURE_CROSS_STRIPE = 4;

	// protected int textureType = TEXTURE_IMAGE;
	protected int textureType = TEXTURE_H_STRIPE;

	Gc baseGc = null;

	/**
	 * Constructs a Gc linestyles helper class with this base Gc. Creation date:
	 * (1/21/2002 2:48:48 PM)
	 * 
	 * @param gc
	 *            javachart.chart.Gc
	 */
	public GcHelper(Gc gc) {
		this.baseGc = gc;
	}

	private void buildGradient(int width) {
		if (userGradient != null) {
			gradient = userGradient;
			return;
		}
		gradientWidth = width;
		switch (gradientType) {
		case GRAD_LEFT_RIGHT_MIRROR:
			gradient = new GradientPaint(0, 0, baseGc.fillColor,
					gradientWidth / 2, 0, secondaryFillColor, true); // true
			// means
			// to
			// mirror
			// pattern
			break;
		case GRAD_LEFT_RIGHT:
			gradient = new GradientPaint(0, 0, baseGc.fillColor, gradientWidth,
					0, secondaryFillColor, false);
			break;
		case GRAD_TOP_BOTTOM_MIRROR:
			gradient = new GradientPaint(0, 0, baseGc.fillColor, 0,
					gradientWidth / 2, secondaryFillColor, true);
			break;
		case GRAD_TOP_BOTTOM:
			gradient = new GradientPaint(0, 0, baseGc.fillColor, 0,
					gradientWidth, secondaryFillColor, false);
			break;
		}
	}

	/**
	 * Builds a TexturePaint as required by this Helper. Creation date:
	 * (1/22/2002 3:18:46 PM)
	 */
	protected void buildTexture() {
		int sz = 6;
		if (texture != null)
			return; // already constructed.

		if(baseGc.imageBytes != null){
			ImageIcon icon = new ImageIcon(baseGc.imageBytes);
			
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			img.getGraphics().drawImage(icon.getImage(), 0, 0, null);
			baseGc.image = img;
		}
		
		if (baseGc.image != null && textureType == TEXTURE_IMAGE) { // build a
			// texturePaint
			// image
			// property

			BufferedImage img = null;

			if (baseGc.image.getWidth(null) > baseGc.getGlobals().getMaxX()
					&& baseGc.image.getHeight(null) > baseGc.getGlobals()
							.getMaxY()) {
				ImageFilter resizeFilter = new ReplicateScaleFilter(baseGc
						.getGlobals().maxX, baseGc.getGlobals().maxY);
				FilteredImageSource source = new FilteredImageSource(
						baseGc.image.getSource(), resizeFilter);

				baseGc.image = Toolkit.getDefaultToolkit().createImage(source);
			}
			int xSize = baseGc.image.getWidth(null);
			int ySize = baseGc.image.getHeight(null);
			if ((xSize < 1) || (ySize < 1))
				return;

			if (baseGc.image instanceof BufferedImage) {
				img = (BufferedImage) baseGc.image;
			} else { // convert to BufferedImage
				System.out.println("doing conversion");
				img = new BufferedImage(xSize, ySize,
						BufferedImage.TYPE_INT_RGB);
				img.getGraphics().drawImage(baseGc.image, 0, 0, null);
			}
			Rectangle r = new Rectangle(0, 0, xSize, ySize);
			texture = new TexturePaint((BufferedImage) img, r);
			return;
		}

		// build a text by drawing
		BufferedImage img = new BufferedImage(sz, sz,
				BufferedImage.TYPE_INT_RGB);
		Graphics ig = img.createGraphics();
		if (textureType == TEXTURE_IMAGE)
			ig.setColor(baseGc.fillColor);
		else
			ig.setColor(secondaryFillColor);
		ig.fillRect(0, 0, sz, sz);
		ig.setColor(baseGc.fillColor);
		switch (textureType) {
		case TEXTURE_H_STRIPE: {
			ig.drawLine(0, sz / 2, sz, sz / 2);
		}
			break;
		case TEXTURE_V_STRIPE: {
			ig.drawLine(sz / 2, 0, sz / 2, sz);
		}
			break;
		case TEXTURE_DOWN_STRIPE: {
			ig.drawLine(0, 0, sz - 1, sz - 1);
		}
			break;
		case TEXTURE_UP_STRIPE: {
			ig.drawLine(0, sz - 1, sz - 1, 0);
		}
			break;
		case TEXTURE_CROSS_STRIPE: {
			ig.drawLine(0, sz - 1, sz - 1, 0);
			ig.drawLine(0, 0, sz, sz);
		}
			break;

		}
		Rectangle r = new Rectangle(0, 0, sz, sz);
		texture = new TexturePaint(img, r);

	}

	/**
	 * Draws a line in the baseGc's linestyle, colors and width. Uses the user
	 * defined Stroke, if available. Creation date: (1/29/2002 3:46:50 PM)
	 * 
	 * @param g
	 *            java.awt.Graphics
	 * @param p1
	 *            java.awt.Point
	 * @param p2
	 *            java.awt.Point
	 */
	public void drawLine(Graphics g, Point p1, Point p2) {
		Point[] points = new Point[2];
		points[0] = p1;
		points[1] = p2;
		drawPoly2D((Graphics2D) g, points);
	}

	/**
	 * Draws a polyline in the baseGc's linestyle and width
	 * 
	 * @param g
	 *            Graphics2D
	 * @param points
	 *            java.awt.pts[]
	 */
	public final void drawPoly2D(Graphics2D g, Point[] pts) {

		if (userStroke != null)
			stroke = userStroke;

		if (stroke == null) {
			try {
				if (baseGc.lineStyle != Gc.LINE_SOLID) {
					stroke = new BasicStroke((float) baseGc.lineWidth,
							BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
							10.0f, lineStyles[baseGc.lineStyle], 0.0f);
				} else
					stroke = new BasicStroke((float) baseGc.lineWidth);
			} catch (Exception e) { // no line style?
				stroke = new BasicStroke((float) baseGc.lineWidth);
			}
		}

		Stroke saveStroke = g.getStroke();
		g.setStroke(stroke);

		int[] x = new int[pts.length];
		int[] y = new int[pts.length];
		for (int i = 0; i < pts.length; i++) {
			/*
			 * g.drawLine(pts[i-1].x, baseGc.globals.maxY - pts[i-1].y,
			 * pts[i].x, baseGc.globals.maxY - pts[i].y);
			 */
			x[i] = pts[i].x;
			y[i] = baseGc.globals.maxY - pts[i].y;
		}
		g.drawPolyline(x, y, pts.length);
		g.setStroke(saveStroke);

	}

	/**
	 * Floating point precision polygon fill. Primarily used for pie edges
	 * 
	 * @param g
	 *            The affected graphics
	 * @param pts
	 *            Array of polygon's points
	 */
	public void drawPolygon(Graphics g, float[] x, float[] y) {

		GeneralPath shape = new GeneralPath();
		shape.moveTo(x[0], baseGc.globals.maxY - y[0]);
		for (int i = 1; i < x.length; i++) {
			shape.lineTo(x[i], baseGc.globals.maxY - y[i]);
		}
		if (baseGc.fillStyle == Gc.FILL_TEXTURE) {
			buildTexture();
			((Graphics2D) g).setPaint(texture);
		} else if (baseGc.fillStyle == Gc.FILL_GRADIENT) {
			Point[] pts = new Point[x.length];
			for (int i = 0; i < x.length; i++) {
				pts[i] = new Point(Math.round(x[i]), Math.round(y[i]));
			}
			fillPolygonGradient((Graphics2D) g, pts);
		} else {
			g.setColor(baseGc.fillColor);
		}
		if(!(baseGc.fillColor == Gc.TRANSPARENT && baseGc.fillStyle == Gc.FILL_SOLID))
		((Graphics2D) g).fill(shape);

		if (baseGc.outlineFills) {
			if(baseGc.lineColor != Gc.TRANSPARENT){
			shape.closePath();
			g.setColor(baseGc.lineColor);
			((Graphics2D) g).draw(shape);
			}
		}
	}

	/**
	 * doesn't draw the closing line when outlining (this method is usually used
	 * for pie slices...) of points in chart's coordinate system.
	 * 
	 * @param g
	 *            The affected graphics
	 * @param pts
	 *            Array of polygon's points
	 */
	public void drawPolygon(Graphics g, Point pts[]) {
		/*
		 * int xarr[]; int yarr[];
		 */

		if (baseGc.fillStyle == Gc.FILL_GRADIENT)
			fillPolygonGradient((Graphics2D) g, pts);
		else
			fillPolygonTexture((Graphics2D) g, pts);

		if (baseGc.outlineFills) {
			baseGc.drawPolyline(g, pts);
			baseGc.drawLine(g, pts[0], pts[pts.length - 1]);
		}
	}

	/**
	 * Draws a line in the baseGc's linestyle and width.
	 * 
	 * @param g
	 *            java.awt.Graphics
	 * @param points
	 *            java.awt.Point[]
	 */
	public void drawPolyline(Graphics g, Point[] points) {
		drawPoly2D((Graphics2D) g, points);
	}

	/**
	 * Calculates alignment and rotates text in Graphics2D environments.
	 * Creation date: (1/24/2002 11:28:15 AM)
	 * 
	 * @param g
	 *            java.awt.Graphics
	 * @param startx
	 *            int
	 * @param starty
	 *            int
	 * @param alignment
	 *            int
	 * @param angle
	 *            int
	 * @param fm
	 *            java.awt.FontMetrics
	 * @param str
	 *            java.lang.String
	 * @param globals
	 *            javachart.chart.Globals
	 */
	public static void drawSmartString(Graphics g, int startx, int starty,
			int alignment, int angle, FontMetrics fm, String str,
			Globals globals) {
		Rectangle extent;
		int shiftX = 0, shiftY = 0, adjAngle = 0;

		if (g.getColor() == Gc.TRANSPARENT)
			return;

		StringTokenizer strtoke = new StringTokenizer(str, Gc.LINE_BREAK);
		int lines = strtoke.countTokens();
		String currentLine;
		int lineHeight = fm.getMaxAscent();

		switch (alignment) {
		case Gc.keepBELOW: {
			if (angle == 0) {
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx
							- (int) (fm.stringWidth(currentLine) / 2), starty
							- (lineHeight * 1) + shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				return;
			}
			if ((angle < -90) || (angle >= 90)) {
				adjAngle = 90;
				double theta = -(adjAngle / 360d) * Math.PI * 2;
				((Graphics2D) g).rotate(theta, startx, globals.maxY - starty);
				shiftY = (int) ((lineHeight + ((int) (lineHeight / 5) + 1))
						* (lines - 1) - lineHeight) / 2;
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx - fm.stringWidth(currentLine), starty
							+ shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				((Graphics2D) g).rotate(-theta, startx, globals.maxY - starty);
				return;
			}
			// adjAngle = 90;
			// else
			adjAngle = angle;
			extent = globals.stringRotator.getExtent(str, startx, globals.maxY
					- starty, adjAngle, fm);
			if (adjAngle == 90) {
				shiftX = fm.getMaxAscent() / 2;
				shiftY = extent.height;
			} else if (adjAngle > 0) {
				shiftX = -extent.width + fm.getMaxAscent();
				shiftY = extent.height;
			} else {
				shiftX = -fm.getMaxAscent() / 2;
				shiftY = fm.getMaxAscent() / 2;
			}
			break;
		}
		case Gc.keepABOVE: {
			if (angle == 0) {
				// shift up first
				shiftY = (lineHeight + ((int) (lineHeight / 5) + 1))
						* (lines - 1);
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx
							- (int) (fm.stringWidth(currentLine) / 2), starty
							+ shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				// drawString(g, startx - (int)(fm.stringWidth(str)/2), starty,
				// str, globals);
				return;
			}
			if ((angle < -90) || (angle >= 90)) {
				adjAngle = 90;
				double theta = -(adjAngle / 360d) * Math.PI * 2;
				((Graphics2D) g).rotate(theta, startx, globals.maxY - starty);
				shiftY = (int) ((lineHeight + ((int) (lineHeight / 5) + 1))
						* (lines - 1) - lineHeight) / 2;
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx, starty + shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				((Graphics2D) g).rotate(-theta, startx, globals.maxY - starty);
				return;
			}
			// adjAngle = 90;
			// else
			adjAngle = angle;
			extent = globals.stringRotator.getExtent(str, startx, globals.maxY
					- starty, adjAngle, fm);
			if (adjAngle == 90) {
				shiftX = 0;
			}
			if (adjAngle == -90) {
				shiftX = -fm.getMaxAscent() / 2;
				shiftY = -extent.height;
			} else if (angle < 0) {
				shiftX = -extent.width + fm.getMaxAscent() / 2;
				shiftY = fm.getMaxAscent() - extent.height;
			}
			break;
		}
		case Gc.keepRIGHT: {
			if (angle == 0) {
				// shift up by half
				shiftY = (int) ((lineHeight + ((int) (lineHeight / 5) + 1))
						* (lines - 1) - lineHeight) / 2;
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx, starty + shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				// drawString(g, startx, starty - fm.getMaxAscent()/2, str,
				// globals);
				return;
			}
			if ((angle < -90) || (angle >= 90)) {
				adjAngle = 90;
				double theta = -(adjAngle / 360d) * Math.PI * 2;
				((Graphics2D) g).rotate(theta, startx, globals.maxY - starty);
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx
							- (int) (fm.stringWidth(currentLine) / 2), starty
							- (lineHeight * 1) + shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				((Graphics2D) g).rotate(-theta, startx, globals.maxY - starty);
				return;
			}
			// adjAngle = 90;
			// else
			adjAngle = angle;
			extent = globals.stringRotator.getExtent(str, startx, globals.maxY
					- starty, adjAngle, fm);
			if (angle > 0)
				shiftX = fm.getMaxAscent();
			else
				shiftX = 0;
			if (angle == 90)
				shiftY = fm.stringWidth(str) / 2;
			else if (angle == -90) {
				shiftY = -fm.stringWidth(str) / 2;
				shiftX = 0;
			}
			break;
		}
		case Gc.keepLEFT: {
			if (angle == 0) {
				// shift up by half
				shiftY = (int) ((lineHeight + ((int) (lineHeight / 5) + 1))
						* (lines - 1) - lineHeight) / 2;
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx - fm.stringWidth(currentLine), starty
							+ shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				// drawString(g, startx - fm.stringWidth(str), starty -
				// fm.getMaxAscent()/2, str, globals);
				return;
			}
			if ((angle < -90) || (angle >= 90)) {
				adjAngle = 90;
				double theta = -(adjAngle / 360d) * Math.PI * 2;
				((Graphics2D) g).rotate(theta, startx, globals.maxY - starty);
				shiftY = (lineHeight + ((int) (lineHeight / 5) + 1))
						* (lines - 1);
				while (strtoke.hasMoreElements()) {
					currentLine = strtoke.nextToken();
					drawString(g, startx
							- (int) (fm.stringWidth(currentLine) / 2), starty
							+ shiftY, currentLine, globals);
					shiftY -= (lineHeight + (int) (lineHeight / 5) + 1);
				}
				((Graphics2D) g).rotate(-theta, startx, globals.maxY - starty);
				return;
			}
			// adjAngle = 90;
			// else
			adjAngle = angle;
			extent = globals.stringRotator.getExtent(str, startx, globals.maxY
					- starty, adjAngle, fm);
			shiftX = -extent.width; // + fm.getMaxAscent();
			if (angle == 90) {
				shiftY = fm.stringWidth(str) / 2;
				shiftX = 0;
			} else if (angle == -90) {
				shiftY = -fm.stringWidth(str) / 2;
				shiftX = -fm.getMaxAscent();
			} else if (angle > 0)
				shiftY = extent.height - fm.getMaxAscent() / 2;
			else {
				shiftY = fm.getMaxAscent() - extent.height;
				// shiftX -= fm.getMaxAscent()/2;
			}
			break;
		}
		default:
			return;
		}

		// globals.stringRotator.drawString(str, adjX, globals.maxY - adjY,
		// adjAngle, globals.image);
		double theta = -(adjAngle / 360d) * Math.PI * 2;
		g.translate(shiftX, shiftY);
		((Graphics2D) g).rotate(theta, startx, globals.maxY - starty);
		/*
		 * if(angle==90) { int shift = 0; switch (alignment) { case Gc.keepBELOW : {
		 * shift = (int)((lineHeight + ((int)(lineHeight/5)+1))*(lines-1) -
		 * lineHeight)/2; while(strtoke.hasMoreElements()){ currentLine =
		 * strtoke.nextToken(); drawString(g, startx, starty + shift,
		 * currentLine, globals); shift -= (lineHeight + (int)(lineHeight/5) +
		 * 1); } break; } case Gc.keepABOVE : { shift = (int)((lineHeight +
		 * ((int)(lineHeight/5)+1))*(lines-1) - lineHeight)/2;
		 * while(strtoke.hasMoreElements()){ currentLine = strtoke.nextToken();
		 * drawString(g, startx - fm.stringWidth(currentLine) , starty + shift,
		 * currentLine, globals); shift -= (lineHeight + (int)(lineHeight/5) +
		 * 1); } break; } case Gc.keepRIGHT : { shift = (lineHeight +
		 * ((int)(lineHeight/5)+1))*(lines-1); while(strtoke.hasMoreElements()){
		 * currentLine = strtoke.nextToken(); drawString(g,
		 * startx-(int)(fm.stringWidth(currentLine)/2), starty + shift,
		 * currentLine, globals); shift -= (lineHeight + (int)(lineHeight/5) +
		 * 1); } break; } case Gc.keepLEFT : { while(strtoke.hasMoreElements()){
		 * currentLine = strtoke.nextToken(); drawString(g,
		 * startx-(int)(fm.stringWidth(currentLine)/2), starty-(lineHeight*1) +
		 * shift, currentLine, globals); shift -= (lineHeight +
		 * (int)(lineHeight/5) + 1); } break; } default: break; } } else
		 */
		g.drawString(str, startx, globals.maxY - starty);
		((Graphics2D) g).rotate(-theta, startx, globals.maxY - starty);
		g.translate(-shiftX, -shiftY);

	}

	/**
	 * Draws a string in chart's coordinate space. Used by
	 * drawSmartString(). Creation date: (1/24/2002 11:34:02 AM)
	 * 
	 * @param g
	 *            java.awt.Graphics
	 * @param str
	 *            java.lang.String
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param globals
	 *            javachart.chart.Globals
	 */
	protected static void drawString(Graphics g, int x, int y, String str,
			Globals globals) {
		if (g.getColor() == Gc.TRANSPARENT)
			return;
		g.drawString(str, x, globals.maxY - y);
	}

	/**
	 * @param g
	 *            The affected graphics
	 * @param pts
	 *            Array of polygon's points
	 */
	public void fillPolygonGradient(Graphics2D g2d, Point pts[]) {
		int xarr[];
		int yarr[];

		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		xarr = new int[pts.length];
		yarr = new int[pts.length];
		for (int i = 0; i < pts.length; i++) {
			xarr[i] = pts[i].x;
			yarr[i] = baseGc.globals.maxY - pts[i].y;
			minX = Math.min(minX, xarr[i]);
			maxX = Math.max(maxX, xarr[i]);
			minY = Math.min(minY, yarr[i]);
			maxY = Math.max(maxY, yarr[i]);
		}
		if ((gradientType == GRAD_LEFT_RIGHT_MIRROR)
				|| (gradientType == GRAD_LEFT_RIGHT)) {
			if ((maxX - minX != gradientWidth) || (gradient == null))
				buildGradient(maxX - minX);
			for (int i = 0; i < xarr.length; i++) {
				xarr[i] = xarr[i] - minX;
			}
			g2d.translate(minX, 0);
			g2d.setPaint(gradient);
			g2d.fillPolygon(xarr, yarr, pts.length);
			g2d.translate(-minX, 0);
		} else { // top/bottom gradient
			if ((maxY - minY != gradientWidth) || (gradient == null))
				buildGradient(maxY - minY);
			for (int i = 0; i < yarr.length; i++) {
				yarr[i] = yarr[i] - minY;
			}
			g2d.translate(0, minY);
			g2d.setPaint(gradient);
			g2d.fillPolygon(xarr, yarr, pts.length);
			g2d.translate(0, -minY);
		}
	}

	/**
	 * @param g
	 *            The affected graphics
	 * @param pts
	 *            Array of polygon's points
	 */
	public void fillPolygonTexture(Graphics2D g2d, Point pts[]) {
		int xarr[];
		int yarr[];

		buildTexture();

		xarr = new int[pts.length];
		yarr = new int[pts.length];
		for (int i = 0; i < pts.length; i++) {
			xarr[i] = pts[i].x;
			yarr[i] = baseGc.globals.maxY - pts[i].y;
		}
		g2d.setPaint(texture);
		g2d.fillPolygon(xarr, yarr, pts.length);
	}

	/**
	 * Fills a rectangle with the current fill color, but only outlines the
	 * sides of the rect
	 * 
	 * @param g
	 *            Graphics class to draw
	 * @param ll
	 *            lower left corner of rectangle
	 * @param ur
	 *            upper right corner of rectangle
	 */
	public void fillRect(Graphics g, Point ll, Point ur) {
		if (baseGc.fillStyle == Gc.FILL_GRADIENT)
			fillRectGradient((Graphics2D) g, ll, ur);
		else
			fillRectTexture((Graphics2D) g, ll, ur);
	}

	/**
	 * Fills a rectangle with the current gradient
	 * 
	 * @param g
	 *            Graphics class to draw
	 * @param ll
	 *            lower left corner of rectangle
	 * @param ur
	 *            upper right corner of rectangle
	 */
	public void fillRectGradient(Graphics2D g2d, Point ll, Point ur) {

		int llx, urx;
		if (ll.x > ur.x) {
			llx = ur.x;
			urx = ll.x;
		} else {
			llx = ll.x;
			urx = ur.x;
		}
		if (ll.y > ur.y) {
			int y = ur.y;
			ur.y = ll.y;
			ll.y = y;
		}
		// System.out.println("urx:" + urx + ", llx:" + llx + ", ur:" + ur + ",
		// ll:" + ll);
		if ((gradientType == GRAD_LEFT_RIGHT_MIRROR)
				|| (gradientType == GRAD_LEFT_RIGHT)) {
			if ((urx - llx != gradientWidth) || (gradient == null))
				buildGradient(urx - llx);
			g2d.setPaint(gradient);
			// g2d.fillRect(llx, globals.getMaxY() - ur.y, urx - llx, ur.y -
			// ll.y);
			g2d.translate(llx, 0);
			g2d.fillRect(0, baseGc.globals.maxY - ur.y, urx - llx, ur.y - ll.y);
			g2d.translate(-llx, 0);
		} else { // top_bottom gradient
			if ((ur.y - ll.y != gradientWidth) || (gradient == null))
				buildGradient(ur.y - ll.y);
			g2d.setPaint(gradient);
			g2d.translate(0, baseGc.globals.maxY - ur.y);
			g2d.fillRect(llx, 0, urx - llx, ur.y - ll.y);
			g2d.translate(0, -(baseGc.globals.maxY - ur.y));
		}
		if (baseGc.outlineFills) {
			if (baseGc.lineColor == Gc.TRANSPARENT)
				return;
			g2d.setColor(baseGc.lineColor);
			g2d.drawLine(llx, baseGc.globals.maxY - ur.y, llx,
					baseGc.globals.maxY - ll.y);
			g2d.drawLine(urx, baseGc.globals.maxY - ur.y, urx,
					baseGc.globals.maxY - ll.y);
			g2d.drawLine(llx, baseGc.globals.maxY - ur.y, urx,
					baseGc.globals.maxY - ur.y);
			g2d.drawLine(llx, baseGc.globals.maxY - ll.y, urx,
					baseGc.globals.maxY - ll.y);
		}
	}

	/**
	 * Fills a rectangle with the current gradient
	 * 
	 * @param g
	 *            Graphics class to draw
	 * @param ll
	 *            lower left corner of rectangle
	 * @param ur
	 *            upper right corner of rectangle
	 */
	public void fillRectTexture(Graphics2D g2d, Point ll, Point ur) {

		/*
		 * if (baseGc.image != null && textureType == TEXTURE_IMAGE) {
		 * buildTexture(ll, ur); } else { buildTexture(); }
		 */
		buildTexture();
		int llx, urx;
		if (ll.x > ur.x) {
			llx = ur.x;
			urx = ll.x;
		} else {
			llx = ll.x;
			urx = ur.x;
		}
		if (ll.y > ur.y) {
			int y = ur.y;
			ur.y = ll.y;
			ll.y = y;
		}

		g2d.setPaint(texture);
		g2d.fillRect(llx, baseGc.globals.maxY - ur.y, urx - llx, ur.y - ll.y);
		// g2d.fillRect(llx, ur.y, urx - llx, ur.y - ll.y);

		if (baseGc.outlineFills) {
			if (baseGc.lineColor == Gc.TRANSPARENT)
				return;
			g2d.setColor(baseGc.lineColor);
			g2d.drawLine(llx, baseGc.globals.maxY - ur.y, llx,
					baseGc.globals.maxY - ll.y);
			g2d.drawLine(urx, baseGc.globals.maxY - ur.y, urx,
					baseGc.globals.maxY - ll.y);
			g2d.drawLine(llx, baseGc.globals.maxY - ur.y, urx,
					baseGc.globals.maxY - ur.y);
			g2d.drawLine(llx, baseGc.globals.maxY - ll.y, urx,
					baseGc.globals.maxY - ll.y);
		}
	}

	private void buildTexture(Point ll, Point ur) {
		BufferedImage img = null;

		int w = Math.abs(ur.x - ll.x);
		int h = Math.abs(ur.y - ll.y);
		if (baseGc.image.getWidth(null) > w && baseGc.image.getHeight(null) > h) {
			ImageFilter resizeFilter = new ReplicateScaleFilter(w, h);
			FilteredImageSource source = new FilteredImageSource(baseGc.image
					.getSource(), resizeFilter);

			baseGc.image = Toolkit.getDefaultToolkit().createImage(source);
		}
		int xSize = baseGc.image.getWidth(null);
		int ySize = baseGc.image.getHeight(null);
		if ((xSize < 1) || (ySize < 1))
			return;

		if (baseGc.image instanceof BufferedImage) {
			img = (BufferedImage) baseGc.image;
		} else { // convert to BufferedImage
			System.out.println("doing conversion");
			img = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
			img.getGraphics().drawImage(baseGc.image, 0, 0, null);
		}
		Rectangle r = new Rectangle(0, 0, xSize, ySize);
		texture = new TexturePaint((BufferedImage) img, r);
	}

	/**
	 * Returns the user defined GradientPaint. Creation date: (1/22/2002 9:48:23
	 * AM)
	 * 
	 * @return java.awt.GradientPaint
	 * @uml.property name="gradient"
	 */
	public GradientPaint getGradient() {
		return userGradient;
	}

	/**
	 * Returns a user-defined Texture. Creation date: (1/22/2002 9:59:52 AM)
	 * 
	 * @return java.awt.TexturePaint
	 * @uml.property name="texture"
	 */
	public TexturePaint getTexture() {
		return userTexture;
	}

	/**
	 * Lets a user define a GradientPaint for use with this Object. Creation
	 * date: (1/22/2002 9:47:40 AM)
	 * 
	 * @param gp
	 *            java.awt.GradientPaint
	 * @uml.property name="gradient"
	 */
	public void setGradient(GradientPaint gp) {
		userGradient = gp;
	}

	/**
	 * Assigns a user-defined Stroke to this object. Creation date: (1/22/2002
	 * 9:59:15 AM)
	 * 
	 * @param tp
	 *            java.awt.TexturePaint
	 * @uml.property name="stroke"
	 */
	public void setStroke(Stroke stroke) {
		userStroke = stroke;
	}

	/**
	 * Assigns a user-defined Texture to this object. Creation date: (1/22/2002
	 * 9:59:15 AM)
	 * 
	 * @param tp
	 *            java.awt.TexturePaint
	 */
	public void setStroke(TexturePaint tp) {
		userTexture = tp;
	}

	/**
	 * Assigns a user-defined Texture to this object. Creation date: (1/22/2002
	 * 9:59:15 AM)
	 * 
	 * @param tp
	 *            java.awt.TexturePaint
	 * @uml.property name="texture"
	 */
	public void setTexture(TexturePaint tp) {
		userTexture = tp;
	}
}
