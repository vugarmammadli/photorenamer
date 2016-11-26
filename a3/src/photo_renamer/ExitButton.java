package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ExitButton extends JButton implements ActionListener {
	ExitButton(String label) {
		super(label);
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
