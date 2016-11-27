package photo_renamer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Configuration;
import backend.ImageFile;
import backend.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AddNewTag extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldTagName;
	private JButton btnAdd;
	private User user;

	/**
	 * Create the frame.
	 */
	public AddNewTag() {
		user = User.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 166);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddNewTag = new JLabel("Add new tag");
		lblAddNewTag.setBounds(110, 11, 125, 20);
		lblAddNewTag.setFont(new Font("Calibri", Font.BOLD, 24));
		contentPane.add(lblAddNewTag);

		JLabel lblTagName = new JLabel("Tag name:");
		lblTagName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTagName.setBounds(10, 52, 75, 21);
		contentPane.add(lblTagName);

		textFieldTagName = new JTextField();
		textFieldTagName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldTagName.setBounds(95, 54, 223, 20);
		contentPane.add(textFieldTagName);
		textFieldTagName.setColumns(10);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.addTag(textFieldTagName.getText());
				contentPane.setVisible(false);
				dispose();
				AllTags allTags = new AllTags();
				allTags.setVisible(true);
			}
		});

		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.setBounds(229, 85, 89, 23);
		contentPane.add(btnAdd);

		FrameChangeButton btnBack = new FrameChangeButton("Back");
		btnBack.addObserver(new FrameChangeButtonListener(this, new AllTags()));
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.setBounds(10, 85, 89, 23);
		contentPane.add(btnBack);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		AddNewTag frame = new AddNewTag();
		frame.setVisible(true);
	}
}
