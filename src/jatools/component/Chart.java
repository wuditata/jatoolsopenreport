package jatools.component;

import jatools.accessor.PropertyDescriptor;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Chart extends Component {
    /**
     * Creates a new Image object.
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public Chart(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Creates a new Image object.
     */
    public Chart() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public PropertyDescriptor[] getRegistrableProperties() {
        return new PropertyDescriptor[] {
            ComponentConstants._INIT_PRINT, ComponentConstants._AFTERPRINT,
            ComponentConstants._BEFOREPRINT2,
        };
    }
}
