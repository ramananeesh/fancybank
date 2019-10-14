package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.FlowLayout;

public class Welcome {

	private JFrame frmWelcomeToMy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frmWelcomeToMy.setVisible(true);
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWelcomeToMy = new JFrame();
		frmWelcomeToMy.setTitle("My Fancy Bank ATM\r\n");
		frmWelcomeToMy.setBounds(100, 100, 545, 380);
		frmWelcomeToMy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcomeToMy.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcomeToMy = new JLabel("Welcome to My Fancy Bank");
		lblWelcomeToMy.setHorizontalAlignment(SwingConstants.CENTER);
		frmWelcomeToMy.getContentPane().add(lblWelcomeToMy, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frmWelcomeToMy.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnManager = new JButton("Manager ");
		panel.add(btnManager);
		
		JButton btnCustomer = new JButton("Customer");
		panel.add(btnCustomer);
	}

}
