package photo_renamer;

import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class imageButton extends JButton implements ActionListener {
	static File f;
	imageButton(String label, File file) {
		super(label);
		Image image;
		f = file;
		try {
			image = ImageIO.read(file);
			Image newImage = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(newImage);
			this.setIcon(icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(200, 150));
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		ImageTagging frame = new ImageTagging(f);
		frame.setVisible(true);
	}
}