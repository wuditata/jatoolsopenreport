package jatools.component.chart.component;

import jatools.util.Util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class ReportListRenderer extends JLabel implements ListCellRenderer {
    private static final int INSETS = 10;
    private static final int GAP = 5;
    private static final int BORDER = 3;
    private static final int ROW_ICONSNUM = 6;
    private static final int COL_ICONSNUM = 5;
    private static final int ICON_WIDTH = 53;
    private static final int ICON_HEIGHT = 54;
    private static final String[] labels = new String[] {
            "线形图",
            "柱形图",
            "饼图",
            "堆叠条形图",
            "堆叠柱形图",
            "面积图",
            "线形图",
            "柱形线形图",
            "柱形堆积图",
            "时间线形图",
            "时间面积图",
            "时间柱形图",
            "时间堆叠柱形图",
            "时间条形图",
            "双轴时间线形图",
            "时间堆叠条形图",
            "汽泡图",
            "趋势图",
            "测量盘图",
            "雷达图",
            "方块图",
            "双轴柱形堆积图",
            "双轴柱形线形图",
            "双轴线形图",
            "高低条形图",
            "高低柱形图",
            "K线图",
            "高低收盘图",
            "甘特图"
        };
    private ImageIcon[] icons = new ImageIcon[3];
    private boolean atHeader = true;
    private String type = "1";
    private int hitIndex = -1;
    private int selectedIndex = 1;
    int bw = ICON_WIDTH + (2 * BORDER);
    int bh = ICON_HEIGHT + (2 * BORDER);

    /**
     * Creates a new ReportListRenderer object.
     */
    public ReportListRenderer() {
        for (int i = 0; i < icons.length; i++) {
            icons[i] = Util.getIcon("/jatools/component/chart/icons/chart" + i + ".gif");
        }

        int width = (INSETS * 2) + (ROW_ICONSNUM * ((2 * BORDER) + ICON_WIDTH)) +
            ((ROW_ICONSNUM - 1) * GAP);
        int height = (INSETS * 2) + (COL_ICONSNUM * ((2 * BORDER) + ICON_HEIGHT)) +
            ((COL_ICONSNUM - 1) * GAP);
        setPreferredSize(new Dimension(width, height));
    }

    private void setAtHeader(boolean yesno) {
        atHeader = yesno;
    }

    /**
     * DOCUMENT ME!
     *
     * @param g1 DOCUMENT ME!
     */
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1.create();

        if (atHeader) {
            g.setColor(Color.BLACK);
            g.drawString(labels[Integer.parseInt(type)], 3, 13);

            return;
        }

        int x = INSETS;
        int y = INSETS;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getBounds().width, getBounds().height);

        for (int i = 0; i < icons.length; i++) {
            g.setColor(Color.GRAY);

            if (selectedIndex == i) {
                g.fillRect(x, y, bw, bh);
            } else {
                g.drawRect(x, y, bw, bh);
            }

            icons[i].paintIcon(this, g, x + BORDER, y + BORDER);

            if (((i + 1) % ROW_ICONSNUM) == 0) {
                x = INSETS;
                y += (bh + GAP);
            } else {
                x += (bw + GAP);
            }
        }

        g.dispose();
    }

    /**
     * DOCUMENT ME!
     *
     * @param list DOCUMENT ME!
     * @param value DOCUMENT ME!
     * @param index DOCUMENT ME!
     * @param isSelected DOCUMENT ME!
     * @param cellHasFocus DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
        setAtHeader(index == -1);

        return this;
    }

    private int hitIndex(int x, int y) {
        int row = (y - INSETS + GAP) / (bh + GAP);
        int col = (x - INSETS + GAP) / (bw + GAP);
        int returnVal = (row * ROW_ICONSNUM) + col;

        if ((x < (INSETS + (col * (bw + GAP)))) || (y < (INSETS + (row * (bh + GAP))))) {
            return -1;
        }

        if (returnVal >= 29) {
            returnVal = -1;
        }

        return returnVal;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getType() {
        return type;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void mousePressed(MouseEvent e) {
        if (hitIndex != -1) {
            type = String.valueOf(selectedIndex);

            /*JComponent c = (JComponent) e.getSource();
            c.repaint();*/

            //repaint();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void mouseMoved(MouseEvent e) {
        hitIndex = hitIndex(e.getX(), e.getY());

        if ((hitIndex != -1) && (hitIndex != selectedIndex)) {
            setToolTipText(labels[hitIndex]);
            selectedIndex = hitIndex;

            JComponent c = (JComponent) e.getSource();
            c.repaint();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param type DOCUMENT ME!
     */
    public void setType(String type) {
        this.type = type;
        selectedIndex = Integer.parseInt(type);
    }
}
