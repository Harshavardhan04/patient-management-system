package trial.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DoctorAddNotes extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorAddNotes frame = new DoctorAddNotes();
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
	public DoctorAddNotes() {
		
		setTitle("Add Notes For Appointment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Add Notes");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(187, 6, 96, 22);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(30, 45, 390, 180);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btnAdd.setOpaque(true);
		btnAdd.setForeground(new Color(0, 0, 0));
		btnAdd.setBackground(new Color(153, 102, 204));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String notes = textField.getText();

				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration",
							"harshM", "Brentwood1");

					PreparedStatement st = connection
							.prepareStatement("UPDATE appointment_bookings set doctor_notes = '" + notes
									+ "' where appointment_date = '" + DoctorUpcomingAppointments.date_collect
									+ "' and appointment_time ='" + DoctorUpcomingAppointments.time_collect + "'");

					int rs = st.executeUpdate();
					if (rs == 0) {

						JOptionPane.showMessageDialog(null, "failure");
					} else {
						JOptionPane.showMessageDialog(null, "added");
						dispose();
					}

					connection.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(303, 237, 117, 29);
		contentPane.add(btnAdd);
	}
}
