import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Manage extends JPanel {
	private JTable table;
	JList list;
	JPanel panel;
	JPanel panelTable;
	JButton btnAdd;
	JComboBox comboBox;
	JLabel lblPats;
	JComboBox comboBoxPats;
	JLabel lblRemainingStudents;
	JComboBox comboBoxRemaing;
	JComboBox comboBoxYear;
	JLabel lblDept;

	/**
	 * Create the panel.
	 */
	public Manage() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(66, 116, 844, 399);
		add(panel);
		
		panelTable = new JPanel();
		
		list = new JList();
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnAdd = new JButton("Add");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panelTable, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(74)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(42, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(130))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(panelTable, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnAdd)
					.addGap(18))
		);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		));
		panelTable.add(table, BorderLayout.CENTER);
		panel.setLayout(gl_panel);
		
		lblPats = new JLabel("PATs");
		lblPats.setHorizontalAlignment(SwingConstants.CENTER);
		lblPats.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPats.setBounds(109, 61, 75, 26);
		add(lblPats);
		
		comboBoxPats = new JComboBox();
		comboBoxPats.setBounds(201, 64, 121, 22);
		add(comboBoxPats);
		
		lblRemainingStudents = new JLabel("Remaining :");
		lblRemainingStudents.setBounds(552, 67, 80, 16);
		add(lblRemainingStudents);
		
		comboBoxRemaing = new JComboBox();
		comboBoxRemaing.setBounds(632, 64, 97, 22);
		add(comboBoxRemaing);
		
		comboBoxYear = new JComboBox();
		comboBoxYear.setBounds(825, 64, 85, 22);
		add(comboBoxYear);
		
		lblDept = new JLabel("Dept.");
		lblDept.setBounds(757, 67, 56, 16);
		add(lblDept);

	}
}
