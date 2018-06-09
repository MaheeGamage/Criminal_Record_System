package activity;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Register_Criminal {

	private JFrame frame;
	private JTextField txtNIC;
	private JTextField txtFullName;
	private JTextField txtAddress;
	
	private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register_Criminal window = new Register_Criminal();
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
	public Register_Criminal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 576, 315);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Register");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(236, 24, 108, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNic = new JLabel("NIC");
		lblNic.setBounds(40, 88, 56, 16);
		frame.getContentPane().add(lblNic);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setBounds(40, 117, 74, 16);
		frame.getContentPane().add(lblFullName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(40, 146, 56, 16);
		frame.getContentPane().add(lblAddress);
		
		txtNIC = new JTextField();
		txtNIC.setBounds(155, 85, 351, 22);
		frame.getContentPane().add(txtNIC);
		txtNIC.setColumns(10);
		
		txtFullName = new JTextField();
		txtFullName.setColumns(10);
		txtFullName.setBounds(155, 114, 351, 22);
		frame.getContentPane().add(txtFullName);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(155, 143, 351, 22);
		frame.getContentPane().add(txtAddress);
		
		JButton btnAddRecord = new JButton("Add Record");
		btnAddRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/criminal_records","root","");
					Statement stmt = con.createStatement();
					String sql2 = "INSERT INTO criminal_record (`id`, `NIC`, `Fullname`, `Address`) VALUES (NULL, '"+ txtNIC.getText() + "', '"+ txtFullName.getText() +"', '"+ txtAddress.getText()+"')";
					
					// PreparedStatements can use variables and are more efficient
					preparedStatement = con.prepareStatement("INSERT INTO criminal_record (`id`, `NIC`, `Fullname`, `Address`) VALUES (NULL, ?, ?, ?)");
		            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
		            // Parameters start with 1
		            preparedStatement.setString(1, txtNIC.getText());
		            preparedStatement.setString(2, txtFullName.getText());
		            preparedStatement.setString(3, txtAddress.getText());
		            preparedStatement.executeUpdate();

		            preparedStatement = con.prepareStatement("SELECT * FROM criminal_record WHERE NIC ='"+ txtNIC.getText() +"' ");
		            resultSet = preparedStatement.executeQuery();
//		            writeResultSet(resultSet);
					
					if(resultSet.next()) {
						frame.dispose();
						JOptionPane.showMessageDialog(null, "Record Inserted Successfully");
						Main_window main = new Main_window();
						main.NewScreen();
					}
					else {
						JOptionPane.showMessageDialog(null, "Record Insertion failed");
					}
					con.close();
						
				} 
				catch(Exception e) 
				{
					System.out.print(e);
				}
				
			}
		});
		btnAddRecord.setBounds(224, 203, 108, 25);
		frame.getContentPane().add(btnAddRecord);
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
