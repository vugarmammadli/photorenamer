package photo_renamer;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import backend.Configuration;
import backend.Tag;
import backend.User;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Frame to display all tags.
 *
 */
public class AllTagsFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private User user;

	/**
	 * Create the frame.
	 */
	public AllTagsFrame() {
		// Uploads existed tags
		try {
			Configuration.uploadTags();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		user = User.getInstance();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblListOfAll = new JLabel("List of all tags");
		lblListOfAll.setFont(new Font("Calibri", Font.BOLD, 24));
		lblListOfAll.setBounds(157, 10, 135, 20);
		contentPane.add(lblListOfAll);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 414, 171);
		contentPane.add(scrollPane);

		// creates table and populate it with list of all tags
		table = TagTable.createTable(Tag.getAllTags());
		scrollPane.setViewportView(table);

		JButton btnAddNewTag = new JButton("Add new tag");
		btnAddNewTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddNewTagFrame addNewTagFrame = new AddNewTagFrame();
				addNewTagFrame.setVisible(true);
			}
		});
		btnAddNewTag.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddNewTag.setBounds(316, 227, 108, 23);
		contentPane.add(btnAddNewTag);

		JButton btnDeleteSelectedTags = new JButton("Delete selected tags");
		btnDeleteSelectedTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					Boolean isChecked = Boolean.valueOf(table.getValueAt(i, 0).toString());

					if (isChecked)
						user.deleteTag(user.getTag(table.getValueAt(i, 1).toString()));
				}

				setVisible(false);
				new AllTagsFrame().setVisible(true);
			}
		});
		btnDeleteSelectedTags.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDeleteSelectedTags.setBounds(10, 228, 152, 23);
		contentPane.add(btnDeleteSelectedTags);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PhotoRenamer().setVisible(true);
			}
		});
		btnBack.setBounds(10, 9, 89, 23);
		contentPane.add(btnBack);
	}
}
