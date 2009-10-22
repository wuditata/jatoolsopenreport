package jatools.dataset;

import jatools.component.table.PowerTable;
import jatools.dom.field.AbstractField;
import bsh.BSHArguments;
import bsh.CallStack;
import bsh.EvalError;
import bsh.Filter;
import bsh.Interpreter;
import bsh.Node;
import bsh.Primitive;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.7 $
  */
public class CrossField extends AbstractField implements Filter {
    private CrossIndexView indexView;
    Key key;
    private Key key2;
    int col;

    /**
     * Creates a new CrossField object.
     *
     * @param indexView DOCUMENT ME!
     * @param col DOCUMENT ME!
     * @param key DOCUMENT ME!
     * @param key2 DOCUMENT ME!
     */
    public CrossField(CrossIndexView indexView, int col, Key key, Key key2) {
        this.indexView = indexView;

        this.col = col;

        this.key = key;
        this.key2 = key2;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getColumn() {
        return this.col;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object value(Interpreter it) {
        try {
            RowSet rowset = this.indexView.locate(this.key, this.key2);

            if (rowset != null) {
                return rowset.valueAt(col);
            }
        } catch (DatasetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object value() {
        return value(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object[] values() {
        try {
            RowSet rowset = this.indexView.locate(this.key, this.key2);

            if (rowset != null) {
                return rowset.valuesAt(col);
            }
        } catch (DatasetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param callstack DOCUMENT ME!
     * @param interpreter DOCUMENT ME!
     * @param parameterNode DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws EvalError DOCUMENT ME!
     */
    public Object filter(CallStack callstack, Interpreter interpreter, Node parameterNode)
        throws EvalError {
        int col = getColumn();
        Key key = null;

        if (parameterNode instanceof BSHArguments) {
            Object[] keys = ((BSHArguments) parameterNode).getArguments(callstack, interpreter);

            for (int i = 0; i < keys.length; i++) {
                keys[i] = Primitive.unwrap(keys[i]);
            }

            if ((keys.length == 1) && (keys[0] == PowerTable.CURRENT_ROW_KEY)) {
                key = this.key;
            } else {
                key = new Key(keys);
            }
        } else if (parameterNode == null) {
            key = Dataset.STAR;
        }

        if (key != null) {
            return new CrossField2(this.indexView.locate(key), col, key2);
        } else {
            return null;
        }
    }
}
