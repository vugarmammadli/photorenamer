package photo_renamer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.User;

public class PhotoRenamer extends JFrame {
	private JPanel buttonPanel;
	private JPanel imagePanel;
	private JPanel titlePanel;
	private ExitButton exitButton;
	private seeAllTagsButton seeAllTagsButton;

	public PhotoRenamer() {
		imagePanel = new JPanel(new GridLayout(3, 4));
		File directory; // Directory we're working with

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			directory = fileChooser.getSelectedFile();
			makeImageButton(directory);
		}

		titlePanel = new JPanel(new FlowLayout());
		JLabel title = new JLabel("Photo Renamer");
		title.setFont(new Font("Calibri", Font.BOLD, 24));
		titlePanel.add(title);

		buttonPanel = new JPanel(new FlowLayout());
		exitButton = new ExitButton("Exit");
		seeAllTagsButton = new seeAllTagsButton("See All Tags");
		buttonPanel.add(seeAllTagsButton);
		buttonPanel.add(exitButton);

		add(titlePanel, BorderLayout.PAGE_START);
		add(buttonPanel, BorderLayout.CENTER);
		add(imagePanel, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
	}

	/*
	 * Helper function to create a ImageButton for all images in the directory
	 * f.
	 * 
	 * @param f the directory which has the images
	 */
	public void makeImageButton(File f) {
		for (File item : f.listFiles()) {
			if (item.isFile()) {
				imagePanel.add(new imageButton(item.getName(), item));
			} else if (item.isDirectory()) {
				makeImageButton(item);
			}
		}

	}

	public static void main(String[] args) {
		PhotoRenamer frame = new PhotoRenamer();
		frame.setVisible(true);
	}
}