/*
* Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
*
* This software is the proprietary information of Visual Engineering, Inc.
* Use is subject to license terms.
*/
package jatools.component.chart.chart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.Serializable;

/**
 * 	A class for rotating and otherwise transforming graphics strings.
 *	Since Java's Graphics class supports only non-transformed raster
 *	strings, the RotateString class accomplishes its work with raster
 *	operations.
 *@see	jatools.component.chart.chart.Globals
 *@see	jatools.component.chart.chart.Gc
 */

public class RotateString implements Serializable
{
	private Component parent;
	private Color c;
	private Font f;

	private transient Image tmp;

/**
 *	Constructs a RotateString class with the specified Component class
 *@param	p	Component
 */
	public RotateString(Component p)
		{
		parent=p;
		c=null;
		f=null;
		}
private void drawString(String str, int x, int y, int ax, int ay, int angle, Image img)
{
	if ((str==null)||(img==null))
		return;
		
	double th= -Math.PI*(double)angle/180.0;
	Graphics tg, g=img.getGraphics();
	PixelGrabber grab;
 
	int[] txtPix, newPix;
 
	int nx, ny, nw, nh;
	int ox, oy, ow, oh;
 
	int txtRGB;     // marker of blank area */
	int clrRGB;     // text color
 
	/* get font sizes */
	if (f==null) f=g.getFont();
	if (c==null) c=g.getColor();
	if (c==null) c=Color.black;
 
	/* original boundary of the str */
	Rectangle rect = getExtent(str, x, y, 0, f);
	ox=rect.x;
	oy=rect.y;
	ow=rect.width;
	oh=rect.height;	

	/* new boundary after rotation */
	if (angle==0)
	{
		nx=ox;
		ny=oy;
		nw=ow;
		nh=oh;
	}
	else
	{
		rect = rotateRectangle(rect, ax, ay, th);
		nx=rect.x;
		ny=rect.y;
		nw=rect.width;
		nh=rect.height;
	}
 
	/* prepare text area */
	tmp=parent.createImage(ow, oh);
	tg=tmp.getGraphics();
/*
	tg.setColor(Color.white);
	tg.fillRect(0, 0, ow, oh);
*/
 
				/* draw string */
/*********** changed here...
				tg.setColor(c);
				tg.setFont(f);
				tg.drawString(str, x-ox, y-oy);
*/
	clrRGB=txtRGB=c.getRGB();
	/*
	if (txtRGB==Color.white.getRGB())
	{
		tg.setColor(Color.black);
		txtRGB=Color.black.getRGB();
	}
	else
	{  
			tg.setColor(c);
	}
	*/
	txtRGB=Color.black.getRGB();
	tg.setColor(Color.black);
	tg.setFont(f);
	/** buggy JVMs need some string adjustment - baseline is actually at descent */
	//tg.drawString(str, x-ox, y-oy+5);


	tg.drawString(str, x-ox, y-oy);

	/* get text area pixel */
	txtPix = new int[ow*oh];
	grab = new PixelGrabber(tmp, 0, 0, ow, oh, txtPix, 0, ow);
	try {grab.grabPixels();}
	catch (InterruptedException e) {}
 
	/* prepare new image pixel */
	newPix = new int[nw*nh];

	/* merge */
	for (int i=ny, index=0, ii, ix, iy; i<nh+ny; i++)
	{
		for (int j=nx; j<nw+nx; j++)
		{
			ix=(int)(0.5+(double)(j-ax)*Math.cos(-th)-(double)(i-ay)*Math.sin(-th)+(double)ax);
			iy=(int)(0.5+(j-ax)*Math.sin(-th)+(double)(i-ay)*Math.cos(-th)+(double)ay);
 
			ix-=ox;
			iy-=oy;
			if (ix>=0 && iy>=0 && ix<ow && iy<oh)
			{
				ii=iy*ow+ix;
				if (txtPix[ii] == txtRGB)
						newPix[index] = clrRGB;
			}
			index++;
		}
	}
 
	/* put onto img */
	tmp=parent.createImage(new MemoryImageSource(nw, nh, ColorModel.getRGBdefault(), newPix, 0, nw));
	g.drawImage(tmp, nx, ny, null);
	tg.dispose();
}
/**
 *	Draws a rotated string at the specified location and angle
 */
public void drawString(String str, int x, int y, int angle, Image img)
{
	drawString(str, x, y, x, y, angle, img);
}
private void drawString(String str, int x, int y, Image img)
{
		drawString(str, x, y, x, y, 0, img);
}
private Rectangle getExtent(String str, int x, int y, int angle)
{
		return(getExtent(str, x, y, x, y, angle, f));
}
private Rectangle getExtent(String str, int x, int y, int ax, int ay, int angle)
{
 
		return(getExtent(str, x, y, ax, ay, angle, f));
}
private Rectangle getExtent(String str, int x, int y, int ax, int ay, int angle, Font fn)
{
	Rectangle rect = new Rectangle();

	FontMetrics fm=parent.getFontMetrics(fn);
	int descent=fm.getMaxDescent();
	int ascent=fm.getMaxAscent();

	rect.x = x;
	rect.y = y-ascent+descent;
	rect.width = fm.stringWidth(str);
	//required for buggy JVMs: 
	//rect.height = ascent + descent;
	//good -- 
	rect.height = ascent;

	if (angle!=0) rect=rotateRectangle(rect, ax, ay, -Math.PI*(double)angle/180.0);

	return (rect);
}
private Rectangle getExtent(String str, int x, int y, int ax, int ay, int angle, FontMetrics fm)
{
	Rectangle rect = new Rectangle();

			int descent=fm.getMaxDescent();
			int ascent=fm.getMaxAscent();

	rect.x = x;
	rect.y = y-(ascent+descent);
	rect.width = fm.stringWidth(str);
	rect.height = ascent+descent;

	if (angle!=0) rect=rotateRectangle(rect, ax, ay, -Math.PI*(double)angle/180.0);

	return (rect);
}
private Rectangle getExtent(String str, int x, int y, int angle, Font fn)
{
		return(getExtent(str, x, y, x, y, angle, fn));
}
/**
 *	Calculates an extent box for the specified String at the specified
 *	location and angles.
 */
protected Rectangle getExtent(String str, int x, int y, int angle, FontMetrics fm)
{
		return(getExtent(str, x, y, x, y, angle, fm));
}
private Rectangle rotateRectangle(Rectangle rect, int ax, int ay, double th)
{
	double xmin, ymin, xmax, ymax, fx, fy;

	xmin=xmax=(double)(rect.x-ax)*Math.cos(th)-(double)(rect.y-ay)*Math.sin(th);
	ymin=ymax=(double)(rect.x-ax)*Math.sin(th)+(double)(rect.y-ay)*Math.cos(th);
	fx=(double)(rect.x+rect.width-1-ax)*Math.cos(th)-(double)(rect.y-ay)*Math.sin(th);
	fy=(double)(rect.x+rect.width-1-ax)*Math.sin(th)+(double)(rect.y-ay)*Math.cos(th);
	if (fx<xmin) xmin=fx; if (fx>xmax) xmax=fx;
	if (fy<ymin) ymin=fy; if (fy>ymax) ymax=fy;
	fx=(double)(rect.x-ax)*Math.cos(th)-(double)(rect.y+rect.height-1-ay)*Math.sin(th);
	fy=(double)(rect.x-ax)*Math.sin(th)+(double)(rect.y+rect.height-1-ay)*Math.cos(th);
	if (fx<xmin) xmin=fx; if (fx>xmax) xmax=fx;
	if (fy<ymin) ymin=fy; if (fy>ymax) ymax=fy;
	fx=(double)(rect.x+rect.width-1-ax)*Math.cos(th)-(double)(rect.y+rect.height-1-ay)*Math.sin(th);
	fy=(double)(rect.x+rect.width-1-ax)*Math.sin(th)+(double)(rect.y+rect.height-1-ay)*Math.cos(th);
	if (fx<xmin) xmin=fx; if (fx>xmax) xmax=fx;
	if (fy<ymin) ymin=fy; if (fy>ymax) ymax=fy;
 
	rect.x=(int)xmin+ax;
	rect.y=(int)ymin+ay;
	rect.width=(int)(xmax-xmin+1);
	rect.height=(int)(ymax-ymin+1);

	return(rect);
}
/**
 *	Sets the current color.
 *@param	color	Color
 *@return	None
 */
	void setColor(Color color)
	{
		c=color;
	}
/**
 *	Sets the current font
 *@param	font	Font
 *@return	None
 */
	void setFont(Font font)
	{
		f=font;
	}
}
