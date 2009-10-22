package jatools.engine.printer;

import jatools.core.view.AbstractView;
import jatools.core.view.CompoundView;

import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Iterator;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class RepeatStore {
    CompoundView copiedView = null; // 如果有复制,则复制的所有组件,都放里面
    AbstractView firstView = null;

    /**
     * DOCUMENT ME!
     */
    public void reset() {
        copiedView = null;
        firstView = null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param view DOCUMENT ME!
     */
    public void add(AbstractView view) {
        //    	 如果是正在复制中,则加到copyview
        if (firstView == null) {
            firstView = view;
        } else if (copiedView != null) { // 
            copiedView.add(view);
        } else {
            //  firstView != null && copiedView == null
            //  是复制的view;
            // 	所以 copiedView 至少有两个view;
            copiedView = new CompoundView();
            copiedView.setBounds(new Rectangle());
            copiedView.add(firstView);
            copiedView.add(view);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public AbstractView getResultView() {
        if (copiedView == null) {
            return firstView;
        } else {
            Rectangle union = calculateBounds(copiedView);

            copiedView.setBounds(calculateBounds(copiedView));
            copiedView.setPadding(new Insets(-union.y, -union.x, 0, 0));
//            copiedView.union = true;
         //   copiedView.setBackColor(Color.red);

            return copiedView;
        }
    }

    private Rectangle calculateBounds(CompoundView copiedView) {
        Iterator it = copiedView.elementCache.iterator();
        AbstractView child = (AbstractView) it.next(); // 肯定大于一个子件
        child.setLayoutRule(null);

        Rectangle r = child.getBounds();

        while (it.hasNext()) {
            child = (AbstractView) it.next();
            child.setLayoutRule(null);
            r = r.union(child.getBounds());
        }

        return r;
    }
}
