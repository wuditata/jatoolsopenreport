package jatools.designer.property.editor;
import jatools.accessor.PropertyDescriptor;
import jatools.designer.Main;
import jatools.formatter.DateFormat;
import jatools.formatter.DecimalFormat;
import jatools.formatter.Format2;
import jatools.swingx.Chooser;
import jatools.swingx.ListEditor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * @author   java9
 */
public class FormatChooser extends JDialog implements Chooser, ActionListener {
	static final int NULL = 0;
	static final int FIXED = 1;
	static final int CURRENCY = 2;
	static final int PERCENTIGE = 3;
	static final int SCIENTIFIC = 4;
	static final int DATE = 5;
	static final int TIME = 6;

	// 预览板卡片名称
	static final String NULL_CARD = "null"; //
	static final String DECIMAL_CARD = "decimal"; //
	static final String DATE_CARD = "date"; //
	static final String TIME_CARD = "time"; //

	// 每一类格式化对象可选属性
	static final String SELECTABLE = "selectable"; //
	static String[] patterns = new String[]{//普通
			"0", // //
			"0.0", // //
			"0.00", // //
			"0.000", // //
			"0.0000", // //
			"#,##0", // //
			"#,##0.0", // //
			"#,##0.00", // //
			"#,##0.000", // //
			"#,##0.0000", // //
			null, //货币
			"￥0", // //
			"￥0.0", // //
			"￥0.00", // //
			"￥0.000", // //
			"￥0.0000", // //
			"￥#,##0", // //
			"￥#,##0.0", // //
			"￥#,##0.00", // //
			"￥#,##0.000", // //
			"￥#,##0.0000", // //
			null, //百分比
			"0%", // //
			"0.0%", // //
			"0.00%", // //
			"0.000%", // //
			"0.0000%", // //
			null, // 科学计数法
			"0.##E0", "0.00E0", null}; // //$NON-NLS-2$
	static Format2 NULL_FORMAT = new Format2() {
		public String format(Object o) {
			return ""; //
		}

		public PropertyDescriptor[] getRegistrableProperties() {
			return new PropertyDescriptor[0];
		}

		public boolean equals(Object obj) {
			return (obj == null);
		}

		public String toString() {
			return "无格式"; //
		}

		public Object clone() {
			return this;
		}

		public String toExcel() {
			// TODO Auto-generated method stub
			return null;
		}

		public String toPattern() {
			// TODO Auto-generated method stub
			return null;
		}

		
	};

	static Format2[][] formats;
	boolean exitOK = false;
	ListEditor patternSelector;
	CardLayout card;
	JPanel preview;
	ZFormatPreview activePreview;
	JRadioButton[] options;
	private FormatChooser instance;

	/**
	 * Creates a new ZFormatChooser object.
	 */
	public FormatChooser(Frame owner, boolean needui) {
		super(owner, "格式", true); //
		if (needui) {
			buildUI();
			instance = this;
		}
	}

	public FormatChooser(Frame owner) {
		this(owner, true);

	}


	//
	public void actionPerformed(ActionEvent e) {
		// 显示面板
		int i = Integer.parseInt(e.getActionCommand());
		String cardId = null;

		switch (i) {
			case NULL :
				cardId = NULL_CARD;

				break;

			case FIXED :
			case CURRENCY :
			case PERCENTIGE :
			case SCIENTIFIC :
				cardId = DECIMAL_CARD;

				break;

			case DATE :
				cardId = DATE_CARD;

				break;

			case TIME :
				cardId = TIME_CARD;

				break;
		}

		card.show(preview, cardId);

		Format2[] formats = (Format2[]) ((JComponent) e.getSource())
				.getClientProperty(SELECTABLE);
		boolean nullFormat = e.getSource() == options[0];
		patternSelector.setSelectable(formats);
		preview.setEnabled(!nullFormat);
		patternSelector.setEnabled(!nullFormat);
	}

	Format2[][] getFormats() {
		if (formats == null) {
			formats = new Format2[TIME + 1][];

			//建立无格式
			int j = 0;
			formats[j] = new Format2[]{NULL_FORMAT};
			j++;

			//建立ZDecimalFormat
			ArrayList tmp = new ArrayList();

			for (int i = 0; i < patterns.length; i++) {
				if (patterns[i] != null) {
					tmp.add(new DecimalFormat(patterns[i]));
				}
				else {
					formats[j] = (Format2[]) tmp.toArray(new Format2[0]);
					tmp.clear();
					j++;
				}
			}

			//建立日期格式
			for (int i = 0; i < 4; i++) {
				tmp.add(new DateFormat(DateFormat.DATE, i));
			}

			formats[j] = (Format2[]) tmp.toArray(new Format2[0]);
			tmp.clear();
			j++;

			for (int i = 0; i < 4; i++) {
				tmp.add(new DateFormat(DateFormat.TIME, i));
			}

			formats[j] = (Format2[]) tmp.toArray(new Format2[0]);
		}

		return formats;
	}

	/**
	 * DOCUMENT ME!
	 */
	private void buildUI() {
		patternSelector = new ListEditor(new Object[]{NULL_FORMAT}, "格式选择:", false); //
		card = new CardLayout();
		preview = new JPanel(card);

		options = new JRadioButton[]{
				new JRadioButton("无"), //
				new JRadioButton("数值"), //
				new JRadioButton("货币"), //
				new JRadioButton("百分比"), //
				new JRadioButton("科学计数法"), //
				new JRadioButton("日期"), //
				new JRadioButton("时间")}; //
		JPanel optionPane = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.WEST;

		ButtonGroup group = new ButtonGroup();

		Format2[][] formats = getFormats();

		for (int i = 0; i < options.length; i++) {
			//选择扭,使其对应相应的可选格式对象
			options[i].putClientProperty(SELECTABLE, formats[i]);
			options[i].setActionCommand(i + ""); //
			options[i].addActionListener(this);
			optionPane.add(options[i], gbc);
			group.add(options[i]);
		}

		// 组装上边的面板, option + pattern + preview
		JPanel content = new JPanel(new BorderLayout());
		preview.setPreferredSize(new Dimension(10, 70));
		preview.setBorder(BorderFactory.createTitledBorder("预览")); //
		patternSelector.setBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 0));
		patternSelector.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int i = patternSelector.getSelectedIndex();
				selectFormat((Format2) patternSelector.getElementAt(i));
			}
		});

		content.add(optionPane, BorderLayout.WEST);
		content.add(patternSelector, BorderLayout.CENTER);

		// 在预览面板中加入四张卡片
		preview.add(NULL_CARD, new ZNullPreview());
		preview.add(DECIMAL_CARD, new ZDecimalPreview());
		preview.add(DATE_CARD, new ZDatePreview());
		preview.add(TIME_CARD, new ZTimePreview());

		///
		content.add(preview, BorderLayout.SOUTH);

		JPanel commandPane = new JPanel(
				new FlowLayout(FlowLayout.RIGHT, 10, 10));
		JButton ok = new JButton("确认"); //
		commandPane.add(ok);

		JButton cancel = new JButton("取消"); //
		commandPane.add(cancel);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitOK = true;
				instance.hide();
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instance.hide();
			}
		});

		Border border = BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10), BorderFactory
				.createEtchedBorder());

		border = BorderFactory.createCompoundBorder(border, BorderFactory
				.createEmptyBorder(10, 10, 5, 10));

		content.setBorder(border);
		getContentPane().add(content, BorderLayout.CENTER);
		getContentPane().add(commandPane, BorderLayout.SOUTH);
		options[0].doClick();
		pack();
		setSize(new Dimension(400, 390));
	}

	private void selectFormat(Format2 format) {
		if (format == null) {
			return;
		}

		//找到当前可见的预览板,设置格式化器
		Component[] c = preview.getComponents();

		for (int i = 0; i < c.length; i++) {
			if (c[i].isVisible()) {
				((ZFormatPreview) c[i]).setFormat(format);

				return;
			}
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getValue() {
		Object value = patternSelector.getElementAt(patternSelector
				.getSelectedIndex());

		if (value == NULL_FORMAT) {
			return null;
		}
		else {
			return value;
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param value
	 *            DOCUMENT ME!
	 */
	public void setValue(Object value) {
		Format2[][] formats = getFormats();

		for (int i = 0; i < formats.length; i++) {
			for (int j = 0; j < formats[i].length; j++) {
				if (formats[i][j].equals(value)) {
					options[i].doClick();
					patternSelector.setSelectedValue(formats[i][j]);

					return;
				}
			}
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean showChooser(JComponent owner) {
		exitOK = false;
		setLocationRelativeTo(owner);
		show();

		return exitOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jatools.swing.ZChooser#showChooser(javax.swing.JComponent,
	 *      com.jatools.core.ZReportDocument, java.lang.Object)
	 */
	public boolean showChooser(JComponent owner, Object value) {
		instance = new FormatChooser(Main.getInstance() ,false);

		setValue(value);
		exitOK = false;
		instance.setContentPane(this.getContentPane());
		instance.pack();
		instance.setSize(new Dimension(400, 390));
		instance.setLocationRelativeTo(owner);
		instance.show();
		this.setContentPane(instance.getContentPane());
		return exitOK;

	}
}

/**
 * @author   java9
 */
abstract class ZFormatPreview extends JPanel {
	Format2 format;
	JLabel preview = new JLabel("12345890..14456"); //

	/**
	 * Creates a new ZFormatPreview object.
	 */
	ZFormatPreview() {
		preview.setPreferredSize(new Dimension(150, 25));

		JComponent editor = this.getEditor();
		editor.setPreferredSize(preview.getPreferredSize());
		add(editor);
		add(preview);
	}

	/**
	 * DOCUMENT ME!
	 * @param f   DOCUMENT ME!
	 * @uml.property   name="format"
	 */
	public void setFormat(Format2 f) {
		this.format = f;
		format();
	}

	/**
	 * DOCUMENT ME!
	 */
	public void format() {
		Object value = getValue();

		if (value != null) {
			preview.setText(format.format(value));
		}
		else {
			preview.setText(""); //
		}
	}

	/**
	 * DOCUMENT ME!
	 * @return   DOCUMENT ME!
	 * @uml.property   name="format"
	 */
	public Format2 getFormat() {
		return format;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	abstract public Object getValue();

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	abstract public JComponent getEditor();
}

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.6 $
 * @author $author$
 */
class ZDecimalPreview extends ZFormatPreview {
	JTextField text;

	/**
	 * Creates a new ZDecimalPreview object.
	 */
	ZDecimalPreview() {
		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				format();
			}
		});
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public JComponent getEditor() {
		text = new JTextField("1234567.89"); //

		return text;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getValue() {
		return new Double(text.getText());
	}
}

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.6 $
 * @author $author$
 */
class ZDatePreview extends ZFormatPreview {
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getValue() {
		return new Date();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public JComponent getEditor() {
		return new JLabel("  今天是:"); //
	}
}

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.6 $
 * @author $author$
 */
class ZTimePreview extends ZFormatPreview {
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getValue() {
		return new Date();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public JComponent getEditor() {
		return new JLabel("  现在时间是:"); //
	}
}

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.6 $
 * @author $author$
 */
class ZNullPreview extends ZFormatPreview {
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getValue() {
		return null;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public JComponent getEditor() {
		return new JLabel("  不指定格式"); //
	}
}
