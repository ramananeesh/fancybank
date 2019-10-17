package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controller.Bank;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Welcome extends JFrame{

	private Login loginFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Welcome() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setTitle("My Fancy Bank ATM\r\n");
		this.setBounds(100, 100, 1400, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcomeToMy = new JLabel("Welcome to My Fancy Bank");
		lblWelcomeToMy.setFont(new Font("Tahoma", Font.PLAIN, 64));
		lblWelcomeToMy.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblWelcomeToMy, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnManager = new JButton("Manager ");
		btnManager.setFont(new Font("Tahoma", Font.PLAIN, 55));
		panel.add(btnManager);
		
		JButton btnCustomer = new JButton("Customer");
		btnCustomer.setFont(new Font("Tahoma", Font.PLAIN, 55));
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame=new Login(new Bank());
				loginFrame.setVisible(true);
			}
		});
		panel.add(btnCustomer);
	}

}