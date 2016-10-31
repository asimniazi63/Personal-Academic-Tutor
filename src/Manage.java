import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.ListView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Manage extends JPanel {
	private JTable table;
	JComboBox comboBoxPat;
	JPanel panel;
	JScrollPane scrollpaneTable;
	JList list;
	JButton btnAutoAssign;
	private JScrollPane scrollPane;
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private ResultSetMetaData rss;

	ArrayList<String> listPats = new ArrayList<String>();
	ArrayList<String> students;
	ArrayList<Integer> uob = new ArrayList<Integer>();

	String capacity = null;
	int id = 0;
	int idCount = 0;
	private JLabel lblRemaingStudents;
	int deleteState =0;
	JFileChooser fc;
	File useFile;



	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public Manage(){
		setBackground(Color.WHITE);
		setLayout(null);

		comboBoxPat = new JComboBox();
		comboBoxPat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setVisible(true);
				if(comboBoxPat.getSelectedItem()=="Select Teacher"){
					table.setVisible(false);
					JOptionPane.showMessageDialog(null, "Please select a teacher see data and assign a Student");
				}
				else{
					setTableData((String) comboBoxPat.getSelectedItem());
				}
			}
		});
		comboBoxPat.setModel(new DefaultComboBoxModel(new String[] {"Select Teacher"}));
		comboBoxPat.setBounds(210, 29, 157, 34);
		patNames();
		add(comboBoxPat);

		JPanel panel = new JPanel();
		panel.setBounds(22, 104, 635, 355);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(comboBoxPat.getSelectedItem()!="Select Teacher"){
					int i = table.getSelectedRow();
					deleteState = (int) table.getModel().getValueAt(i, 1);
					System.out.println(deleteState);
					
				}
				else{
					JOptionPane.showMessageDialog(null, "please select some teacher and data");
				}
				
			}
		});
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setRowHeight(40);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{"",""}
				},
				new String[] {
						"UOB", "Name", "Year"
				}
				));
		scrollpaneTable = new JScrollPane(table);


		panel.add(scrollpaneTable, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(669, 104, 261, 355);
		add(scrollPane);

		remainingStduents();
		listVal();
		list = new JList(students.toArray());
		//list = new JList();
		scrollPane.setViewportView(list);
		list.setFont(new Font("Tahoma", Font.PLAIN, 16));

		list.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel lblSelectPat = new JLabel("Select PAT: ");
		lblSelectPat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelectPat.setBounds(53, 36, 111, 19);
		add(lblSelectPat);

		btnAutoAssign = new JButton("Auto Assign");
		btnAutoAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxPat.getSelectedItem()!="Select Teacher"){
					getcount(id);
					//getStatus(id);
					convertStudents(students);

					System.out.println("here" + getStatus(id)+" "+"count" + idCount);
					if(getStatus(id).equals("half")){
						for(int i=0;i<4-idCount;i++){
							assign(id,uob.get(i));
						}
						repaint();

					}
					else if(getStatus(id).equals("full")){
						for(int i=0;i<8-idCount;i++){
							assign(id,uob.get(i));
						}
						repaint();

					}
					setTableData((String) comboBoxPat.getSelectedItem());
					//removedata(students);
					remainingStduents();
					for (String string : students) {
						System.out.println("setting " + string);
					}
					DefaultListModel model = new DefaultListModel();
					for (String string : students) {
						model.addElement(string);
					}
					
					list.setModel(model);
				}
				else{
					
					JOptionPane.showMessageDialog(null, "Please select a teacher to see data and assign a Student");
				}
			}


		});
		btnAutoAssign.setBounds(412, 472, 122, 34);
		add(btnAutoAssign);
		
		JButton buttonRemove = new JButton("Remove");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxPat.getSelectedItem()!="Select Teacher"){
					System.out.println(deleteState);
					int i = JOptionPane.showConfirmDialog(null,"Are you Sure" , "Delete", JOptionPane.OK_CANCEL_OPTION);
					if(i==0){
						if(deleteFromPat(deleteState)==true){
							setTableData((String) comboBoxPat.getSelectedItem());
							remainingStduents();
							DefaultListModel model = new DefaultListModel();
							for (String string : students) {
								model.addElement(string);
							}
							list.setModel(model);
							JOptionPane.showMessageDialog(null, "Deleted from list");
						}
					}
					
				}
				else{
					JOptionPane.showMessageDialog(null, "Slect teacher/row to perform action");
				}
			}

		});
		buttonRemove.setBounds(546, 472, 122, 34);
		add(buttonRemove);
		
		lblRemaingStudents = new JLabel("Remaing Students:");
		lblRemaingStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemaingStudents.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblRemaingStudents.setBounds(675, 57, 157, 34);
		add(lblRemaingStudents);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				download();
				if(comboBoxPat.getSelectedItem()!="Select Teacher"){
					fc = new JFileChooser();
					fc.setCurrentDirectory(new File("user.home"));
					fc.setDialogTitle("This is a JFileChooser");
					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					if (fc.showSaveDialog(btnDownload) == JFileChooser.APPROVE_OPTION) {
						//JOptionPane.showMessageDialog(null, fc.getSelectedFile().getAbsolutePath());
						System.out.println(fc.getSelectedFile().getAbsolutePath());
						useFile = new File(fc.getSelectedFile().getAbsolutePath());
						System.out.println(comboBoxPat.getSelectedItem());
						String s = (String) comboBoxPat.getSelectedItem();
						downloadList(useFile);
						
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select a list to download");
				}
			}
		});
		btnDownload.setBounds(42, 472, 122, 34);
		add(btnDownload);

	}
	public void patNames(){
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "Select name from teachers ORDER by name";
		try {
			rs = st.executeQuery(sql);
			while(rs.next()){
				//System.out.println(rs.getString(1));
				//comboBox.addItem(rs.getString("name"));
				listPats.add(rs.getString("name"));

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
		for (String string : listPats) {
			//System.out.println(string + " ");
			comboBoxPat.addItem(string);
		}
	}
	public void remainingStduents(){
		students = new ArrayList<String>();
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "SELECT `name`, `uob` FROM `students` WHERE `uob` not in (SELECT `UOB` FROM `allocations`) ORDER BY `name`";
		try {

			rs = st.executeQuery(sql);
			//int i = 1;
			while(rs.next()){
				//System.out.println(rs.getString("name"));
				String s = rs.getString("name");
				students.add(s);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error is: "+ e);
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error is: "+ e);
		}
	}
	public void listVal(){
		for(int j =0;j<students.size();j++){
			System.out.println("Val :" + students.get(j));
		}
	}
	public void setTableData(String name){
		//		table.setVisible(true);
		System.out.println(name);
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "Select id from teachers where name ="+'"'+name+'"';
		System.out.println(sql);
		st = db.startDatabaseConn();
		try {
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt("id");
				System.out.println(id);
			}

		} catch (SQLException e) {
			System.err.println(e+ "At setTableData");
		}
		//		String sql1 = "SELECT uob FROM `allocations` WHERE `ID` ="+ id;
		String sql1 = "select name,uob,year from students where uob in (SELECT uob FROM `allocations` WHERE `ID` ="+ id+")";
		try {
			rs = st.executeQuery(sql1);
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			//			System.err.println(e+" "+"error at sql1 settabledata");
			e.printStackTrace();
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e+" "+"rs closing: method setTableData");
		}
	}
	public void getcount(int id){
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "SELECT count(`ID`) FROM `allocations` where id ="+id;
		System.out.println(sql);
		st = db.startDatabaseConn();
		try {
			rs = st.executeQuery(sql);
			rs.next();
			idCount = rs.getInt(1);

			System.out.println("Count: "+idCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e+" "+"At get count method");
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e+" "+"closing getcount");
		}



	}
	public String getStatus(int id){
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "SELECT `Load` FROM `teachers` WHERE `id`="+id;
		System.out.println(sql);
		st = db.startDatabaseConn();
		try {
			rs = st.executeQuery(sql);
			rs.next();
			capacity = rs.getString(1);
			System.out.println("Capacity: "+capacity);
			return capacity;

			//System.out.println("Capacity: "+capacity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e+" "+"At get count method");
		}
		try {
			rs.close();
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e+" "+"closing getcount");
		}
		return capacity;
	}
	public void convertStudents(ArrayList<String> students){
		Database db;
		for (String str : students) {
			db = new Database();
			st = db.startDatabaseConn();
			String sql = "SELECT `uob` FROM `students` WHERE `name`="+'"'+str+'"';
			//System.out.println(sql);
			st = db.startDatabaseConn();
			try {
				rs = st.executeQuery(sql);
				while(rs.next()){
					uob.add(rs.getInt(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e+" "+"error at convertstudents");
			}
		}

	}
	private void assign(int id, Integer integer) {
		// TODO Auto-generated method stub
		System.out.println("assign method : "+ id +" "+ integer);
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "INSERT INTO `allocations`(`ID`, `UOB`) VALUES ("+'"'+id+'"'+','+integer+')';
		//			System.out.println(sql);
		st = db.startDatabaseConn();
		try {
			int rs = st.executeUpdate(sql);
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
	private void removedata(ArrayList<String> students) {
		// TODO Auto-generated method stub
		for (int i = students.size()-1; i>=0; i--) {
			students.remove(i);
		}

	}
	private boolean deleteFromPat(int deleteState) {
		
		Database db = new Database();
		st = db.startDatabaseConn();
		String sql = "DELETE FROM `allocations` WHERE `UOB` = "+deleteState;
		System.out.println(sql);
		st = db.startDatabaseConn();
		
		try {
			int rs = st.executeUpdate(sql);
			if (rs==1){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	private void downloadList(File useFile){
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
			Paragraph p2 = new Paragraph("                                                            "+
						"                       "+comboBoxPat.getSelectedItem());

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
