package trial.swing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


class User {
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private String mobNum;

	// getter and setter for first name
	public void setFirstName(String obtainFirstName) {
		this.firstName = obtainFirstName;
	}

	public String getFirstName() {
		return firstName;
	}

	// getter and setter for last name
	public void setLastName(String obtainLastName) {
		this.lastName = obtainLastName;
	}

	public String getLastName() {
		return lastName;
	}

	// getter and setter for email

	public void setEmail(String obtainEmail) {
		this.email = obtainEmail;
	}

	public String getEmail() {
		return email;
	}

	// getter and setter for username
	public void setUsername(String obtainUsername) {
		this.userName = obtainUsername;
	}

	public String getUsername() {
		return userName;
	}

	// getter and setter for password
	public void setPassword(String obtainPassword) {

		this.password = obtainPassword;
	}

	public String getPassword() {
		return password;
	}

	// getter and setter for mobile number
	public void setMobNum(String obtainMobNum) {
		this.mobNum = obtainMobNum;
	}

	public String getMobNum() {
		return mobNum;
	}
}

public class UserRegistration extends JFrame {
	private String firstName;
	private String first_name;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstname;
	private JTextField lastname;
	private JTextField email;
	private JTextField username;
	private JTextField mob;
	private JPasswordField passwordField;
	private JButton registerButton;
	private JButton loginButton2;
	private JButton btnNewButton3;
	boolean verify1 = false;
	boolean verify2 = false;
	boolean verify3 = false;
	private JLabel label;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegistration frame = new UserRegistration();
					frame.setVisible(true);
					WebLookAndFeel.install(true);
					WebLookAndFeel.updateAllComponentUIs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * check that password meets requirements. My client requested that passwords
	 * should be secure enough, hence I ensured that all passwords contain an upper
	 * case letter, a lower case letter, and a number, and were between 8-16
	 * characters in length
	 */
	public static boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=\\S+$).{8,16}$";
		Pattern p = Pattern.compile(regex);
		if (password == null) {
			return false;
		}
		Matcher matcher = p.matcher(password);
		return matcher.matches();
	}

	// check that the user provided a valid email-id.
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern emailPattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher2 = emailPattern.matcher(email);
		return matcher2.matches();

	}

	/*
	 * check that the user provided a valid mobile number. The below code checks if
	 * the mobile number is 10 digits in length or not. This is the standard length
	 * of an Indian mobile number.
	 */
	public static boolean isValidMobNumber(String mobNumber) {
		String mobNumRegex = "^[0-9]{10}$";
		Pattern emailPattern = Pattern.compile(mobNumRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher3 = emailPattern.matcher(mobNumber);
		return matcher3.matches();

	}

	/**
	 * Create the frame.
	 */

	public UserRegistration() {

		setTitle("New Patient? Register!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(147, 112, 219));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("First name");
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblName.setBounds(480, 262, 99, 43);
		contentPane.add(lblName);

		JLabel lblNewLabel = new JLabel("Last name");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(480, 337, 110, 29);
		contentPane.add(lblNewLabel);

		JLabel lblEmailAddress = new JLabel("Email ID\r\n ");
		lblEmailAddress.setForeground(new Color(255, 255, 255));
		lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEmailAddress.setBounds(480, 412, 124, 36);
		contentPane.add(lblEmailAddress);

		firstname = new JTextField();
		firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
		firstname.setBounds(707, 250, 228, 50);
		contentPane.add(firstname);
		firstname.setColumns(10);

		lastname = new JTextField();
		lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lastname.setBounds(707, 325, 228, 50);
		contentPane.add(lastname);
		lastname.setColumns(10);

		email = new JTextField();

		email.setFont(new Font("Tahoma", Font.PLAIN, 32));
		email.setBounds(707, 400, 228, 50);
		contentPane.add(email);
		email.setColumns(10);

		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 32));
		username.setBounds(707, 25, 228, 50);
		contentPane.add(username);
		username.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(480, 37, 99, 29);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(480, 112, 99, 24);
		contentPane.add(lblPassword);

		JLabel lblMobileNumber = new JLabel("Mobile number");
		lblMobileNumber.setForeground(new Color(255, 255, 255));
		lblMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMobileNumber.setBounds(480, 187, 139, 26);
		contentPane.add(lblMobileNumber);

		mob = new JTextField();
		mob.setFont(new Font("Tahoma", Font.PLAIN, 32));
		mob.setBounds(707, 175, 228, 50);
		contentPane.add(mob);
		mob.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		passwordField.setBounds(707, 100, 228, 50);
		contentPane.add(passwordField);

		registerButton = new JButton("Register");
		registerButton.setOpaque(true);
		registerButton.setForeground(new Color(0, 0, 0));
		registerButton.setBackground(new Color(255, 255, 255));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				User patient = new User();
				patient.setFirstName(firstname.getText());
				patient.setLastName(lastname.getText());
				patient.setUsername(username.getText());
				patient.setMobNum(mob.getText());
				patient.setPassword(passwordField.getText());
				patient.setEmail(email.getText());

				verify1 = isValidPassword(patient.getPassword());
				verify2 = isValidEmail(patient.getEmail());
				verify3 = isValidMobNumber(patient.getMobNum());
				if (verify1) /* if password is valid */
				{
					if (verify2) /* if emailID is valid */
					{
						if (verify3) /* if number is valid */
						{
							try {
								Connection connection = DriverManager.getConnection(
										"jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
								String query = "INSERT INTO account values('" + patient.getFirstName() + "','"
										+ patient.getLastName() + "','" + patient.getUsername() + "','"
										+ patient.getPassword() + "','" + patient.getEmail() + "','"
										+ patient.getMobNum() + "')";
								Statement sta = connection.createStatement();
								int x = sta.executeUpdate(query);
								if (x == 0) {
									JOptionPane.showMessageDialog(registerButton, "This  already exists");
								} else {
									JOptionPane.showMessageDialog(registerButton, "Welcome, " + patient.getFirstName()
											+ ". Your account is sucessfully created");
									String username = patient.getUsername();
									String patient_details = "Patient Name: " + patient.getFirstName() + " "
											+ patient.getLastName() + "\r\n" + "Patient Email: " + patient.getEmail()
											+ "\r\n" + "Patient Mobile Number: " + patient.getMobNum() + "\r\n";
									File file = new File(
											"/Users/harshmishra/Desktop/patientDetails/" + username + ".txt");
									file.createNewFile();
									FileOutputStream fos = new FileOutputStream(
											"/Users/harshmishra/Desktop/patientDetails/" + username + ".txt", false);
									byte[] b = patient_details.getBytes();
									fos.write(b);
									fos.close();
								}
								connection.close();
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(registerButton, "Enter a valid mobile number");
							mob.setText("");
						}

					} else {
						JOptionPane.showMessageDialog(registerButton,
								"Your email seems incorrect. Re-enter and click register again.");
						email.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(registerButton,
							"Your password must contain an uppercase letter, a lowercase letter, and a number, and be between 8-16 characters in length.");
					passwordField.setText("");
				}
			}
		});

		registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		registerButton.setBounds(434, 475, 259, 74);
		contentPane.add(registerButton);

		loginButton2 = new JButton("Login");
		loginButton2.setOpaque(true);
		loginButton2.setForeground(new Color(0, 0, 0));
		loginButton2.setBackground(new Color(255, 255, 255));
		loginButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginScreen frame2 = new LoginScreen();
				dispose();
				frame2.setVisible(true);

			}
		});
		loginButton2.setFont(new Font("Tahoma", Font.PLAIN, 22));
		loginButton2.setBounds(723, 475, 259, 74);
		contentPane.add(loginButton2);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 421, 587);
		contentPane.add(panel);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(6, 5, 409, 557);

		ImageIcon imageIcon = new ImageIcon(UserRegistration.class.getResource("/images/MjcyNDE4Mg.jpg"));
		Image img1 = imageIcon.getImage();
		Image img2 = img1.getScaledInstance(lblNewLabel_2.getWidth() - 30, lblNewLabel_2.getHeight() - 50,
				Image.SCALE_SMOOTH);
		ImageIcon finalImg = new ImageIcon(img2);

		lblNewLabel_3 = new JLabel("Your Health is Our Priority");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		panel.add(lblNewLabel_3);
		lblNewLabel_2.setIcon(finalImg);

		panel.add(lblNewLabel_2);

	}

}
