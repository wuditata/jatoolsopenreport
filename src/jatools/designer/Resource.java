package jatools.designer;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Resource extends java.util.ListResourceBundle {
    static final Object[][] contents = new String[][] {
            { "BackColor", App.messages.getString("res.141") },
            { "Border", App.messages.getString("res.142") },
            { "Caption", App.messages.getString("res.143") },
            { "Font", App.messages.getString("res.144") },
            { "ForeColor", App.messages.getString("res.145") },
            { "Format", App.messages.getString("res.146") },
            { "Height", App.messages.getString("res.147") },
            { "X", "X" },
            { "LinePattern", App.messages.getString("res.148") },
            { "LineSize", App.messages.getString("res.149") },
            { "Name", App.messages.getString("res.81") },
            { "BreakPage", App.messages.getString("res.150") },
            { "VerticalAlignment", App.messages.getString("res.151") },
            { "HorizontalAlignment", App.messages.getString("res.152") },
            { "SizeMode", App.messages.getString("res.153") },
            { "Source", App.messages.getString("res.80") },
            { "Y", "Y" },
            { "Width", App.messages.getString("res.154") },
            { "LeftMargin", App.messages.getString("res.155") },
            { "TopMargin", App.messages.getString("res.156") },
            { "RightMargin", App.messages.getString("res.157") },
            { "BottomMargin", App.messages.getString("res.158") },
            { "HeaderVisible", App.messages.getString("res.159") },
            { "FooterVisible", App.messages.getString("res.160") }
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
