package jatools.designer.action;

import jatools.ReportDocument;
import jatools.designer.App;
import jatools.designer.Main;
import jatools.designer.chooser.ReportChooser;
import jatools.swingx.MessageBox;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;



/**
 * @author   java9
 */
public class NewAction extends ReportAction {


    /**
     * Creates a new NewAction object.
     *
     * @param owner DOCUMENT ME!
     */
    public NewAction() {
        super(App.messages.getString("res.508") + "...   ", getIcon("/jatools/icons/new.gif")); 
        setStroke(ctrl(KeyEvent.VK_N));
    }

    /**
     * DOCUMENT ME!
     *
     * @param e
     *            DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        ReportChooser chooser = ReportChooser.getInstance();

        if (chooser.showDialog(App.messages.getString("res.508"), ReportChooser.SHOW_NEW)) { 

            ReportDocument doc = chooser.getDocument();

            if (doc != null) {
           
                Main.getInstance().createEditor(doc, App.messages.getString("res.531"), null);
            }
        }
    }

    protected boolean saveAs() {
        int answer = MessageBox.show(getEditor(), App.messages.getString("res.389"),
        		App.messages.getString("res.save"), MessageBox.YES_NO_CANCEL); 

        if (answer == MessageBox.YES) {
            ReportDocument doc = getEditor().getDocument();

            if (doc != null) {
                ReportChooser chooser = ReportChooser.getInstance();
                File docFile = ReportDocument.getCachedFile(doc);

                if (docFile != null) {
                    chooser.setSelectedFile(docFile);
                }

                chooser.setDocument(doc);
                chooser.showSaveDialog();
            }
        }

        return answer != MessageBox.CANCEL;
    }

    /**
         * @param newWindow   The newWindow to set.
         * @uml.property   name="newWindow"
         */
    public void setNewWindow(boolean newWindow) {
     //   this.newWindow = newWindow;
    }
}
