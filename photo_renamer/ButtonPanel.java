package photo_renamer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The class for exit buttons with observer design pattern.
 * 
 * @author Vugar Mammadli
 *
 */
public class ButtonPanel extends JPanel implements ActionListener {
	List<JButton> myboxes = new ArrayList<>();

	public void addButtonToListen(Component comp) {
		((JButton) comp).addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
