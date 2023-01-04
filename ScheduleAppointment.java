package trial.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mysql.cj.x.protobuf.MysqlxNotice.Warning.Level;
import java.util.logging.Logger;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Get_the_date {
	private static String date;

	public void setDate(String dateObtain) {
		date = dateObtain;
	}

	public String getDate() {
		return date;
	}
}

public class ScheduleAppointment extends JFrame {
	LoginScreen LoginScreen1 = new LoginScreen();
	private String patient_name_local = LoginScreen1.getName();
	JRadioButton radButNewApp;
	JRadioButton radButFolApp;
	ButtonGroup bg;
	String filename;
	File report;
	InputStream in;
	private String extension = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	static ScheduleAppointment fr2 = new ScheduleAppointment();
	private JPanel contentPane;
	private String date_local;
	ManageAppointments readVar = new ManageAppointments();
	boolean quick_check = readVar.getBooleanVar();
	String notes;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ScheduleAppointment frame = new ScheduleAppointment();
					frame.setVisible(true);
//					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}

	/**
	 * Create the frame.
	 */
	public ScheduleAppointment() {

		setTitle("Book An Appointment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Choose appointment date");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(125, 131, 239, 22);
		contentPane.add(lblNewLabel);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(399, 119, 195, 50);
		contentPane.add(dateChooser);

		JButton bookButton = new JButton("Book");
		bookButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		bookButton.setOpaque(true);
		bookButton.setForeground(new Color(0, 0, 0));
		bookButton.setBackground(new Color(153, 102, 204));
		contentPane.add(bookButton);
		bookButton.setBounds(805, 476, 146, 60);

		JLabel chooseTimeLabel = new JLabel("Choose Appointment Time");
		chooseTimeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		chooseTimeLabel.setBounds(125, 218, 246, 22);
		contentPane.add(chooseTimeLabel);

		JButton chooseTimeButton = new JButton("Choose Time");
		chooseTimeButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		chooseTimeButton.setOpaque(true);
		chooseTimeButton.setForeground(new Color(0, 0, 0));
		chooseTimeButton.setBackground(new Color(153, 102, 204));

		chooseTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ChooseTime_ScheduleAppointment fr3 = new ChooseTime_ScheduleAppointment();
				fr3.setTitle("Available Appointment Slots");
				fr3.setVisible(true);
			}
		});
		chooseTimeButton.setBounds(399, 201, 195, 39);
		contentPane.add(chooseTimeButton);

		JButton confirmDateButton = new JButton("Confirm Date");
		confirmDateButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		confirmDateButton.setOpaque(true);
		confirmDateButton.setForeground(new Color(0, 0, 0));
		confirmDateButton.setBackground(new Color(153, 102, 204));
		confirmDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Get_the_date date_writer = new Get_the_date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				date_local = sdf.format(dateChooser.getDate());

				date_writer.setDate(date_local);

			}
		});
		confirmDateButton.setBounds(609, 119, 187, 50);
		contentPane.add(confirmDateButton);

		JLabel lblNewLabel_3 = new JLabel("Choose Appointment Type");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel_3.setBounds(125, 314, 245, 22);
		contentPane.add(lblNewLabel_3);

		radButNewApp = new JRadioButton(" New Appointment");
		radButNewApp.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		radButNewApp.setBounds(415, 289, 205, 27);
		contentPane.add(radButNewApp);

		radButFolApp = new JRadioButton("Follow-up appointment");
		radButFolApp.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		radButFolApp.setBounds(415, 348, 251, 27);
		contentPane.add(radButFolApp);

		bg = new ButtonGroup();
		bg.add(radButNewApp);
		bg.add(radButFolApp);

		JButton goBackButton = new JButton("Go Back");
		goBackButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		goBackButton.setOpaque(true);
		goBackButton.setForeground(new Color(0, 0, 0));
		goBackButton.setBackground(new Color(153, 102, 204));

		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PatientMenu check = new PatientMenu(patient_name_local);
				check.setTitle("Welcome");
				check.setVisible(true);

			}
		});
		goBackButton.setBounds(6, 17, 157, 39);
		contentPane.add(goBackButton);

		// if 'new appointment' radio button is selected
		radButNewApp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JLabel symptomsLabel = new JLabel("Enter any symptoms ");
				symptomsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
				symptomsLabel.setBounds(6, 451, 194, 22);
				contentPane.add(symptomsLabel);
				symptomsLabel.setForeground(new Color(0, 0, 0));
				symptomsLabel.setBackground(Color.gray);

				textField = new JTextField();
				textField.setBounds(234, 366, 499, 203);
				contentPane.add(textField);
				textField.setColumns(10);
				textField.setBackground(Color.gray);
				textField.setForeground(Color.white);

			}
		});

		// if 'follow-up appointment' radio button is selected
		radButFolApp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JLabel uploadReportLabel = new JLabel(
						"<html>Upload the test report of the test the doctor asked you to get done. No need to if its NA.</html>");
				uploadReportLabel.setForeground(new Color(0, 0, 0));
				uploadReportLabel.setBackground(Color.gray);
				uploadReportLabel.setBounds(198, 420, 230, 80);
				contentPane.add(uploadReportLabel);
				uploadReportLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));

				JButton uploadButton = new JButton("Upload");
				uploadButton.setBounds(442, 439, 205, 60);
				contentPane.add(uploadButton);
				uploadButton.setOpaque(true);
				uploadButton.setForeground(Color.BLACK);
				uploadButton.setBackground(new Color(153, 102, 204));
				uploadButton.setFont(new Font("Lucida Grande", Font.BOLD, 14));

				uploadButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						JFileChooser chooser = new JFileChooser();
						chooser.showOpenDialog(null);
						report = chooser.getSelectedFile();
						filename = report.getAbsolutePath();

						int i = filename.lastIndexOf('.');
						if (i > 0) {
							extension = filename.substring(i + 1);
						}

						try {

							in = new FileInputStream(filename);

						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});

			}
		});

		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bg.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "You must select either new or follow-up.");
				} else {
					ChooseTime_ScheduleAppointment set_the_time = new ChooseTime_ScheduleAppointment();
					String time_local = set_the_time.getTime();

					JLabel lblNewLabel_2 = new JLabel("");

					lblNewLabel_2.setBounds(635, 223, 61, 16);
					contentPane.add(lblNewLabel_2);
					lblNewLabel_2.setText(time_local);

					try {

						if (dateChooser.getDate() == null) {
							JOptionPane.showMessageDialog(null, "Select Date. Hit Confirm. Select Time. Hit Submit");
						} else {
							Connection connection = DriverManager
									.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");

							Statement myStmt = connection.createStatement();

							FileOutputStream fos = new FileOutputStream(
									"/Users/harshmishra/Desktop/patientDetails/" + patient_name_local + ".txt", true);
							byte[] b = null;

							if (radButNewApp.isSelected()) {

								notes = textField.getText();
								int indexSize = getuserIndex(patient_name_local);
								PreparedStatement st = connection.prepareStatement(
										"INSERT INTO appointment_bookings(patient_name,appointment_date,appointment_time,patient_symptoms,newOrFollow) \r\n"
												+ "VALUES ('" + patient_name_local + "','" + date_local + "','"
												+ time_local + "','" + notes + "','N')");

								String appointment_details = "\n " + indexSize
										+ ". Appointment date (new app) Appointment Time: " + date_local + "  "
										+ time_local + "\r\n";
								b = appointment_details.getBytes();
								fos.write(b);
                     
								st.executeUpdate();
							}

							else if (radButFolApp.isSelected()) {

								PreparedStatement pstmt = connection.prepareStatement(
										"INSERT INTO appointment_bookings(patient_name,appointment_date,appointment_time,patient_reports,newOrFollow,patient_reports_file_type) VALUES(?,?,?,?,?,?)");
								pstmt.setString(1, patient_name_local);
								pstmt.setString(2, date_local);
								pstmt.setString(3, time_local);

								pstmt.setBlob(4, in);
								pstmt.setString(5, "F");
								pstmt.setString(6, extension);
								int indexSize = getuserIndex(patient_name_local);
								String appointment_details = "\n " + indexSize
										+ ". Appointment date (fol app) Appointment Time: " + date_local + "  "
										+ time_local + "\r\n";
								b = appointment_details.getBytes();
								fos.write(b);

								pstmt.executeUpdate();
							}
							fos.close();

							connection.close();

							if (quick_check) {
								JOptionPane.showMessageDialog(null,
										patient_name_local + ", your appointment has been rescheduled for " + date_local
												+ " at " + time_local);
							} else if (!quick_check) {
								JOptionPane.showMessageDialog(null, patient_name_local
										+ ", your appointment has been booked for " + date_local + " at " + time_local);
								PatientMenu newScreen = new PatientMenu(patient_name_local);
								newScreen.setVisible(true);
								dispose();

							}
						}
					} catch (Exception exception) {
						exception.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"You haven't chosen a time, or forgot to click submit after choosing a date. Retry");
					}

				}

			}
		});

	}

	private int getuserIndex(String pName) {
		int index = 0;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");
			String query1 = "SELECT count(*) FROM appointment_bookings where patient_name ='" + pName + "'";
			Statement sta1 = connection.createStatement();
			ResultSet rs1 = sta1.executeQuery(query1);
			rs1.next();
			index = rs1.getInt("count(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return index + 1;
	}
}



//PreparedStatement st = connection.prepareStatement("INSERT INTO appointment_bookings(patient_name,appointment_date,appointment_time,patient_reports) \r\n" + 
//"VALUES ('" + patient_name_local +"','"+date_local+"','"+time_local+"','"+in+"');");

//String sql = "INSERT INTO appointment_bookings(patient_Reports) VALUES(?)";
//PreparedStatement st1 =  connection.prepareStatement(sql);
//
//  try (FileInputStream fin = new FileInputStream(filename)) {
//
//    st1.setBinaryStream(1, fin, (int) filename.length());
//    st1.executeUpdate();
//    
//   
//  } catch (IOException ex) {
//
//   JOptionPane.showMessageDialog(null, "oops");
//  }
//System.out.println(ScheduleAppointment.test(myStmt));
//if(ScheduleAppointment.test(myStmt) == 0)
//	{
//	JOptionPane.showMessageDialog(bookButton, "appointment bookws");
//
//	}
//else
//{
//	JOptionPane.showMessageDialog(bookButton, "full");
//	
//}
