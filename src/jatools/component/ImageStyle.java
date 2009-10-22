package jatools.component;

import jatools.engine.css.CSSValue;
import jatools.engine.imgloader.ImageSource;

import java.awt.Graphics2D;
import java.awt.Image;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ImageStyle implements ImageSource {
    public static final int UNKNOWN = -1;
    public static final int PNG = 0;
    public static final int GIF = 1;
    public static final int JPG = 2;
    public static final int FLASH = 3;
    public static final int AWT = 4;
    public static final int URL = 5;
    private static CSSValue[] ALIGNS = {
            new CSSValue("0%"), new CSSValue("50%"), new CSSValue("100%")
        };
    protected String imageSrc;
    protected CSSValue x;
    protected CSSValue y;
    private boolean stretches;
    private Object image;
    private ImageObjectFormat imageObjectFormat;
    private int exportImageFormat = PNG;

    /**
     * Creates a new ImageStyle object.
     *
     * @param imageSrc DOCUMENT ME!
     * @param ha DOCUMENT ME!
     * @param va DOCUMENT ME!
     * @param stretches DOCUMENT ME!
     * @param requiredHtmlImageFormat DOCUMENT ME!
     */
    public ImageStyle(String imageSrc, int ha, int va, boolean stretches,
        int requiredHtmlImageFormat) {
        this.imageSrc = imageSrc;
        this.x = ALIGNS[ha];
        this.y = ALIGNS[va];
        this.stretches = stretches;
        this.exportImageFormat = requiredHtmlImageFormat;
    }

    /**
     * Creates a new ImageStyle object.
     */
    public ImageStyle() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param image DOCUMENT ME!
     */
    public void setAwtObject(Object image) {
        this.image = image;
        this.imageObjectFormat = ImageObjectFormat.AWT;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public CSSValue getX() {
        return x;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public CSSValue getY() {
        return y;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getImageFileSrc() {
        return imageSrc;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isStretches() {
        return stretches;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getImageObject() {
        return image;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ImageObjectFormat getImageObjectFormat() {
        return imageObjectFormat;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getExportImageFormat() {
        return exportImageFormat;
    }

    /**
     * DOCUMENT ME!
     *
     * @param g2 DOCUMENT ME!
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public void paint(Graphics2D g2, int width, int height) {
        Image image = (Image) ((getImageObjectFormat() == ImageObjectFormat.AWT) ? getImageObject()
                                                                                 : null);

        if (image != null) {
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

            if (isStretches()) {
                g2.drawImage(image, 0, 0, width, height, null);
            } else {
                int xoff = 0;

                if (getX().isPercent()) {
                    float percent = getX().percentValue();
                    xoff += Math.round((width * percent) - (imageWidth * percent));
                } else {
                    xoff += getX().floatValue();
                }

                int yoff = 0;

                if (getY().isPercent()) {
                    float percent = getY().percentValue();
                    yoff += Math.round((height * percent) - (imageHeight * percent));
                } else {
                    yoff += getY().floatValue();
                }

                g2.drawImage(image, xoff, yoff, null);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param image DOCUMENT ME!
     */
    public void setUrlObject(Object image) {
        this.image = image;
        this.imageObjectFormat = ImageObjectFormat.URL;
    }
}
