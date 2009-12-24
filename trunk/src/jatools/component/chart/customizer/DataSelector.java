package jatools.component.chart.customizer;

import jatools.component.chart.Chart;
import jatools.component.chart.PlotData;
import jatools.component.chart.Tip;
import jatools.data.reader.DatasetReader;
import jatools.dataset.Column;
import jatools.swingx.SimpleTreeNode;
import jatools.swingx.SwingUtil;
import jatools.util.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;

import jatools.designer.App;
//import jatools.designer.CustomTable;
import jatools.designer.DataTree;
import jatools.designer.DataTreeUtil;
import jatools.designer.data.ReaderChooser;
import jatools.swingx.MessageBox;
import jatools.swingx.CustomTable;

/**
 * @author   java9
 */
public class DataSelector extends JPanel implements ChangeListener, ListSelectionListener,
    TableModelListener {
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel labelPanel = new JPanel();
    private JPanel dataPanel = new JPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel labelCommandPanel = new JPanel(new GridBagLayout());
    private JPanel dataCommandPanel = new JPanel(new GridBagLayout());
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private GridLayout gridLayout1 = new GridLayout();
    JLabel jLabel3;
    JLabel jLabel2;
    JButton labelSelectCommand;
    JButton labelUnselectCommand;
    JButton dataSelectCommand;
    JButton dataUnselectCommand;
    JButton dataUnselectAllCommand;
    JButton downCommand;
    JButton upCommand;
    JPanel commandPanel;
    JPanel jPanel1;
    CustomTable labelTable;
    CustomTable dataTable;

    // ZCustomTable table;
    DataTree variableExplorer;
    Column hitField;
    DatasetReader reader;
    private Vector changeListeners;
    private JLabel promptLabel;
    private int type;

    /**
     * Creates a new ZDataSelector object.
     */
    public DataSelector() {
        this.setLayout(gridBagLayout1);

        labelSelectCommand = new JButton(Util.getIcon("/jatools/icons/select.gif")); //
        labelUnselectCommand = new JButton(Util.getIcon("/jatools/icons/unselect.gif")); //

        dataSelectCommand = new JButton(Util.getIcon("/jatools/icons/select.gif")); //
        dataUnselectCommand = new JButton(Util.getIcon("/jatools/icons/unselect.gif")); //
        dataUnselectAllCommand = new JButton(Util.getIcon("/jatools/icons/unselectall.gif")); //

        downCommand = new JButton(Util.getIcon("/jatools/icons/movedown.gif")); //
        upCommand = new JButton(Util.getIcon("/jatools/icons/moveup.gif")); //

        rightPanel.setLayout(new GridLayout(2, 1));
        dataPanel.setLayout(borderLayout1);
        labelPanel.setLayout(borderLayout2);

        dataPanel.add(dataCommandPanel, BorderLayout.WEST);
        rightPanel.add(labelPanel);
        rightPanel.add(dataPanel);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = gbc2.BOTH;
        gbc2.weighty = 1.0;
        gbc2.weightx = 1.0;
        this.add(leftPanel, gbc2);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(14, 0, 10, 0));
        gbc2.gridwidth = gbc2.REMAINDER;

        this.add(rightPanel, gbc2);
        gbc2.weighty = 0.0;
        this.promptLabel = new JLabel("提示:");
        //   this.promptLabel.setPreferredSize( new Dimension(20,100));
        this.promptLabel.setMinimumSize(new Dimension(20, 60));
        this.add(promptLabel, gbc2);
        SwingUtil.setBorder6(this);

        // 左面
        variableExplorer = new DataTree(null);
        variableExplorer.addChangeListener(this);

        leftPanel.setLayout(new BorderLayout());

        leftPanel.setPreferredSize(new java.awt.Dimension(132, 183));

        leftPanel.add(new JLabel("可用字段"), //
            BorderLayout.NORTH); //

        leftPanel.add(new JScrollPane(variableExplorer), BorderLayout.CENTER);

        JButton moreReaderCommand = new JButton("选择数据集..."); //

        moreReaderCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectReader();
                }
            });

        leftPanel.add(moreReaderCommand, BorderLayout.SOUTH);

        labelTable = new CustomTable(new String[] {
                    "名称" //
                }); //

        JPanel labelPanel1 = new JPanel(new BorderLayout());

        labelPanel1.add(new JLabel("X轴标签数据"), //
            BorderLayout.NORTH); //
        labelPanel1.add(new JScrollPane(labelTable), BorderLayout.CENTER);
        labelPanel1.setBorder(BorderFactory.createEmptyBorder(14, 0, 10, 0));
        labelSelectCommand = new JButton(Util.getIcon("/jatools/icons/select.gif")); //

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 0, 0, 0);

        labelCommandPanel.add(labelSelectCommand, gbc);
        labelUnselectCommand = new JButton(Util.getIcon("/jatools/icons/unselect.gif")); //
        labelCommandPanel.add(labelUnselectCommand, gbc);

        labelPanel.add(labelCommandPanel, BorderLayout.WEST);
        labelCommandPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

        labelPanel.add(labelPanel1, BorderLayout.CENTER);

        dataTable = new CustomTable(new String[] {
                    "名称", //
                "热点标签" //
                }); // //$NON-NLS-2$

        JPanel dataPanel1 = new JPanel(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0f;
        // dataPanel1.add(new JLabel(Z"显示数据(必须为数值型)"),gbc
        // );
        dataPanel1.add(new JLabel("显示数据"), gbc);

        gbc.weightx = 0.0f;
        dataPanel1.add(upCommand, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        dataPanel1.add(downCommand, gbc);
        gbc.weighty = 1.0f;

        gbc.fill = GridBagConstraints.BOTH;

        dataPanel1.add(new JScrollPane(dataTable), gbc);
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 0, 2, 0);
        dataCommandPanel.add(dataSelectCommand, gbc);
        dataCommandPanel.add(dataUnselectCommand, gbc);
        dataCommandPanel.add(dataUnselectAllCommand, gbc);
        dataCommandPanel.setBorder(labelCommandPanel.getBorder());

        dataPanel.add(dataCommandPanel, BorderLayout.WEST);
        dataPanel.add(dataPanel1, BorderLayout.CENTER);

        labelPanel.add(labelPanel1, BorderLayout.CENTER);
        dataPanel.add(dataPanel1, BorderLayout.CENTER);

        dataPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        labelSelectCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String data = null;

       

                    if (hitField != null) {
                        SelectCommand cmd = new SelectCommand(hitField, SelectCommand.LABEL);
                        fireChangeListener(cmd);

                        if (cmd.error != null) {
                            MessageBox.error(DataSelector.this, cmd.error); //

                            return;
                        }

                        data = hitField.getName();
             
                    } else {
                        data = "标签列";
                    }

        
                        labelTable.removeAllRows();
            

                    labelTable.addRow(new Object[] {
                            data
                        }, true);
                    treeNextRow();
                    fireChangeListener();
                }
            });

        dataSelectCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String data = null;

                    if (hitField != null) {
                        SelectCommand cmd = new SelectCommand(hitField, SelectCommand.PLOTDATA);
                        fireChangeListener(cmd);

                        if (cmd.error != null) {
                            MessageBox.error(DataSelector.this, cmd.error); //

                            return;
                        }

                        data = hitField.getName();
                    } else {
                        data = "显示列";
                    }

                    dataTable.addRow(new Object[] {
                            data,
                            null
                        }, true);
                    treeNextRow();
                    fireChangeListener();
                }
            });

        labelUnselectCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int row = labelTable.getSelectedRow();
                    labelTable.removeRow(row, true);
                    fireChangeListener();
                }
            });

        dataUnselectCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int row = dataTable.getSelectedRow();
                    dataTable.removeRow(row, true);
                    fireChangeListener();
                }
            });

        dataUnselectAllCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dataTable.removeAllRows();
                    fireChangeListener();
                }
            });
        upCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dataTable.upRow(); // listUp();
                    fireChangeListener();
                }
            });

        downCommand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dataTable.downRow(); // listUp();
                    fireChangeListener();
                }
            });

        labelTable.getSelectionModel().addListSelectionListener(this);

        labelTable.getModel().addTableModelListener(this); // {

        labelTable.setEditable(0, true);

        dataTable.getModel().addTableModelListener(this);
        dataTable.getSelectionModel().addListSelectionListener(this);
        dataTable.setEditable(0, true);

        TableColumn col = dataTable.getColumn("热点标签");
        ButtonCellEditor tooltip = new ButtonCellEditor("...");

        col.setCellEditor(new ButtonCellEditor("..."));
        col.setCellRenderer(tooltip);

        dataTable.setRowHeight(20);
        labelTable.setRowHeight(20);

        //		labelSelectCommand.setEnabled(false);
        labelUnselectCommand.setEnabled(false);
        //dataSelectCommand.setEnabled(false);
        dataUnselectCommand.setEnabled(false);
        dataUnselectAllCommand.setEnabled(false);

        Dimension size = new Dimension(50, 25);
        labelSelectCommand.setPreferredSize(size);
        labelUnselectCommand.setPreferredSize(size);
        dataSelectCommand.setPreferredSize(size);
        dataUnselectCommand.setPreferredSize(size);
        dataUnselectAllCommand.setPreferredSize(size);

        upCommand.setEnabled(false);
        downCommand.setEnabled(false);
        // upCommand.setPreferredSize( new Dimension(20,20));
        // downCommand.setPreferredSize( new Dimension(20,20));
        downCommand.setMargin(new Insets(2, 2, 2, 2));
        upCommand.setMargin(new Insets(2, 2, 2, 2));
        dataTable.setEditable(1, true);

        // setReader(null);

        // labelTable.setEditable(1, true);
    }

    protected void fireChangeListener() {
        this.fireChangeListener(this);
    }

    /**
    * DOCUMENT ME!
    *
    * @param prompt DOCUMENT ME!
    */
    public void setPrompt(String prompt) {
        promptLabel.setText("<html>提示:<br>" + prompt + "</html>");
    }

    /**
     *
     */
    protected void selectReader() {
        ReaderChooser chooser = new ReaderChooser(Util.getCDF(this), App.getConfiguration());

        chooser.show();

        DatasetReader r = chooser.getReader();

        if (r != null) {
            this.setReader(r);
        }

        fireChangeListener();
    }

    void treeNextRow() {
        if (variableExplorer.getSelectionRows() != null) {
            int index = variableExplorer.getSelectionRows()[0];

            if (index < (variableExplorer.getRowCount() - 1)) {
                variableExplorer.setSelectionRow(variableExplorer.getSelectionRows()[0] + 1);
            } else if (index == (variableExplorer.getRowCount() - 1)) {
                refreshSelectedNode();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ArrayList getSelectedRows() {
        return labelTable.cloneRows();
    }

    /**
     * DOCUMENT ME!
     *
     * @param e
     *            DOCUMENT ME!
     */
    public void stateChanged(ChangeEvent e) {
        hitField = null;

        if (e.getSource() == variableExplorer) {
            // 可用数据集列表，选取发生变化，应使选择按钮变灰
            TreePath path = variableExplorer.getSelectionPath();

            if (path != null) {
                SimpleTreeNode node = (SimpleTreeNode) path.getLastPathComponent();
                boolean hitReader = node.getUserObject() instanceof Column && !node.isDisabled();

                if (hitReader) {
                    hitField = (Column) ((SimpleTreeNode) variableExplorer.getSelectionPath()
                                                                          .getLastPathComponent()).getUserObject();
                }
            }
        }

        //
        //		labelSelectCommand.setEnabled(hitField != null);
        //		dataSelectCommand.setEnabled(hitField != null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param lst DOCUMENT ME!
     */
    public void addChangeListener(ChangeListener lst) {
        if (this.changeListeners == null) {
            changeListeners = new Vector();
        }

        changeListeners.add(lst);
    }

    void fireChangeListener(Object src) {
        if (changeListeners != null) {
            for (Iterator it = changeListeners.iterator(); it.hasNext();) {
                ChangeListener lst = (ChangeListener) it.next();
                lst.stateChanged(new ChangeEvent(src));
            }
        }
    }

    /**
         * DOCUMENT ME!
         * @param reader   DOCUMENT ME!
         * @uml.property   name="reader"
         */
    public void setReader(DatasetReader reader) {
        variableExplorer.setRoot(DataTreeUtil.asTree(reader));
        labelTable.removeAllRows();
        dataTable.removeAllRows();

        if (variableExplorer.getRowCount() > 0) {
            variableExplorer.expandRow(0);
        }

        this.reader = reader;
    }

    /**
     * Auto-generated code - any changes you make will disappear!!! This static
     * method creates a new instance of this class and shows it inside a new
     * JFrame, (unless it is already a JFrame).
     */
    public static void showGUI() {
//        try {
//            JFrame frame = new JFrame();
//            DataSelector inst = new DataSelector();
//            frame.setContentPane(inst);
//            frame.pack();
//            frame.setVisible(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
     */

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void valueChanged(ListSelectionEvent e) {
        upCommand.setEnabled(dataTable.canUp());
        downCommand.setEnabled(dataTable.canDown());
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
     */

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void tableChanged(TableModelEvent e) {
        labelUnselectCommand.setEnabled(labelTable.getRowCount() > 0);
        //		labelSelectCommand.setEnabled(hitField != null);
        dataUnselectCommand.setEnabled(dataTable.getRowCount() > 0);
        dataUnselectAllCommand.setEnabled(dataUnselectCommand.isEnabled());
        //	dataSelectCommand.setEnabled(hitField != null);
        refreshSelectedNode();
        variableExplorer.repaint();
        this.fireChangeListener();
    }

    void refreshSelectedNode() {
        int[] selectedRows = variableExplorer.getSelectionRows();

        variableExplorer.setSelectionRow(0);
        variableExplorer.setSelectionRows(selectedRows);
    }

    /**
     * @param chart
     */
    public void init(Chart chart) {
        //        setReader((DatasetReader) App.getActiveDocument().getVariable(chart.getReaderVariable()));
        //
        //        //if (reader != null) {
        //        String xlabel = chart.getLabelData();
        //
        //        if (xlabel != null) {
        //            labelTable.addRow(new Object[] {
        //                    xlabel
        //                }, true);
        //        }
        //
        //        Vector showData = chart.getShowData();
        //
        //        if ((showData != null) && !showData.isEmpty()) {
        //            for (Iterator iter = showData.iterator(); iter.hasNext();) {
        //                ZShowData e = (ZShowData) iter.next();
        //
        //                dataTable.addRow(new Object[] {
        //                        e.getField(),
        //                        e.getZLabel()
        //                    }, false);
        //            }
        //
        //            dataTable.setSelectedRow(0);
        //        }
        //
        //        //}
    }

    /**
     * DOCUMENT ME!
     *
     * @param reader DOCUMENT ME!
     * @param xlabel DOCUMENT ME!
     * @param showData DOCUMENT ME!
     */
    public void init2(DatasetReader reader, String xlabel, PlotData[] showData) {
        setReader(reader);

        //if (reader != null) {
        if (xlabel != null) {
            labelTable.addRow(new Object[] {
                    xlabel
                }, true);
        }

        if ((showData != null) && (showData.length > 0)) {
            //            	
            for (int i = 0; i < showData.length; i++) {
                dataTable.addRow(new Object[] {
                        showData[i].getField(),
                        showData[i].getTooltip()
                    }, false);
            }

            dataTable.setSelectedRow(0);
        }

        //}
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public PlotData[] getShowData() {
        PlotData[] data = new PlotData[dataTable.getRowCount()];

        for (int i = 0; i < dataTable.getRowCount(); i++) {
            data[i] = new PlotData((String) dataTable.getValueAt(i, 0),
                    (Tip) dataTable.getValueAt(i, 1));
        }

        return data;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getLabelField() {
        return (labelTable.getRowCount() > 0) ? (String) labelTable.getValueAt(0, 0) : null;
    }
    


    /**
         * @return   Returns the reader.
         * @uml.property   name="reader"
         */
    public DatasetReader getReader() {
        return reader;
    }

    /**
     * DOCUMENT ME!
     *
     * @param type DOCUMENT ME!
     */
    public void setType(int type) {
        this.type = type;
    }
}
