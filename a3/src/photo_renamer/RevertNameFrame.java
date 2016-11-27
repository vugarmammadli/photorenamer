package photo_renamer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import backend.ImageFile;
import backend.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The frame to display name history of selected images and revert back to one
 * of the selected one.
 *
 */
public class RevertNameFrame extends JFrame {

	private JPanel contentPane;
	private ImageFile selectedImage;
	private String selectedName;

	/**
	 * Create the frame.
	 */
	public RevertNameFrame(ImageFile selectedImage) {
		this.selectedImage = selectedImage;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblNameHistory = new JLabel("List of all name changes");
		lblNameHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblNameHistory.setFont(new Font("Calibri", Font.BOLD, 24));
		contentPane.add(lblNameHistory, BorderLayout.NORTH);

		// displays list of all name changes one by one.
		JList<String> listOfNames = new JList(selectedImage.getNameHistory().split("\n"));
		listOfNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(listOfNames, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		JButton btnRevertName = new JButton("Revert name");
		btnRevertName.setEnabled(false);
		btnRevertName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User.getInstance().revertName(selectedImage, selectedName);
				setVisible(false);
				new PhotoRenamer().setVisible(true);
			}
		});
		buttonPanel.add(btnRevertName, BorderLayout.EAST);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PhotoRenamer().setVisible(true);
			}
		});
		buttonPanel.add(btnBack, BorderLayout.WEST);

		// sets selected name according to jlist.
		listOfNames.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				btnRevertName.setEnabled(true);
				selectedName = listOfNames.getSelectedValue();
				selectedName = selectedName.split("Name: ")[1];
			}
		});
	}
}
