package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class seeAllTagsButton extends JButton implements ActionListener {
	seeAllTagsButton(String label) {
		super(label);
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		AllTags frame = new AllTags();
		frame.setVisible(true);
	}
}
