package jatools.component.chart.customizer;

import jatools.dataset.Column;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class SelectCommand {
	final static int LABEL = 0;
	final static int PLOTDATA = 1;
	
	
    Column field;
    int type;
    String error;

    

    /**
     * Creates a new SelectCommand object.
     *
     * @param field DOCUMENT ME!
     * @param type DOCUMENT ME!
     */
    public SelectCommand(Column field, int type) {
        this.field = field;
        this.type = type;
    }

    /**
     * DOCUMENT ME!
     *
     * @param a DOCUMENT ME!
     */
    public void setError(String err) {
        this.error = err;
    }
}
