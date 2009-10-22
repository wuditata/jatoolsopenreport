package jatools.dataset;

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
 * @version $Revision: 1.5 $
  */
public class CrossField2 extends AbstractField implements Filter {
    private CrossRowSet rowset;
    private Key def;
    int col;

    /**
     * Creates a new CrossField2 object.
     *
     * @param rowset DOCUMENT ME!
     * @param col DOCUMENT ME!
     * @param def DOCUMENT ME!
     */
    public CrossField2(CrossRowSet rowset, int col, Key def) {
        this.rowset = rowset;
        this.col = col;
        this.def = def;
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
        if (this.rowset != null) {
            try {
                RowSet rowset = this.rowset.locate(def);

                if (rowset != null) {
                    return rowset.valueAt(col);
                }
            } catch (DatasetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object[] values() {
        if (this.rowset != null) {
            try {
                RowSet rowset = this.rowset.locate(def);

                if (rowset != null) {
                    return rowset.valuesAt(col);
                }
            } catch (DatasetException e) {
                e.printStackTrace();
            }
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
        Key key = null;

        if (parameterNode instanceof BSHArguments) {
            Object[] keys = ((BSHArguments) parameterNode).getArguments(callstack, interpreter);

            for (int i = 0; i < keys.length; i++) {
                keys[i] = Primitive.unwrap(keys[i]);
            }
            key = new Key(keys);
        } else if (parameterNode == null) {
            key = Dataset.STAR;
        }

        if (key != null) {
            this.def = key;

            return this;
        } else {
            return null;
        }
    }
}
