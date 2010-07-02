package jatools.designer.variable;

import jatools.data.reader.DatasetReader;
import jatools.dataset.Dataset;
import jatools.dataset.DatasetException;
import jatools.dom.src.CrossIndexNodeSource;
import jatools.dom.src.DatasetNodeSource;
import jatools.engine.script.ReportContext;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.6 $
  */
public class CrossIndexDialog extends JDialog implements ActionListener {
    private static CrossIndexNodeSource crossSource;
    private static boolean exitedOK;
    static final String info = "选择索引字段，不能重复，不能为空";

    private JTextField nameField;
    private JList leftList;
    private JList rowList;
    private JList columnList;
    private DefaultListModel leftModel;
    private DefaultListModel rowModel;
    private DefaultListModel columnModel;
    private Component c;
    private JLabel infoLabel;
    private JTabbedPane leftTab;
    private JTabbedPane rightTab;

    private CrossIndexDialog(DatasetNodeSource parentSource,CrossIndexNodeSource crossSource, Component parent) {
        super((Frame) javax.swing.SwingUtilities.getWindowAncestor(parent));
        exitedOK = false;
        this.setModal(true);
        this.setTitle("交叉索引定义");
        this.crossSource = crossSource;
        this.c = parent;
        initUI();
        setSource(parentSource,crossSource);
    }

    private void initUI() {
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        int x = (w - 480) / 2;
        int y = (h - 180) / 3;

        this.setLocation(x, y);
        this.setSize(new Dimension(480, 360));

        this.getContentPane().setLayout(new BorderLayout());

        JPanel centerPanel=new JPanel(new BorderLayout());

        Box center = Box.createHorizontalBox();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        centerPanel.add(center,BorderLayout.CENTER);

        JLabel nameLabel=new JLabel("名称：");
        nameField=new JTextField();
        Box nameBox=Box.createHorizontalBox();
        nameBox.add(nameLabel);
        nameBox.add(Box.createHorizontalStrut(5));
        nameBox.add(nameField);
        nameBox.add(Box.createHorizontalGlue());
        nameBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        center.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        centerPanel.add(nameBox,BorderLayout.NORTH);

        infoLabel = new JLabel(info);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(8, 5, 0, 5));
        centerPanel.add(infoLabel, BorderLayout.SOUTH);

        leftList = new JList();
        rowList = new JList();
        columnList = new JList();

        leftTab = new JTabbedPane();
        leftTab.addTab("可选字段", new JScrollPane(leftList));
        rightTab = new JTabbedPane();
        rightTab.addTab("行索引", new JScrollPane(rowList));
        rightTab.addTab("列索引", new JScrollPane(columnList));

        leftTab.setPreferredSize(new Dimension(200, 350));
        rightTab.setPreferredSize(new Dimension(200, 350));

        center.add(leftTab);
        center.add(controlPanel());
        center.add(rightTab);

        JButton ok = new JButton("确定");
        JButton cancel = new JButton("取消");
        ok.setActionCommand("ok");
        ok.setPreferredSize(new Dimension(78, 23));
        cancel.setActionCommand("cancel");
        cancel.setPreferredSize(new Dimension(78, 23));
        ok.addActionListener(this);
        cancel.addActionListener(this);

        JPanel southPanel=new JPanel(new BorderLayout());
        JSeparator seprator=new JSeparator(JSeparator.HORIZONTAL);
        southPanel.add(seprator,BorderLayout.NORTH);
        Box south = Box.createHorizontalBox();
        south.add(Box.createHorizontalGlue());
        south.add(ok);
        south.add(Box.createHorizontalStrut(10));
        south.add(cancel);
        south.add(Box.createHorizontalStrut(10));

        south.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        southPanel.add(south,BorderLayout.CENTER);

        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    private void setSource(DatasetNodeSource parentSource,CrossIndexNodeSource crossSource) {
        nameField.setText(crossSource.getTagName());

        String[] fields = getFields(parentSource.getReader());
        leftModel = new DefaultListModel();

        for (int i = 0; i < fields.length; i++) {
            leftModel.addElement(fields[i]);
        }

        leftList.setModel(leftModel);

        String[] selectedFields = crossSource.getIndexFields();
        rowModel = new DefaultListModel();

        if (selectedFields != null) {
            for (int i = 0; i < selectedFields.length; i++) {
                rowModel.addElement(selectedFields[i]);
            }
        }

        rowList.setModel(rowModel);

        selectedFields = crossSource.getIndexFields2();
        columnModel = new DefaultListModel();

        if (selectedFields != null) {
            for (int i = 0; i < selectedFields.length; i++) {
                columnModel.addElement(selectedFields[i]);
            }
        }

        columnList.setModel(columnModel);
    }

    /**
     * 取得所有字段
     * @param reader DatasetReader
     * @return String[]
     */
    private String[] getFields(DatasetReader reader) {
        if (reader != null) {
            Dataset dataSet = null;

            try {
                dataSet = reader.read(ReportContext.getDefaultContext(), 0);
            } catch (DatasetException ex) {
                ex.printStackTrace();

                return null;
            }

            String[] fieldNames = dataSet.getFieldNames(reader);

            return fieldNames;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param _crossSource DOCUMENT ME!
     * @param c DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static CrossIndexNodeSource getNodeSource(DatasetNodeSource parentSource,CrossIndexNodeSource _crossSource, Component c) {
        CrossIndexDialog dialog = new CrossIndexDialog(parentSource,_crossSource, c);
        dialog.setVisible(true);

        if (exitedOK) {
            return crossSource;
        } else {
            return null;
        }
    }

    private JPanel createListPanel(JList list, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, JLabel.LEFT);
        JScrollPane listPane = new JScrollPane(list);
        panel.add(label, BorderLayout.NORTH);
        panel.add(listPane, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //        panel.setMinimumSize(new Dimension(160,100));
        panel.setPreferredSize(new Dimension(200, 350));

        return panel;
    }

    private Box controlPanel() {
        JButton add = createButton(">", "add");
      //  JButton addAll = createButton(">>", "addall");
        JButton del = createButton("<", "del");
    //    JButton delAll = createButton("<<", "delall");

        add.addActionListener(this);
     //   addAll.addActionListener(this);
        del.addActionListener(this);
    //    delAll.addActionListener(this);

        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalGlue());

        Box addBox = Box.createHorizontalBox();
        addBox.add(Box.createHorizontalStrut(5));
        addBox.add(add);
        addBox.add(Box.createHorizontalStrut(5));
        box.add(addBox);
        addBox.setMaximumSize(new Dimension(60, 25));

        box.add(Box.createVerticalStrut(5));

        Box addallBox = Box.createHorizontalBox();
        addallBox.add(Box.createHorizontalStrut(5));
    //    addallBox.add(addAll);
        addallBox.add(Box.createHorizontalStrut(5));
        box.add(addallBox);
        addallBox.setMaximumSize(new Dimension(60, 25));

        box.add(Box.createVerticalStrut(5));

        Box delBox = Box.createHorizontalBox();
        delBox.add(Box.createHorizontalStrut(5));
        delBox.add(del);
        delBox.add(Box.createHorizontalStrut(5));
        box.add(delBox);
        delBox.setMaximumSize(new Dimension(60, 25));

        box.add(Box.createVerticalStrut(5));

        Box delAllBox = Box.createHorizontalBox();
        delAllBox.add(Box.createHorizontalStrut(5));
 //       delAllBox.add(delAll);
        delAllBox.add(Box.createHorizontalStrut(5));
        box.add(delAllBox);
        delAllBox.setMaximumSize(new Dimension(60, 25));

        box.add(Box.createVerticalGlue());

        return box;
    }

    private JButton createButton(String name, String command) {
        JButton b = new JButton(name);
        b.setActionCommand(command);
        b.setPreferredSize(new Dimension(50, 25));
        b.setMinimumSize(new Dimension(50, 25));
        b.setMaximumSize(new Dimension(50, 25));
        b.addActionListener(this);

        return b;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            String[] rowIndexs = new String[rowModel.getSize()];
            String[] columnIndexs = new String[columnModel.getSize()];

            if(nameField.getText()==null||nameField.getText().trim().equals("")){
              JOptionPane.showConfirmDialog(nameField, "名称不能为空！", "提示", JOptionPane.CLOSED_OPTION,
                   JOptionPane.ERROR_MESSAGE);
               return;

            }


            if ((rowIndexs.length == 0) || (columnIndexs.length == 0)) {
                JOptionPane.showConfirmDialog(nameField, "行索引和列索引字段不能为空！", "提示", JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE);

                return;
            }

            for (int i = 0; i < rowIndexs.length; i++) {
                rowIndexs[i] = rowModel.get(i).toString();
            }

            crossSource.setIndexFields(rowIndexs);

            for (int i = 0; i < columnIndexs.length; i++) {
                columnIndexs[i] = columnModel.get(i).toString();
            }

            crossSource.setIndexFields2(columnIndexs);
            crossSource.setTagName(nameField.getText());
            exitedOK = true;
            this.dispose();
        } else if (e.getActionCommand().equals("cancel")) {
            this.dispose();
        } else if (e.getActionCommand().equals("add")) {
            //((DefaultListModel)leftList.getModel()).addElement();
            Object l = leftList.getSelectedValue();

            if (l != null) {
                if (rightTab.getSelectedIndex() == 0) {
                    if (!rowModel.contains(l)) {
                        rowModel.addElement(l);
                    }
                } else if (rightTab.getSelectedIndex() == 1) {
                    if (!columnModel.contains(l)) {
                        columnModel.addElement(l);
                    }
                }
            }
        } else if (e.getActionCommand().equals("addall")) {
            if (rightTab.getSelectedIndex() == 0) {
                rowModel.removeAllElements();

                for (int i = 0; i < leftModel.getSize(); i++) {
                    rowModel.addElement(leftModel.get(i));
                }
            } else if (rightTab.getSelectedIndex() == 1) {
                columnModel.removeAllElements();

                for (int i = 0; i < leftModel.getSize(); i++) {
                    columnModel.addElement(leftModel.get(i));
                }
            }
        } else if (e.getActionCommand().equals("del")) {
            if (rightTab.getSelectedIndex() == 0) {
                Object l = rowList.getSelectedValue();

                if (l != null) {
                    rowModel.removeElement(l);
                }
            } else if (rightTab.getSelectedIndex() == 1) {
                Object l = columnList.getSelectedValue();

                if (l != null) {
                    columnModel.removeElement(l);
                }
            }
        } else if (e.getActionCommand().equals("delall")) {
            if (rightTab.getSelectedIndex() == 0) {
                rowModel.removeAllElements();
            } else if (rightTab.getSelectedIndex() == 1) {
                columnModel.removeAllElements();
            }
        }
    }


}
