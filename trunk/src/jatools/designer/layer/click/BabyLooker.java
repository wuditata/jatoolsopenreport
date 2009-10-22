package jatools.designer.layer.click;


import jatools.component.Component;
import jatools.component.table.Cell;
import jatools.component.table.PanelStore;
import jatools.component.table.TableBase;
import jatools.designer.Point2;
import jatools.designer.ReportPanel;
import jatools.designer.peer.ComponentPeer;
import jatools.designer.peer.ComponentPeerFactory;
import jatools.designer.peer.TablePeer;
import jatools.designer.undo.AddEdit;
import jatools.designer.undo.DeleteEdit;
import jatools.designer.undo.GroupEdit;
import jatools.designer.undo.TablePropertyEdit;

import java.awt.Insets;
import java.awt.Point;

import javax.swing.undo.CompoundEdit;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class BabyLooker {
    private static Point location = new Point();
    ReportPanel owner;
    ComponentPeer childPeer;
    Point2 xPoint;
    Point2 yPoint;

    /**
     * Creates a new BabyLooker object.
     *
     * @param owner DOCUMENT ME!
     */
    public BabyLooker(ReportPanel owner) {
        this.owner = owner;
    }

    /**
     * DOCUMENT ME!
     *
     * @param comp DOCUMENT ME!
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     */
    public void clickGrow(Component comp, int x, int y) {
        location.setLocation(x, y);

        comp.validate();

        ComponentPeer nearestContainer = findNearestContainter(comp, x, y);

        if (nearestContainer != null) {
            owner.screenPointAsChildPoint(nearestContainer, location);

            Insets is = nearestContainer.getComponent().getPadding();
            location.translate(-is.left, -is.top);
            comp.setX(location.x);
            comp.setY(location.y);

            childPeer = createPeer(comp);

            add(nearestContainer, childPeer);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param comp DOCUMENT ME!
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     */
    public void dragDropGrow(Component comp, int x, int y) {
        if (this.owner.getScale() != 1.0f) {
            x = (int) (x / owner.getScale());
            y = (int) (y / owner.getScale());
        }

        location.setLocation(x, y);

        comp.validate();

        ComponentPeer nearestContainer = findNearestDroppableContainter(comp, x, y);

        if (nearestContainer != null) {
            owner.screenPointAsChildPoint(nearestContainer, location);

            Insets is = nearestContainer.getComponent().getPadding();
            location.translate(-is.left, -is.top);
            childPeer = createPeer(comp);

            if (nearestContainer instanceof TablePeer) {
                CompoundEdit edit = new GroupEdit();
                edit.addEdit(new TablePropertyEdit(nearestContainer));

                TableBase t = (TableBase) nearestContainer.getComponent();

                Cell cell = t.getCellAt(location.x, location.y);
                Component deleted = t.getCellstore().getComponentOver(cell.row, cell.column);

                if (deleted != null) {
                    ComponentPeer deletedPeer = owner.getComponentPeer(deleted);

                    int index = deletedPeer.getParent().indexOf(deletedPeer);

                    if (deletedPeer.getParent().remove(deletedPeer)) {
                        edit.addEdit(new DeleteEdit(deletedPeer, index));
                    }

                    cell = (Cell) deleted.getCell().clone();
                }

                comp.setCell(cell);

                PanelStore liststore = t.getPanelstore();

                Component p = liststore.getComponentOver(cell.row, cell.column);
                ComponentPeer parentPeer = owner.getComponentPeer(p);

                parentPeer.add(childPeer);

                AddEdit e1 = new AddEdit(childPeer, true);
                edit.addEdit(e1);

                edit.addEdit(new TablePropertyEdit(nearestContainer));
                owner.addEdit(edit);
            } else {
                comp.setX(location.x);
                comp.setY(location.y);
                add(nearestContainer, childPeer);
            }
        }
    }

    void add(ComponentPeer parent, ComponentPeer child) {
        parent.add(child);

        AddEdit edit = new AddEdit(child, true);

        owner.addEdit(edit);

        owner.repaint();
    }

    private ComponentPeer createPeer(Component target) {
        ComponentPeer peer = ComponentPeerFactory.createPeer(owner, target);

        for (int i = 0; i < target.getChildCount(); i++) {
            Component child = target.getChild(i);

            peer.add(createPeer(child));
        }

        return peer;
    }

    private ComponentPeer findNearestContainter(Component comp, int x, int y) {
        ComponentPeer hitPeer = owner.findComponentPeerAt(x, y);

        while (hitPeer != null) {
            if (hitPeer.isAcceptableChild(comp)) {
                return hitPeer;
            }

            hitPeer = hitPeer.getParent();
        }

        return null;
    }

    private ComponentPeer findNearestDroppableContainter(Component comp, int x, int y) {
        ComponentPeer hitPeer = owner.findComponentPeerAt(x, y);

        while (hitPeer != null) {
            if (hitPeer.isAcceptableDropedChild(comp)) {
                return hitPeer;
            }

            hitPeer = hitPeer.getParent();
        }

        return null;
    }
}
