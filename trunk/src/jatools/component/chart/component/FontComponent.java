/*
 * Author: John.
 * 
 * 杭州杰创软件 All Copyrights Reserved.
 */
package jatools.component.chart.component;

import jatools.swingx.SwingUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jatools.designer.Main;



/**
 *
 *  DOCUMENT ME!
 *  
 *  @version $Revision: 1.1 $
 *  @author $author$
 * 
 */

public class FontComponent extends JPanel
	implements
		ChangeListener,
		ItemListener,
		ActionListener {
	private static FontComponent defaultChooser;
	private final static String SAMPLE_TEXT = "Hello !"; //
	private ZListEditor faceSelector;
	private ZListEditor styleSelector;
	private ZListEditor sizeSelector;
	private String sampleText = SAMPLE_TEXT;
	private _SampleLabel sampleLabel = new _SampleLabel(sampleText); //us; //use
	// paintComponent
	// method

	//    private JLabel sampleLabel = new JLabel(new _SampleIcon()); //use icon
	// method

	Object[] styles = {"常规","粗体","斜体","斜体 粗体"};
			//Z"常规", Z"粗体", Z"斜体", Z"粗体+斜体" }; // //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	boolean exitOK = false;
	JDialog shareInstance;
	//JCheckBox checkbox;
	//JButton backgroundButton;
	JButton foregroundButton;
	//private ZColorIcon backIcon;
	private ZColorIcon foreIcon;

	/**
	 * Creates a new ZFontChooser object.
	 */
	public FontComponent() {
		buildDialog();
	}

	/**
	 * @param changeEvent
	 *            DOCUMENT ME!
	 */
	public void stateChanged(ChangeEvent changeEvent) {
		sampleLabel.revalidate();
		sampleLabel.repaint();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param font
	 *            DOCUMENT ME!
	 */
	public void setValue(Object value) {
		ZFont f = (ZFont) value;
		String face = (f == null)
			? "Dialog" : f.getFace(); //
		int style = (f == null) ? 0 : f.getStyle();
		int size = (f == null) ? 12 : f.getSize();

		faceSelector.setSelectedValue(face);
		styleSelector.setSelectedValue(styles[style]);
		sizeSelector.setSelectedValue(size + ""); //

		//new added
		foreIcon.setColor((f == null) ? Color.white : f.getForeColor());
		//backIcon.setColor((f == null) ? Color.blue : f.getBackColor());
		//checkbox.setSelected(backIcon.getColor() == null);

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Object getValue() {
		int size = 16;

		try {
			size = Integer.parseInt((String) sizeSelector.getSelectedValue());
		} catch (NumberFormatException ex) {
			// 不能转换成数字
		}

		return new ZFont(
			(String) faceSelector.getSelectedValue(),
			styleSelector.getSelectedIndex(),
			size,
			foreIcon.getColor(),
			null);
	}

	/**
	 *
	 *  DOCUMENT ME!
	 *  
	 *  @return DOCUMENT ME!
	 * 
	 */

	public static FontComponent getDefault() {
		if (defaultChooser == null) {
			defaultChooser = new FontComponent();
		}
		return defaultChooser;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean showChooser(JComponent owner) {
		exitOK = false;

		JButton okButton = new JButton("确定"); //
		JButton cancelButton = new JButton("取消");//
			
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitOK = true;
				shareInstance.hide();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shareInstance.hide();
			}
		});
		shareInstance = new JDialog(Main.getInstance() , "字体选择"); //
		shareInstance.setModal(true);

		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p.add(okButton);
		p.add(cancelButton);

	//	p.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 40));
	SwingUtil.setBorder6( p);
		shareInstance.getContentPane().add(this, BorderLayout.CENTER);
		shareInstance.getContentPane().add(p, BorderLayout.SOUTH);
		this.shareInstance.setSize( new Dimension(300,380));
		shareInstance.setLocationRelativeTo(owner);
		shareInstance.show();

		return exitOK;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param args
	 *            DOCUMENT ME!
	 */
	private void buildDialog() {
		Object[] faces = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getAvailableFontFamilyNames();
		faceSelector = new ZListEditor(faces, "字体", false); //

		faceSelector.setPreferredSize(new Dimension(120, 10));

		styleSelector = new ZListEditor(styles,"风格", false); //
		styleSelector.setPreferredSize(new Dimension(90, 10));

		Object[] sizes = {
				"6", "8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "32", "48", "72" }; // //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$
		sizeSelector = new ZListEditor(sizes, "字号",true); //
		sizeSelector.setPreferredSize(new Dimension(60, 10));
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0f;
		add(faceSelector, gbc);
		add(Box.createHorizontalStrut(5), gbc);
		add(styleSelector, gbc);
		add(Box.createHorizontalStrut(5), gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(sizeSelector, gbc);

		//add the font foreground and background setting
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.0f;
		gbc.insets = new Insets(5, 0, 2, 0);
		add(buildGroundPanel(), gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		add(Box.createVerticalStrut(5), gbc);

		//checkbox.addItemListener(this);
		foregroundButton.addActionListener(this);
		//backgroundButton.addActionListener(this);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.0f;
		add(Box.createVerticalStrut(5), gbc);

		add(sampleLabel, gbc);

		sampleLabel.setBorder(BorderFactory.createEtchedBorder());
		sampleLabel.setPreferredSize(new Dimension(170, 70));
		faceSelector.addChangeListener(this);
		styleSelector.addChangeListener(this);
		sizeSelector.addChangeListener(this);

	//	this.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 20));

	//	this.setPreferredSize(new Dimension(350, 350));
	}

	/**
	 * @param itemEvent
	 *            DOCUMENT ME!
	 */
	public void itemStateChanged(ItemEvent itemEvent) {
		/*backgroundButton
			.setEnabled(itemEvent.getStateChange() != ItemEvent.SELECTED);
		Color backColor = backgroundButton.isEnabled() ? Color.WHITE : null;
		backIcon.setColor(backColor);*/

		sampleLabel.repaint();
	}

	/**
	 * @param actionEvent
	 *            DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e) {
		Color color = JColorChooser.showDialog(this, "颜色选择", Color.black); //

		if (color != null) {
			if (e.getSource() == foregroundButton) {
				foreIcon.setColor(color);
			} else {
				//backIcon.setColor(color);

			}

		}
		sampleLabel.repaint();
	}

	/**
	 * @param args
	 *            DOCUMENT ME!
	 */
	private JPanel buildGroundPanel() {
		JPanel panel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		panel.setLayout(gridbag);

		GridBagConstraints c;
		int gridx;
		int gridy;
		int gridwidth;
		int gridheight;
		int anchor;
		int fill;
		int ipadx;
		int ipady;
		double weightx;
		double weighty;
		Insets inset;
		panel.setLayout(gridbag);

		panel.setBorder(BorderFactory.createTitledBorder("颜色")); //
		/*checkbox = new JCheckBox("透明?尘?", true); //
		backIcon = new ZColorIcon(16, 16);*/
		foreIcon = new ZColorIcon(16, 16);

		foregroundButton = new JButton(
			"前景", foreIcon); //
		/*backgroundButton = new JButton(
			"背景", backIcon); //
*/		/*gridx = 0;
		gridy = 0;
		gridwidth = 1;
		gridheight = 1;
		weightx = 1;
		weighty = 1;
		anchor = GridBagConstraints.CENTER;
		fill = GridBagConstraints.HORIZONTAL;
		inset = new Insets(0, 0, 0, 0);
		ipadx = 0;
		ipady = 0;
		c = new GridBagConstraints(
			gridx,
			gridy,
			gridwidth,
			gridheight,
			weightx,
			weighty,
			anchor,
			fill,
			inset,
			ipadx,
			ipady);
		gridbag.setConstraints(checkbox, c);
		panel.add(checkbox);*/

		gridx = 0;
		gridy = 1;
		gridwidth = 1;
		gridheight = 1;
		weightx = 1;
		weighty = 1;
		anchor = GridBagConstraints.CENTER;
		fill = GridBagConstraints.HORIZONTAL;
		inset = new Insets(3, 5, 3, 5);
		ipadx = 0;
		ipady = 0;
		c = new GridBagConstraints(
			gridx,
			gridy,
			gridwidth,
			gridheight,
			weightx,
			weighty,
			anchor,
			fill,
			inset,
			ipadx,
			ipady);
		gridbag.setConstraints(foregroundButton, c);
		panel.add(foregroundButton);

		/*gridx = 1;
		gridy = 1;
		gridwidth = 1;
		gridheight = 1;
		weightx = 1;
		weighty = 1;
		anchor = GridBagConstraints.CENTER;
		fill = GridBagConstraints.HORIZONTAL;
		inset = new Insets(3, 5, 3, 5);
		ipadx = 0;
		ipady = 0;
		c = new GridBagConstraints(
			gridx,
			gridy,
			gridwidth,
			gridheight,
			weightx,
			weighty,
			anchor,
			fill,
			inset,
			ipadx,
			ipady);
		gridbag.setConstraints(backgroundButton, c);
		panel.add(backgroundButton);*/

		return panel;
	}

	class _SampleLabel extends JLabel { //use paintComponent method

		String title = new String();

		public _SampleLabel(String title) {
			this.title = title;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			ZFont font = (ZFont) getValue();

			FontMetrics fm = g.getFontMetrics(font.asFont());
			int textWidth = fm.stringWidth(sampleText);
			int textHeight = fm.getHeight();

			int width = getWidth();
			int height = getHeight();

			int x = (width - textWidth) / 2;
			int y = ((height - textHeight) / 2);
			g.translate(x, y);
			font.paint(g, sampleText, 0, 0);
			g.translate(-x, -y);

		}
	}
	class ColorIcon extends ZEmptyIcon {
		Color color = Color.red;

		/**
		 * Creates a new ColorIcon object.
		 *
		 * @param width DOCUMENT ME!
		 * @param height DOCUMENT ME!
		 */
		public ColorIcon(int width, int height) {
			super(width, height);
		}

		/**
		 * DOCUMENT ME!
		 *
		 * @param color DOCUMENT ME!
		 */
		public void setColor(Color color) {
			this.color = color;
		}

		/**
		 * DOCUMENT ME!
		 *
		 * @param c DOCUMENT ME!
		 * @param g DOCUMENT ME!
		 * @param x DOCUMENT ME!
		 * @param y DOCUMENT ME!
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(x, y, getIconWidth(), getIconHeight());
			g.setColor(Color.black);
			g.drawRect(x, y, getIconWidth(), getIconHeight());
		}
	}
}