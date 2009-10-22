package jatools.engine;

import jatools.accessor.ProtectPublic;
import bsh.Interpreter;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public interface ValueIfClosed extends ProtectPublic {
    /**
     * DOCUMENT ME!
     *
     * @param it DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object value(Interpreter it);
}
