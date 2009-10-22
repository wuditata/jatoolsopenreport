package jatools.designer.data;

import jatools.data.Formula;
import jatools.designer.Main;
import jatools.designer.VariableTreeModel;
import jatools.engine.AmbiguousNameNodePattern;
import jatools.engine.PrintConstants;
import jatools.swingx.CommandPanel;
import jatools.swingx.MessageBox;
import jatools.swingx.SimpleTreeNode;
import jatools.swingx.scripteditor.ScriptEditor;
import jatools.util.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;




/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class CustomFormulaDialog extends JDialog implements ActionListener {
    static final String[] FUNCTIONS = new String[] {
            "数据值型函数", "double abs(double)", "double asin(double)", "double acos(double)",
            "double atan(double)", "double atan2(double,double)", "double ceil(double)",
            "double cos(double)", "double exp(double)", "double floor(double)",
            
            "double log(double)", "double max(double,double)", "double min(double,double)",
            "double pow(double,double)", "double random()", "double rint(double)",
            "double sin(double)", "double sqrt(double)", "double tan(double)",
            "double toDegrees(double)", "double toRadians(double)", "float abs(float)",
            "float max(float,float)", "float min(float,float)", "int abs(int)", "int max(int,int)",
            "int min(int,int)", "int round(float)", "long abs(long)", "long max(long,long)",
            "long min(long,long)", "long round(double)",
            
            "字符串函数", "int length(String)", "char charAt(String, int)",
            
            "boolean startsWith(String, String)", "boolean endsWith(String, String)",
            
            "int indexOf(String, String)",
            
            "int lastIndexOf(String, String)",
            
            "String substring(String, int)", "String substring(String, int,int)",
            
            "String replaceAll(String, String , String )",
            
            "String[] split(String, String)",
            
            "String toLowerCase(String)",
            
            "String toUpperCase(String )",
            
            "日期函数",
            
            "int getYear(Date )", "int getMonth(Date )", "int getDate(Date )", "int getDay(Date )",
            "int getHours(Date )", "int getMinutes(Date )", "int getSeconds(Date )",
            "boolean before(Date,Date )", "boolean after(Date,Date )",
            
            "类型转换函数",
            
            "String toRmbString(double)", "String toHZYear(int)", "String toHZMonth(int)",
            "String toHZDay(int)",
            
            "String format(float,String)", "String format(double,String)",
            "String format(Date,String)", "其它类型函数", "String p(String )",
            "String clobString(String )"
        };
    static final HashMap tips = new HashMap(FUNCTIONS.length);
    IconTree functionTree;
    VariableTree varTree;
    JButton check;
    JButton ok;
    JButton cancel;
    ScriptEditor textArea;
    private Formula formula;
    protected boolean nullPermitted;
    JLabel tipLabel;
   

    /**
     * Creates a new CustomFormulaDialog object.
     *
     * @param owner DOCUMENT ME!
     * @param astemp DOCUMENT ME!
     */
    public CustomFormulaDialog(Frame owner, boolean astemp) {
        this(owner, true, false, astemp);
    }

    /**
     * Creates a new CustomFormulaDialog object.
     *
     * @param owner DOCUMENT ME!
     * @param showVariable DOCUMENT ME!
     * @param nullPermitted DOCUMENT ME!
     * @param astemp DOCUMENT ME!
     */
    public CustomFormulaDialog(Frame owner, boolean showVariable, boolean nullPermitted,
        boolean astemp) {
        super(owner, "公式定义", true);

        varTree = new VariableTree();

        TreeModel variableModel = null;

        if ((Main.getInstance() != null) && (Main.getInstance().getActiveEditor() != null)) {
            variableModel = Main.getInstance().getActiveEditor().getVariableModel();
        } else {
            variableModel = VariableTreeModel.getDefaults();
        }

        varTree.setModel(variableModel);

        varTree.setShowPopupMenu(false);

        varTree.setDoubleClickAction(this);

        functionTree = createFunctionTree();

        JComponent first = null;

        tips.put("double abs(double)", "获取参数变量的绝对值,abs(-12.345)的值为12.345");
        tips.put("double asin(double)", "获取参数变量的反正弦值 ,asin(1)的值是1.57");
        tips.put("double acos(double)", "获取参数变量的反余弦值,acos(0)的值是1.57");
        tips.put("double atan(double)", "获取参数变量的反正切值,atan(1)的值是0.78");
        tips.put("double atan2(double,double)", "获取参数变量的反余弦值,acos(0)的值是1.57");
        tips.put("double ceil(double)", "获取不小于参数变量的最大整数,ceil(-12.3)的值是-12.0.");
        tips.put("double cos(double)", "获取参数变量的余弦值,cos(0)的值是1.0");
        tips.put("double exp(double)", "获取自然底数e的d次幂方,exp(1)的值是2.71");
        tips.put("double floor(double)", "获取不大于参数变量的最大整数,floor(12.34)的值是12.0");
        tips.put("double log(double)", "获取底数为e对数为该参数变量的所得值,log(1.23)的值是0.207");
        tips.put("double max(double,double)", "获取两个参数变量中较大的一个,max(1.23,1.24)的值是1.24");
        tips.put("double min(double,double)", "获取两个参数变量中较小的一个,min(1.23,1.24)的值是1.23");
        tips.put("double pow(double,double)", "获取参数变量1的参数变量2次方之后的值 ,pow(2.5,3)的值是15.625");
        tips.put("double random()", "获取0.0到1.0之间一个随机数,random()的值是0.12");
        tips.put("double rint(double)", "获取参数变量四舍五入之后的值,rint(12.34)的值是12.0");
        tips.put("double sin(double)", "获取参数变量的正弦值,sin(0)的值是0.0");
        tips.put("double sqrt(double)", "获取参数变量开平方之后的值,sqrt(6.25)的值是2.");
        tips.put("double tan(double)", "获取参数变量的正切值,tan(1.5)的值是14.10");
        tips.put("double toDegrees(double)", "获取底数为e对数为该参数变量的所得值,log(1.23)的值是0.207");
        tips.put("double toRadians(double)", "获取与该参数变量弧度相对应的角度,toDegrees(1.57)的值是89.95");
        tips.put("float abs(float)", "获取浮点型参数变量的绝对值,abs(-12.345f)的值是12.345");
        tips.put("float max(float,float)", "获取两个浮点型参数变量中比较大的一个,max(1.23f,1.24f)的值是1 ");
        tips.put("float min(float,float)", "获取两个浮点型参数变量中比较小的一个, min(1.23f,1.24f)的值是1.23.");
        tips.put("int abs(int)", "获取两个浮点型参数变量中比较小的一个,min(1.23f,1.24f)的值是1.23.");
        tips.put("int max(int,int)", "获取两个整型参数变量中比较大的一个,max(23,24)的值是24  .");
        tips.put("int min(int,int)", "获取两个整型参数变量中比较小的一个,min(23,24).");
        tips.put("int round(float)", "获取返回大于给定的浮点型参数变量的最小整型变量,round(-12.34f)的值是.");
        tips.put("long abs(long)", "获取长整型参数变量的绝对值,abs(-12345678.9)的值是1.23456789E7");
        tips.put("long max(long,long)", "获取两个长整型参数变量中比较大的一个,max(1234,1235)的值是1235");
        tips.put("long min(long,long)", "获取两个参数变量中比较小的一个,min(1.23f,1.24f)的值是1.23");
        tips.put("long round(double)", "获取返回大于双精度参数变量的最小整型变量,round(-12.34)的值是-12.");

        tips.put("int length(String)", "获取字符串参数变量的长度,length(\"abc\")的值为3,length(\"大家好\")的值为6");
        tips.put("char charAt(String, int)",
            "获取字符串参数变量中位于i+1位置的字符,charAt(\"abcdefg\",3)的值为d,charAt(\"abcdefg\",0)的值为a.");
        tips.put("boolean startsWith(String, String)",
            "获取参数变量1是否以参数变量2为开头内容的布尔值,startsWith(\"afc\",\"b\")的值为false,startsWith(\"afc\",\"a\")的值为true");
        tips.put("boolean endsWith(String, String)",
            "获取参数变量1是否以参数变量2为结尾内容的布尔值,endsWith(\"afcccb\",\"asb\")的值为false,endsWith(\"acccb\",\"ccb\")的值为true");
        tips.put("int indexOf(String, String)",
            "获取字符串2在字符串1中出现的第一个位置,indexOf(\"abcdefg\",\"de\")的值为3.");
        tips.put("int lastIndexOf(String, String)",
            "获取字符串2在字符串1中出现的最后一个位置,lastIndexOf(\"abcdabcdefg\",\"ab\")的值为4");
        tips.put("String substring(String, int)",
            "获取字符串中从第i+1位开始的字符串,substring(\"abcdefg\",2)的值为\"cdefg\".");
        tips.put("String substring(String, int,int)",
            "获取字符串参数变量的长度,length(\"abc\")的值为3,length(\"大家好\")的值为6");
        tips.put("String replaceAll(String, String , String )",
            "获取用字符串3代替字符串1之中的所有字符串2所得到的新字符串,\nreplaceAll(\"abcdeabcdeabcde\",\"bc\",\"xy\")的值为\"axydeaxydeaxyde\".");
        tips.put("String[] split(String, String)",
            "把字符串1从有字符串2的地方开始进行分割，得到一个字符串数组,String str =\"xd::abc::cde\",String [] r= str.split(\"::\"),r就是{\"xd\",\"abc\",\"cde\"}");
        tips.put("String toLowerCase(String)",
            "用给定的位置规则,把该字符串中的所有字符转换为小写,toLowerCase(\"aBcDeFg\",Locale.PRC);的值为\"abcdefg\".");
        tips.put("String toUpperCase(String )", "获取把字符串转为大写后的字符串,toUpperCase(\"acdEfg\")的值为ACDEFG.");

        tips.put("int getYear(Date )", "获取日期d中的年,若日期d值为1970-12-31 00:00:00.0，getYear(d)的值为1970.");
        tips.put("int getMonth(Date )", "获取日期d中的月,d的值为1970-12-31 00:00:00.0,getMonth(d)的值为12 .");
        tips.put("int getDate(Date )", "获取日期d中的日,d的值为1970-12-31 00:00:00.0，getDate(d)的值为31.");
        tips.put("int getDay(Date )", "获取日期d为一个星期的第几天,d的值为1970-12-31 00:00:00.0，getDay(d)的值为4 .");
        tips.put("int getHours(Date )", "获取日期d中的小时,d的值为1970-12-31 00:00:00.0，getHours(d)的值为0.");
        tips.put("int getMinutes(Date )", "获取日期d中的分钟,d的值为1970-12-31 00:00:00.0，getMinutes(d)的值为0 .");
        tips.put("int getSeconds(Date )", "获取日期d中的秒,d的值为1970-12-31 00:00:00.0，getSeconds(d)的值为0.");
        tips.put("boolean before(Date,Date )",
            "获取日期d是否比日期p早的布尔值,d的值为1970-12-31 00:00:00.0，p的值为1981-10-10 00:00:00.0 before(d,p)的值为true.");
        tips.put("int getYear(Date )", "获取日期d中的年,若日期d值为1970-12-31 00:00:00.0，getYear(d)的值为1970.");
        tips.put("boolean after(Date,Date )",
            "获取日期d是否比日期p晚的布尔值,d的值为1970-12-31 00:00:00.0，p的值为1981-10-10 00:00:00.0after(d,p)的值为false .");

        tips.put("String toRmbString(double)", "获取双精度参数变量d的人民币大写,toRmbString(12.34)的值为￥ 壹拾贰元叁角肆分 .");
        tips.put("String toHZYear(int)", "获取年份的汉字大写,toHZYear(2005)的值为贰零零伍 .");
        tips.put("String toHZMonth(int)", "获取汉字大写的月份,toHZMonth(12)的值为壹拾贰 .");
        tips.put("String toHZDay(int)", "获取汉字大写的日期,toHZDay(12)的值为壹拾贰.");
        tips.put("String format(float,String)",
            "获取以字符串参数变量s形式表示的单精度参数变量f的值,format(123456.789f,\"###,##0.000\")的值为123,456.789 .");
        tips.put("String format(double,String)",
            "获取以字符串参数变量s形式表示的双精度变量d的值,format(123.456,\"###,##0.00\")的值为123.46.");
        tips.put("String format(Date,String)",
            "获取以字符串参数变量s形式表示的日期变量d的值,d的值为1970-12-31 00:00:00.0，format(d,\"yyyy-MM-dd\")的值为1970-12-31.");

        tips.put("String p(String )", "获取简表属性文件jatools.properties中的属性值,p(\"userid\")的值为875704628");
        tips.put("String clobString(String )",
            "取得数据集中的某一类型为该参数变量的字段的值,String memo = clobString(\"mydata.my_memo\"),表示将从数据集中的my_memo字段中，取得一个字符串值，$my_memo字段类型为 BLOB");

        JPanel scrollPanel = new JPanel(new BorderLayout());
        tipLabel = new JLabel("单击相应函数，显示该函数的用法及有关示例", JLabel.LEFT);
        tipLabel.setHorizontalAlignment(JLabel.LEFT);
        tipLabel.setVerticalAlignment(JLabel.TOP);
        tipLabel.setPreferredSize(new Dimension(20, 50));

        if (varTree != null) {
            JTabbedPane tab = new JTabbedPane();
            tab.addTab("变量", new JScrollPane(varTree));

            jatools.designer.variable.XmlSourceTree tree = new jatools.designer.variable.XmlSourceTree();
            tree.setToggleClickCount(10000);
            tree.setEnablePopup(false);

            if (((Main.getInstance() != null) && (Main.getInstance().getActiveEditor() != null)) &&
                    (Main.getInstance().getActiveEditor().getReport() != null)) {
                tree.initTreeData(Main.getInstance().getActiveEditor().getReport().getNodeSource());
            }

            tree.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals("select.value")) {
                            String selectedText = evt.getNewValue().toString();
                            textArea.replaceSelection(selectedText);
                            textArea.requestFocus();
                        }
                    }
                });
            tab.addTab("数据集变量", new JScrollPane(tree));

            scrollPanel.add(new JScrollPane(functionTree), "Center");
            scrollPanel.add(tipLabel, "South");
            tab.add("函数", scrollPanel);

            first = tab;
        } else {
            first = new JScrollPane(functionTree);
        }

        JPanel formulaTextPanel = new JPanel(new BorderLayout());

        JLabel formulaLabel = new JLabel("公式:", JLabel.LEFT);
        formulaTextPanel.add(formulaLabel, BorderLayout.NORTH);
        textArea = new ScriptEditor(astemp);

        JScrollPane bottom = new JScrollPane(textArea);
        formulaTextPanel.add(bottom);

        JSplitPane spthird = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        spthird.setDividerLocation(193);
        spthird.setTopComponent(first);
        spthird.setBottomComponent(formulaTextPanel);

        getContentPane().add(spthird);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel leftButtonPanel = new JPanel(new FlowLayout());

        CommandPanel rightButtonPanel = CommandPanel.createPanel(false);

        check = new JButton(" 验证 ");
        ok = new JButton("确定");
        cancel = new JButton("取消");

        JButton empty = new JButton("为空");

        ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done();
                }
            });

        cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    hide();
                }
            });

        empty.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    empty();
                }
            });

        rightButtonPanel.addComponent(ok);
        rightButtonPanel.addComponent(cancel);

        if (nullPermitted) {
            rightButtonPanel.addComponent(empty);
        }

        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        this.nullPermitted = nullPermitted;

        pack();

        Dimension size = showVariable ? new Dimension(630, 480) : new Dimension(420, 430);
        setSize(size);
        this.setLocationRelativeTo(owner);
    }

    protected void empty() {
        textArea.setText(null);
        done();
    }

    private IconTree createFunctionTree() {
        Icon icon = Util.getIcon("/jatools/icons/function.gif");

        SimpleTreeNode rootNode = new SimpleTreeNode("函数", icon);

        SimpleTreeNode parentNode = rootNode;

        for (int i = 0; i < FUNCTIONS.length; i++) {
            if (FUNCTIONS[i].indexOf("(") == -1) {
                SimpleTreeNode n = new SimpleTreeNode(FUNCTIONS[i], icon);
                rootNode.add(n);
                parentNode = n;
            } else {
                parentNode.add(new SimpleTreeNode(FUNCTIONS[i], icon, 1));
            }
        }

        IconTree tree = new IconTree();
        tree.setRoot(rootNode);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
                String selectedNode = null;
                String value = null;

                public void valueChanged(TreeSelectionEvent e) {
                    TreePath path = e.getNewLeadSelectionPath();
                    SimpleTreeNode simpleNode = (SimpleTreeNode) path.getLastPathComponent();

                    if (simpleNode != null) {
                        selectedNode = simpleNode.getUserObject().toString();

                        if (tips.containsKey(selectedNode)) {
                            value = (String) tips.get(selectedNode);
                            tipLabel.setText("<html><p>" + value + "</p></html>");
                        } else {
                            tipLabel.setText(selectedNode);
                        }
                    }
                }
            });
        tree.setDoubleClickAction(this);

        return tree;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        String text = null;

        if ((e.getSource() == functionTree) && (functionTree.getSelectedType() == 1)) {
            text = (String) functionTree.getSelectedObject();

            if (text != null) {
                int index = text.indexOf(" ");
                int left = text.indexOf("(");

                int count = 0;
                int start = 0;

                for (int i = 0; i < text.length(); i++) {
                    int comma = text.indexOf(",", start);

                    if (comma >= 0) {
                        start = comma + 1;
                        count++;
                    }
                }

                String textShort;
                String str = "";

                if (index >= 0) {
                    textShort = text.substring(index + 1, left);

                    if (count > 0) {
                        for (int i = 0; i < count; i++) {
                            str = str + ",  ";
                        }

                        text = textShort + "(" + str + ")";
                    } else {
                        text = textShort + "()";
                    }
                } else {
                    textArea.requestFocus();
                }

                textArea.replaceSelection(text + " ");
                textArea.requestFocus();

                return;
            }
        } else if ((e.getSource() == varTree) && varTree.isSettable()) {
            text = (String) varTree.getVariable();
        } else if (e.getSource() instanceof JToggleButton) {
            text = ((JToggleButton) e.getSource()).getText() + " ";
        }

        if (text != null) {
            textArea.replaceSelection(text);
            textArea.requestFocus();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Formula start() {
        return start((String) null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param formula DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Formula start(Formula formula) {
        if (formula != null) {
            textArea.setText(formula.getText());
        }

        this.formula = null;
        show();

        return this.formula;
    }

    /**
     * DOCUMENT ME!
     *
     * @param text DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Formula start(String text) {
        textArea.setText(text);

        this.formula = null;
        show();

        return this.formula;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean showChooser(Object value) {
        textArea.setText((String) value);
        this.formula = null;
        show();

        return this.formula != null;
    }

    private void done() {
        String txt = textArea.getText();

        boolean isNull = (txt == null) || txt.trim().equals("");

        if (isNull && !nullPermitted) {
            MessageBox.error(CustomFormulaDialog.this, "公式表达式不能为空.");
            textArea.requestFocus();

            return;
        } else if ((txt.indexOf(PrintConstants.TOTAL_PAGE_NUMBER) != -1) &&
                (!txt.trim().equals(PrintConstants.TOTAL_PAGE_NUMBER))) {
            if (AmbiguousNameNodePattern.matches(txt, PrintConstants.TOTAL_PAGE_NUMBER)) {
                MessageBox.error(CustomFormulaDialog.this,
                    "总页数变量:" + PrintConstants.TOTAL_PAGE_NUMBER + ",不能参与运算.");

                textArea.requestFocus();

                return;
            }
        }

        formula = new Formula(isNull ? null : txt);
        hide();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getValue() {
        return this.formula.getText();
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
    }
}
