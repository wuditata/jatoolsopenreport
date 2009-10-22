package jatools.component.table;

import jatools.component.Component;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
  */
public class PanelStore {
    TableBase grid;
    Component[][] comps;

    /**
     * Creates a new CellStore object.
     *
     * @param grid DOCUMENT ME!
     */
    public PanelStore(TableBase grid) {
        this.grid = grid;
    }

    /**
     * DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     * @param col DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Cell getCellOver(int row, int col) {
        Component c = getComponentOver(row, col);

        if (c != null) {
            return c.getCell();
        } else {
            return null;
        }
    }

    private Component[][] getComponents() {
        if (this.comps == null) {
            this.comps = new Component[this.grid.getRowCount()][this.grid.getColumnCount()];
            populateComponent(this.grid, comps);
        }

        return comps;
    }

    private void populateComponent(Component p, Component[][] to) {
        if (p instanceof GridComponent) {
            Cell cel = p.getCell();

            if (cel != null) {
                for (int j = cel.row; j <= cel.row2(); j++) {
                    for (int k = cel.column; k <= cel.column2(); k++) {
                        to[j][k] = p;
                    }
                }
            }

            for (int i = 0; i < p.getChildCount(); i++) {
                Component c = p.getChild(i);
                populateComponent(c, to);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     * @param column DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Component getComponentOver(int row, int column) {
        Component c = this.getComponents()[row][column];

        if (c != null) {
            return c;
        } else {
            return grid;
        }
    }
}
