package jatools.engine.printer;

import jatools.component.Page;
import jatools.component.Panel;
import jatools.core.view.CompoundView;
import jatools.engine.layout.FreePrinterLayout;
import jatools.engine.layout.PrinterLayout;
import jatools.engine.script.Context;
import jatools.engine.script.Script;

import java.awt.Insets;
import java.awt.Rectangle;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class PanelPrinter extends AbstractContainerPrinter {
    /**
     * DOCUMENT ME!
     *
     * @param layout DOCUMENT ME!
     * @param script DOCUMENT ME!
     */

    //    public void requestFooterReservation(PrinterLayout layout, Script script) {
    //    	
    //             PageRule rule = getPrintStyle().getPageRule();
    //
    //             if ((rule != null) && rule.footer) {
    //                 layout.makeFooterReservation(this.getComponent().getWidth());
    //             }
    //      
    //    }
    protected PrinterLayout createLayout(Context context) {
        Rectangle r = new Rectangle(this.getComponent().getBounds());

        CompoundView view = new CompoundView();

        view.setBackColor(this.getComponent().getBackColor());
        view.setBounds(r);
        view.setPadding(this.getComponent().getPadding());
        setBackgroundImageStyle(context.getScript(), view);
        view.setBorder(this.getComponent().getBorder());

        Insets is = this.getComponent().getPadding();
        Rectangle imageable = this.getComponent().getBounds();
        imageable.x = is.left;
        imageable.y = is.top;

        imageable.height = context.getLayout().getMaxBottom(this.getComponent()) - is.top -
            is.bottom;
        imageable.width = context.getLayout().getMaxRight(this.getComponent()) - is.left -
            is.right;

        //    view.setImageable((Rectangle) imageable.clone());
        return new FreePrinterLayout(this, imageable, view);
    }

    //    private void loadBackgroundImage(BackgroundImageCSS css) {
    /**
     * DOCUMENT ME!
     *
     * @param script DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isEveryPagePrint(Script script) {
        Panel p = (Panel) this.getComponent();

        if ((p.getType() == Page.FOOTER) || (p.getType() == Page.HEADER)) {
        	return true;
        } else {
            return super.isEveryPagePrint(script);
        }
    }
}
//class FreeComparator implements Comparator {
//    private static FreeComparator instance = new FreeComparator();
//
//    private FreeComparator() {
//    }
//
//    /**
//     * DOCUMENT ME!
//     *
//     * @param o1 DOCUMENT ME!
//     * @param o2 DOCUMENT ME!
//     *
//     * @return DOCUMENT ME!
//     */
//    public int compare(Object o1, Object o2) {
//        Printer p1 = (Printer) o1;
//        Printer p2 = (Printer) o2;
//        int y1 = p1.getComponent().getY();
//        int y2 = p2.getComponent().getY();
//
//        if (y1 > y2) {
//            return 1;
//        } else if (y1 < y2) {
//            return -1;
//        } else {
//            int x1 = p1.getComponent().getX();
//            int x2 = p2.getComponent().getX();
//
//            if (x1 > x2) {
//                return 1;
//            } else if (x1 < x2) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//    }
//
//    /**
//     * DOCUMENT ME!
//     *
//     * @return DOCUMENT ME!
//     */
//    public static FreeComparator getInstance() {
//        return instance;
//    }
//}
