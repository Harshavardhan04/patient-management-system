package trial.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
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
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DoctorMenu extends JFrame {

	private JPanel contentPane;
	private JTable jt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorMenu frame = new DoctorMenu();
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
	public DoctorMenu() {
		setTitle("Doctor's Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton upcomingAppointmentsButton = new JButton("Upcoming Appointments");
		upcomingAppointmentsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		upcomingAppointmentsButton.setOpaque(true);
		upcomingAppointmentsButton.setBackground(new Color(153, 102, 204));
		upcomingAppointmentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DoctorUpcomingAppointments frame2 = new DoctorUpcomingAppointments();
				frame2.setVisible(true);
				dispose();

			}
		});
		upcomingAppointmentsButton.setBounds(252, 101, 340, 107);
		contentPane.add(upcomingAppointmentsButton);

		JButton patDirButton = new JButton("Patient Directory");
		patDirButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		patDirButton.setOpaque(true);
		patDirButton.setBackground(new Color(153, 102, 204));
		patDirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PatientDirectory frame = new PatientDirectory();
				frame.setVisible(true);
				dispose();
			}
		});
		patDirButton.setBounds(252, 267, 340, 107);
		contentPane.add(patDirButton);

		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		logoutButton.setBounds(782, 459, 158, 66);
		contentPane.add(logoutButton);

		logoutButton.setOpaque(true);
		logoutButton.setBackground(new Color(153, 102, 204));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(logoutButton, "Are you sure?");
				if (a == JOptionPane.YES_OPTION) {
					dispose();
					LoginScreen obj = new LoginScreen();
					obj.setTitle("Student-Login");
					obj.setVisible(true);
				}
				dispose();
				LoginScreen obj = new LoginScreen();

				obj.setTitle("Login");
				obj.setVisible(true);

			}
		});
	}
}

//JOptionPane.setRootFrame(null);
//ArrayList<String> times_to_display = new ArrayList<String>();
//ArrayList<String> dates_to_display = new ArrayList<String>();
// Date date_current = java.util.Calendar.getInstance().getTime();
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//String current_Date = sdf.format(date_current);
//try {
//	Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
//	
//	  String query =("Select * from appointment_bookings where appointment_date>='"+current_Date+"'");
//	  Statement sta = connection.createStatement();
//	  ResultSet rs =sta.executeQuery(query);
//	  while (rs.next())
//      {
// 
//    	dates_to_display.add(rs.getDate("appointment_date").toString());
//    	dates_to_display.sort((d1,d2) -> d1.compareTo(d2));
//    	
//      }
//    for (int i = 0; i< dates_to_display.size(); i++)
//    {
//    String query1 = "SELECT appointment_time FROM appointment_bookings where appointment_date = '"+dates_to_display.get(i)+"'";
//    Statement sta1 = connection.createStatement();
//    ResultSet rs1 = sta1.executeQuery(query1);
//    while (rs1.next())
//      {
//    	times_to_display.add(rs1.getTime("appointment_time").toString());
//      }
//    }
//    
//    String column[]={"Date", "Time"};       
//    DefaultTableModel tableModel = new DefaultTableModel(column, 0);
//    jt=new JTable(tableModel)
//    		{
//    	public boolean editCellAt(int row, int column, java.util.EventObject e) {
//            return false;
//    		}
//    		};
//    jt.setFocusable(false);
//    
//   String[] date_time_combo = new String[dates_to_display.size()+1];
//    
//    for(int j = 0; j<dates_to_display.size();j++)
//    {
//   String date = dates_to_display.get(j);
//   String time = times_to_display.get(j);
//   Object[] data = {date,time};
//   date_time_combo[j+1] = date + " " + time;
//   	
//tableModel.addRow(data);
//jt.setBounds(210,84,200,300);          
//
//contentPane.add(jt);
//
//      
//    }
//  
//    Border border = new LineBorder(Color.BLACK, 1, true);
//    jt.setBorder(border);
//	 
//	  
//} catch (SQLException e1) {
//	// TODO Auto-generated catch block
//	e1.printStackTrace();
//}
//jt.addMouseListener(new MouseAdapter() {
//     public void mouseClicked(MouseEvent me) {
//        if (me.getClickCount() == 2) {     // to detect doble click events
//           JTable target = (JTable)me.getSource();
//           int row = target.getSelectedRow(); // select a row
//           int column = target.getSelectedColumn(); // select a column
//          try {
//        	  JOptionPane.showMessageDialog(null,"Date: " + jt.getValueAt(row, column) + "Time:" + jt.getValueAt(row, column+1)); // get the value of a row and column.
//          }
//          
//          catch(ArrayIndexOutOfBoundsException exception )
//          {
//        	  JOptionPane.showMessageDialog(null,"Date: " + jt.getValueAt(row, column-1) + "Time:" + jt.getValueAt(row, column));
//          }
//        }
//     }
//  });