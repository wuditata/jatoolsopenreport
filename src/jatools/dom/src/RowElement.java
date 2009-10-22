package jatools.dom.src;

import jatools.dataset.Dataset;
import bsh.CallStack;
import bsh.Interpreter;
import bsh.PropertyGetter;
import bsh.UtilEvalError;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.2 $
  */
public class RowElement implements PropertyGetter {
    private Dataset ds;
    private int row;

    /**
     * Creates a new RowElement object.
     *
     * @param ds DOCUMENT ME!
     * @param row DOCUMENT ME!
     */
    public RowElement(Dataset ds, int row) {
        this.ds = ds;
        this.row = row;
    }

    /**
     * DOCUMENT ME!
     *
     * @param prop DOCUMENT ME!
     * @param callstack DOCUMENT ME!
     * @param interpreter DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws UtilEvalError DOCUMENT ME!
     */
    public Object getProperty(String prop, CallStack callstack, Interpreter interpreter)
        throws UtilEvalError {
        int col = this.ds.getColumnIndex(prop);

        return this.ds.getValueAt(this.row, col);
    }
}
