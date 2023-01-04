package trial.swing;

import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;

public class DoctorUpcomingAppointments extends JFrame {
	private JTable jt;
	private JPanel contentPane;
	static String date_collect;
	static String time_collect;
	private String choice;
	private String date_chosen;
	private static String query;
	static DefaultTableModel tableModel;

	public static String getDateRetreive() {
		return date_collect;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorUpcomingAppointments frame = new DoctorUpcomingAppointments();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static void update(String choice, String date_chosen, boolean check) {

		int rowCount = tableModel.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}


		Set<PatientInfoForDoctor> pDetailsSet = new TreeSet<>();
		Date date_current = java.util.Calendar.getInstance().getTime();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String current_Date = sdf2.format(date_current);
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");
			//return different appointments depending on filters
			if (check) {
				query = ("Select * from appointment_bookings where appointment_date>='" + current_Date + "'");
			} else if (date_chosen == null) {
				if (choice.equals("All appointments")) {
					query = ("Select * from appointment_bookings where appointment_date>='" + current_Date + "'");
				} else if (choice.equals("New appointments")) {
					query = ("SELECT * from appointment_bookings where appointment_date>='" + current_Date
							+ "' and newOrFollow = 'N'");
				} else if (choice.equals("Follow appointments")) {
					query = ("SELECT * from appointment_bookings where appointment_date >= '" + current_Date
							+ "' and newOrFollow = 'F'");
				}

			} else {
				if (choice.equals("All appointments")) {
					query = ("Select * from appointment_bookings where appointment_date ='" + date_chosen + "'");
				} else if (choice.equals("New appointments") && date_chosen != null) {
					query = ("SELECT * from appointment_bookings where appointment_date='" + date_chosen
							+ "' and newOrFollow = 'N'");
				} else if (choice.equals("Follow appointments") && date_chosen != null) {
					query = ("SELECT * from appointment_bookings where appointment_date >= '" + date_chosen
							+ "' and newOrFollow = 'F'");
				} else {
					query = ("Select * from appointment_bookings where appointment_date ='" + date_chosen + "'");
				}

			}

			Statement sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(query);
			String pName = "";
			
			//add details to object
			while (rs.next()) {
				PatientInfoForDoctor infoForDoctor = new PatientInfoForDoctor();
				infoForDoctor.setpDate(rs.getDate("appointment_date").toString());
				infoForDoctor.setpName(rs.getString("patient_name"));
				infoForDoctor.setpTime(rs.getTime("appointment_time").toString());
				pDetailsSet.add(infoForDoctor);

			}
			
			//enhanced for loop to add each returned appoinment record to the table
			for (PatientInfoForDoctor pInfo : pDetailsSet) {

				Object[] data = { pInfo.getpName() + ".  " + pInfo.getpDate() + ".  " + pInfo.getpTime() };
				tableModel.addRow(data);
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// public static

	/**
	 * Create the frame.
	 */
	public DoctorUpcomingAppointments() {
		setTitle("Booked appointments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();

		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(217, 113, 193, 26);
		contentPane.add(dateChooser);

		JComboBox<String> comboBox = new JComboBox();
		comboBox.addItem("All appointments");
		comboBox.addItem("New appointments");
		comboBox.addItem("Follow appointments");

		String column[] = { "Date" };
		tableModel = new DefaultTableModel(column, 0);
		jt = new JTable(tableModel) {
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
				return false;
			}
		};
		jt.setFocusable(false);
		jt.setBounds(312, 165, 255, 300);
		contentPane.add(jt);
		Border border = new LineBorder(Color.BLACK, 1, true);
		jt.setBorder(border);

		comboBox.setBounds(420, 113, 232, 27);
		contentPane.add(comboBox);

		JCheckBox chckbxNewCheckBox = new JCheckBox("View All Appointments", false);
		chckbxNewCheckBox.setFont(new Font("Lucide Grande", Font.BOLD, 18));
		chckbxNewCheckBox.setBounds(313, 78, 332, 23);
		contentPane.add(chckbxNewCheckBox);

		JButton updateButton = new JButton("update");
		updateButton.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		updateButton.setOpaque(true);
		updateButton.setForeground(new Color(0, 0, 0));
		updateButton.setBackground(new Color(153, 102, 204));
		updateButton.setBounds(697, 113, 117, 29);
		contentPane.add(updateButton);

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

		JLabel upcomingAppsLbl = new JLabel("Upcoming Appointments");
		upcomingAppsLbl.setFont(new Font("Lucide Grande", Font.BOLD, 18));
		upcomingAppsLbl.setBounds(327, 7, 255, 45);
		contentPane.add(upcomingAppsLbl);

		updateButton.addActionListener(new ActionListener() {
			boolean check;

			public void actionPerformed(ActionEvent e) {

				if (chckbxNewCheckBox.isSelected()) {
					comboBox.setSelectedIndex(0);
					dateChooser.setCalendar(null);
					check = true;
				} else {
					check = false;
				}
				choice = (String) comboBox.getSelectedItem();
				if (choice == null) {
					choice = "All appointments";
				}

				if (dateChooser.getDate() == null) {
					update(choice, null, check);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					date_chosen = sdf.format(dateChooser.getDate());
					// run the method
					update(choice, date_chosen, check);
				}
			}
		});

		jt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) { // to detect doble click events
					JTable target = (JTable) me.getSource();
					int row = target.getSelectedRow(); // select a row
					int column = target.getSelectedColumn(); // select a column

					String rowDetails = (String) jt.getValueAt(row, column);
					List<String> items = Arrays.asList(rowDetails.split(".  "));

					date_collect = items.get(1);

					time_collect = items.get(2);

					DoctorViewPatientDetails frame3 = new DoctorViewPatientDetails();
					frame3.setVisible(true);
					dispose();

				}
			}
		});

	}
}



//while (rs.next())
//{
//  pNameSet.add(rs.getString(0));
//	dates_to_display.add(rs.getDate("appointment_date").toString());
//	dates_to_display.sort((d1,d2) -> d1.compareTo(d2));
//	
//}
//for (int i = 0; i< dates_to_display.size(); i++)
//{
//String query1 = "SELECT appointment_time FROM appointment_bookings where appointment_date = '"+dates_to_display.get(i)+"'";
//Statement sta1 = connection.createStatement();
//ResultSet rs1 = sta1.executeQuery(query1);
//while (rs1.next())
//{
//	times_to_display.add(rs1.getTime("appointment_time").toString());
//}
//}
//
//
//
//String[] date_time_combo = new String[dates_to_display.size()+1];
//
//for(int j = 0; j<dates_to_display.size();j++)
//{
//	String date = dates_to_display.get(j);
//  String time = times_to_display.get(j);
//  
//
//
// 
// 
// Object[] data = {date,time};
// date_time_combo[j+1] = date + " " + time;
//	
// tableModel.addRow(data);
//
//
//
//
//
//}

//ArrayList<String> times_to_display = new ArrayList<>();
//ArrayList<String> dates_to_display = new ArrayList<>();
