package trial.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViewDoctorNotes extends JFrame {
	LoginScreen LoginScreen1 = new LoginScreen();
	private String patient_name_local = LoginScreen1.getName();

	private JPanel contentPane;
	private String doctor_notes;
	static ViewDoctorNotes fr2 = new ViewDoctorNotes();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewDoctorNotes frame = new ViewDoctorNotes();
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
	public ViewDoctorNotes() {

		setTitle("Doctor's nots for this appointment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(247, 140, 448, 303);
		contentPane.add(lblNewLabel);

//        PriorConsultations;

		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");

//            String query = "SELECT appointment_date, appointment_time FROM appointment_bookings where patient_name = '" + patient_name_local + "'";
			String query = "SELECT doctor_notes FROM appointment_bookings where patient_name= '" + patient_name_local
					+ "'and appointment_date = '" + PriorConsultations.date_collect1 + "' and appointment_time = '"
					+ PriorConsultations.time_collect1 + "'";
			Statement sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(query);
			while (rs.next()) {

				doctor_notes = rs.getString(1);

			}

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		lblNewLabel.setText(doctor_notes);

		JButton goBackButton = new JButton("Go Back");
		goBackButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		goBackButton.setOpaque(true);
		goBackButton.setForeground(new Color(0, 0, 0));
		goBackButton.setBackground(new Color(153, 102, 204));
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PriorConsultations frame = new PriorConsultations();
				frame.setVisible(true);
				dispose();
			}
		});
		goBackButton.setBounds(21, 18, 117, 29);
		contentPane.add(goBackButton);
	}
}
