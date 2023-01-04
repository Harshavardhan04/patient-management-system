package trial.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Font;

public class ManageAppointments extends JFrame {
	private static boolean check2;

	public void setBooleanVar(boolean local) {
		check2 = local;
	}

	public boolean getBooleanVar() {
		return check2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ManageAppointments fr2 = new ManageAppointments();
	private JPanel contentPane;
	private String date_local;
	private String time_local;
	private JTable table;
	static JComboBox box1;
	static JComboBox<String> box2;
	String new_chosen_date_local;
	private String date_local2;
	private String date_local3;
	public static boolean check = false;
	Scanner sc;
	String lineToReplace;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ManageAppointments frame = new ManageAppointments();
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
	public ManageAppointments() {
		setTitle("Manage Existing Appoinments");
		
		ArrayList<String> times_to_display = new ArrayList<String>();
		ArrayList<String> dates_to_display = new ArrayList<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Manage Appointments");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(306, 20, 252, 36);
		contentPane.add(lblNewLabel);
		LoginScreen LoginScreen3 = new LoginScreen();
		String patient_name_local = LoginScreen3.getName();

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
		goBackButton.setBounds(6, 17, 121, 23);
		contentPane.add(goBackButton);

		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");

			String query = "SELECT appointment_date FROM appointment_bookings where patient_name = '"
					+ patient_name_local + "'";
			Statement sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(query);
			while (rs.next()) {
			
				dates_to_display.add(rs.getDate("appointment_date").toString());
				dates_to_display.sort((d1, d2) -> d1.compareTo(d2));

			}
			for (int i = 0; i < dates_to_display.size(); i++) {
				String query1 = "SELECT appointment_time FROM appointment_bookings where patient_name = '"
						+ patient_name_local + "' AND appointment_date = '" + dates_to_display.get(i) + "'";
				Statement sta1 = connection.createStatement();
				ResultSet rs1 = sta1.executeQuery(query1);
				while (rs1.next()) {
					times_to_display.add(rs1.getTime("appointment_time").toString());
				}
			}
			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		String column[] = { "Date", "Time" };

		DefaultTableModel tableModel = new DefaultTableModel(column, 0);
		JTable jt = new JTable(tableModel);

		String[] date_time_combo = new String[dates_to_display.size() + 1];

		for (int j = 0; j < dates_to_display.size(); j++) {
			String date = dates_to_display.get(j);
			String time = times_to_display.get(j);
			Object[] data = { date, time };
			date_time_combo[j + 1] = date + " " + time;

			tableModel.addRow(data);

		}

		Border border = new LineBorder(Color.BLACK, 1, true);
		jt.setBorder(border);
		date_time_combo[0] = "select appointment";
		box1 = new JComboBox(date_time_combo);
		box1.setSize(237, 50);
		box1.setLocation(600, 68);

		// add ItemListener

		// create labels
		JLabel select_appointment = new JLabel("select appointment");
		select_appointment.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		select_appointment.setLocation(434, 84);
		JLabel appointment_selected = new JLabel();
		appointment_selected.setLocation(835, 84);

		// set color of text
		select_appointment.setForeground(Color.BLACK);
		appointment_selected.setForeground(Color.blue);
//     appointment_selected.setText(" selected"); 
		appointment_selected.setSize(159, 16);
		select_appointment.setSize(166, 16);
		contentPane.add(box1);
		contentPane.add(select_appointment);
		contentPane.add(appointment_selected);
		box1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				appointment_selected.setText(box1.getSelectedItem() + " selected");

			}
		});

		box2 = new JComboBox();
		box2.addItem("Please choose an option");
		box2.addItem("Reschedule");
		box2.addItem("Cancel");

		box2.setSize(237, 50);
		box2.setLocation(600, 105);
		contentPane.add(box2);

		JLabel option_selected = new JLabel();

		option_selected.setSize(159, 35);
		option_selected.setLocation(845, 110);
		option_selected.setForeground(Color.blue);
		contentPane.add(option_selected);

		box2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option_selected.setText(box2.getSelectedItem() + " ");
				String decision = (String) box2.getSelectedItem();
				String str = (String) box1.getSelectedItem();
				String[] arrOfStr = str.split(" ", 2);
				date_local = arrOfStr[0];
				time_local = arrOfStr[1];

				if (decision.equals("Cancel")) {

					int result = JOptionPane.showConfirmDialog(null,
							patient_name_local + ", are you sure you want to cancel your appointment scheduled for "
									+ date_local + " at " + time_local,
							"ManageAppointments", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {
						try {
							Connection connection = DriverManager
									.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");

							String query = "DELETE FROM appointment_bookings where appointment_date = '" + date_local
									+ "' and patient_name = '" + patient_name_local + "' and appointment_time = '"
									+ time_local + "'";

							Statement sta = connection.createStatement();
							int rs = sta.executeUpdate(query);
							if (rs == 1) {
								JOptionPane.showMessageDialog(null,
										patient_name_local + " your appointment has been cancelled");
								String filePath = "/Users/harshmishra/Desktop/patientDetails/" + patient_name_local
										+ ".txt";
								try {

									sc = new Scanner(new File(filePath));
								} catch (FileNotFoundException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								// instantiating the StringBuffer class
								StringBuffer buffer = new StringBuffer();

								// Reading lines of the file and appending them to StringBuffer
								System.out.println(date_local);
								while (sc.hasNextLine()) {
									buffer.append(sc.nextLine() + System.lineSeparator());

									String line = sc.nextLine();
									if (line.contains(date_local)) {

										lineToReplace = line;

									}

								}
								String fileContents = buffer.toString();

								// closing the Scanner object
								sc.close();

								String cancel = " ";

								// Replacing the old line with new line
								fileContents = fileContents.replace(lineToReplace, cancel);

								// instantiating the FileWriter class
								FileWriter writer;
								try {
									writer = new FileWriter(filePath);
									writer.append(fileContents);
									writer.flush();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								JOptionPane.showMessageDialog(null,
										patient_name_local + " Oops, system error. Please try again later");

							}

							connection.close();
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					} else if (result == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, patient_name_local + " great, see you soon!");
						box1.setSelectedIndex(0);
						box2.setSelectedIndex(0);
						option_selected.setText("");
						appointment_selected.setText("");
					}

				}

				else if (decision.equals("Reschedule")) {
					JDateChooser dateChooser = new JDateChooser();
					contentPane.add(dateChooser);
					dateChooser.setBounds(666, 195, 195, 50);

					int result1 = JOptionPane.showConfirmDialog(null,
							patient_name_local + ", are you sure you want to reschedule your appointment scheduled for "
									+ date_local + " at " + time_local,
							"ManageAppointments", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (result1 == JOptionPane.YES_OPTION) {

						JLabel lblNewLabel2 = new JLabel("Choose appointment date");
						lblNewLabel2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
						contentPane.add(lblNewLabel2);
						lblNewLabel2.setBounds(450, 209, 200, 16);

						JLabel lblNewLabel_1 = new JLabel("Choose Appointment Time");
						lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 13));
						contentPane.add(lblNewLabel_1);
						lblNewLabel_1.setBounds(450, 305, 200, 16);

						JButton btnNewButton_2 = new JButton("confirm");
						btnNewButton_2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
						btnNewButton_2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								Get_the_date date_writer = new Get_the_date();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

								date_local2 = sdf.format(dateChooser.getDate());

								date_writer.setDate(date_local2);

							}
						});

						JButton btnNewButton_1 = new JButton("Choose Time");
						btnNewButton_1.setFont(new Font("Lucida Grande", Font.BOLD, 13));
						btnNewButton_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {

								ChooseTime_ScheduleAppointment fr3 = new ChooseTime_ScheduleAppointment();
								fr3.setTitle("Available Appointment Slots");
								fr3.setVisible(true);
							}
						});
						btnNewButton_1.setBounds(666, 300, 195, 29);
						contentPane.add(btnNewButton_1);
						btnNewButton_2.setBounds(891, 209, 117, 16);
						contentPane.add(btnNewButton_2);

						JButton btnNewButton = new JButton("submit");
						btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
						Get_the_date date_getter = new Get_the_date();
						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									Connection connection = DriverManager.getConnection(
											"jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
									ChooseTime_ScheduleAppointment set_the_time = new ChooseTime_ScheduleAppointment();
									String newTime = set_the_time.getTime();

									date_local3 = date_getter.getDate();

									String query = ("UPDATE appointment_bookings set appointment_time = '" + newTime
											+ "', appointment_date = '" + date_local3 + "' where appointment_date = '"
											+ date_local + "' and appointment_time = '" + time_local + "'");
									Statement sta = connection.createStatement();
									int x = sta.executeUpdate(query);
									if (x == 1) {
										JOptionPane.showMessageDialog(null,
												patient_name_local + " your appointment has been rescheduled");
										String filePath = "/Users/harshmishra/Desktop/patientDetails/"
												+ patient_name_local + ".txt";

										// Instantiating the Scanner class to read the file

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
											buffer.append(sc.nextLine() + System.lineSeparator());
										}
										String fileContents = buffer.toString();

										// closing the Scanner object
										sc.close();
										String oldDate = date_local;

										String date_local4 = date_getter.getDate();
										String newDateTime = date_local4 + " Time : " + newTime;
										
										
										// Replacing the old line with new line
							
										try {
											int position = fileContents.lastIndexOf(oldDate);
											writeToFile(filePath, newDateTime, position + 3);
										} catch (IOException e2) {
											e2.printStackTrace();
										}

									} else {
										JOptionPane.showMessageDialog(null,
												patient_name_local + " Oops, system error. Please try again later");

									}

								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						btnNewButton.setBounds(815, 492, 117, 29);
						contentPane.add(btnNewButton);

					} else if (result1 == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, patient_name_local + " great, see you soon!");
						box1.setSelectedIndex(0);
						box2.setSelectedIndex(0);
						option_selected.setText("");
						appointment_selected.setText("");
					}
				}
			}
		});

		jt.setBounds(210, 84, 200, 300);

		contentPane.add(jt);
		JLabel lblSelectAction = new JLabel("select action");
		lblSelectAction.setForeground(Color.BLACK);
		lblSelectAction.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSelectAction.setBounds(480, 121, 166, 16);
		contentPane.add(lblSelectAction);
	}


	private static void writeToFile(String filePath, String data, int position) throws IOException {
		RandomAccessFile file = new RandomAccessFile(filePath, "rw");
		file.seek(position);
		file.write(data.getBytes());
		file.close();
	}
}























//String query = "SELECT appointment_date, appointment_time FROM appointment_bookings where patient_name = '" + patient_name_local + "'";
// System.out.println(rs.getTime("appointment_time").toString());
// timesToRemove.add(rs.getTime("appointment_time").toString());
// System.out.println(rs.getTime("appointment_time").toString());
// times_to_display.add(rs.getTime("appointment_time").toString());
// "DELETE FROM appointment_bookings where appointment_date = '"+date_local+"'
// and patient_name = '"+patient_name_local+"' and appointment_time =
// '"+time_local+"'";
//     
//
//     Statement sta = connection.createStatement();
//     int rs1 = sta.executeUpdate(query);
//     if (rs1 ==1)
//     {
//    	 ManageAppointments writeVar = new ManageAppointments();
//    	 boolean local_check = true;
//    	 writeVar.setBooleanVar(local_check);
//    	 dispose();
//     
//     	 ScheduleAppointment fr1 = new ScheduleAppointment();
//
//     	 fr1.setVisible(true);
//     	
//     }
//     else
//     {
//    	 JOptionPane.showMessageDialog(null,patient_name_local + " Oops, system error. Please try again later");
//    	 
//     }
////instantiating the FileWriter class
//FileWriter writer;
//try {
//	writer = new FileWriter(filePath);
//	 writer.append(fileContents);
//    writer.flush();
//} catch (IOException e1) {
//	// TODO Auto-generated catch block
//	e1.printStackTrace();
//}
//int rs1 = sta.executeUpdate(query);
// connection.close();
// if the state combobox is changed
//if (e.getSource() == box1) { 
//
//	 appointment_selected.setText(box1.getSelectedItem() + " selected"); 
//} 
////
////model.addRow(row);
//92	}

//package trial.swing;

//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.RandomAccessFile;
//import java.sql.Blob;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import javax.swing.table.DefaultTableModel;
//
//
//import com.toedter.calendar.JDateChooser;
//
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTable;
//import java.awt.Color;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class ManageAppointments extends JFrame {
//	private static boolean check2;
//	public void setBooleanVar(boolean local)
//	{
//		check2 = local;
//	}
//
//	public boolean getBooleanVar()
//	{
//		return check2;
//	}
//	
//	
//	   
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		static ManageAppointments fr2 = new ManageAppointments();
//		private JPanel contentPane;
//	private String date_local;
//	private String time_local;
//	private JTable table;
//	   static JComboBox box1;
//	   static JComboBox<String> box2;
//	   String new_chosen_date_local;
//	   private String date_local2;
//	   private String date_local3;
//	   public static boolean check = false;
//	   Scanner sc;
//	   String lineToReplace;
//
//		/**
//		 * Launch the application.
//		 */
//		
//
//		public static void main(String[] args) {
//	EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					
//					ManageAppointments frame = new ManageAppointments();
//	frame.setVisible(true);
////						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//			}
//		
//		});
//			
//		}
//		
//		
//	/**
//	 * Create the frame.
//	 */
//	public ManageAppointments() {
//		
//	
//		
//		//String[][] dateTime = new String[][];
//		ArrayList<String> times_to_display = new ArrayList<String>();
//		ArrayList<String> dates_to_display = new ArrayList<String>();
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(450, 190, 1014, 597);
//        setResizable(false);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//        
//        JLabel lblNewLabel = new JLabel("Manage Appointments");
//        lblNewLabel.setBounds(375, 20, 141, 16);
//        contentPane.add(lblNewLabel);
//        LoginScreen LoginScreen3 = new LoginScreen();
//        String patient_name_local = LoginScreen3.getName();
//        
//    	JButton btnNewButton_4 = new JButton("Go Back");
//		btnNewButton_4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				 dispose();
//                 PatientMenu check = new PatientMenu(patient_name_local);
//                 check.setTitle("Welcome");
//                 check.setVisible(true);
//				
//			}
//		});
//		btnNewButton_4.setBounds(6, 17, 117, 29);
//		contentPane.add(btnNewButton_4);
//        
//        try {
//        	  
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
//
////            String query = "SELECT appointment_date, appointment_time FROM appointment_bookings where patient_name = '" + patient_name_local + "'";
//            String query = "SELECT appointment_date FROM appointment_bookings where patient_name = '" + patient_name_local + "'";
//            Statement sta = connection.createStatement();
//            ResultSet rs = sta.executeQuery(query);
//            while (rs.next())
//		      {
//          // System.out.println(rs.getTime("appointment_time").toString());
//		        //timesToRemove.add(rs.getTime("appointment_time").toString());
//            	//System.out.println(rs.getTime("appointment_time").toString());
//            	//times_to_display.add(rs.getTime("appointment_time").toString());
//            	dates_to_display.add(rs.getDate("appointment_date").toString());
//            	dates_to_display.sort((d1,d2) -> d1.compareTo(d2));
//            	
//		      }
//            for (int i = 0; i< dates_to_display.size(); i++)
//            {
//            String query1 = "SELECT appointment_time FROM appointment_bookings where patient_name = '"+patient_name_local+ "' AND appointment_date = '"+dates_to_display.get(i)+"'";
//            Statement sta1 = connection.createStatement();
//            ResultSet rs1 = sta1.executeQuery(query1);
//            while (rs1.next())
//		      {
//            	times_to_display.add(rs1.getTime("appointment_time").toString());
//		      }
//            }
//            connection.close();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//
//     String column[]={"Date", "Time"};       
//  
//    
//
//     DefaultTableModel tableModel = new DefaultTableModel(column, 0);
//     JTable jt=new JTable(tableModel);  
//     
//    String[] date_time_combo = new String[dates_to_display.size()+1];
//     
//     for(int j = 0; j<dates_to_display.size();j++)
//     {
//    String date = dates_to_display.get(j);
//    String time = times_to_display.get(j);
//    Object[] data = {date,time};
//    date_time_combo[j+1] = date + " " + time;
//
//tableModel.addRow(data);
//
//       
//     }
//   
//     Border border = new LineBorder(Color.BLACK, 1, true);
//     jt.setBorder(border);
//     date_time_combo[0] = "select appointment";
//     box1 = new JComboBox(date_time_combo); 
//     box1.setSize(237, 50);
//     box1.setLocation(600, 68);
//     
//     // add ItemListener 
//  
//
//     // create labels 
//     JLabel select_appointment = new JLabel("select appointment"); 
//     select_appointment.setLocation(467, 84);
//     JLabel appointment_selected = new JLabel(); 
//     appointment_selected.setLocation(835, 84);
//
//     // set color of text 
//     select_appointment.setForeground(Color.red); 
//     appointment_selected.setForeground(Color.blue); 
////     appointment_selected.setText(" selected"); 
//     appointment_selected.setSize(159, 16);
//     select_appointment.setSize(121, 16);
//     contentPane.add(box1);
//     contentPane.add(select_appointment);
//     contentPane.add(appointment_selected);
//     box1.addActionListener (new ActionListener () {
//    	 
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				appointment_selected.setText(box1.getSelectedItem() + " selected"); 
//			
//				
//			}
//    	});
//     
//     box2 = new JComboBox(); 
//     box2.addItem("Please choose an option");
//     box2.addItem("Reschedule");
//     box2.addItem("Cancel");
//   
//     box2.setSize(237, 50);
//     box2.setLocation(600, 105);
//     contentPane.add(box2);
//     
//     JLabel option_selected = new JLabel(); 
//     
//     option_selected.setSize(159, 35);
//     option_selected.setLocation(845, 120);
//     option_selected.setForeground(Color.blue); 
//     contentPane.add(option_selected);
//     
//  
//
//  
//     box2.addActionListener (new ActionListener () {
//    	 
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				option_selected.setText(box2.getSelectedItem()+" "); 
//				String decision = (String) box2.getSelectedItem();
//				String str = (String)box1.getSelectedItem(); 
//		        String[] arrOfStr = str.split(" ", 2); 
//		         date_local = arrOfStr[0];
//		         time_local = arrOfStr[1];
//				
//				if(decision.equals("Cancel"))
//				{
//				   
//				    int result = JOptionPane.showConfirmDialog(null,patient_name_local + ", are you sure you want to cancel your appointment scheduled for " + date_local + " at " + time_local, "ManageAppointments",  JOptionPane.YES_NO_OPTION,
//				               JOptionPane.QUESTION_MESSAGE);
//				    	
//				    if (result  == JOptionPane.YES_OPTION) {
//				    	 try {
//			                 Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
//
//			                 String query = "DELETE FROM appointment_bookings where appointment_date = '"+date_local+"' and patient_name = '"+patient_name_local+"' and appointment_time = '"+time_local+"'";
//			                 
//
//			                 Statement sta = connection.createStatement();
//			                 int rs = sta.executeUpdate(query);
//			                 if (rs ==1)
//			                 {
//			                 	 JOptionPane.showMessageDialog(null,patient_name_local + " your appointment has been cancelled");
//			                 	String filePath = "/Users/harshmishra/Desktop/patientDetails/"+patient_name_local+".txt";
//			                 	try {
//			                 		
//									sc = new Scanner(new File(filePath));
//								} catch (FileNotFoundException e2) {
//									// TODO Auto-generated catch block
//									e2.printStackTrace();
//								}
//				                 //instantiating the StringBuffer class
//				                 StringBuffer buffer = new StringBuffer();
//				                 
//				                 //Reading lines of the file and appending them to StringBuffer
//				                 System.out.println(date_local);
//				                 while (sc.hasNextLine()) {
//				                	 buffer.append(sc.nextLine()+System.lineSeparator());
//				                	
//				                	
//				                	 String line = sc.nextLine();
//				                	 if(line.contains(date_local))
//				                	 {
//				                		
//				                		  lineToReplace = line;
//				            
//				                		  
//				                	 }
//				                	
//				                    
//				                 }
//				                 String fileContents = buffer.toString();
//				             
//				                 
//				                 //closing the Scanner object
//				                 sc.close();
//				               
//				               String cancel = " ";
//				              
//				             
//				                
//				                 //Replacing the old line with new line
//				                 fileContents = fileContents.replace(lineToReplace, cancel);
//				                 
//				                 //instantiating the FileWriter class
//				                 FileWriter writer;
//								try {
//									writer = new FileWriter(filePath);
//									 writer.append(fileContents);
//					                 writer.flush();
//								} catch (IOException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//			                 }
//			                 else
//			                 {
//			                	 JOptionPane.showMessageDialog(null,patient_name_local + " Oops, system error. Please try again later");
//			                	 
//			                 }
//			               
//			                
//			                 connection.close();
//			             } catch (Exception exception) {
//			                 exception.printStackTrace();
//			             }
//				    } else if (result  == JOptionPane.NO_OPTION) {
//				   	 JOptionPane.showMessageDialog(null,patient_name_local + " great, see you soon!");
//				   	 box1.setSelectedIndex(0);
//				   	 box2.setSelectedIndex(0);
//				   	 option_selected.setText("");
//				   	 appointment_selected.setText("");
//				    }
//				    
//				}
//				
//				else if(decision.equals("Reschedule"))
//						{
//					   JDateChooser dateChooser = new JDateChooser();
//			    	     contentPane.add(dateChooser);
//			    	     dateChooser.setBounds(666, 195, 195, 50);
//					
//					int result1 = JOptionPane.showConfirmDialog(null,patient_name_local + ", are you sure you want to reschedule your appointment scheduled for " + date_local + " at " + time_local, "ManageAppointments",  JOptionPane.YES_NO_OPTION,
//				               JOptionPane.QUESTION_MESSAGE);
//				    	
//				    if (result1  == JOptionPane.YES_OPTION) {
//				    	
//			               // "DELETE FROM appointment_bookings where appointment_date = '"+date_local+"' and patient_name = '"+patient_name_local+"' and appointment_time = '"+time_local+"'";
////			                 
////
////			                 Statement sta = connection.createStatement();
////			                 int rs1 = sta.executeUpdate(query);
////			                 if (rs1 ==1)
////			                 {
////			                	 ManageAppointments writeVar = new ManageAppointments();
////			                	 boolean local_check = true;
////			                	 writeVar.setBooleanVar(local_check);
////			                	 dispose();
////			                 
////			                 	 ScheduleAppointment fr1 = new ScheduleAppointment();
////		
////			                 	 fr1.setVisible(true);
////			                 	
////			                 }
////			                 else
////			                 {
////			                	 JOptionPane.showMessageDialog(null,patient_name_local + " Oops, system error. Please try again later");
////			                	 
////			                 }
//				    		 
//				    	   
//				    	     
//			                 JLabel lblNewLabel2 = new JLabel("Choose appointment date");  
//			                 contentPane.add(lblNewLabel2);
//			                 lblNewLabel2.setBounds(472, 209, 162, 16);
//			                
//			                 
//			                 JLabel lblNewLabel_1 = new JLabel("Choose Appointment Time");
//			                 contentPane.add(lblNewLabel_1);
//			                 lblNewLabel_1.setBounds(572, 305, 187, 16);
//			        
//			                 
//			              
//			  
//			                
//			                 JButton btnNewButton_2 = new JButton("confirm");
//			                 btnNewButton_2.addActionListener(new ActionListener() {
//			                 	public void actionPerformed(ActionEvent e) {
//			                 	
//			                 		 Get_the_date date_writer = new Get_the_date();
//			                 		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			                 		 
//			                 		  date_local2 = sdf.format(dateChooser.getDate());
//			                 		  
//			                 		  
//			            		        
//			            		        date_writer.setDate(date_local2);
//			            		        
//			            		        
//			            		      
//			                 	}
//			                 });
//			                 
//			                 
//			                 
//			                 JButton btnNewButton_1 = new JButton("Choose Time");
//			                 btnNewButton_1.addActionListener(new ActionListener() {
//			                 	public void actionPerformed(ActionEvent e) {
//			                 		 
//			                          ChooseTime_ScheduleAppointment fr3 = new ChooseTime_ScheduleAppointment();
//			                          fr3.setTitle("Available Appointment Slots");
//			                          fr3.setVisible(true);
//			                 	}
//			                 });
//			                 btnNewButton_1.setBounds(766, 300, 117, 29);
//			                 contentPane.add(btnNewButton_1);
//			                 btnNewButton_2.setBounds(891, 294, 117, 29);
//			                 contentPane.add(btnNewButton_2);
//			                 
//			                 JButton btnNewButton = new JButton("submit");
//			                 Get_the_date date_getter = new Get_the_date();
//			                 btnNewButton.addActionListener(new ActionListener() {
//			                 	public void actionPerformed(ActionEvent e) {
//			                 		 try {
//										Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
//										ChooseTime_ScheduleAppointment set_the_time = new ChooseTime_ScheduleAppointment();
//				                		  String newTime = set_the_time.getTime();
//				                		  
//				                		   date_local3 = date_getter.getDate();
//				                		
//				                		  String query = ("UPDATE appointment_bookings set appointment_time = '"+newTime+"', appointment_date = '"+date_local3+"' where appointment_date = '"+date_local+"' and appointment_time = '"+time_local+"'");
//				                		  Statement sta = connection.createStatement();
//				                		  int x = sta.executeUpdate(query);
//				                		  if (x ==1)
//							                 {
//							                 	 JOptionPane.showMessageDialog(null,patient_name_local + " your appointment has been rescheduled");
//							                 	String filePath = "/Users/harshmishra/Desktop/patientDetails/"+patient_name_local+".txt";
//								                 
//								                 //Instantiating the Scanner class to read the file
//								              
//												try {
//													sc = new Scanner(new File(filePath));
//												} catch (FileNotFoundException e2) {
//													// TODO Auto-generated catch block
//													e2.printStackTrace();
//												}
//								                 //instantiating the StringBuffer class
//								                 StringBuffer buffer = new StringBuffer();
//								                 //Reading lines of the file and appending them to StringBuffer
//								                 while (sc.hasNextLine()) {
//								                    buffer.append(sc.nextLine()+System.lineSeparator());
//								                 }
//								                 String fileContents = buffer.toString();
//								               
//								                 
//								                 //closing the Scanner object
//								                 sc.close();
//								                 String oldDate = date_local;
//								               
//								              
//								                 String date_local4 = date_getter.getDate();
//								                 String newDateTime = date_local4 +" Time : "+ newTime;
//								                 //Replacing the old line with new line
//								                 //fileContents = fileContents.replaceAll(oldDate, date_local4);
//								                 try {
//								                	int position = fileContents.lastIndexOf(oldDate);
//													writeToFile(filePath,newDateTime,position+3);
//												} catch (IOException e2) {
//													// TODO Auto-generated catch block
//													e2.printStackTrace();
//												}
//								                 
////								                 //instantiating the FileWriter class
////								                 FileWriter writer;
////												try {
////													writer = new FileWriter(filePath);
////													 writer.append(fileContents);
////									                 writer.flush();
////												} catch (IOException e1) {
////													// TODO Auto-generated catch block
////													e1.printStackTrace();
////												}
//							                 }
//							                 else
//							                 {
//							                	 JOptionPane.showMessageDialog(null,patient_name_local + " Oops, system error. Please try again later");
//							                	 
//							                 }
//				                		  
//				                		  
//				                		  
//									} catch (SQLException e1) {
//										// TODO Auto-generated catch block
//										e1.printStackTrace();
//									}
//
//			                        
//			                  		
////						                 int rs1 = sta.executeUpdate(query);
//			                 	}
//			                 });
//			                 btnNewButton.setBounds(815, 492, 117, 29);
//			                 contentPane.add(btnNewButton);
//			                 
//			                // connection.close();
//			                 
//			                
//			                
//			              
//			           
//				        
//				        
//					
//						}
//				    else if (result1  == JOptionPane.NO_OPTION) {
//					   	 JOptionPane.showMessageDialog(null,patient_name_local + " great, see you soon!");
//					   	 box1.setSelectedIndex(0);
//					   	 box2.setSelectedIndex(0);
//					   	 option_selected.setText("");
//					   	 appointment_selected.setText("");
//					 
//					    }
//				        
//				        
//					
//						}
//			
//				
//			}
// 	});
//     
//     
//    
//         // if the state combobox is changed 
////         if (e.getSource() == box1) { 
////   
////        	 appointment_selected.setText(box1.getSelectedItem() + " selected"); 
////         } 
//     
//  
//    
//jt.setBounds(210,84,200,300);          
//   
//contentPane.add(jt);
//
//
//
//
//////
//////        model.addRow(row);
////	
//	}
//	
//	
//	private static void writeToFile(String filePath, String data, int position)  
//            throws IOException {  
//        RandomAccessFile file = new RandomAccessFile(filePath, "rw");  
//        file.seek(position);  
//        file.write(data.getBytes());  
//        file.close();  
//    }  
//}
// String[][] dateTime = new String[][];
