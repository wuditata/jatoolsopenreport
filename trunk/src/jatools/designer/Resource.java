package jatools.designer;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Resource extends java.util.ListResourceBundle {
    static final Object[][] contents = new String[][] {
            { "BackColor", "背景" },
            { "Border", "边框" },
            { "Caption", "文本" },
            { "Font", "字体" },
            { "ForeColor", "前景" },
            { "Format", "格式" },
            { "Height", "高度" },
            { "X", "X" },
            { "LinePattern", "线型" },
            { "LineSize", "线宽" },
            { "Name", "名称" },
            { "BreakPage", "打印前换页" },
            { "VerticalAlignment", "纵向对齐" },
            { "HorizontalAlignment", "横向对齐" },
            { "SizeMode", "显示方式" },
            { "Source", "数据集" },
            { "Y", "Y" },
            { "Width", "宽度" },
            { "LeftMargin", "左边距" },
            { "TopMargin", "上边距" },
            { "RightMargin", "右边距" },
            { "BottomMargin", "下边距" },
            { "HeaderVisible", "页眉可见" },
            { "FooterVisible", "页脚可见" }
        };

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object[][] getContents() {
        return contents;
    }
}
