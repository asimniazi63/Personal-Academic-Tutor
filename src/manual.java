

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;

public class manual extends JPanel {

	/**
	 * Create the panel.
	 */
	public manual() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblheading = new JLabel("User Manual");
		lblheading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblheading.setHorizontalAlignment(SwingConstants.CENTER);
		lblheading.setBounds(340, 13, 201, 38);
		add(lblheading);
		
		JTextArea txtrMainMenudetails = new JTextArea();
		txtrMainMenudetails.setFont(new Font("Monospaced", Font.PLAIN, 17));
		txtrMainMenudetails.setText("Main Menu\r\n1.Details\r\n2.Manage\r\n3.Search");
		txtrMainMenudetails.setEditable(false);
		txtrMainMenudetails.setBounds(27, 65, 793, 395);
		add(txtrMainMenudetails);

	}
}
