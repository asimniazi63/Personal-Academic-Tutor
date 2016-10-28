import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class Details extends JPanel {
	//database variables
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private ResultSetMetaData rss;
	JLabel lblNewLabel;
	JLabel lblHome;
	JLabel lblShowList;
	JComboBox comboBox;
	JPanel panel;
	JButton btnADD;
	JPanel Dialog_Add;
	JButton btnEdit;
	JButton btnDelete;
	private JTable table;
	DefaultTableModel model;
	Object[] columns = {"UOB","Name","Email","Year","Department","Address"};

	String s1,s2,s3,s4,s5,s6;
	String s2E,s3E,s5E,s6E;
	int s1E;
	int s4E;
	String s4contact;
	String temp = "Students"; //for viewing current state

	JScrollPane scrollpane;

	/**
	 * Create the panel.
	 */
	public Details() {
		//setVisible(false);
		setBackground(Color.WHITE);
		setLayout(null);

		lblNewLabel = new JLabel("Student/PAT Details");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel.setBounds(172, 13, 673, 38);
		add(lblNewLabel);


		lblShowList = new JLabel("Show List: ");
		lblShowList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblShowList.setBounds(172, 75, 83, 16);
		add(lblShowList);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox comb = (JComboBox) e.getSource();
				String s = (String) comb.getSelectedItem();
				if(s=="Students"){
					table.setVisible(false);
					temp="Students";
					stdDetails();

				}
				else{
					temp="PATs";
					table.setVisible(false);
					patDetails();

				}

			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Students","PATs"}));
		comboBox.setBounds(286, 73, 149, 22);
		add(comboBox);

		//Panel for showing tabular data
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(30, 111, 950, 379);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableModel model = new DefaultTableModel();

		// set the model to the table
		table.setModel(model);
		stdDetails();
		scrollpane = new JScrollPane(table);
		panel.add(scrollpane, BorderLayout.CENTER);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){

				// i = the index of the selected row
				int i = table.getSelectedRow();
				int count = table.getColumnCount();

				s1E = (int) table.getModel().getValueAt(i, 0);
				s2E = (String) table.getModel().getValueAt(i, 1);
				s3E = (String) table.getModel().getValueAt(i, 2);
				s4E = (int) table.getModel().getValueAt(i, 3);
				s5E = (String) table.getModel().getValueAt(i, 4);
				s6E = (String) table.getModel().getValueAt(i, 5);
				System.out.println(s1E+s2E+s3E+s4E+s5E+s6E);



				//	            for(int j=0;j<count;j++){
				//	            System.out.println(table.getModel().getValueAt(i, j));
				//	            }
				//	            System.out.println(count);

			}
		});

		btnADD = new JButton("Add");
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(temp=="Students"){
					JTextField jf1 = new JTextField();
					JTextField jf2 = new JTextField();
					JTextField jf3 = new JTextField();
					JTextField jf4 = new JTextField();
					JTextField jf5 = new JTextField();
					JTextField jf6 = new JTextField();
					JComboBox c = new JComboBox();
					c.addItem("Computer Science");
					c.addItem("Electrical Engineering");
					JComboBox d = new JComboBox();
					d.addItem("1");
					d.addItem("2");
					d.addItem("3");
					d.addItem("4");
					Object[] message = {
							"Uob",jf1,
							"Name", jf2,
							"Email",jf3,
							"Year",d,
							"Department",c,
							"Address",jf6


					};
					int option = JOptionPane.showConfirmDialog(Dialog_Add,message,"Enter info",JOptionPane.OK_CANCEL_OPTION);
					System.out.println(option);
					s1=jf1.getText();
					s2=jf2.getText();
					s3=jf3.getText();
					s4=(String) d.getSelectedItem();
					s5=(String) c.getSelectedItem();
					s6=jf6.getText();
					stdAdd();
					stdDetails();
				}
				else{
					JTextField jf1 = new JTextField();
					JTextField jf2 = new JTextField();
					JTextField jf3 = new JTextField();
					JTextField jf4 = new JTextField();
					JTextField jf5 = new JTextField();
					JTextField jf6 = new JTextField();
					JComboBox c = new JComboBox();
					c.addItem("Computer Science");
					c.addItem("Electrical Engineering");
					JComboBox d = new JComboBox();
					d.addItem("Full");
					d.addItem("Half");
					Object[] message = {
							"ID",jf1,
							"Name", jf2,
							"Email",jf3,
							"Contact",jf4,
							"Department",c,
							"Load",d


					};
					int option = JOptionPane.showConfirmDialog(Dialog_Add,message,"Enter info",JOptionPane.OK_CANCEL_OPTION);
					System.out.println(option);
					s1=jf1.getText();
					s2=jf2.getText();
					s3=jf3.getText();
					s4=jf4.getText();
					s5=(String) c.getSelectedItem();
					if(d.getSelectedItem()=="Full"){
						s6="f";
					}
					else{
						s6 = "h";
					}
					
					patAdd();
					patDetails();

				}
			}
		});
		btnADD.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnADD.setBounds(82, 503, 108, 38);
		add(btnADD);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(temp=="Students"){
					JTextField jf1 = new JTextField();
					JTextField jf2 = new JTextField();
					JTextField jf3 = new JTextField();
					JTextField jf4 = new JTextField();
					JTextField jf5 = new JTextField();
					JTextField jf6 = new JTextField();

					jf1.setText(""+s1E);
					jf2.setText(s2E);
					jf3.setText(s3E);
					jf4.setText(""+s4E);
					jf5.setText(s5E);
					jf6.setText(s6E);
					Object[] message = {
							"Uob",jf1,
							"Name", jf2,
							"Email",jf3,
							"Year",jf4,
							"Department",jf5,
							"Address",jf6


					};

					int option = JOptionPane.showConfirmDialog(Dialog_Add,message,"Enter info",JOptionPane.OK_CANCEL_OPTION);
					System.out.println(option);
				}
				else{
					//for pats
				}


			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnEdit.setBounds(706, 503, 97, 38);
		add(btnEdit);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(temp=="Students"){
					System.out.println(s1E);
					if(s1E!=0){
						//if uob is not zero
						Database db = new Database();
						Statement st = db.startDatabaseConn();
						String sql = "delete from students where uob = "+s1E;
						int i = JOptionPane.showConfirmDialog(null,"The Selected Data will be deleted, are you really "
								+ "sure ? ","Are you sure ?", JOptionPane.YES_NO_OPTION);
						if (i==0){
							System.out.println(0);
							try {
								int rs = st.executeUpdate(sql);
								stdDetails();


							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								rs.close();
								st.close();
								db.closeDatabaseConn();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if(i==1){
							s1E=0;
							try {
								st.close();
								db.closeDatabaseConn();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					else{
						JOptionPane.showConfirmDialog(null, "Please select a row to delete", "Warning",JOptionPane.CLOSED_OPTION);
					}
				}
				else{
					//for pats
					System.out.println(s1E);
					if(s1E!=0){
						//if id is not zero
						Database db = new Database();
						Statement st = db.startDatabaseConn();
						String sql = "delete from teachers where id = "+s1E;
						int i = JOptionPane.showConfirmDialog(null,s1E+" The Selected Data will be deleted, are you really "
								+ "sure ? ","Are you sure ?", JOptionPane.YES_NO_OPTION);
						if (i==0){
							System.out.println(0);
							try {
								int rs = st.executeUpdate(sql);
								patDetails();


							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								rs.close();
								st.close();
								db.closeDatabaseConn();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						if(i==1){
							s1E=0;
							try {
								st.close();
								db.closeDatabaseConn();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					else{
						JOptionPane.showConfirmDialog(null, "Please select a row to delete", "Warning",JOptionPane.CLOSED_OPTION);
					}
				}

			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(820, 503, 97, 38);
		add(btnDelete);


	}
	//fetching Students Data
	public void stdDetails(){
		table.setVisible(true);
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "Select * from students ORDER by name";
		try {
			rs = st.executeQuery(sql);
			rss = rs.getMetaData();
			int count = rss.getColumnCount();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			panel.add(table.getTableHeader(),BorderLayout.NORTH);
			table.setRowHeight(50);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//fetching Students Data
	public void stdAdd(){
		Database db = new Database();

		int str1 =Integer.parseInt(s1);
		int str4 = Integer.parseInt(s4);
		
		String sql = "insert into students "+ " VALUES(?, ?, ?, ?, ?, ?)";
		try {
			st = (Statement) db.startDatabaseConn2().createStatement();
			PreparedStatement pst = (PreparedStatement) db.startDatabaseConn2().prepareStatement(sql);
			pst.setInt(1, str1);
			pst.setString(2, s2);
			pst.setString(3, s3);
			pst.setInt(4, str4);
			pst.setString(5, s5);
			pst.setString(6, s6);
			pst.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void stdEdit(){
		Database db = new Database();

		int s =Integer.parseInt(s1);
		int str4 = Integer.parseInt(s4);
		String sql = "insert into students "+ " VALUES(?, ?, ?, ?, ?, ?)";
		try {
			st = (Statement) db.startDatabaseConn2().createStatement();
			PreparedStatement pst = (PreparedStatement) db.startDatabaseConn2().prepareStatement(sql);
			//pst.setInt(1, str1);
			pst.setString(2, s2);
			pst.setString(3, s3);
			pst.setInt(4, str4);
			pst.setString(5, s5);
			pst.setString(6, s6);
			pst.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void patDetails(){
		table.setVisible(true);
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "Select * from teachers ORDER by name";
		try {
			rs = st.executeQuery(sql);
			rss = rs.getMetaData();
			int count = rss.getColumnCount();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			panel.add(table.getTableHeader(),BorderLayout.NORTH);
			table.setRowHeight(50);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void patAdd(){
		Database db = new Database();

		int str1 =Integer.parseInt(s1);
		//int str4 = Integer.parseInt(s4);
		String sql = "insert into teachers "+ " VALUES(?, ?, ?, ?, ?, ?)";
		try {
			st = (Statement) db.startDatabaseConn2().createStatement();
			PreparedStatement pst = (PreparedStatement) db.startDatabaseConn2().prepareStatement(sql);
			pst.setInt(1, str1);
			pst.setString(2, s2);
			pst.setString(3, s3);
			pst.setString(4, s4);
			pst.setString(5, s5);
			pst.setString(6, s6);
			pst.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
