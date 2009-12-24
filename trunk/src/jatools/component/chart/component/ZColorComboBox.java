package jatools.component.chart.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
 * 组合框式的颜色选择器
 * 自带自定义的弹出list绘制器,该选择器只有一项备选项，而且始终选中
 * @version $Revision: 1.1 $
 * @author $author$
 */
public class ZColorComboBox extends JComboBox implements ListCellRenderer/*自定义的弹出list绘制器*/
{
    public static final int NULL_BOX_VISIBLE = 1;
    public static final int OTHER_BOX_VISIBLE = 2;
    public static final String PROPERTY_POPUP_CLOSED = "popup_closed"; //
    private static final Dimension HEAD_ICON_SIZE = new Dimension(20, 10);

    // 调色板，也是弹出式列表的绘制器
    private ZColorPallette listRenderer;

    // 是一个项目集，始终只有一个颜色对象，而且始终选定第一项，两种情况改变唯一元素
    // 1.当用setColor 初始颜色时，改变此集合的唯一元素
    // 2.当弹出式列表关闭时后 ，从列表中即调色板中取回
    private Color color = Color.black;

    // 本组合框的模型，以colors作为备选项目
    private DefaultComboBoxModel model = new DefaultComboBoxModel();
    
    private FillStyleInterface style;

    /**
    * Creates a new ZColorPicker object.
    */
    public ZColorComboBox() {
        this(OTHER_BOX_VISIBLE | NULL_BOX_VISIBLE);
    }

    
    /**
     * Creates a new ZColorComboBox object.
     *
     * @param textVisible DOCUMENT ME!
     */
    public ZColorComboBox(int textVisible) {
        // 填充唯一项目对象，默认为黑色，一般会通过setColor进行修改
        model.addElement(color);

        setModel(model);


        //创建调色板列表绘制器
        listRenderer = new ZColorPallette(textVisible);


        // 告诉组合框到此地找到列表绘制器
        setRenderer(this);

        // 创建ui,该ui可以定制弹出式列表的大小及即时取到弹出式列表的实例
        ZFixedSizeComboBoxUI ui = new ZFixedSizeComboBoxUI();
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
    *
    * @param color DOCUMENT ME!
    */
    public void setColor(Color color) {
        this.color = color;
        //setSelectedItem(this.color);
        repaint();
    }

    public void setFillStyle(FillStyleInterface style){
    	this.style = style;
    	repaint();
    }
    public FillStyleInterface getFillStyle(){
    	return style;
    }
    /**
    * DOCUMENT ME!
    *
    * @return DOCUMENT ME!
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

        if(index == -1){
           	return style.createLabel(getSize());
         }

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
        ZColorComboBox content = new ZColorComboBox();
        SingleColor s = new SingleColor(Color.RED);
        content.setFillStyle(s);
        JDialog d = new JDialog((Frame) null, "Just For Test !"); //
        d.setModal(true);
        d.getContentPane().add(content, BorderLayout.CENTER);
        d.pack();
        d.show();
    }
}