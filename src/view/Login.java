package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Login extends JFrame {

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Login window = new Login();
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Login() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_center = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_center.getLayout();
		panel.add(panel_center, BorderLayout.CENTER);
		
		JButton button = new JButton("Sign In");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.minimumSize",new Dimension(800, 800));
				UIManager.put("TextField.font", new Font("Tahoma", Font.BOLD, 45));
				UIManager.put("PasswordField.font", new Font("Tahoma", Font.BOLD, 45));
				UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 45));
				UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 40));
				
				JTextField emailField = new JTextField();
				JPasswordField passwordField = new JPasswordField();
				
				Object[] fields = {
						"Email: ", emailField,
						"Password: ", passwordField
				};
				
				int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
				
				if(reply==JOptionPane.OK_OPTION) {
					String email = emailField.getText();
					String password=String.copyValueOf(passwordField.getPassword());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 45));
		panel_center.add(button);
		
		JButton button_1 = new JButton("Sign Up");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 45));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UIManager.put("OptionPane.minimumSize",new Dimension(800, 800));
				UIManager.put("TextField.font", new Font("Tahoma", Font.BOLD, 45));
				UIManager.put("PasswordField.font", new Font("Tahoma", Font.BOLD, 45));
				UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 45));
				UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 40));
				JTextField nameField = new JTextField();
				JTextField emailField = new JTextField();
				JPasswordField passwordField = new JPasswordField();
				JTextField phoneField = new JTextField();
				JTextField streetField = new JTextField();
				JTextField houseField =new JTextField();
				JTextField stateField = new JTextField();
				JTextField zipcodeField = new JTextField();
				JTextField ssnField = new JTextField();
				
				Object[] fields = {
						"Name: ", nameField,
						"Email: ", emailField,
						"Password: ", passwordField,
						"Phone #: ", phoneField,
						"SSN: ", ssnField,
						"Street Address: ", streetField,
						"House #: ", houseField,
						"State: ", stateField, 
						"Zip Code: ", zipcodeField
				};
				

				int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
				if(reply==JOptionPane.OK_OPTION) {
					String name = nameField.getText();
					String email = emailField.getText();
					String password=String.copyValueOf(passwordField.getPassword());
					String phone=phoneField.getText();
					String street=streetField.getText();
					String house=houseField.getText();
					String state=stateField.getText();
					String zipcode=zipcodeField.getText();
					String ssn=ssnField.getText();
				}
			}
		});
		panel_center.add(button_1);
		
		JPanel panel_1 = new JPanel();
		this.getContentPane().add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		this.getContentPane().add(panel_2, BorderLayout.NORTH);
		
		JLabel lblChooseFromAn = new JLabel("Choose from an option below:");
		lblChooseFromAn.setFont(new Font("Tahoma", Font.PLAIN, 55));
		panel_2.add(lblChooseFromAn);
		
		JPanel panel_3 = new JPanel();
		this.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		JPanel panel_4 = new JPanel();
		this.getContentPane().add(panel_4, BorderLayout.EAST);
		
		
	}

}
