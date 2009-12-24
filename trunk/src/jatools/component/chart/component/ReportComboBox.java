package jatools.component.chart.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;


public class ReportComboBox extends JComboBox {
	
	private DefaultComboBoxModel model = new DefaultComboBoxModel();
	private ReportListRenderer renderer;
	
	private String type = "1";
	
	public ReportComboBox(){
		renderer = new ReportListRenderer();
		setRenderer(renderer);
		setModel(model);
		ZFixedSizeComboBoxUI ui = new ZFixedSizeComboBoxUI();
        setUI(ui);
        listenToList(ui.getPopupList());
		model.addElement(type);
	}
	
	public void setType(String type){
		this.type = type;
		renderer.setType(type);
		repaint();
	}
	
	public String getType(){
		return type;
	}
	
	private void listenToList(JList popupList) {
		JList list = popupList;
		list.addMouseListener(new MouseAdapter(){

			public void mousePressed(MouseEvent e) {
				renderer.mousePressed(e);
				type = renderer.getType();
				setSelectedIndex(0);
			}
			
			
		});
		list.addMouseMotionListener(new MouseMotionAdapter(){

			public void mouseMoved(MouseEvent e) {
				renderer.mouseMoved(e);
			}
			
		});
	}

	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		panel.add(new ReportComboBox(), gbc);
		//panel.add(new ReportListRenderer());
		frame.getContentPane().add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
