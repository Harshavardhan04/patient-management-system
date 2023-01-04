package trial.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChooseTime_ScheduleAppointment extends JFrame {
	private static String time;

	public void setTime(String obtainTime) {
		time = obtainTime;
	}

	public String getTime() {
		return time;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseTime_ScheduleAppointment frame = new ChooseTime_ScheduleAppointment();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChooseTime_ScheduleAppointment() {
		setTitle("Choose Appointment Time");
		Get_the_date date_writer = new Get_the_date();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		ArrayList<String> times = new ArrayList<String>();
		ArrayList<String> timesToRemove = new ArrayList<String>();

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM",
					"Brentwood1");

			String query = "SELECT appointment_time FROM appointment_bookings where appointment_date = '"
					+ date_writer.getDate() + "'";

			Statement sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(query);
			while (rs.next()) {

				timesToRemove.add(rs.getTime("appointment_time").toString());
			}

			connection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		times.add("09:00:00");
		times.add("10:00:00");
		times.add("11:00:00");
		times.add("12:00:00");
		times.add("13:00:00");
		for (int a = 0; a < timesToRemove.size(); a++) {
			if (times.contains(timesToRemove.get(a))) {
				times.remove(timesToRemove.get(a));
			}
		}

		final JList<String> list = new JList(times.toArray());
		getContentPane().add(list);
		JButton btn = new JButton(" Done ");
		getContentPane().add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				String msg = ("Your Chosen time is" + list.getSelectedValue());
				ChooseTime_ScheduleAppointment set_the_time = new ChooseTime_ScheduleAppointment();
				set_the_time.setTime(list.getSelectedValue());

				JOptionPane.showMessageDialog(null, msg);
				dispose();

			}

		});

		setSize(250, 250);
	}

}

// System.out.println(rs.getTime("appointment_time").toString());
//System.out.println(set_the_time.getTime());
//ScheduleAppointment switchBack = new ScheduleAppointment();
//switchBack.setVisible(true);
// System.out.println("In the new class the date is" +date_writer.getDate());
/**
 * Create the frame.
 */

// select appointment_time from appointment_bookings
//where appointment_date = '2020-10-17';
