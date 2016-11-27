package photo_renamer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import backend.Configuration;
import backend.ImageFile;
import backend.Tag;
import backend.User;

public class TagImage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private User user;
	private ImageFile selectedImage;

	/**
	 * Create the frame.
	 */
	public TagImage(ImageFile image) {
		selectedImage = image;

		if (!ImageFile.getAllImageFiles().isEmpty()) {
			for (ImageFile f : ImageFile.getAllImageFiles()) {
				if (f.getName().equals(image.getName()))
					selectedImage = f;
			}
		}

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

		table = createTable();
		scrollPane.setViewportView(table);

		JButton btnAddNewTag = new JButton("Add tag to image");
		btnAddNewTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Tag> selectedTags = new ArrayList<Tag>();
				for (int i = 0; i < table.getRowCount(); i++) {
					Boolean isChecked = Boolean.valueOf(table.getValueAt(i, 0).toString());

					if (isChecked)
						selectedTags.add(user.getTag(table.getValueAt(i, 1).toString()));

				}
				user.selectTag(selectedImage, selectedTags);
				setVisible(false);
				new PhotoRenamer().setVisible(true);
			}
		});
		btnAddNewTag.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddNewTag.setBounds(272, 227, 152, 23);
		contentPane.add(btnAddNewTag);
	}

	private JTable createTable() {
		String col[] = { "", "Tag name", "Usage Number" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
			@Override
			public Class getColumnClass(int column) {
				return column == 0 ? Boolean.class : String.class;
			}
		};

		for (int i = 0; i < Tag.getAllTags().size(); i++) {
			String name = Tag.getAllTags().get(i).getName();
			int numOfUsed = Tag.getAllTags().get(i).getNumUsed();
			Object[] data = { false, name, numOfUsed };
			tableModel.addRow(data);
		}

		JTable table = new JTable(tableModel);
		return table;
	}

}
