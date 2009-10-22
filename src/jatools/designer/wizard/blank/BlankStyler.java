package jatools.designer.wizard.blank;


import jatools.ReportDocument;
import jatools.component.Page;
import jatools.component.PagePanel;
import jatools.designer.wizard.BuilderContext;
import jatools.designer.wizard.ReportBuilder;
import jatools.designer.wizard.ReportStyler;
import jatools.dom.src.RootNodeSource;
import jatools.util.Util;

import java.awt.Frame;

import javax.swing.Icon;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class BlankStyler implements ReportStyler {
    static final String STYLER_NAME = "空白报表";
    static final String ICON_URL = "/jatools/icons/styleblank.gif";
    ReportBuilder builder;

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return getName();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return STYLER_NAME;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Icon getIcon() {
        return Util.getIcon(ICON_URL);
    }

    /**
     * DOCUMENT ME!
     *
     * @param doc DOCUMENT ME!
     * @param context DOCUMENT ME!
     */
    public void format(ReportDocument doc, BuilderContext context) {
        Page page = new Page();

        PagePanel header = new PagePanel();
        header.setHeight(80);

        page.setHeader(header);

        PagePanel footer = new PagePanel();
        footer.setHeight(280);

        page.setFooter(footer);

        PagePanel body = new PagePanel();

        page.setBody(body);

        doc.setPage(page);

        doc.setNodeSource(getRootNodeSource1());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ReportBuilder getBuilder() {
        if (builder == null) {
            builder = new _BlankBuilder();
        }

        return builder;
    }

    private RootNodeSource getRootNodeSource1() {
        return new RootNodeSource();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getDescription() {
        return "生成一个包含页脚,页眉,主体的报表.";
    }

    class _BlankBuilder implements ReportBuilder {
        public ReportDocument build(Frame owner, BuilderContext context) {
            ReportDocument doc = new ReportDocument();

            format(doc, context);

            return doc;
        }
    }
}
