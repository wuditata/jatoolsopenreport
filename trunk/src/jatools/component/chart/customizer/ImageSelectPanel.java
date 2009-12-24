package jatools.component.chart.customizer;

import jatools.component.chart.CommonFinal;
import jatools.component.chart.chart.Gc;
import jatools.component.chart.chart.GcHelper;
import jatools.component.chart.component.ZImageFileChooser;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;


public class ImageSelectPanel extends Dialog implements ActionListener {

	Gc gc;
	Image image;
	byte[] bytes;
	public ImageSelectPanel(){
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		setBorder(CommonFinal.EMPTY_BORDER);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = CommonFinal.INSETS;
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		

		JButton button = new JButton("选择图片");
		button.addActionListener(this);
		
		add(button, gbc);
		gbc.weighty = 1;
		add(Box.createGlue(), gbc);

	}
	
	public void show(){
		gc.setFillStyle(Gc.FILL_TEXTURE);
		gc.setTexture(GcHelper.TEXTURE_IMAGE);
		setVals();
		super.show();
		fireChange(null);
	}

	public void setObject(Object object) {
		gc = (Gc) object;
	}

	public void getVals() {
		gc.setImage(image);
		gc.setImageBytes(bytes);
	}

	public void setVals() {
		image = gc.getImage();
		bytes = gc.getImageBytes();
	}

	public void actionPerformed(ActionEvent e) {

		ZImageFileChooser.getSharedInstance(this).show();
		File file = ZImageFileChooser.getSharedInstance(this).getSelectedFile();
		try {
			image = createImage(file);

			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			int c;
			while ((c = fis.read(buf)) != -1) {
				bos.write(buf, 0, c);
			}
			bytes = bos.toByteArray();
			fis.close();
			bos.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		fireChange(null);
		
		/*JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				image = createImage(file);
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[4096];
				int c;
				while((c = fis.read(buf)) != -1){
					bos.write(buf, 0, c);
				}
				bytes = bos.toByteArray();
				fis.close();
				bos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		fireChange(null);*/
	}
	
	public Image createImage(File file) throws Exception{
		int start = file.getName().lastIndexOf(".") + 1;
		String ext = file.getName().substring(start);
		if(!ext.equals("jpg") && !ext.equals("gif") && !ext.equals("jpeg") && !ext.equals("tif") && !ext.equals("tiff")){
			throw new Exception("文件类型不正确");
		}
		
		return ImageIO.read(file);
	}
}
