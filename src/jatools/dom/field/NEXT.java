package jatools.dom.field;

import bsh.CallStack;
import bsh.Interpreter;
import bsh.Primitive;
import bsh.PropertyGetter;
import bsh.UtilEvalError;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class NEXT implements PropertyGetter {
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
        Object node = callstack.top().getLocalVariable(prop);

        if (node instanceof NodeField) {
            return ((NodeField) node).next();
        } else {
            return Primitive.VOID;
        }
    }
}
