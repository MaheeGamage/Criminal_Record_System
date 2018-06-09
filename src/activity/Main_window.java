package activity;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Event.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Main_window {

	private JFrame frame;
	private JTextField txtSearch;
	
	private String attr = "NIC";

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_window window = new Main_window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 667, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(12, 13, 56, 16);
		frame.getContentPane().add(lblSearch);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 31, 613, 2);
		frame.getContentPane().add(separator);
		
		String[] attribute = { "NIC", "Full Name", "Address" };
		
		JComboBox comboBox = new JComboBox(attribute);
		comboBox.setBounds(22, 41, 135, 22);
		frame.getContentPane().add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
		        String attribute = (String)cb.getSelectedItem();

		        switch (attribute) {
		        	case "NIC": 
		        		attr = "NIC";
		        		break;
		        	case "Full Name": 
		        		attr = "Fullname";
		        		break;
		        	case "Address": 
		        		attr = "Address";
		        		break;
		        }
		        
		        System.out.println(attr);
			}
		});
		
		txtSearch = new JTextField();
		txtSearch.setBounds(169, 41, 254, 22);
		frame.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/criminal_records","root","");
					Statement stmt = con.createStatement();
					
					String sql = "SELECT * FROM `criminal_record` WHERE '"+ attr +"' = '"+ btnSearch.getText() +"'";
					
					ResultSet rs= stmt.executeQuery(sql);
					
					writeResultSet(rs);
					
					if(rs.next()) {
						rs.first();
						System.out.println(rs);
					}
					else {
						
					}
					con.close();
						
				} 
				catch(Exception err) 
				{
					System.out.print(err);
				}
			}
		});
		btnSearch.setBounds(449, 40, 97, 25);
		frame.getContentPane().add(btnSearch);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFiles = new JMenu("Files");
		menuBar.add(mnFiles);
		
		JMenuItem mnRegister = new JMenuItem("Add Record");
		mnFiles.add(mnRegister);
		
		mnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register_Criminal main = new Register_Criminal();
				main.NewScreen();
			}
		});
		
		JMenuItem mnExit = new JMenuItem("Exit");
		mnFiles.add(mnExit);
		
		class exitaction implements ActionListener{
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		}
		
		mnExit.addActionListener(new exitaction());
		
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String nic = resultSet.getString("NIC");
            String fullname = resultSet.getString("Fullname");
            String address = resultSet.getString("Address");
            System.out.println("NIC: " + nic);
            System.out.println("Full Name: " + fullname);
            System.out.println("Address: " + address);
        }
    }
}
