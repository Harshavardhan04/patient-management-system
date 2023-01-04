package trial.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class DoctorViewPatientDetails extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String patient_name;
	private String patient_symptoms;
	private String newOrFollow;
	private byte[] img;
	private String file_Ext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorViewPatientDetails frame = new DoctorViewPatientDetails();
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
	public DoctorViewPatientDetails() {

		setTitle("Appointment Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDate = new JLabel("date");
		lblDate.setForeground(Color.BLUE);
		lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblDate.setBounds(484, 91, 177, 16);
		contentPane.add(lblDate);

		JLabel lblTime = new JLabel("time");
		lblTime.setForeground(Color.BLUE);
		lblTime.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblTime.setBounds(484, 139, 177, 16);
		contentPane.add(lblTime);

		JLabel lblShowName = new JLabel("Name");
		lblShowName.setForeground(Color.BLUE);
		lblShowName.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblShowName.setBounds(484, 43, 177, 22);
		contentPane.add(lblShowName);

		JLabel lblName = new JLabel("Patient name:");
		lblName.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblName.setBounds(325, 43, 126, 22);
		contentPane.add(lblName);

		JLabel lblAppointmentDate = new JLabel("Appointment Date:");
		lblAppointmentDate.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblAppointmentDate.setBounds(281, 91, 173, 22);
		contentPane.add(lblAppointmentDate);

		JLabel lblAppointmentTime = new JLabel("Appointment Time:");
		lblAppointmentTime.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblAppointmentTime.setBounds(281, 139, 176, 22);
		contentPane.add(lblAppointmentTime);

		String date_local = DoctorUpcomingAppointments.date_collect;
		String time_local = DoctorUpcomingAppointments.time_collect;
		lblDate.setText(date_local);
		lblTime.setText(time_local);

		JButton btnNewButton_1 = new JButton("Go back");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		btnNewButton_1.setOpaque(true);
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBackground(new Color(153, 102, 204));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoctorMenu frame = new DoctorMenu();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(6, 16, 117, 29);
		contentPane.add(btnNewButton_1);

		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");

			String query = "SELECT * FROM appointment_bookings where appointment_date = '" + date_local
					+ "' and appointment_time = '" + time_local + "'";
			Statement sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(query);
			while (rs.next()) {
				patient_name = rs.getString(1);

				patient_symptoms = rs.getString(4);
				newOrFollow = rs.getString(6);
				img = rs.getBytes(5);
				file_Ext = rs.getString(8);

			}
			lblShowName.setText(patient_name);

			JButton btnAddNotes = new JButton("Add Notes");
			btnAddNotes.setFont(new Font("Lucida Grande", Font.BOLD, 18));
			btnAddNotes.setOpaque(true);
			btnAddNotes.setForeground(new Color(0, 0, 0));
			btnAddNotes.setBackground(new Color(153, 102, 204));
			btnAddNotes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					DoctorAddNotes frame = new DoctorAddNotes();
					frame.setVisible(true);
				}
			});
			btnAddNotes.setBounds(55, 216, 140, 30);
			contentPane.add(btnAddNotes);
			//if new appointment
			if (newOrFollow.equals("N")) {
				JLabel lblSymptoms = new JLabel("Patient Symptoms:");
				lblSymptoms.setFont(new Font("Lucida Grande", Font.BOLD, 18));

				lblSymptoms.setBounds(281, 249, 173, 22);
				contentPane.add(lblSymptoms);

				JLabel lblShowSymptoms = new JLabel(patient_symptoms);
				lblShowSymptoms.setFont(new Font("Lucida Grande", Font.BOLD, 18));
				lblShowSymptoms.setBounds(484, 191, 600, 132);
				contentPane.add(lblShowSymptoms);
				
				//if follow-up appointment
			} else if (newOrFollow.equals("F")) {
				JLabel lblReport = new JLabel("Report");
				lblReport.setFont(new Font("Lucida Grande", Font.BOLD, 18));
				lblReport.setBounds(363, 185, 88, 16);
				contentPane.add(lblReport);

				JLabel lblShowReport = new JLabel();
				lblShowReport.setBounds(484, 185, 371, 384);
				contentPane.add(lblShowReport);
				ImageIcon image = new ImageIcon(img);
//				  
				Image im = image.getImage();
				Image myImg = im.getScaledInstance(lblShowReport.getWidth(), lblShowReport.getHeight(),
						Image.SCALE_SMOOTH);
				ImageIcon newImg = new ImageIcon(myImg);
				lblShowReport.setIcon(newImg);

				JButton btnDownloadReport = new JButton("Download Report");
				btnDownloadReport.setFont(new Font("Lucida Grande", Font.BOLD, 12));
				btnDownloadReport.setOpaque(true);
				btnDownloadReport.setForeground(new Color(0, 0, 0));
				btnDownloadReport.setBackground(new Color(153, 102, 204));
				btnDownloadReport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							//if report file type is png
							if (file_Ext.equals("png")) {
								File file = new File(
										"/Users/harshmishra/Desktop/patientDetails/" + patient_name + "report.PNG");
								file.createNewFile();
								FileOutputStream fos = new FileOutputStream(
										"/Users/harshmishra/Desktop/patientDetails/" + patient_name + "report.PNG",
										false);
								fos.write(img);
								fos.close();
							} 
								//if report file type is pdf
								else if (file_Ext.equals("pdf")) {
								File file1 = new File(
										"/Users/harshmishra/Desktop/patientDetails/" + patient_name + "report.pdf");
								file1.createNewFile();
								OutputStream fos1 = new FileOutputStream(
										"/Users/harshmishra/Desktop/patientDetails/" + patient_name + "report.pdf");
								fos1.write(img);
								fos1.close();
							}

						} catch (Exception e1) {

							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
				btnDownloadReport.setBounds(40, 307, 152, 29);
				contentPane.add(btnDownloadReport);

			}

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}

//String query = "SELECT appointment_date, appointment_time FROM appointment_bookings where patient_name = '" + patient_name_local + "'";