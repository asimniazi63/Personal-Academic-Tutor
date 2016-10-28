import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.plaf.OptionPaneUI;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.management.openmbean.OpenDataException;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Login extends JPanel {
	private JLabel username;
	private JLabel password;
	private JTextField textUsername;
	private JPasswordField textpassword;
	private JButton btnLogin;

	//database variables
	private ResultSet rs = null;
	private JLabel logo;
	Boolean B= false;



	/**
	 * Create the panel.
	 */
	public Login() {
		
		//setToolTipText("Please Enter your username and password");
		setBackground(Color.WHITE);
		setVisible(true);
		setForeground(Color.WHITE);
		setLayout(null);

		username = new JLabel("Username");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setFont(new Font("Tahoma", Font.PLAIN, 18));
		username.setBounds(235, 280, 159, 56);
		add(username);

		password = new JLabel("Password");
		password.setFont(new Font("Tahoma", Font.PLAIN, 18));
		password.setHorizontalAlignment(SwingConstants.CENTER);
		password.setBounds(235, 351, 159, 41);
		add(password);

		textUsername = new JTextField();
		textUsername.setBounds(426, 294, 268, 33);
		add(textUsername);
		textUsername.setColumns(10);

		textpassword = new JPasswordField();
		textpassword.setBounds(426, 357, 268, 33);
		add(textpassword);

		btnLogin = new JButton("Login");
		
		btnLogin.setBounds(509, 417, 97, 25);
		add(btnLogin);
		
		logo = new JLabel("");
		logo.setIcon(new ImageIcon(Login.class.getResource("/images/welcome.png")));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(86, 26, 839, 218);
		add(logo);
		

	}
//	public boolean logSys(){
//		btnLogin.addActionListener(new ActionListener() {
//			@SuppressWarnings("deprecation")
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println(textUsername.getText());
//				System.out.println(textpassword.getPassword());
//				System.out.println("Method executing");
//				if(loginSystem()==true){
//					setVisible(false);
//					B=true;
//					}
//
//			}
//		});
//		if(B==true){
//			return true;
//		}
//		return false;
//	}
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
						JOptionPane.showConfirmDialog(null,"Username or Password incorrect","Message",JOptionPane.OK_CANCEL_OPTION);
						return false;
					}
				}
				else{
					JOptionPane.showConfirmDialog(null,"Username or Password incorrect","Message",JOptionPane.OK_CANCEL_OPTION);
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

}
