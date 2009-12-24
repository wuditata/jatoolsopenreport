package jatools.component.chart;

import jatools.accessor.AutoAccessor;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Tip extends AutoAccessor {
    /**
     * DOCUMENT ME!
     */
    public String tip;

    /**
     * DOCUMENT ME!
     */
    public String url;

    /**
     * DOCUMENT ME!
     */
    public String target;

    /**
     * Creates a new Tip object.
     *
     * @param tip DOCUMENT ME!
     * @param url DOCUMENT ME!
     * @param target DOCUMENT ME!
     */
    public Tip(String tip, String url, String target) {
        this.tip = tip;
        this.url = url;
        this.target = target;
    }

    /**
     * Creates a new Tip object.
     */
    public Tip() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getTarget() {
        return target;
    }

    /**
     * DOCUMENT ME!
     *
     * @param target DOCUMENT ME!
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getTip() {
        return tip;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tip DOCUMENT ME!
     */
    public void setTip(String tip) {
        this.tip = tip;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getUrl() {
        return url;
    }

    /**
     * DOCUMENT ME!
     *
     * @param url DOCUMENT ME!
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws CloneNotSupportedException DOCUMENT ME!
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
