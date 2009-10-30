package jatools.designer.data;




import jatools.data.reader.DatasetReader;
import jatools.designer.App;
import jatools.designer.DatasetReaderConfigureTree;
import jatools.designer.config.DatasetReaderList;
import jatools.swingx.CommandPanel;
import jatools.swingx.MessageBox;
import jatools.swingx.SimpleTreeNode;
import jatools.swingx.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ReaderChooser extends JDialog implements ChangeListener {
    protected boolean exitOk;
    private JButton previewButton;
    private JButton addButton;
    DatasetReaderConfigureTree datasetTree;

    /**
     * Creates a new ReaderChooser object.
     *
     * @param owner DOCUMENT ME!
     * @param proxyContainer DOCUMENT ME!
     */
    public ReaderChooser(Frame owner, DatasetReaderList proxyContainer) {
        super(owner, App.messages.getString("res.494"), true);

        datasetTree = new DatasetReaderConfigureTree();

        datasetTree.addChangeListener(this);

        JScrollPane scrollPane = new JScrollPane(datasetTree);

        this.getContentPane().add(scrollPane, BorderLayout.CENTER);

        previewButton = new JButton(App.messages.getString("res.163"));
        previewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    preview();
                }
            });

        addButton = new JButton(App.messages.getString("res.3"));
        addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    exitOk = true;
                    hide();
                }
            });

        JButton removeButton = new JButton(App.messages.getString("res.4"));
        removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    exitOk = false;
                    hide();
                }
            });

        CommandPanel commandsPane = CommandPanel.createPanel(false);

        commandsPane.addComponent(previewButton);
        commandsPane.addComponent(addButton);
        commandsPane.addComponent(removeButton);

        datasetTree.expandAll();

        getContentPane().add(commandsPane, BorderLayout.SOUTH);
        SwingUtil.setBorder6((JComponent) getContentPane());
        pack();

        setSize(350, 400);
        this.setLocationRelativeTo(owner);
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == datasetTree) {
            boolean hitReader = ((SimpleTreeNode) datasetTree.getSelectionPath()
                                                             .getLastPathComponent()).getUserObject() instanceof DatasetReader;
            previewButton.setEnabled(hitReader);
            addButton.setEnabled(hitReader);
        }
    }

    private void preview() {
        try {
            DatasetReader proxy = (DatasetReader) ((SimpleTreeNode) datasetTree.getSelectionPath()
                                                                               .getLastPathComponent()).getUserObject();

            DatasetPreviewer preview = new DatasetPreviewer((Frame) getOwner());
            preview.setLocationRelativeTo(this);
            preview.setReader(proxy);
            preview.show();
        } catch (Exception ex) {
            MessageBox.error(this, ex.getMessage());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public DatasetReader getReader() {
        if (this.exitOk) {
            return (DatasetReader) ((SimpleTreeNode) datasetTree.getSelectionPath()
                                                                .getLastPathComponent()).getUserObject();
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isExitOk() {
        return exitOk;
    }
}
