package photo_renamer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class DeleteTagFromImage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private User user;
	private ImageFile selectedImage;

	/**
	 * Create the frame.
	 */
	public DeleteTagFromImage(ImageFile image) {
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

		JButton btnAddNewTag = new JButton("Delete tag from image");
		btnAddNewTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					Boolean isChecked = Boolean.valueOf(table.getValueAt(i, 0).toString());

					if (isChecked)
						user.deleteTagFromImage(selectedImage, user.getTag(table.getValueAt(i, 1).toString()));

				}
				setVisible(false);
				new PhotoRenamer().setVisible(true);
			}
		});
		btnAddNewTag.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddNewTag.setBounds(229, 227, 195, 23);
		contentPane.add(btnAddNewTag);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PhotoRenamer().setVisible(true);
			}
		});
		btnBack.setBounds(10, 227, 89, 23);
		contentPane.add(btnBack);
	}

	private JTable createTable() {
		String col[] = { "", "Tag name", "Usage Number" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
			@Override
			public Class getColumnClass(int column) {
				return column == 0 ? Boolean.class : String.class;
			}
		};

		for (int i = 0; i < selectedImage.getTags().size(); i++) {
			String name = selectedImage.getTags().get(i).getName();
			int numOfUsed = selectedImage.getTags().get(i).getNumUsed();
			Object[] data = { false, name, numOfUsed };
			tableModel.addRow(data);
		}

		JTable table = new JTable(tableModel);
		return table;
	}

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// DeleteTagFromImage frame = new DeleteTagFromImage();
	// frame.setVisible(true);
	// }
}
