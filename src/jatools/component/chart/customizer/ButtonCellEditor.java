package jatools.component.chart.customizer;

//import jatools.component.chart.Tip;
import jatools.swingx.CommandPanel;
import jatools.swingx.GridBagConstraints2;
import jatools.swingx.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import jatools.swingx.TemplateTextField;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor,
    TableCellRenderer {
    _TipDialog tipEditor;
    JButton b;
    JTable t;
//    Tip tip;

    /**
     * Creates a new ButtonCellEditor object.
     *
     * @param text DOCUMENT ME!
     */
    public ButtonCellEditor(String text) {
        b = new JButton(text);
        b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    doEdit();
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param table DOCUMENT ME!
     * @param value DOCUMENT ME!
     * @param isSelected DOCUMENT ME!
     * @param row DOCUMENT ME!
     * @param column DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
        int row, int column) {
//        tip = (Tip) value;
        this.t = table;

        return b;
    }

    private void doEdit() {
        if (tipEditor == null) {
            tipEditor = new _TipDialog((JDialog) t.getTopLevelAncestor());
        }

//        tipEditor.setTip(tip);
        tipEditor.show();

        if (tipEditor.done) {
//            tip = tipEditor.getTip();
            stopCellEditing();
        } else {
            cancelCellEditing();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param table DOCUMENT ME!
     * @param value DOCUMENT ME!
     * @param isSelected DOCUMENT ME!
     * @param hasFocus DOCUMENT ME!
     * @param row DOCUMENT ME!
     * @param column DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
        return b;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getCellEditorValue() {
//        return tip;
    	return null;
    }
}


class _TipDialog extends JDialog {
//    Tip tip;
    TemplateTextField labelField = new TemplateTextField();
    TemplateTextField urlField = new TemplateTextField();
    boolean done;
    JComboBox targetCombo = new JComboBox(new Object[] {
                "_blank",
                "_parent",
                "_self",
                "_top"
            });

    _TipDialog(JDialog d) {
        super(d, "热点提示及超链接设置", true);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints2 gbc = new GridBagConstraints2(p);
        p.add(new JLabel("热点提示:"), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        p.add(labelField, gbc);
        gbc.weightx = 0;

        // p.add(new JButton("..."), gbc);
        gbc.gridwidth = 1;
        p.add(new JLabel("超链接:"), gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        p.add(urlField, gbc);

        // p.add(new JButton("..."), gbc);
        gbc.gridwidth = 1;
        p.add(new JLabel("目标:"), gbc);
    
        gbc.add(targetCombo,100 );
        this.getContentPane().add(p, BorderLayout.CENTER);

        ActionListener okListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done();
                }
            };

        ActionListener cancelListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancel();
                }
            };

        JPanel command = CommandPanel.createPanel(okListener, cancelListener);

        command.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        this.getContentPane().add(command, BorderLayout.SOUTH);
        
        SwingUtil.setBorder6( (JPanel) this.getContentPane());
        setSize(new Dimension(400, 180));

        this.setLocationRelativeTo(d);
    }

    protected void done() {
        done = true;
        hide();
    }

    protected void cancel() {
        hide();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
//    public Tip getTip() {
//        String tip = labelField.getText();
//
//        if ((tip != null) && (tip.trim().length() == 0)) {
//            tip = null;
//        }
//
//        String url = urlField.getText();
//
//        if ((url != null) && (url.trim().length() == 0)) {
//            url = null;
//        }
//
//        if ((tip == null) && (url == null)) {
//            return null;
//        }
//
//        String target = null;
//
//        if (url != null) {
//            target = (String) targetCombo.getSelectedItem();
//        }
//
//        return new Tip(tip, url, target);
//    }

    /**
     * DOCUMENT ME!
     *
     * @param tip DOCUMENT ME!
     */
//    public void setTip(Tip tip) {
//        if (tip == null) {
//            tip = new Tip();
//        }
//
//        labelField.setText(tip.getTip());
//        urlField.setText(tip.getUrl());
//        targetCombo.setSelectedItem(tip.getTarget());
//    }
}
