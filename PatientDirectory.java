package trial.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

public class PatientDirectory extends JFrame {

	private JPanel contentPane;
	private String name;
	ArrayList<String> names;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientDirectory frame = new PatientDirectory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PatientDirectory() {

		setTitle("Patient Directory - General Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton goBackButton = new JButton("Go back");
		goBackButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		goBackButton.setOpaque(true);
		goBackButton.setForeground(new Color(0, 0, 0));
		goBackButton.setBackground(new Color(153, 102, 204));
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorMenu frame = new DoctorMenu();
				frame.setVisible(true);
				dispose();
			}
		});
		goBackButton.setBounds(6, 16, 117, 29);
		contentPane.add(goBackButton);

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Lucida Grande", Font.BOLD, 18));

		comboBox.setBounds(195, 67, 449, 66);
		comboBox.setEditable(false);
		contentPane.add(comboBox);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBounds(218, 41, 419, 337);
		contentPane.add(lblNewLabel);
		comboBox.addItem("Choose Name");

		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");

			String query = "SELECT first_name FROM account";
			Statement sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(query);
			names = new ArrayList<String>();

			while (rs.next()) {
				comboBox.addItem(rs.getString(1));
			}

		} catch (Exception e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setText(" ");
				name = (String) comboBox.getSelectedItem();
				Scanner sc = null;

				String filePath = "/Users/harshmishra/Desktop/patientDetails/" + name + ".txt";
				try {

					sc = new Scanner(new File(filePath));
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				// instantiating the StringBuffer class
				StringBuffer buffer = new StringBuffer();

				// Reading lines of the file and appending them to StringBuffer

				while (sc.hasNextLine()) {
					buffer.append(sc.nextLine() + System.lineSeparator() + "  ");

				}
				String fileContents = buffer.toString();
				String str = fileContents;
				String[] arrOfStr = str.split("  ", 3);
				String display = " ";
				for (String a : arrOfStr) {

					display = display + "\n" + a;

				}
				lblNewLabel.setText("<html>" + display + "<html>");

				// closing the Scanner object
				sc.close();

			}
		});
	}
}
