import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class search extends JPanel {
	private JTextField textField;
	private JTable table;
	JScrollPane scroll;
	JButton btnGo;
	JComboBox comboBoxFilter;
	JComboBox comboBoxStdTeacher;

	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;



	String key;
	String current = "students";
	String keyWord ;

	/**
	 * Create the panel.
	 */
	public search() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSearch.setBounds(36, 31, 87, 22);
		add(lblSearch);

		textField = new JTextField();
		textField.setBounds(449, 23, 198, 42);
		add(textField);
		textField.setColumns(10);

		btnGo = new JButton("Go");

		btnGo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGo.setBounds(683, 25, 66, 34);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("search "+comboBoxStdTeacher.getSelectedItem()+" "+comboBoxFilter.getSelectedItem());
				if((comboBoxFilter.getSelectedItem()!="Select") && (comboBoxStdTeacher.getSelectedItem()!="Select")){
					System.out.println(1);
					if(comboBoxStdTeacher.getSelectedItem().equals("Student")){
						String t = textField.getText();
						if(comboBoxFilter.getSelectedItem().equals("UOB")){

							String sql = "SELECT * FROM students WHERE uob like '%"+t+"%'"+"ORDER BY `students`.`year` ASC";
							System.out.println(sql+" "+textField);
							searchQuery(sql);
						}
						else{
							String sql = "SELECT * FROM students WHERE name like '%"+t+"%'"+"ORDER BY `students`.`year` ASC";
							System.out.println(sql+" "+textField);
							searchQuery(sql);
						}

					}
					else{
						String t = textField.getText();
						if(comboBoxFilter.getSelectedItem().equals("ID")){

							String sql = "SELECT * FROM teachers WHERE id like '%"+t+"%'"+"ORDER BY `teachers`.`id` ASC";
							System.out.println(sql+" "+textField);
							searchQuery(sql);
						}
						else{
							String sql = "SELECT * FROM teachers WHERE name like '%"+t+"%'"+"ORDER BY `teachers`.`name` ASC";
							System.out.println(sql+" "+textField);
							searchQuery(sql);
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please select one");
				}
			}

		});
		add(btnGo);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(36, 96, 886, 376);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setBackground(Color.WHITE);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(50);
		table.setModel(new DefaultTableModel(
				new Object[][] {


				},
				new String[] {

				}
				));
		scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);

		comboBoxFilter = new JComboBox();
		//comboBoxFilter.setModel(new DefaultComboBoxModel(new String[] {"UOB", "Name"}));
		comboBoxFilter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBoxFilter.setBounds(324, 23, 113, 42);
		add(comboBoxFilter);

		comboBoxStdTeacher = new JComboBox();
		comboBoxStdTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxStdTeacher.getSelectedItem().equals("Student")){
					current = "students";
					comboBoxFilter.setModel(new DefaultComboBoxModel(new String[] {"Select","UOB", "Name"}));
					//					if(comboBoxFilter.getSelectedItem().equals("UOB")){
					//						key = "uob";
					//						
					//
					//					}
					//					else if(comboBoxFilter.getSelectedItem().equals("Name")){
					//						key = "name";
					//
					//					}
					//					else{
					//						JOptionPane.showMessageDialog(null, "Please select one");
					//					}


				}
				else if(comboBoxStdTeacher.getSelectedItem().equals("Teacher")){
					current = "teachers";
					comboBoxFilter.setModel(new DefaultComboBoxModel(new String[] {"Select","ID", "Name"}));
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select one");
				}

			}
		});
		comboBoxStdTeacher.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxStdTeacher.setModel(new DefaultComboBoxModel(new String[] {"Select","Student","Teacher"}));
		comboBoxStdTeacher.setBounds(135, 22, 87, 42);
		add(comboBoxStdTeacher);

		JLabel lblFilter = new JLabel("Filter by");
		lblFilter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilter.setBounds(234, 23, 78, 42);
		add(lblFilter);

	}
	private void searchQuery(String sql) {
		// TODO Auto-generated method stub
		Database db = new Database();
		st = db.startDatabaseConn();
		st = db.startDatabaseConn();
		try {
			rs = st.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
