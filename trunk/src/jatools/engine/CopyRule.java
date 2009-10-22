package jatools.engine;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class CopyRule {
    final static int EVERY_PAGE_COPY = 0;
    final static int ROW_COPY = 1;
    final static int WRAPPED_ROW_COPY = 2;
    final static int COLUMN_COPY = 3;
    final static int WRAPPED_COLUMN_COPY = 4;
    private int rule;
    private int gapx;
    private int gapy;

    //
    //    /**
    //     * Creates a new CopyRule object.
    //     */
    //    public CopyRule(int rule, PrinterLayout layout) {
    //        this.rule = rule;
    //        this.layout = layout;
    //    }
    //
    //    /**
    //     * DOCUMENT ME!
    //     *
    //     * @return DOCUMENT ME!
    //     */
    //    public boolean isInsideCopySupported() {
    //        return rule != EVERY_PAGE_COPY;
    //    }
    //
    //    /**
    //     * DOCUMENT ME!
    //     *
    //     * @param layout DOCUMENT ME!
    //     */
    //    public boolean prepareCopy() {
    //        switch (rule) {
    //        case COLUMN_COPY:
    //            return columnCopy(layout);
    //        }
    //
    //        return false;
    //    }
    //
    //    private boolean columnCopy(PrinterLayout layout) {
    //        // 取得最后一次打印的区域,
    //        // 取得本组件大小
    //        // 在本组件下面,试着加,不能加,则退出
    //        Rectangle lastcopy = layout.getLastCopyBounds();
    //        Rectangle mincopy = layout.getMinCopyBounds();
    //
    //        int ymax = lastcopy.y + lastcopy.height;
    //
    //        if (layout.bottomContained(ymax)) {
    //            layout.addCopy(gapx, gapy,);
    //        }
    //
    //        //   layout.bottomContained(mincopy);
    //
    //        //layout.getLastCopyBounds();
    //
    //        //layout.getComponentBounds();
    //        return false;
    //    }
}
