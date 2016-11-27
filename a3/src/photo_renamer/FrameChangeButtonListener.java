package photo_renamer;

import javax.swing.JFrame;

public class FrameChangeButtonListener implements ButtonObserver {

	private JFrame oldFrame, newFrame;

	public FrameChangeButtonListener(JFrame oldFrame, JFrame newFrame) {
		this.oldFrame = oldFrame;
		this.newFrame = newFrame;
	}

	@Override
	public void buttonClicked() {
		oldFrame.dispose();
		newFrame.setVisible(true);
	}

}
