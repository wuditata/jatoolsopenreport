package jatools.designer.wizard.crosstab;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author Jiang Dehua
 * @version 1.0
 */
public class OrderBy {
    public static final String ASC = "ÉýÐò";
    public static final String DESC = "½µÐò";
    public static final String ORIGINAL = "Ô­Ðò";
    String orderby;

    /**
     * Creates a new OrderBy object.
     *
     * @param orderby DOCUMENT ME!
     */
    public OrderBy(String orderby) {
        super();
        this.orderby = orderby;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getOrderby() {
        return orderby;
    }

    /**
     * DOCUMENT ME!
     *
     * @param orderBy DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int getIntOrder(String orderBy) {
        if (orderBy.equals(ASC)) {
            return jatools.dom.Group.ASCEND;
        } else if (orderBy.equals(DESC)) {
            return jatools.dom.Group.DESEND;
        } else {
            return jatools.dom.Group.ORIGINAL;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param orderby DOCUMENT ME!
     */
    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return orderby;
    }
}
