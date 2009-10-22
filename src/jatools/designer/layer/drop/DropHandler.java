package jatools.designer.layer.drop;


import jatools.component.Component;
import jatools.component.Text;
import jatools.component.table.Table;
import jatools.designer.ReportPanel;
import jatools.designer.data._Variable;
import jatools.designer.layer.click.BabyLooker;
import jatools.designer.variable.TableFactory;
import jatools.designer.variable.TreeNodeValue;
import jatools.dom.src.NodeSource;
import jatools.dom.src.RootNodeSource;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class DropHandler implements DropTargetListener {
    private ReportPanel owner;
    BabyLooker looker;

    /**
     * Creates a new DropHandler object.
     *
     * @param owner DOCUMENT ME!
     * @param c DOCUMENT ME!
     */
    public DropHandler(ReportPanel owner, java.awt.Component c) {
        this.owner = owner;

        new DropTarget(c, this);
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void dragEnter(DropTargetDragEvent dtde) {
        if (!isDragAccetable(dtde)) {
            dtde.rejectDrag();

            return;
        }
    }

    private boolean isDragAccetable(DropTargetDragEvent dtde) {
        return (dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void dragOver(DropTargetDragEvent dtde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param dtde DOCUMENT ME!
     */
    public void drop(DropTargetDropEvent dtde) {
        if (!isDropAcceptable(dtde)) {
            dtde.rejectDrop();
        }

        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable trans = dtde.getTransferable();
        DataFlavor[] flavors = trans.getTransferDataFlavors();

        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(_Variable.thisFlavor)) {
                try {
                    _Variable var = (_Variable) trans.getTransferData(flavors[i]);

                    if (looker == null) {
                        looker = new BabyLooker(owner);
                    }

                    Component baby = growBaby(var);

                    if (baby != null) {
                        Point loc = dtde.getLocation();

                        looker.dragDropGrow(baby, loc.x, loc.y);
                    }
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        dtde.dropComplete(true);

        return;
    }

    private Component growBaby(_Variable var) {
        Component baby = null;

        Object value = owner.getDocument().getVariable(var.getVariableName());

        if (var instanceof TreeNodeValue) {
            TreeNodeValue nodeValue = (TreeNodeValue) var;
            NodeSource nodeSource = nodeValue.getNodeSource();
            Table table = TableFactory.getTable(nodeSource);
            baby = table;

            owner.getDocument().setNodeSource(this.getRootSource(nodeSource));
        } else {
            Text text = new Text(0, 0, 90, 20);
            text.setVariable("=" + var.getVariableName());
            baby = text;
        }

        return baby;
    }

    private boolean isDropAcceptable(DropTargetDropEvent dtde) {
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param dte DOCUMENT ME!
     */
    public void dragExit(DropTargetEvent dte) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param ns DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public RootNodeSource getRootSource(NodeSource ns) {
        if (ns != null) {
            if (ns instanceof RootNodeSource) {
                return (RootNodeSource) ns;
            } else {
                return getRootSource(ns.getParent());
            }
        }

        return null;
    }
}
