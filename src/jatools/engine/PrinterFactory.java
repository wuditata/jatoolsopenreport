package jatools.engine;

import jatools.component.ColumnPanel;
import jatools.component.Component;
import jatools.component.Image;
import jatools.component.Label;
import jatools.component.Page;
import jatools.component.PagePanel;
import jatools.component.Panel;
import jatools.component.Text;
import jatools.component.table.HeaderTable;
import jatools.component.table.PowerTable;
import jatools.component.table.RowPanel;
import jatools.component.table.Table;
import jatools.designer.App;
import jatools.engine.printer.ColumnPanelPrinter;
import jatools.engine.printer.ImagePrinter;
import jatools.engine.printer.LabelPrinter;
import jatools.engine.printer.PanelPrinter;
import jatools.engine.printer.PowerTablePrinter;
import jatools.engine.printer.RowPanelPrinter;
import jatools.engine.printer.TextPrinter;
import jatools.engine.printer.table.TablePrinter;
import jatools.engine.script.Script;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class PrinterFactory {
    static final Map printerClassesCache = new HashMap();

    static {
        printerClassesCache.put(Label.class, LabelPrinter.class);
        printerClassesCache.put(Page.class, TablePrinter.class);
        printerClassesCache.put(Table.class, TablePrinter.class);

        printerClassesCache.put(HeaderTable.class, TablePrinter.class);
        printerClassesCache.put(PowerTable.class, PowerTablePrinter.class);

        printerClassesCache.put(ColumnPanel.class, ColumnPanelPrinter.class);

        printerClassesCache.put(Panel.class, PanelPrinter.class);
        printerClassesCache.put(PagePanel.class, PanelPrinter.class);
        printerClassesCache.put(RowPanel.class, RowPanelPrinter.class);

        printerClassesCache.put(Text.class, TextPrinter.class);

        printerClassesCache.put(Image.class, ImagePrinter.class);
    }

    private final Map printerCache = new HashMap();

    /**
     * DOCUMENT ME!
     *
     * @param forClass DOCUMENT ME!
     * @param printerClass DOCUMENT ME!
     */
    public static void registerPrinterClass(Class forClass, Class printerClass) {
        printerClassesCache.put(forClass, printerClass);
    }

    /**
     * DOCUMENT ME!
     *
     * @param c DOCUMENT ME!
     * @param script DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Printer getPrinter(Component c, Script script) {
        Printer printer = (Printer) printerCache.get(c);

        if (printer == null) {
            Class printerClass = (Class) printerClassesCache.get(c.getClass());

            if (printerClass == null) {
                System.out.println(c.getClass() + App.messages.getString("res.23") + c.getClass());

                return null;
            }

            try {
                printer = (Printer) printerClass.newInstance();
                printer.open(c, script);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (printer != null) {
                this.printerCache.put(c, printer);
                printer.setLocalID(this.printerCache.size());
            }
        }

        return printer;
    }

    /**
     * DOCUMENT ME!
     */
    public void close() {
        Iterator it = printerCache.values().iterator();

        while (it.hasNext()) {
            Printer printer = (Printer) it.next();
            printer.close();
        }
    }
}
