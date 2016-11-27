package photo_renamer;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import backend.Tag;

public class TagTable {
	/**
	 * Populates table with given list of tags.
	 * 
	 * @param tags
	 *            list of tags to display in the table
	 * @return JTable with list of tags.
	 */
	public static JTable createTable(List<Tag> tags) {
		String col[] = { "", "Tag name", "Usage Number" };

		DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
			@Override
			public Class getColumnClass(int column) {
				// converts first column to checkboxes.
				return column == 0 ? Boolean.class : String.class;
			}
		};

		// adds row dynamically according to list of tags.
		for (int i = 0; i < tags.size(); i++) {
			String name = tags.get(i).getName();
			int numOfUsed = tags.get(i).getNumUsed();
			Object[] data = { false, name, numOfUsed };
			tableModel.addRow(data);
		}

		JTable table = new JTable(tableModel);
		return table;
	}

}
