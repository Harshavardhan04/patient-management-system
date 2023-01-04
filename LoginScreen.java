package trial.swing;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public JTextField userNameText;
	public static String userName_check;
	public String password_check;


	static LoginScreen fr1 = new LoginScreen();
	public JPasswordField passwordField1;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					fr1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public LoginScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel welcomeLabel = new JLabel("Welcome");
		welcomeLabel.setBounds(632, 18, 224, 60);
		welcomeLabel.setFont(new Font("Arial Black", Font.PLAIN, 42));

		contentPane.add(welcomeLabel);

		JButton regButton = new JButton("New User? Click here to register");
		regButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fr1.dispose();
				fr1.setVisible(false);
				fr1.dispose();
				UserRegistration us1 = new UserRegistration();
				us1.setVisible(true);
			}
		});
		regButton.setBounds(632, 458, 255, 49);
		regButton.setOpaque(true);
		regButton.setBackground(new Color(153, 102, 204));
		contentPane.add(regButton);

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(541, 153, 140, 36);
		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		contentPane.add(usernameLabel);

		userNameText = new JTextField();
		userNameText.setFont(new Font("Tahoma", Font.PLAIN, 32));
		userNameText.setBounds(719, 143, 228, 50);
		contentPane.add(userNameText);
		userNameText.setColumns(10);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		passwordLabel.setBounds(541, 223, 133, 36);
		contentPane.add(passwordLabel);

		passwordField1 = new JPasswordField();
		passwordField1.setBounds(719, 220, 228, 50);
		contentPane.add(passwordField1);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(0, 0, 423, 569);
		contentPane.add(panel);

		JLabel imgLabel = new JLabel(" ");
		panel.add(imgLabel);
		imgLabel.setBounds(6, 5, 409, 557);

		ImageIcon imageIcon = new ImageIcon(LoginScreen.class.getResource("/images/img1.png"));
		Image img1 = imageIcon.getImage();
		Image img2 = img1.getScaledInstance(imgLabel.getWidth() - 30, imgLabel.getHeight() - 50, Image.SCALE_SMOOTH);
		ImageIcon finalImg = new ImageIcon(img2);

		JLabel lblNewLabel_3 = new JLabel("Your Health is Our Priority");
		lblNewLabel_3.setBackground(new Color(0, 0, 204));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		panel.add(lblNewLabel_3);
		imgLabel.setIcon(finalImg);

		panel.add(imgLabel);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		loginButton.setForeground(new Color(0, 0, 0));
		loginButton.setOpaque(true);
		loginButton.setBackground(new Color(153, 102, 204));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component btnNewButton = null;
				userName_check = userNameText.getText();
				password_check = passwordField1.getText();

				if (userName_check.equals("admin") && password_check.contentEquals("admin")) {
					dispose();
					DoctorMenu frame = new DoctorMenu();
					frame.setVisible(true);
				} else {
					try {
						// connect to database to check if user-name and password are valid
						Connection connection = (Connection) DriverManager
								.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
						PreparedStatement st = (PreparedStatement) connection.prepareStatement(
								"Select user_name, password from account where user_name=? and password=?");

						st.setString(1, userName_check);
						st.setString(2, password_check);
						ResultSet rs = st.executeQuery();
						// if valid
						if (rs.next()) {
							dispose();
							PatientMenu check = new PatientMenu(userName_check);
							check.setTitle("Welcome");
							check.setVisible(true);
							JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
							// if not valid
						} else {
							JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
						}
						/*
						 * sqlException will provide the developer with information regarding the error
						 * which took place in the database, such as database access error
						 */
					} catch (SQLException sqlException) {
						sqlException.printStackTrace();
						// A graphical message to convey the current inaccessibility to the user.
						JOptionPane.showMessageDialog(btnNewButton,
								"Sorry, our database seems to be inaccessible right now, please try again later!");
					}
				}

			}
		});

		loginButton.setBounds(670, 371, 174, 59);
		contentPane.add(loginButton);

		this.userName_check = userName_check;

	}

	public String getName() {
		return this.userName_check;
	}
}