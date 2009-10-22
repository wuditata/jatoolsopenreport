/*
 * Author: John.
 *
 * 杭州杰创软件 All Copyrights Reserved.
 */
package jatools.designer.peer;

import jatools.component.Component;
import jatools.component.table.Cell;
import jatools.component.table.TableBase;
import jatools.designer.ReportPanel;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;

import javax.swing.SwingUtilities;



/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.4 $
 * @author $author$
 */
public class TablePeer extends ComponentPeer {
    /**
     * DOCUMENT ME!
     */
    public static final int NULL = 0;

    /**
     * DOCUMENT ME!
     */
    public static final int _SOUTH_EAST = 1;

    /**
     * DOCUMENT ME!
     */
    public static final int _SOUTH = 2;

    /**
     * DOCUMENT ME!
     */
    public static final int _EAST = 3;
    private static Rectangle testRect = new Rectangle();
    private boolean focused;
    private Rectangle selection;
    private boolean gridVisible = true;

    /**
     * Creates a new TablePeer object.
     *
     * @param owner DOCUMENT ME!
     * @param target DOCUMENT ME!
     */
    public TablePeer(ReportPanel owner, Component target) {
        super(owner, target);
    }

    /**
     * DOCUMENT ME!
     *
     * @param b DOCUMENT ME!
     */
    public void setFocused(boolean b) {
        this.focused = b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isFocused() {
        return focused;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.jatools.designer.ZComponentPeer#isAcceptableChild(com.jatools.component.ZComponent)
     */

    /**
     * DOCUMENT ME!
     *
     * @param child DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isAcceptableChild(Component child) {
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param child DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isAcceptableDropedChild(Component child) {
        return (this.getComponent().getCell() == null) && !(child instanceof TableBase);
    }

    /**
     * 检测mouse在选择框的位置,在东面中间位置的,在南面中间位置,
     *
     * @param x
     *            DOCUMENT ME!
     * @param y
     *            DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int hitSelection(int x, int y) {
        TableBase grid = (TableBase) getComponent();
        Rectangle bounds = getSelection();

        Rectangle sel = grid.getBounds(bounds.y, bounds.x, bounds.width, bounds.height);

        // 1
        int x0 = sel.x + (sel.width / 2);
        int y0 = sel.y + sel.height;

        testRect.setRect(x0 - 3, y0 - 3, 6, 6);

        if (testRect.contains(x, y)) {
            return _SOUTH;
        }

        // 2
        x0 = sel.x + sel.width;
        y0 = sel.y + (sel.height / 2);
        testRect.setRect(x0 - 3, y0 - 3, 6, 6);

        if (testRect.contains(x, y)) {
            return _EAST;
        }

        // 3
        x0 = sel.x + sel.width;
        y0 = sel.y + sel.height;
        testRect.setRect(x0 - 3, y0 - 3, 6, 6);

        if (testRect.contains(x, y)) {
            return _SOUTH_EAST;
        }

        return NULL;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isResizable() {
        return false;
    }

    /**
    * DOCUMENT ME!
    *
    * @param selection
    *            DOCUMENT ME!
    */
    public void setSelection(Rectangle selection) {
        /**
         * @param selection
         */
        setSelection(selection, true);
    }

    /**
     * @param selection
     */
    public void setSelection(Rectangle selection, boolean invokelater) {
        /**
         * @param selection
         */
        _setSelection(selection);

        if (invokelater) {
            SwingUtilities.invokeLater(new _SelectionNotifier());
        } else {
            selectChildren();
        }
    }

    /**
     *
     */
    public void refreshSelection() {
        setSelection(getSelection());
    }

    /**
     *
     */
    private void selectChildren() {
        //        if (!((ZTable) this.getTarget()).isFocused()) {
        //            return;
        //        }
        this.getOwner().unselectAll();

        Rectangle selection = getSelection();
        Iterator it = ((TableBase) getComponent()).getCellstore().iterator();

        while (it.hasNext()) {
            Component c = (Component) it.next();

            Cell cell = (Cell) c.getCell();

            if (selection.contains(cell.column, cell.row)) {
                this.getOwner().select(c);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param selection DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */

    /**
     * DOCUMENT ME!
     *
     * @param loc
     *            DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int hitHot(Point loc) {
        return ComponentPeer.NOT_HIT;
    }

    /**
     * DOCUMENT ME!
     *
     * @param focusedBoxes
     *            DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getFocusedBoxes(Point[] focusedBoxes) {
        //        if (getComponent().getParent() instanceof GridBase) {
        //            return 0;
        //        }
        Point pointCache = focusedBoxes[0];
        Insets is = this.getComponent().getPadding();
        pointCache.setLocation(-is.left, -is.top);
        getOwner().childPointAsScreenPoint(this, pointCache);

        int x1 = pointCache.x + 2;
        int y1 = pointCache.y + 2;
        int x2 = (x1 + getWidth()) - 5;
        int y2 = (y1 + getHeight()) - 5;

        /*
         * p1(x1,y1) p2 p3 +-----------+----------+ | | p8+ + cx,cy + p4 | |
         * +-----------+----------+ p5(x2,y2) p7 p6
         */
        focusedBoxes[0].setLocation(x1, y1); // p1

        focusedBoxes[1].setLocation(x2, y1); // p3

        focusedBoxes[2].setLocation(x2, y2); // p5

        focusedBoxes[3].setLocation(x1, y2); // p7

        return 4;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */

    /**
    * DOCUMENT ME!
    *
    * @param selection DOCUMENT ME!
    */
    public void _setSelection(Rectangle selection) {
        Iterator children = ((TableBase) getComponent()).getCellstore().iterator();

        while (children.hasNext()) {
            Cell cell = (Cell) ((Component) children.next()).getCell();
            Rectangle r = new Rectangle(cell.column, cell.row, cell.colSpan, cell.rowSpan);

            if (selection.intersects(r)) {
                selection.add(r);
            }
        }

        this.selection = selection;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isFixedHeight() {
        return false;
    }

    /**
    * DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
    public Rectangle getSelection() {
        return selection;
    }

    /**
    * DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
    public Cell getFocusedCell() {
        return new Cell(selection.y, selection.x);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isGridVisible() {
        return gridVisible;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isHandlerVisible() {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param gridVisible DOCUMENT ME!
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }

    class _SelectionNotifier implements Runnable {
        public void run() {
            selectChildren();
        }
    }
}
