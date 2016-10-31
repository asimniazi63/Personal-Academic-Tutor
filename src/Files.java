import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Files extends JPanel {
	JFileChooser fc;
	String temp = "students"; //current state
	JButton openFileChooser;
	JComboBox comboBox;
	JButton btnNewButton;
	JButton btnUpload;
	JLabel lblpath;
	File useFile;
	ArrayList<String> inputList = new ArrayList<String>();
	
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private ResultSetMetaData rss;
	
	String errat = "";

	/**
	 * Create the panel.
	 */
	public Files() {
		repaint();
		setBackground(Color.WHITE);
		setLayout(null);

		openFileChooser = new JButton("    Browse");
		openFileChooser.setIcon(new ImageIcon(Files.class.getResource("/images/1477684375_Open_file.png")));
		openFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc = new JFileChooser();
				fc.setCurrentDirectory(new File("user.home"));
				fc.setDialogTitle("This is a JFileChooser");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (fc.showOpenDialog(openFileChooser) == JFileChooser.APPROVE_OPTION) {
					//JOptionPane.showMessageDialog(null, fc.getSelectedFile().getAbsolutePath());
					lblpath.setText(fc.getSelectedFile().getAbsolutePath());
					useFile = new File(fc.getSelectedFile().getAbsolutePath());
					try {
						Scanner input = new Scanner(useFile);
						int i =0;
						while(input.hasNext()){
							//System.out.println(input.nextLine());
							String s = input.nextLine();
							inputList.add(s);
							i++;
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				int i=0;
				for (String string : inputList) {
					System.out.println(inputList.get(i));
					i++;
				}
			}
		});
		openFileChooser.setBounds(476, 187, 150, 42);
		add(openFileChooser);

		comboBox = new JComboBox();
		comboBox.setBounds(471, 112, 155, 29);
		add(comboBox);
		comboBox.addItem("stdents");
		comboBox.addItem("teachers");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox comb = (JComboBox) e.getSource();
				String s = (String) comb.getSelectedItem();
				if(s=="students"){
					temp = "students";

				}
				else{
					temp = "teachers";

				}
				System.out.println(temp);

			}
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Files.class.getResource("/images/1477683806_file_add.png")));
		lblNewLabel.setBounds(89, 48, 117, 86);
		add(lblNewLabel);

		JLabel lblUpload = new JLabel("Choose List :");
		lblUpload.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUpload.setBounds(297, 119, 117, 19);
		add(lblUpload);

		JLabel lblSelectFile = new JLabel("Select File:");
		lblSelectFile.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectFile.setBounds(297, 192, 110, 29);
		add(lblSelectFile);

		btnUpload = new JButton("Upload Data");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i =0;
				String err = "";
				for(String string : inputList){
					String s = inputList.get(i);
					System.out.println("List is:"+s);
					String str[]= s.split(",");
					System.out.println("i is"+i+"length is "+str.length);
					i++;
					if(str.length==6){
						System.out.println(str[0]+" "+str[1]+" "+str[2]+" "+str[3]+" "+str[4]+" "+str[5]);
						stdData(str[0],str[1],str[2],str[3],str[4],str[5]);
						//i++;
					}
					else{
						err = "error";
					}
					
				}
				if(err=="error"){
					JOptionPane.showMessageDialog(null, "Data not inserted please make sure file is in right format");
				}
//				else if(errat=="error"){
//					JOptionPane.showMessageDialog(null, "Error while uploading!! Please check out the data of the file");
//				}
				else if(errat =="good"){
					JOptionPane.showMessageDialog(null, "Data entered Successfully");
				}
			}
		});
		btnUpload.setIcon(new ImageIcon(Files.class.getResource("/images/1477684630_179_Upload.png")));
		btnUpload.setBounds(476, 266, 150, 42);
		add(btnUpload);

		JLabel lblNote = new JLabel("Note:");
		lblNote.setForeground(Color.RED);
		lblNote.setBackground(Color.WHITE);
		lblNote.setBounds(96, 395, 56, 16);
		add(lblNote);
		
		lblpath = new JLabel("");
		lblpath.setBounds(638, 200, 272, 16);
		add(lblpath);

	}
	public void stdData(String s1,String s2,String s3,String s4,String s5,String s6){
		Database db = new Database();

		int str1 =Integer.parseInt(s1);
		int str4 = Integer.parseInt(s4);
		int rs = 0;
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
			rs = pst.executeUpdate();
//			System.out.println("Result : "+rs);
//			if(rs==1){
//				JOptionPane.showMessageDialog(null, "done");
//				
//			}
//			else{
//					JOptionPane.showMessageDialog(null, "error");
//			}
//			System.out.println("hi");


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//validation
		System.out.println("Result : "+rs);
		if(rs==1){
			errat="good";
			//JOptionPane.showMessageDialog(null, "Data entered Successfully");
			
		}
		else{
				//JOptionPane.showMessageDialog(null, "Error while uploading!! Please check out the data of the file");
			errat="error";
		}
		System.out.println("hi");
		
		try {
			st.close();
			db.closeDatabaseConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
