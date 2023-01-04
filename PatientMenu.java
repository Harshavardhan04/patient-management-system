package trial.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class PatientMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton logoutButton;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientMenu frame = new PatientMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PatientMenu() {

	}

	/**
	 * Create the frame.
	 */
	public PatientMenu(String userSes) {
		setBounds(450, 190, 1014, 597);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton logoutButton = new JButton("Logout");
		logoutButton.setOpaque(true);
		logoutButton.setBackground(new Color(153, 102, 204));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(logoutButton, "Are you sure?");
				// JOptionPane.setRootFrame(null);
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
		logoutButton.setBounds(782, 463, 187, 62);
		getContentPane().add(logoutButton);
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
//schedApp Button directs patient to schedule appointment frame
		JButton schedApp = new JButton("Schedule appointment");
		schedApp.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		schedApp.setOpaque(true);
		schedApp.setBackground(new Color(153, 102, 204));
		schedApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScheduleAppointment frame2 = new ScheduleAppointment();
				dispose();
				frame2.setVisible(true);
			}
		});
		schedApp.setBounds(209, 345, 551, 88);
		contentPane.add(schedApp);

//manApp Button directs patient to schedule appointment frame		
		JButton manApp = new JButton("Manage existing appointments");
		manApp.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		manApp.setOpaque(true);
		manApp.setBackground(new Color(153, 102, 204));
		manApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ManageAppointments frame3 = new ManageAppointments();
				frame3.setVisible(true);
			}
		});
		manApp.setBounds(209, 196, 551, 88);
		contentPane.add(manApp);

		JButton priorApps = new JButton("Prior consultations");
		priorApps.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		priorApps.setOpaque(true);
		priorApps.setBackground(new Color(153, 102, 204));
		priorApps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PriorConsultations frame = new PriorConsultations();
				dispose();
				frame.setVisible(true);
			}
		});
		priorApps.setBounds(209, 57, 551, 88);
		contentPane.add(priorApps);
	}
}

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(450, 190, 1014, 597);
//        setResizable(false);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//        
//        JButton logoutButton = new JButton("logout");
//    	logoutButton.addActionListener(new ActionListener() {
//    		public void actionPerformed(ActionEvent e) {
//    			int a = JOptionPane.showConfirmDialog(logoutButton, "Are you sure?");
//                // JOptionPane.setRootFrame(null);
//                if (a == JOptionPane.YES_OPTION) {
//                    dispose();
//                    LoginScreen obj = new LoginScreen();
//                    obj.setTitle("Student-Login");
//                    obj.setVisible(true);
//                }
//                dispose();
//                LoginScreen obj = new LoginScreen();
//
//                obj.setTitle("Login");
//                obj.setVisible(true);
//    			
//    		}
//    	});
//    	logoutButton.setBounds(618, 281, 156, 35);
//    	getContentPane().add(logoutButton);
//    	logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 22));

//        logoutButton = new JButton("logout");
//        logoutButton.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		
//        		int a = JOptionPane.showConfirmDialog(logoutButton, "Are you sure?");
//                // JOptionPane.setRootFrame(null);
//                if (a == JOptionPane.YES_OPTION) {
//                    dispose();
//                    LoginScreen obj = new LoginScreen();
//                    obj.setTitle("Student-Login");
//                    obj.setVisible(true);
//                }
//                dispose();
//                LoginScreen obj = new LoginScreen();
//
//                obj.setTitle("Login");
//                obj.setVisible(true);
//        		
//        	}
//        });
//        logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
//        logoutButton.setBounds(700, 447, 259, 74);
//        contentPane.add(logoutButton);

//        JButton btnNewButton = new JButton("Logout");
//        btnNewButton.setForeground(new Color(0, 0, 0));
//        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
//     
//        btnNewButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
//                // JOptionPane.setRootFrame(null);
//                if (a == JOptionPane.YES_OPTION) {
//                    dispose();
//                    LoginScreen obj = new LoginScreen();
//                    obj.setTitle("Student-Login");
//                    obj.setVisible(true);
//                }
//                dispose();
//                LoginScreen obj = new LoginScreen();
//
//                obj.setTitle("Login");
//                obj.setVisible(true);
//
//            }
//        });
//        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
//        btnNewButton.setBounds(399, 447, 259, 74);
//        contentPane.add(btnNewButton);
//        JButton button = new JButton("Change-password\r\n");
//        button.setBackground(UIManager.getColor("Button.disabledForeground"));
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                ChangePassword bo = new ChangePassword(userSes);
//                bo.setTitle("Change Password");
//                bo.setVisible(true);
//
//            }
//        });
//        button.setFont(new Font("Tahoma", Font.PLAIN, 35));
//        button.setBounds(247, 320, 491, 114);
//        contentPane.add(button);
// public PatientMenu() {

//    	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	        setBounds(450, 190, 1014, 597);
//    	        setResizable(false);
//    	        contentPane = new JPanel();
//    	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//    	        setContentPane(contentPane);
//    	        contentPane.setLayout(null);
//    	        
//    	        JButton logoutButton = new JButton("logout");
//    	    	logoutButton.addActionListener(new ActionListener() {
//    	    		public void actionPerformed(ActionEvent e) {
//    	    			int a = JOptionPane.showConfirmDialog(logoutButton, "Are you sure?");
//    	                // JOptionPane.setRootFrame(null);
//    	                if (a == JOptionPane.YES_OPTION) {
//    	                    dispose();
//    	                    LoginScreen obj = new LoginScreen();
//    	                    obj.setTitle("Student-Login");
//    	                    obj.setVisible(true);
//    	                }
//    	                dispose();
//    	                LoginScreen obj = new LoginScreen();
//
//    	                obj.setTitle("Login");
//    	                obj.setVisible(true);
//    	    			
//    	    		}
//    	    	});
//    	    	logoutButton.setBounds(665, 377, 156, 35);
//    	    	getContentPane().add(logoutButton);
//    	    	logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
//    	    	
//    	    	JButton schedApp = new JButton("Schedule appointment");
//    	    	schedApp.addActionListener(new ActionListener() {
//    	    		public void actionPerformed(ActionEvent e) {
//    	    			
//    	    			
//    	    			
//    	    		}
//    	    	});
//    	    	schedApp.setBounds(298, 212, 184, 29);
//    	    	contentPane.add(schedApp);
//    	    	
//    	    	JButton manApp = new JButton("Manage existing appointments");
//    	    	manApp.setBounds(273, 150, 238, 29);
//    	    	contentPane.add(manApp);

// }
