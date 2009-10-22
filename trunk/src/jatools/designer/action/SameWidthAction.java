package jatools.designer.action;


import jatools.designer.SelectionState;

import java.awt.Point;
import java.awt.Rectangle;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class SameWidthAction extends ResizeAction {
    /**
     * Creates a new SameWidthAction object.
     */
    public SameWidthAction() {
        super("�ȿ�", getIcon("/jatools/icons/samewidth.gif"),
            getIcon("/jatools/icons/samewidth2.gif"));
    }

    /**
     * DOCUMENT ME!
     *
     * @param targetRect DOCUMENT ME!
     * @param peerRect DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Point getSizeDelta(Rectangle targetRect, Rectangle peerRect) {
        return new Point(targetRect.width - peerRect.width, 0);
    }

    /**
     * DOCUMENT ME!
     *
     * @param state DOCUMENT ME!
     */
    public void enabled(SelectionState state) {
        setEnabled((state.getCount() > 1) && state.isResizable());
    }
}
