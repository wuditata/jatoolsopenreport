package jatools.component.chart.component;

import jatools.component.chart.chart.Gc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;





/**
 * ��ɫ��
 *
 * �˵�ɫ�������Ϊ�Ի����е����ʹ�ã�Ҳ���������Ͽ�ʹ��
 *
 * ������Ҫά��һ��ɫֵ���û�����ͨ��setColor���ó�ֵ������ͨ��getColorȡ�ñ�ѡ����ɫֵ
 * @version $Revision: 1.1 $
 * @author $author$
 */
public class ZColorPallette extends JLabel {
    public static final int NULL_BOX_VISIBLE = 1;
    public static final int OTHER_BOX_VISIBLE = 2;
    final static int NULL_COLOR_INDEX = 64; // ����ɫɫ��
    final static int OTHER_COLOR_INDEX = 65; // ������ɫɫ��
    final static int COLUMN = 8; // ɫ����������
    final static int ROW = 8;
    final static String NULL_COLOR_TEXT = "͸��"; // "��ɫ��"����ʾ���ַ� //
    final static String OTHER_COLOR_TEXT = "����"; // "����ɫ��"����ʾ���ַ� //
    final static int COLOR_DROP_SIZE = 12; // ɫ���С
    final static int DROP_GAP = 7; // ɫ����
    final static int TEXT_BOX_HEIGHT = 25; // ��ɫ������ɫ��ĸ߶�

    // ɫ���ɫֵ��������
    private static Color[] colors8x8;

    // ��ǰѡ��ɫ,ÿ�λ�����������ʱ��setColor���г�ʼ��
    // �������ر�ʱ������getColorʱҲ���޸Ĵ˱���
    private Color color;

    // �����ı��ָ��������壬�ڻ���ʱ����ֵ
    Rectangle indexColorBox = new Rectangle();
    Rectangle nullColorBox = new Rectangle();
    Rectangle otherColorBox = new Rectangle();

    // ������Ͽ����ʹ��ʱ����Ͽ�֪ͨ�������Ƿ�����Ƕ�뵽��Ͽ���
    private boolean atHeader;

    // ��ɫ������ɫ���Ƿ�ɼ�
    private int textBoxVisible;

    // ��ѡ���ɫ��
    private int selectedIndex = -1;

    // ��ǰ�����е�ɫ��,����ƶ�ʱ���뿪ʱ�޸ı�ֵ�� ɫ��Ϊ-1��ʲôҲû����
    private int hittedIndex = -1;

    // ���������ϣ����ߵ�ǰɫ�ı�
    Vector listeners = new Vector();

    // ���汳������Ϊ������������Ͽ�ı�
    Color backColor;

    /**
 * Creates a new ZColorPallette object.
 */
    public ZColorPallette() {
        this(NULL_BOX_VISIBLE);
    }

    /**
 * Creates a new ZColorPallette object.
 */
    public ZColorPallette(int textBoxVisible) {
        this.textBoxVisible = textBoxVisible;

        int w = (COLUMN * COLOR_DROP_SIZE) + ((COLUMN + 1) * DROP_GAP);
        int h = (ROW * COLOR_DROP_SIZE) + ((ROW + 2) * DROP_GAP);

        if ((textBoxVisible & NULL_BOX_VISIBLE) != 0) {
            nullColorBox.width = w;
            nullColorBox.height = TEXT_BOX_HEIGHT;
            h += TEXT_BOX_HEIGHT;
        }

        if ((textBoxVisible & OTHER_BOX_VISIBLE) != 0) {
            otherColorBox.width = w;
            otherColorBox.height = TEXT_BOX_HEIGHT;
            h += TEXT_BOX_HEIGHT;
        }

        indexColorBox.width = w;
        this.setPreferredSize(new Dimension(w, h));
        this.setMinimumSize(getPreferredSize());

        backColor = getBackground();

        listenToMouse();
    }

    /**
 * DOCUMENT ME!
 *
 * @return DOCUMENT ME!
 */
    public static Color[] get8x8Colors() {
        if (colors8x8 == null) {
            int[] values = new int[] { 0, 128, 192, 255 };
            colors8x8 = new Color[64];

            int index = 0;

            // ���� 8*8����ɫ
            for (int r = 0; r < values.length; r++) {
                for (int g = 0; g < values.length; g++) {
                    for (int b = 0; b < values.length; b++) {
                        colors8x8[index] = new Color(values[r], values[g], values[b]);
                        index++;
                    }
                }
            }
        }

        return colors8x8;
    }

    /**
 * ����仯������
 *
 * @param lst �仯������
 */
    public void addChangeListener(ChangeListener lst) {
        listeners.add(lst);
    }

    /**
 * ��ȥ�仯������
 *
 * @param lst Ҫ���ߵ�������
 */
    public void removeChangeListener(ChangeListener lst) {
        listeners.remove(lst);
    }

    /**
 * �����仯��������֪ͨ��ǰɫ�ı�
 */
    public void fireChangeListener() {
        ChangeEvent evt = new ChangeEvent(this);

        for (int i = 0; i < listeners.size(); i++) {
            ChangeListener lst = (ChangeListener) listeners.get(i);
            lst.stateChanged(evt);
        }
    }

    /**
 * ֪ͨ�Ƿ���Ƕ�뷽ʽ����
 * �������Ͽ����ʹ��ʱ����������Ͽ�ͨ���ô˷���
 *
 * @param atHeader true/false ��/����Ƕ�뷽ʽ
 * @see #paintComponent
 */
    public void setAtHeader(boolean atHeader) {
        this.atHeader = atHeader;
    }

    /**
 * ���õ�ǰ��ɫֵ
 *
 * @param color ��ǰ��ɫ
 */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
 * ȡ��ѡ������ɫ
 *
 * @return ��ǰѡ��ɫ
 */
    public Color getColor() {
        if ((selectedIndex < 64) && (selectedIndex >= 0)) {
            Color[] colors = get8x8Colors();

            return colors[selectedIndex];
        } else if (selectedIndex == 64) {
            color = Gc.TRANSPARENT;
        }

        //// else if(selectedIndex = -1){  ���ı䣬�԰�setColorʱ��ֵ���� }
        return color;
    }

    /**
 * ����뿪ʱ������ɫ��ָ�
 *
 * @param e ����¼�
 */
    public void mouseExited(MouseEvent e) {
        hittedIndex = -1; // û�л����κ�ɫ��

        // ��������Ͽ�ʹ�ã�����this.repaint()����ʹ�����б�ˢ�£����ڴ�Ӧȡ��Դ���������ˢ��
        JComponent comp = (JComponent) e.getSource();
        comp.repaint();
    }

    /**
 * ����¼�����ʱ��֪ͨɫֵ���ı�
 *
 * @param e ����¼�
 */
    public void mousePressed(MouseEvent e) {
        int i = hitIndex(e.getX(), e.getY());

        boolean isChanged = false;

        if (i == ZColorPallette.OTHER_COLOR_INDEX) {
            Color selectedColor = JColorChooser.showDialog(this,"ѡȡ��ɫ", color); //

            if ((selectedColor != null) && !selectedColor.equals(color)) {
                color = selectedColor;
                isChanged = true;
            }
        } else if (i != selectedIndex) {
            selectedIndex = i;
            color = getColor();
            isChanged = true;
        }

        if (isChanged) {
            selectedIndex = -1;

            JComponent comp = (JComponent) e.getSource();
            comp.repaint();
            fireChangeListener();
        }
    }

    /**
 * ����ƶ�ʱ�����е�ɫ���Գ���ROLLOVER���
 *
 * @param e ����¼�
 */
    public void mouseMoved(MouseEvent e) {
        int i = hitIndex(e.getX(), e.getY());

        if (hittedIndex != i) {
            hittedIndex = i;

            JComponent comp = (JComponent) e.getSource();
            comp.repaint();
        }
    }

    /**
 * ��������¼�
 *
 * �����standalone��ʽʹ��ʱ������ֱ�Ӵӻ����н�������¼���
 * ��������Ͽ�ʹ�ã���÷����������κ�Ч��
 *
 */
    private void listenToMouse() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ZColorPallette.this.mousePressed(e);
            }

            public void mouseExited(MouseEvent e) {
                ZColorPallette.this.mouseExited(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                ZColorPallette.this.mouseMoved(e);
            }
        });
    }

    /**
 * ����������ȡ�ñ����е���ɫ���
 *
 * @param x ģ����
 * @param y ������
 *
 * @return ���е���ɫ���
 */
    public int hitIndex(int x, int y) {
        //-----------------------------
        // 1 2 3 4 5 6 7 8
        // ...
        // 1 2 3 4 5 6 7 8
        // none
        // other
        // -----------------------------
        int i = -1;

        if (nullColorBox.contains(x, y)) {
            i = NULL_COLOR_INDEX;
        } else if (otherColorBox.contains(x, y)) {
            i = OTHER_COLOR_INDEX;
        } else if (indexColorBox.contains(x, y)) {
            // ��indexColorBox��ȡ����ɫ���
            int column = (x - DROP_GAP / 2) / (COLOR_DROP_SIZE + DROP_GAP);
            column = (column >= COLUMN) ? (COLUMN - 1) : column;

            int row = (y - DROP_GAP / 2) / (COLOR_DROP_SIZE + DROP_GAP);
            row = (row >= ROW) ? (ROW - 1) : row;
            i = (row * COLUMN) + column;
        }

        return i;
    }

    /**
 * ����ɫ��
 *
 * ��ɫ�������ֻ������������Ƕ�룬�����һ��ɫ�㣬�������Ƕ�룬������ɫ�㼰���ֿ�
 * ���ֿ��Ƿ񻭳���ѡ�����ݹ��캯����������
 *
 * @param g_ ͼ�ζ���
 */
    public void paintComponent(Graphics g_) {
        Graphics2D g2 = (Graphics2D) g_;

        // �������������Ƕ�뵽��Ͽ��У���ͷ��ʽ����
        if (atHeader) {
            paintAtHeader(g2);

            return;
        }

        // ɫ��ļ��
        int x = DROP_GAP;
        int y = DROP_GAP;


        // ���Ʊ���
        g2.setColor(backColor);

        Rectangle bounds = getBounds();
        g2.fill(bounds);

        Rectangle box = new Rectangle(x, y, COLOR_DROP_SIZE, COLOR_DROP_SIZE);

        // ���� 8*8����ɫ
        Color[] colors = get8x8Colors();

        for (int i = 0; i < colors.length; i++) {
            Color c = colors[i];
            box.x = x;
            box.y = y;
            g2.setColor(c);
            g2.fill(box);
            g2.setColor(Color.darkGray);
            g2.draw(box);

            if ((color != null) && c.equals(color)) {
                // ����Ǳ�ѡ��ɫ�򰼽���ʾ
                draw3DBox(g2, box, DROP_GAP / 2, DROP_GAP / 2, false);
            } else if (hittedIndex == i) {
                // ������б��㣬��ͻ����ʾ
                draw3DBox(g2, box, DROP_GAP / 2, DROP_GAP / 2, true);
            }

            x += (COLOR_DROP_SIZE + DROP_GAP);

            if (((i + 1) % 8) == 0) {
                x = DROP_GAP;
                y += (COLOR_DROP_SIZE + DROP_GAP);
            }
        }

        indexColorBox.height = y;

        // �� nullColorBox
        FontMetrics fm = g2.getFontMetrics(g2.getFont());

        if ((textBoxVisible & NULL_BOX_VISIBLE) != 0) {
            nullColorBox.y = y;
            nullColorBox.x = 0;


            // ���ַ�
            x = (nullColorBox.width - fm.stringWidth(NULL_COLOR_TEXT)) / 2;
            y = (nullColorBox.y + nullColorBox.height) - ((nullColorBox.height - fm.getHeight()) / 2);
            g2.setColor(Color.black);
            g2.drawString(NULL_COLOR_TEXT, x, y);

            g2.setColor(Color.darkGray);
            box = (Rectangle) nullColorBox.clone(); // new Rectangle(1, 2, 18, 10);
            box.grow(-DROP_GAP, -DROP_GAP / 2);

            g2.draw(box);

            // ���߿�
            if (color == null) {
                draw3DBox(g2, nullColorBox, -DROP_GAP, -DROP_GAP / 2, false);
            } else if (hittedIndex == NULL_COLOR_INDEX) {
                draw3DBox(g2, nullColorBox, -DROP_GAP, -DROP_GAP / 2, true);
            }


            // ���赱ǰ������
            y = nullColorBox.y + nullColorBox.height;
        }

        if ((textBoxVisible & OTHER_BOX_VISIBLE) != 0) {
            // �� otherColorBox
            otherColorBox.y = y;
            otherColorBox.x = 0;
            x = (otherColorBox.width - fm.stringWidth(OTHER_COLOR_TEXT)) / 2;
            y = (otherColorBox.y + otherColorBox.height) - ((otherColorBox.height - fm.getHeight()) / 2);

            g2.drawString(OTHER_COLOR_TEXT, x, y - 3);

            if (hittedIndex == OTHER_COLOR_INDEX) {
                draw3DBox(g2, otherColorBox, -DROP_GAP / 2, 0, true);
            }
        }
    }

    /**
 * ��Ƕ�뷽ʽ������ɫ��
 *
 * ����Ͽ�һ��ʹ��ʱҪ�󣬴�ʱ������ǰɫһ��ɫ��
 *
 * @param g2 ͼ�ζ���
 */
    private void paintAtHeader(Graphics2D g2) {
        Rectangle box = new Rectangle(1, 2, 18, 10);
        g2.setColor(color);
        g2.fill(box);
        g2.setColor(Color.DARK_GRAY);
        g2.draw(box);
    }

    /**
 * ����һ������ı߿�
 *
 * @param g2 ͼ�ζ���
 * @param b �߿�
 * @param gx ������Ŵ���
 * @param gy ������Ŵ���
 * @param raised �Ƿ�ͻ����ʾ
 */
    public void draw3DBox(Graphics2D g2, Rectangle b, int gx, int gy, boolean raised) {
        Rectangle border = (Rectangle) b.clone();

        border.grow(gx, gy);
        g2.setColor(Color.LIGHT_GRAY);
        g2.draw3DRect(border.x, border.y, border.width, border.height, raised);
    }
}