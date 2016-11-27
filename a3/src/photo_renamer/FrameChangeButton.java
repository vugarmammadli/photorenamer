package photo_renamer;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.util.*;

public class FrameChangeButton extends JButton {
	private List<ButtonObserver> observers = new ArrayList<>();

	public FrameChangeButton(String name) {
		super(name);
	}

	public void addObserver(ButtonObserver o) {
		observers.add(o);
	}

	@Override
	protected void fireActionPerformed(ActionEvent event) {
		for (ButtonObserver o : observers) {
			o.buttonClicked();
		}
	}
}
