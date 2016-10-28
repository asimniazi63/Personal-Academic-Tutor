import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mysql.jdbc.Statement;



public class Main {
	JPanel panel;
	JTabbedPane tabbedPane;
	
	//<-Login variables
	JPanel panelLogin;
	private JLabel username;
	private JLabel password;
	private JTextField textUsername;
	private JPasswordField textpassword;
	private JButton btnLogin;

	//database variables
	private ResultSet rs = null;
	private JLabel logo;
	//login variable end ->

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				
				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					
					Main window = new Main();
					window.frame.setVisible(true);
					window.frame.setTitle("Personal Academic Advisor Assining System");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setForeground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 1197, 651);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		
		panelLogin = new JPanel();
		frame.getContentPane().add(panelLogin, BorderLayout.CENTER);
		createLoginGUI();
		panelLogin.setLayout(null);
		

	}
	public void createLoginGUI(){
		panelLogin.setBackground(Color.WHITE);
		panelLogin.setVisible(true);
		panelLogin.setForeground(Color.WHITE);
		panelLogin.setLayout(null);

		username = new JLabel("Username");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setFont(new Font("Tahoma", Font.PLAIN, 18));
		username.setBounds(298, 282, 159, 56);
		panelLogin.add(username);

		password = new JLabel("Password");
		password.setFont(new Font("Tahoma", Font.PLAIN, 18));
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setBounds(298, 351, 159, 41);
		panelLogin.add(password);

		textUsername = new JTextField();
		textUsername.setBounds(539, 296, 268, 33);
		panelLogin.add(textUsername);
		textUsername.setColumns(10);

		textpassword = new JPasswordField();
		textpassword.setBounds(539, 357, 268, 33);
		panelLogin.add(textpassword);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(textUsername.getText());
				System.out.println(textpassword.getPassword());
				System.out.println("Method executing");
				if(loginSystem()==true){
						panelLogin.setVisible(false);
						panel = new JPanel();
						panel.setVisible(true);
						frame.getContentPane().add(panel, BorderLayout.CENTER);
						panel.setLayout(new BorderLayout(0,0));
						tabs();
				}
				else{
					JOptionPane.showConfirmDialog(null,"Username or Password incorrect","Message",JOptionPane.OK_CANCEL_OPTION);
					textUsername.setText("");
					textpassword.setText("");
				}


			}
		});
		
		btnLogin.setBounds(618, 418, 97, 25);
		panelLogin.add(btnLogin);
		
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(Login.class.getResource("/images/welcome.png")));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(202, 25, 839, 218);
		panelLogin.add(logo);
		
	}
	public boolean loginSystem(){
		Database db = new Database();
		Statement st = db.startDatabaseConn();
		String user = textUsername.getText();
		char[] pass = textpassword.getPassword();
		String p = String.copyValueOf(pass);

		//String loginQuery = "select * from authentication where username =  "+user+" AND password = "+p;
		String loginQuery = "select * from authentication";
		try {
			rs = st.executeQuery(loginQuery);
			while (rs.next()){
				if(rs.getString(1).equals(user)){
					if(rs.getString(2).equals(p)){
						System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+user+" "+p);
						return true;
					}
					else{
						//JOptionPane.showConfirmDialog(null,"Username or Password incorrect","Message",JOptionPane.OK_CANCEL_OPTION);
						return false;
					}
				}
				else{
					//JOptionPane.showConfirmDialog(null,"Username or Password incorrect","Message",JOptionPane.OK_CANCEL_OPTION);
					return false;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error at rs in login class");
		}

		try {
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error at closing");
		}

		db.closeDatabaseConn();
		return false;

	}
	public void tabs(){
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		//tabbedPane.setToolTipText("hi");
		panel.add(tabbedPane, BorderLayout.CENTER);
		
		
		
		Icon a = new ImageIcon(Main.class.getResource("/images/1477598743_List.png"));
		Details details = new Details();
		details.setBorder(null);
		tabbedPane.addTab("",a,details);
		
		Icon b = new ImageIcon(Main.class.getResource("/images/1477601471_Session Manager.png"));
		tabbedPane.addTab("",b,new Manage());
		
		Icon c = new ImageIcon(Main.class.getResource("/images/search-icon.png"));
		tabbedPane.addTab("", c, null);
		tabbedPane.setBackground(Color.WHITE);
		
		Icon d = new ImageIcon(Main.class.getResource("/images/1477602060_BT_binder.png"));
		tabbedPane.addTab("", d, null);
		tabbedPane.setBackground(Color.WHITE);
		
		Icon e = new ImageIcon(Main.class.getResource("/images/1477601863_Help.png"));
		tabbedPane.addTab("", e, null);
		tabbedPane.setBackground(Color.WHITE);
	}

}
