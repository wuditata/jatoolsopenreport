package jatools.designer.property.editor;

import jatools.swingx.ColorPallette;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListCellRenderer;



/**
 * @author   java9
 */
public class ColorComboBox extends JComboBox implements ListCellRenderer/*自定义的弹出list绘制器*/
{
    public static final int NULL_BOX_VISIBLE = 1;
    public static final int OTHER_BOX_VISIBLE = 2;
    public static final String PROPERTY_POPUP_CLOSED = "popup_closed"; //

    // 调色板，也是弹出式列表的绘制器
    private ColorPallette listRenderer;

    // 是一个项目集，始终只有一个颜色对象，而且始终选定第一项，两种情况改变唯一元素
    // 1.当用setColor 初始颜色时，改变此集合的唯一元素
    // 2.当弹出式列表关闭时后 ，从列表中即调色板中取回
    private Color color = Color.black;

    // 本组合框的模型，以colors作为备选项目
    private DefaultComboBoxModel model = new DefaultComboBoxModel();

    /**
    * Creates a new ZColorPicker object.
    */
    public ColorComboBox() {
        this(NULL_BOX_VISIBLE);
    }

    /**
     * Creates a new ZColorComboBox object.
     *
     * @param textVisible DOCUMENT ME!
     */
    public ColorComboBox(int textVisible) {
        // 填充唯一项目对象，默认为黑色，一般会通过setColor进行修改
        model.addElement(color);

        setModel(model);


        //创建调色板列表绘制器
        listRenderer = new ColorPallette(textVisible);


        // 告诉组合框到此地找到列表绘制器
        setRenderer(this);

        // 创建ui,该ui可以定制弹出式列表的大小及即时取到弹出式列表的实例
        FixedSizeComboBoxUI ui = new FixedSizeComboBoxUI();
        setUI(ui);


        // 取到列表，以便将鼠标事件传给调色板，这样调色板可以呈现rollover风格
        listenToList(ui.getPopupList());


        // 指向第一项
        setSelectedIndex(0);


        // 不可编辑
        setEditable(false);

        JComponent jc = (JComponent) this.getEditor().getEditorComponent();
        jc.setBorder(BorderFactory.createLineBorder(Color.red)); // . .setBackground(Color.red)  ;
    }

    /**
	 * DOCUMENT ME!
	 * @param color   DOCUMENT ME!
	 * @uml.property   name="color"
	 */
    public void setColor(Color color) {
        this.color = color;
        setSelectedItem(this.color);
        repaint();
    }

    /**
	 * DOCUMENT ME!
	 * @return   DOCUMENT ME!
	 * @uml.property   name="color"
	 */
    public Color getColor() {
        return color;
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
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, 
                                                  boolean cellHasFocus) {
        listRenderer.setColor(color);


        // 如果index == -1,则指示绘制器正被嵌入组合框中，而不是处于弹出状态
        listRenderer.setAtHeader(index == -1);

        return listRenderer;
    }

    /**
    * 侦听弹出式列表
    *
    * 取到列表，以便将鼠标事件传给调色板，这样调色板可以呈现rollover风格
    *
    * @param list 弹出式列表
    */
    private void listenToList(JList list) {
        list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                listRenderer.mousePressed(e);
                color = listRenderer.getColor();
                setSelectedIndex(0);
            }

            public void mouseReleased(MouseEvent e) {
                // 通知列表被关闭了
                firePropertyChange(PROPERTY_POPUP_CLOSED, false, true);
            }

            public void mouseExited(MouseEvent e) {
                listRenderer.mouseExited(e);
            }
        });
        list.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                listRenderer.mouseMoved(e);
            }
        });
    }

    /**
     * DOCUMENT ME!
     *
     * @param argv DOCUMENT ME!
     */
    public static void main(String[] argv) {
        ColorComboBox content = new ColorComboBox();

        JDialog d = new JDialog((Frame) null, "Just For Test !"); //
        d.setModal(true);
        d.getContentPane().add(content, BorderLayout.CENTER);
        d.pack();
        d.show();
    }
}