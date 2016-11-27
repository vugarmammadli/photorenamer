package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import backend.Configuration;
import backend.ImageFile;
import backend.Tag;
import backend.User;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class PhotoRenamer extends JFrame {

	private JPanel contentPane;
	private JPanel imageBtnPanels;
	private static File directory = null;
	private JLabel lblImageName, lblImage;
	private JTextArea txtlblImageTags;
	private JTextArea txtImageHistory;
	private ImageFile selectedImage;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public PhotoRenamer() {
		if (directory == null)
			getDirectory();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblWelcome = new JLabel("Welcome to photo renamer!");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWelcome, BorderLayout.NORTH);

		List<ImageFile> allFiles = User.getInstance().getAllImages(directory);

		JList<ImageFile> listOfImages = new JList(allFiles.toArray());
		listOfImages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfImages.setFixedCellWidth(200);
		contentPane.add(listOfImages, BorderLayout.WEST);

		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnSeeAllTags = new JButton("See all tags");
		btnSeeAllTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AllTagsFrame().setVisible(true);
			}
		});
		buttonPanel.add(btnSeeAllTags);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttonPanel.add(btnExit);

		JPanel imgPanel = new JPanel(new BorderLayout(0, 0));
		contentPane.add(imgPanel, BorderLayout.CENTER);

		lblImage = new JLabel("");
		imgPanel.add(lblImage, BorderLayout.WEST);

		JPanel imageInfoPanel = new JPanel(new GridLayout(4, 0));
		imageInfoPanel.setVisible(false);
		imgPanel.add(imageInfoPanel, BorderLayout.EAST);

		lblImageName = new JLabel();
		imageInfoPanel.add(lblImageName);

		txtlblImageTags = new JTextArea();
		txtlblImageTags.setEditable(false);
		txtlblImageTags.setLineWrap(true);
		imageInfoPanel.add(txtlblImageTags);
		scrollPane = new JScrollPane(txtlblImageTags);
		imageInfoPanel.add(scrollPane);

		txtImageHistory = new JTextArea();
		txtImageHistory.setEditable(false);
		txtImageHistory.setLineWrap(true);
		scrollPane = new JScrollPane(txtImageHistory);
		imageInfoPanel.add(scrollPane);

		imageBtnPanels = new JPanel();

		imageInfoPanel.add(imageBtnPanels);

		JButton btnAddTag = new JButton("Add tag to image");	
		btnAddTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TagImageFrame(selectedImage).setVisible(true);
			}
		});
		imageBtnPanels.add(btnAddTag);

		JButton btnRevertName = new JButton("Revert name of image");
		btnRevertName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RevertNameFrame(selectedImage).setVisible(true);
			}
		});
		imageBtnPanels.add(btnRevertName);

		JButton btnDeleteTag = new JButton("Delete tag from image");
		btnDeleteTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DeleteTagFromImageFrame(selectedImage).setVisible(true);
			}
		});
		imageBtnPanels.add(btnDeleteTag);

		listOfImages.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				imageInfoPanel.setVisible(true);
				selectedImage = listOfImages.getSelectedValue();
				populateImagePanel(selectedImage);
			}

		});
	}

	private void populateImagePanel(ImageFile selectedImage) {
		Image img = null;
		try {
			img = ImageIO.read(selectedImage.getFile()).getScaledInstance(300, 300, Image.SCALE_FAST);
		} catch (IOException e) {

		}
		ImageIcon icon = new ImageIcon(img);
		this.lblImage.setIcon(icon);
		this.lblImage.setHorizontalAlignment(JLabel.CENTER);
		this.lblImageName.setText("Name: " + selectedImage.getName());
		String tags = "This image does not have tags.";
		if (!selectedImage.getTags().isEmpty()) {
			tags = "Tags: \n";
			for (Tag t : selectedImage.getTags()) {
				tags += t.getName() + "\n";
			}
		}
		this.txtlblImageTags.setText(tags);
		this.txtImageHistory.setText("Name history: \n" + selectedImage.getNameHistory());
	}

	private void getDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			directory = fileChooser.getSelectedFile();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		PhotoRenamer frame = new PhotoRenamer();
		frame.setVisible(true);
	}
}
