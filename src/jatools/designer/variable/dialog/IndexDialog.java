package jatools.designer.variable.dialog;

import jatools.data.reader.DatasetReader;
import jatools.dataset.Dataset;
import jatools.dataset.DatasetException;
import jatools.designer.App;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.IndexNodeSource;
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

public class IndexDialog
    extends JDialog implements ActionListener {
  private static IndexNodeSource indexSource;
  private JList leftList;
  private JList rightList;
  private DefaultListModel leftModel, rightModel;
  private Component c;
  private static boolean exitedOK;
  private JLabel infoLabel;
  static final String info = App.messages.getString("res.267");

  private JTextField nameField;
  private IndexDialog(DatasetNodeSource parentSrc, IndexNodeSource indexSource,
                      Component parent) {

    super( (Frame) javax.swing.SwingUtilities.getWindowAncestor(parent));
    exitedOK = false;
    this.setModal(true);
    this.setTitle(App.messages.getString("res.278"));
    this.indexSource = indexSource;
    this.c = parent;
    initUI();
    setSource(parentSrc, indexSource);
  }

  private void initUI() {

    int w = Toolkit.getDefaultToolkit().getScreenSize().width;
    int h = Toolkit.getDefaultToolkit().getScreenSize().height;
    int x = (w - 480) / 2;
    int y = (h - 180) / 3;

    this.setLocation(x, y);
    this.setSize(new Dimension(480, 360));

    this.getContentPane().setLayout(new BorderLayout());

    JPanel centerPanel = new JPanel(new BorderLayout());

    Box center = Box.createHorizontalBox();
    centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    centerPanel.add(center, BorderLayout.CENTER);

    JLabel nameLabel = new JLabel(App.messages.getString("res.269"));
    nameField = new JTextField();
    Box nameBox = Box.createHorizontalBox();
    nameBox.add(nameLabel);
    nameBox.add(Box.createHorizontalStrut(5));
    nameBox.add(nameField);
    nameBox.add(Box.createHorizontalGlue());
    nameBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
    center.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

    centerPanel.add(nameBox, BorderLayout.NORTH);

    infoLabel = new JLabel(info);
    infoLabel.setBorder(BorderFactory.createEmptyBorder(8, 5, 0, 5));
    centerPanel.add(infoLabel, BorderLayout.SOUTH);

    leftList = new JList();
    rightList = new JList();

    JTabbedPane leftTab = new JTabbedPane();
    leftTab.addTab(App.messages.getString("res.270"), new JScrollPane(leftList));
    JTabbedPane rightTab = new JTabbedPane();
    rightTab.addTab(App.messages.getString("res.279"), new JScrollPane(rightList));
    leftTab.setPreferredSize(new Dimension(200, 350));
    rightTab.setPreferredSize(new Dimension(200, 350));

    center.add(leftTab);
    center.add(controlPanel());
    center.add(rightTab);

    JButton ok = new JButton(App.messages.getString("res.3"));
    JButton cancel = new JButton(App.messages.getString("res.4"));
    ok.setActionCommand("ok");
    ok.setPreferredSize(new Dimension(78, 23));
    cancel.setActionCommand("cancel");
    cancel.setPreferredSize(new Dimension(78, 23));
    ok.addActionListener(this);
    cancel.addActionListener(this);

    JPanel southPanel = new JPanel(new BorderLayout());
    JSeparator seprator = new JSeparator(JSeparator.HORIZONTAL);
    southPanel.add(seprator, BorderLayout.NORTH);
    Box south = Box.createHorizontalBox();
    south.add(Box.createHorizontalGlue());
    south.add(ok);
    south.add(Box.createHorizontalStrut(10));
    south.add(cancel);
    south.add(Box.createHorizontalStrut(10));

    south.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
    southPanel.add(south, BorderLayout.CENTER);

    this.getContentPane().add(centerPanel, BorderLayout.CENTER);
    this.getContentPane().add(southPanel, BorderLayout.SOUTH);

  }

  private void setSource(DatasetNodeSource parentSrc,
                         IndexNodeSource indexSource) {

    nameField.setText(indexSource.getTagName());

    String[] fields = getFields(parentSrc.getReader());
    leftModel = new DefaultListModel();
    for (int i = 0; i < fields.length; i++) {
      leftModel.addElement(fields[i]);
    }
    leftList.setModel(leftModel);

    String[] selectedFields = indexSource.getIndexFields();
    rightModel = new DefaultListModel();
    if (selectedFields != null)
      for (int i = 0; i < selectedFields.length; i++) {
        rightModel.addElement(selectedFields[i]);
      }
    rightList.setModel(rightModel);

  }

  /**
   * ȡ�������ֶ�
   * @param reader DatasetReader
   * @return String[]
   */
  private String[] getFields(DatasetReader reader) {
    if (reader != null) {
      Dataset dataSet = null;
      try {
        dataSet = reader.read(ReportContext.getDefaultContext(), 0);
      }
      catch (DatasetException ex) {
        ex.printStackTrace();
        return null;
      }
      String[] fieldNames = dataSet.getFieldNames(reader);
      return fieldNames;
    }
    return null;
  }

  public static IndexNodeSource getNodeSource(DatasetNodeSource parentSrc,
                                              IndexNodeSource _indexSource,
                                              Component c) {
    IndexDialog dialog = new IndexDialog(parentSrc, _indexSource, c);
    dialog.setVisible(true);
    if (exitedOK)
      return indexSource;
    else {
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
    JButton addAll = createButton(">>", "addall");
    JButton del = createButton("<", "del");
    JButton delAll = createButton("<<", "delall");

    add.addActionListener(this);
    addAll.addActionListener(this);
    del.addActionListener(this);
    delAll.addActionListener(this);
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
    addallBox.add(addAll);
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
    delAllBox.add(delAll);
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

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("ok")) {
      String indexs[] = new String[rightModel.getSize()];

      if (nameField.getText() == null || nameField.getText().trim().equals("")) {
        JOptionPane.showConfirmDialog(nameField, App.messages.getString("res.273"), App.messages.getString("res.82"),
                                      JOptionPane.CLOSED_OPTION,
                                      JOptionPane.ERROR_MESSAGE);
        return;

      }
      if (indexs.length == 0) {
        JOptionPane.showConfirmDialog(nameField, App.messages.getString("res.280"), App.messages.getString("res.82"),
                                      JOptionPane.CLOSED_OPTION,
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }
      for (int i = 0; i < indexs.length; i++) {
        indexs[i] = rightModel.get(i).toString();
      }
      indexSource.setIndexFields(indexs);
      indexSource.setTagName(nameField.getText());
      exitedOK = true;
      this.dispose();
    }
    else if (e.getActionCommand().equals("cancel")) {
      this.dispose();
    }
    else if (e.getActionCommand().equals("add")) {
      //((DefaultListModel)leftList.getModel()).addElement();
      Object l = leftList.getSelectedValue();
      if (l != null) {
        if (!rightModel.contains(l)) {
          rightModel.addElement(l);
        }
      }
    }
    else if (e.getActionCommand().equals("addall")) {
      rightModel.removeAllElements();
      for (int i = 0; i < leftModel.getSize(); i++) {
        rightModel.addElement(leftModel.get(i));
      }
    }
    else if (e.getActionCommand().equals("del")) {
      Object l = rightList.getSelectedValue();
      if (l != null) {
        rightModel.removeElement(l);
      }
    }
    else if (e.getActionCommand().equals("delall")) {
      rightModel.removeAllElements();
    }

  }

}
