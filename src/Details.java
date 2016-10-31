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
import java.util.Locale;

import javax.swing.SwingConstants;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.print.DocFlavor.STRING;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	int s4E;
	String s4Et;//teachers
	int s1E;
	String s4contact;
	String temp = "Students"; //for viewing current state

	JScrollPane scrollpane;
	private JComboBox comboBoxYear;

	JFileChooser fc;
	File useFile;

	/**
	 * Create the panel.
	 */
	public Details() {
		//setVisible(false);
		repaint();
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
					comboBoxYear.setVisible(true);
					temp="Students";
					stdDetails();

				}
				else{
					comboBoxYear.setVisible(false);
					temp="PATs";
					table.setVisible(false);
					patDetails();

				}

			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Students","PATs"}));
		comboBox.setBounds(276, 73, 149, 22);
		add(comboBox);
		comboBoxYear = new JComboBox();
		comboBoxYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox comb = (JComboBox) e.getSource();
				String s = (String) comb.getSelectedItem();
				System.out.println(s);
				if(s!="Select by Year"){
				sortByYear(s);
				}
				else{
					JOptionPane.showMessageDialog(null, "Select any year to show data");
				}

			}
		});
		comboBoxYear.setBounds(486, 73, 149, 22);
		if(temp =="Students"){
		comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {"Select by Year","1","2","3","4"}));
		}
		add(comboBoxYear);


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
				if(temp=="Students"){
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
				}
				else{
					int i = table.getSelectedRow();
					int count = table.getColumnCount();

					s1E = (int) table.getModel().getValueAt(i, 0);
					s2E = (String) table.getModel().getValueAt(i, 1);
					s3E = (String) table.getModel().getValueAt(i, 2);
					s4Et = (String) table.getModel().getValueAt(i, 3);
					s5E = (String) table.getModel().getValueAt(i, 4);
					s6E = (String) table.getModel().getValueAt(i, 5);
					System.out.println(s1E+s2E+s3E+s4Et+s5E+s6E);
				}
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
					if(option==0){
						stdAdd();
						stdDetails();
					}
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
						s6="full";
					}
					else{
						s6 = "half";
					}

					if(option==0){
						patAdd();
						patDetails();
					}

				}
			}
		});
		btnADD.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnADD.setBounds(580, 503, 108, 38);
		add(btnADD);

		//download list
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				fc.setCurrentDirectory(new File("user.home"));
				fc.setDialogTitle("This is a JFileChooser");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fc.showSaveDialog(btnDownload) == JFileChooser.APPROVE_OPTION) {
					//JOptionPane.showMessageDialog(null, fc.getSelectedFile().getAbsolutePath());
					System.out.println(fc.getSelectedFile().getAbsolutePath());
					useFile = new File(fc.getSelectedFile().getAbsolutePath());
					if(comboBox.getSelectedItem().equals("Students")){
						downloadStudents(useFile);
						System.out.println("hi students");
					}
					else if(comboBox.getSelectedItem().equals("PATs")){
						downloadStudents(useFile);
						System.out.println("hi teachers");
					}
					else{
						JOptionPane.showMessageDialog(null, "No list selected");
					}

				}
				else if(fc.showSaveDialog(btnDownload) == JFileChooser.CANCEL_OPTION){
					System.out.println("canceled");
				}
			}
		});
		btnDownload.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDownload.setBounds(83, 503, 130, 38);
		add(btnDownload);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(s1E!=0){
					if(temp=="Students"){
						JTextField jf1 = new JTextField();
						JTextField jf2 = new JTextField();
						JTextField jf3 = new JTextField();
						JTextField jf4 = new JTextField();
						JTextField jf5 = new JTextField();
						JTextField jf6 = new JTextField();


						jf1.setText(""+s1E);
						jf1.setEditable(false);
						jf2.setText(s2E);
						jf3.setText(s3E);
						jf3.setEditable(false);
						jf4.setText(""+s4E);
						jf5.setText(s5E);
						jf6.setText(s6E);
						jf4.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) {
								char c = e.getKeyChar();
								if(((c >= e.VK_1) && (c <= e.VK_4) || (c >= e.VK_NUMPAD1) && (c <= e.VK_NUMPAD4) ||
										(c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))){
									jf4.setEditable(true);
									jf4.setBackground(Color.white);
								}
								else{
									jf4.setEditable(false);
									jf4.setBackground(Color.red);

								}
							}
						});

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
						String s1 = jf1.getText();
						String s2 = jf2.getText();
						String s3 = jf3.getText();
						String s4 = jf4.getText();
						String s5 = jf5.getText();
						String s6 = jf6.getText();
						if(option==0){
							stdEdit(s1,s2,s3,s4,s5,s6);
							stdDetails();
						}
					}
					else{
						//for pats
						JTextField jf1 = new JTextField();
						JTextField jf2 = new JTextField();
						JTextField jf3 = new JTextField();
						JTextField jf4 = new JTextField();
						JTextField jf5 = new JTextField();
						JTextField jf6 = new JTextField();


						jf1.setText(""+s1E);
						jf1.setEditable(false);
						jf2.setText(s2E);
						jf3.setText(s3E);
						jf3.setEditable(false);
						jf4.setText(""+s4Et);
						jf5.setText(s5E);
						//jf6.setText(s6E);
						jf4.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) {
								char c = e.getKeyChar();
								if(((c >= e.VK_1) && (c <= e.VK_4) || (c >= e.VK_NUMPAD1) && (c <= e.VK_NUMPAD4) ||
										(c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))){
									jf4.setEditable(true);
									jf4.setBackground(Color.white);
								}
								else{
									jf4.setEditable(false);
									jf4.setBackground(Color.red);

								}
							}
						});
						JComboBox ad = new JComboBox();
						if(s6E.equals("half")){
							ad.addItem("half");
							ad.addItem("full");
						}
						else if(s6E.equals("full")){
							ad.addItem("full");
							System.out.println("again"+s6E);
						}

						Object[] message = {
								"Uob",jf1,
								"Name", jf2,
								"Email",jf3,
								"Year",jf4,
								"Department",jf5,
								"Address",ad


						};




						int option = JOptionPane.showConfirmDialog(Dialog_Add,message,"Enter info",JOptionPane.OK_CANCEL_OPTION);
						System.out.println(option);
						String s1 = jf1.getText();
						String s2 = jf2.getText();
						String s3 = jf3.getText();
						String s4 = jf4.getText();
						String s5 = jf5.getText();
						String s6 = (String) ad.getSelectedItem();

						if(option==0){
							patEdit(s1, s2, s3, s4, s5, s6);
							patDetails();
						}

					}


				}
				else{
					JOptionPane.showMessageDialog(null, "select a row");
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
						JOptionPane.showMessageDialog(null, "Please select a row to delete");
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
	public void sortByYear(String s){
		table.setVisible(true);
		Database db = new Database();
		st = db.startDatabaseConn();
		int i = Integer.parseInt(s);
		String sql = "Select * from students where year="+i+" ORDER by name";
		System.out.println(sql);
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
			int rs  = pst.executeUpdate();
			System.out.println(rs);
			if(rs!=1){
				JOptionPane.showMessageDialog(null, "Error while adding");
			}


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
	public void stdEdit(String s11, String s22, String s33, String s44, String s55, String s66){
		Database db = new Database();
		System.out.println(s11+s22+s33+s44+s55+s66);
		int s =Integer.parseInt(s11);
		int str4 = Integer.parseInt(s44);
		String sql = "UPDATE `students` SET `uob` = ?,`name`=?,`email`=?,`year`=?,`department`=?,`address`=? where uob ="+s;
		System.out.println(sql);
		try {
			st = (Statement) db.startDatabaseConn2().createStatement();
			PreparedStatement pst = (PreparedStatement) db.startDatabaseConn2().prepareStatement(sql);
			pst.setInt(1, s);
			pst.setString(2, s22);
			pst.setString(3, s33);
			pst.setInt(4, str4);
			pst.setString(5, s55);
			pst.setString(6, s66);
			int rs = pst.executeUpdate();
			System.out.println(rs);


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
	public void patEdit(String s11, String s22, String s33, String s44, String s55, String s66){
		Database db = new Database();
		System.out.println(s11+s22+s33+s44+s55+s66);
		int s =Integer.parseInt(s11);
		//int str4 = Integer.parseInt(s44);
		String sql = "UPDATE `teachers` SET `id` = ?,`name`=?,`email`=?,`contact`=?,`department`=?,`Load`=? where id ="+s;
		System.out.println(sql);
		try {
			st = (Statement) db.startDatabaseConn2().createStatement();
			PreparedStatement pst = (PreparedStatement) db.startDatabaseConn2().prepareStatement(sql);
			pst.setInt(1, s);
			pst.setString(2, s22);
			pst.setString(3, s33);
			pst.setString(4, s44);
			pst.setString(5, s55);
			pst.setString(6, s66);
			int rs = pst.executeUpdate();
			System.out.println(rs);


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
	private void downloadStudents(File useFile){
		Document document = new Document(PageSize.A4, 0, 0, 0, 0);
		try {
			PdfWriter.getInstance(document,new FileOutputStream(useFile));
			document.open();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PdfPTable ptable = new PdfPTable(table.getColumnCount());
		ptable.setWidthPercentage(90);
		//Jtable into pdf table
		for (int rows = 0; rows < table.getRowCount(); rows++) {
			for (int cols = 0; cols < table.getColumnCount(); cols++) {
				ptable.addCell(table.getModel().getValueAt(rows, cols).toString());

			}
		}
		try {
			//ptable.setHeaderRows(0);
			Paragraph p2;
			if(temp=="Student"){
				p2 = new Paragraph("                                                            "+
						"                       "+"Student Details");
			}
			else {
				p2 = new Paragraph("                                                            "+
						"                       "+"Teacher Details");
			}

			Paragraph p1 = new Paragraph(" ");
			Paragraph p3 = new Paragraph(" ");
			Paragraph p4 = new Paragraph(" ");
			document.add(p1);
			document.add(p2);
			document.add(p3);
			document.add(p4);
			document.add(ptable);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
		System.out.println("done");

	}
}
