package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Icon;


public class TextureColor implements FillStyleInterface {
	int style;

	Color masterColor;

	Color secondColor;

	Image image;
	
	byte[] bytes;

	public Component createLabel(Dimension d) {
		String label;
		if (image != null && style == GcHelper.TEXTURE_IMAGE)
			label = "Í¼Æ¬";
		else
			label = "ÏßÎÆ";
		return new HeaderLabel(createIcon(new Dimension(ICON_WIDTH, ICON_HEIGHT)), label, d);
	}

	public Icon createIcon(Dimension d) {
		TextureIcon icon = new TextureIcon(d);
		icon.setStyle(this);
		return icon;
	}

	public int getType() {
		return FILL_TEXTURE;
	}

	public void setToGc(Gc gc) {
		gc.setFillStyle(Gc.FILL_TEXTURE);
		gc.setTexture(style);
		gc.setFillColor(masterColor);
		gc.setSecondaryFillColor(secondColor);
		gc.setImage(image);
		gc.setImageBytes(bytes);
	}

	public void getFromGc(Gc gc) {
		style = gc.getTexture();
		masterColor = gc.getFillColor();
		secondColor = gc.getSecondaryFillColor();
		image = gc.getImage();
		bytes = gc.getImageBytes();
	}

	public Color getMasterColor() {
		return masterColor;
	}

	public void setMasterColor(Color masterColor) {
		this.masterColor = masterColor;
	}

	public Color getSecondColor() {
		return secondColor;
	}

	public void setSecondColor(Color secondColor) {
		this.secondColor = secondColor;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
