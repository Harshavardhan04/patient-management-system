package trial.swing;

import java.awt.BorderLayout;
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
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class PriorConsultations extends JFrame {
	 LoginScreen LoginScreen1 = new LoginScreen();
	    private String patient_name_local = LoginScreen1.getName();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  static PriorConsultations fr2 = new PriorConsultations();
	private JTable jt;
	private JPanel contentPane;
	static String date_collect1; 
	  static String time_collect1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PriorConsultations frame = new PriorConsultations();
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
	public PriorConsultations() {

		setTitle("Previous Consultations");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 204));
        setContentPane(contentPane);
        contentPane.setLayout(null);
		ArrayList<String> times_to_display = new ArrayList<String>();
		ArrayList<String> dates_to_display = new ArrayList<String>();
        

		Date date_current = java.util.Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_Date = sdf.format(date_current);
		
		
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration", "harshM", "Brentwood1");
			
    		  String query =("Select * from appointment_bookings where appointment_date<='"+current_Date+"' and patient_name = '"+patient_name_local+"'");  		  
    		  Statement sta = connection.createStatement();
    		  ResultSet rs =sta.executeQuery(query);
    		  while (rs.next())
		      {
         
            	dates_to_display.add(rs.getDate("appointment_date").toString());
            	dates_to_display.sort((d1,d2) -> d1.compareTo(d2));
            	
		      }
            for (int i = 0; i< dates_to_display.size(); i++)
            {
            String query1 = "SELECT appointment_time FROM appointment_bookings where appointment_date = '"+dates_to_display.get(i)+"'";
            Statement sta1 = connection.createStatement();
            ResultSet rs1 = sta1.executeQuery(query1);
            while (rs1.next())
		      {
            	times_to_display.add(rs1.getTime("appointment_time").toString());
		      }
            }
            
            String column[]={"Date", "Time"};       
            DefaultTableModel tableModel = new DefaultTableModel(column, 0);
            jt=new JTable(tableModel)
            		{
            	public boolean editCellAt(int row, int column, java.util.EventObject e) {
                    return false;
            		}
            		};
            jt.setFocusable(false);
            
           String[] date_time_combo = new String[dates_to_display.size()+1];
            
            for(int j = 0; j<dates_to_display.size();j++)
            {
           String date = dates_to_display.get(j);
           String time = times_to_display.get(j);
           Object[] data = {date,time};
           date_time_combo[j+1] = date + " " + time;
           	
       tableModel.addRow(data);
       jt.setBounds(210,84,200,300);          
       
       contentPane.add(jt);

              
            }
          
            Border border = new LineBorder(Color.BLACK, 1, true);
            jt.setBorder(border);
    		 
    		  
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jt.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2) {     // to detect doble click events
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               int column = target.getSelectedColumn(); // select a column
	              try {
	            	  date_collect1 = (String) jt.getValueAt(row, column);
	            	  time_collect1 = (String) jt.getValueAt(row,column+1);
	            	  
  	            }
	              
	              catch(ArrayIndexOutOfBoundsException exception )
	              {
	            	  date_collect1 = (String) jt.getValueAt(row, column-1);
	            	  time_collect1 = (String) jt.getValueAt(row,column);
	              }
	              ViewDoctorNotes frame3 = new ViewDoctorNotes();
	              frame3.setVisible(true);
	              dispose();
	              
	            }
	         }
		});
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
		 
	}

}
