package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;

import controller.*;
import model.Address;
import model.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.*;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

public class Login extends JFrame {

	private Bank bank;

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
	public Login(Bank bank) {
		super();
		this.bank = bank;
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

		UIManager.put("OptionPane.minimumSize", new Dimension(800, 800));
		UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 30));
		UIManager.put("PasswordField.font", new Font("Tahoma", Font.BOLD, 30));
		UIManager.put("Label.font", new Font("Tahoma", Font.BOLD, 30));
		UIManager.put("Button.font", new Font("Tahoma", Font.BOLD, 30));

		JButton button = new JButton("Sign In");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JTextField emailField = new JTextField();
				JPasswordField passwordField = new JPasswordField();

				Object[] fields = { "Email: ", emailField, "Password: ", passwordField };
				do {
					UIManager.put("OptionPane.minimumSize", new Dimension(600, 300));
					int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);

					if (reply == JOptionPane.OK_OPTION) {
						String email = emailField.getText();
						String password = String.copyValueOf(passwordField.getPassword());

						BankCustomer customer = bank.login(email, password);
						UIManager.put("OptionPane.minimumSize", new Dimension(50, 200));
						if (customer != null) {
							JOptionPane.showMessageDialog(null, "Login Successful!");
							break;
						}
						JOptionPane.showMessageDialog(null, "Email ID or Password incorrect!");
					}
					if (reply == JOptionPane.CANCEL_OPTION) {
						break;
					}

				} while (true);

			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 45));
		panel_center.add(button);

		JButton button_1 = new JButton("Sign Up");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 45));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JTextField nameField = new JTextField();
				JTextField emailField = new JTextField();
				JPasswordField passwordField = new JPasswordField();
				JTextField phoneField = new JTextField();
				JTextField streetField = new JTextField();
				JTextField houseField = new JTextField();
				JTextField cityField = new JTextField();
				JTextField stateField = new JTextField();
				JTextField zipcodeField = new JTextField();
				JTextField ssnField = new JTextField();
				JCheckBox checkingCheck = new JCheckBox();
				JCheckBox savingsCheck = new JCheckBox();

				Object[] fields = { "Name: ", nameField, "Email: ", emailField, "Password: ", passwordField,
						"Phone #: ", phoneField, "SSN: ", ssnField, "Street Address: ", streetField, "House #: ",
						houseField, "City: ", cityField, "State: ", stateField, "Zip Code: ", zipcodeField,
//						"Savings Account: ", savingsCheck, 
//						"Checking Account ", checkingCheck,
				};

				UIManager.put("OptionPane.layout", new GridLayout(11, 2));
				int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
				if (reply == JOptionPane.OK_OPTION) {
					String name = nameField.getText();
					String email = emailField.getText();
					String password = String.copyValueOf(passwordField.getPassword());
					String phone = phoneField.getText();
					String street = streetField.getText();
					String house = houseField.getText();
					String city = cityField.getText();
					String state = stateField.getText();
					String zipcode = zipcodeField.getText();
					String ssn = ssnField.getText();
//					boolean savings = savingsCheck.isSelected();
//					boolean checking = checkingCheck.isSelected();

					bank.addCustomer(name, new Address(house, street, city, zipcode, state), phone, ssn, email,
							password);
					UIManager.put("OptionPane.minimumSize", new Dimension(50, 200));
					JOptionPane.showMessageDialog(null, "Created New Customer. Please login");
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
