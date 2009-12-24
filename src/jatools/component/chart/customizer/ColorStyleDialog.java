package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.Globals;
import jatools.component.chart.component.FillStyleChooser;
import jatools.component.chart.component.FillStyleFactory;
import jatools.component.chart.component.FillStyleInterface;
import jatools.component.chart.component.SingleColor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class ColorStyleDialog extends JDialog implements ChangeListener,
		ActionListener {

	ChangeListener parent;

	FillStyleChooser chooser;

	FillStyleInterface style;

	Gc gc = new Gc(Color.WHITE, new Globals());

	FillStylePanel panel;

	JButton ok, cancel;

	PreviewPanel view;

	public ColorStyleDialog(Component frame, String title,
			FillStyleChooser chooser, FillStyleInterface init, boolean mode) {
		super((Dialog) frame, title, mode);
		//setLocationRelativeTo(frame);
		this.chooser = chooser;
		if (init == null) {
			style = new SingleColor(Color.WHITE);
		} else {
			style = init;
		}
		style.setToGc(gc);
		this.chooser.setStyle(style);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		getContentPane().setLayout(gbl);

		panel = new FillStylePanel();
		panel.setObject(gc);
		panel.addChangeListener(this);

		ok = new JButton("确定");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		cancel = new JButton("取消");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);

		gbc.weightx = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		getContentPane().add(panel, gbc);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(gbl);
		gbc.weightx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weighty = 0;
		buttonPanel.add(ok, gbc);
		buttonPanel.add(cancel, gbc);
		gbc.weighty = 1;
		buttonPanel.add(Box.createGlue(), gbc);

		getContentPane().add(buttonPanel, gbc);

		view = new PreviewPanel();
		getContentPane().add(Box.createGlue(), gbc);
		gbc.weighty = 0;
		getContentPane().add(view, gbc);

		setSize(400, 250);
	}

	public void addChangeListener(ChangeListener l) {
		parent = l;
	}

	public void fireChange(Object object) {
		style = FillStyleFactory.createFillStyle(gc);
		view.setIcon(style.createIcon(CommonFinal.VIEW_ICON_SIZE));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			chooser.setStyle(style);
		}
		hide();
	}
}
