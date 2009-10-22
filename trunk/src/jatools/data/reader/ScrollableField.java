package jatools.data.reader;

import jatools.engine.ValueIfClosed;
import bsh.Interpreter;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.3 $
  */
public class ScrollableField implements ValueIfClosed {
    /**
     * @uml.property  name="fieldIndex"
     */
    private int col;

    /**
     * @uml.property  name="cursor"
     */
    private DatasetCursor cursor;

    /**
     * Creates a new ScrollableField object.
     *
     * @param col DOCUMENT ME!
     * @param cursor DOCUMENT ME!
     */
    public ScrollableField(int col, DatasetCursor cursor) {
        this.col = col;
        this.cursor = cursor;
    }

    /**
    */
    public Object value(Interpreter it) {
        return cursor.getValue(col);
    }
    public Object value() {
        return cursor.getValue(col);
    }
}
