package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CustomerLogin {

	private JFrame frmMyFancyBank;
	private JTextField signupName;
	private JTextField signupEmail;
	private JTextField signupPhone;
	private JTextField signupSsn;
	private JTextField signupStreet;
	private JTextField signupHouse;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerLogin window = new CustomerLogin();
					window.frmMyFancyBank.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMyFancyBank = new JFrame();
		frmMyFancyBank.setTitle("My Fancy Bank");
		frmMyFancyBank.setBounds(100, 100, 800, 500);
		frmMyFancyBank.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMyFancyBank.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel signupPanel = new JPanel();
		frmMyFancyBank.getContentPane().add(signupPanel, BorderLayout.WEST);
		signupPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblName = new JLabel("Name: ");
		signupPanel.add(lblName);
		
		signupName = new JTextField();
		signupPanel.add(signupName);
		signupName.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email: ");
		signupPanel.add(lblEmail);
		
		signupEmail = new JTextField();
		signupPanel.add(signupEmail);
		signupEmail.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number: ");
		signupPanel.add(lblPhoneNumber);
		
		signupPhone = new JTextField();
		signupPanel.add(signupPhone);
		signupPhone.setColumns(10);
		
		JLabel lblSsn = new JLabel("SSN: ");
		signupPanel.add(lblSsn);
		
		signupSsn = new JTextField();
		signupPanel.add(signupSsn);
		signupSsn.setColumns(10);
		
		JLabel lblStreetAddress = new JLabel("Street Address: ");
		signupPanel.add(lblStreetAddress);
		
		signupStreet = new JTextField();
		signupPanel.add(signupStreet);
		signupStreet.setColumns(10);
		
		JLabel lblHouse = new JLabel("House #: ");
		signupPanel.add(lblHouse);
		
		signupHouse = new JTextField();
		signupPanel.add(signupHouse);
		signupHouse.setColumns(10);
		
		JLabel lblState = new JLabel("State");
		signupPanel.add(lblState);
		
		textField = new JTextField();
		signupPanel.add(textField);
		textField.setColumns(10);
		
		JPanel loginPanel = new JPanel();
		frmMyFancyBank.getContentPane().add(loginPanel, BorderLayout.EAST);
	}

}
