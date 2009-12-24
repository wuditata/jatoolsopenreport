package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ReplicateScaleFilter;



public class TextureIcon extends AbstractColorIcon {

	Color masterColor = Color.WHITE;

	Color secondColor = Color.WHITE;

	int style = GcHelper.TEXTURE_H_STRIPE;

	Image image;

	public TextureIcon(int width, int height) {
		super(width, height);
	}

	public TextureIcon(Dimension dimension) {
		super(dimension.width, dimension.height);
	}

	public void setStyle(FillStyleInterface style) {
		this.style = ((TextureColor) style).style;
		this.masterColor = ((TextureColor) style).masterColor;
		this.secondColor = ((TextureColor) style).secondColor;
		this.image = ((TextureColor) style).image;
	}

	public void paintIcon(Component c, Graphics g1, int x, int y) {

		Graphics2D g = (Graphics2D) g1.create();
		
		if (masterColor != Gc.TRANSPARENT) {
			g.setPaint(buildTexture());
			g.fillRect(x, y, width, height);
		}
		g.setColor(Color.black);
		g.drawRect(x, y, getIconWidth(), getIconHeight());
		g.dispose();
	}

	public TexturePaint buildTexture() {
		TexturePaint texture;
		int sz = 6;

		if (image != null && style == GcHelper.TEXTURE_IMAGE) { // build a texturePaint from the Gc's image
								// property

			BufferedImage img = null;

			ImageFilter resizeFilter = new ReplicateScaleFilter(width, height);
			FilteredImageSource source = new FilteredImageSource(image
					.getSource(), resizeFilter);

			image = Toolkit.getDefaultToolkit().createImage(source);

			int xSize = image.getWidth(null);
			int ySize = image.getHeight(null);
			if ((xSize < 1) || (ySize < 1))
				return null;
			if (image instanceof BufferedImage) {
				img = (BufferedImage) image;
			} else { // convert to BufferedImage
				System.out.println("doing conversion");
				img = new BufferedImage(xSize, ySize,
						BufferedImage.TYPE_INT_RGB);
				img.getGraphics().drawImage(image, 0, 0, null);
			}
			Rectangle r = new Rectangle(0, 0, xSize, ySize);
			texture = new TexturePaint((BufferedImage) img, r);
		} else {
			BufferedImage img = new BufferedImage(sz, sz,
					BufferedImage.TYPE_INT_RGB);
			Graphics ig = img.createGraphics();
			if (style == GcHelper.TEXTURE_IMAGE)
				ig.setColor(masterColor);
			else
				ig.setColor(secondColor);
			ig.fillRect(0, 0, sz, sz);
			ig.setColor(masterColor);
			switch (style) {
			case GcHelper.TEXTURE_H_STRIPE: {
				ig.drawLine(0, sz / 2, sz, sz / 2);
			}
				break;
			case GcHelper.TEXTURE_V_STRIPE: {
				ig.drawLine(sz / 2, 0, sz / 2, sz);
			}
				break;
			case GcHelper.TEXTURE_DOWN_STRIPE: {
				ig.drawLine(0, 0, sz - 1, sz - 1);
			}
				break;
			case GcHelper.TEXTURE_UP_STRIPE: {
				ig.drawLine(0, sz - 1, sz - 1, 0);
			}
				break;
			case GcHelper.TEXTURE_CROSS_STRIPE: {
				ig.drawLine(0, sz - 1, sz - 1, 0);
				ig.drawLine(0, 0, sz, sz);
			}
				break;

			}
			Rectangle r = new Rectangle(0, 0, sz, sz);
			texture = new TexturePaint(img, r);
		}
		return texture;
	}

}
