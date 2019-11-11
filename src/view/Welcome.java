package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import controller.Bank;
import controller.BankBranch;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Welcome extends JFrame {

	private JFrame loginFrame;
	private BankBranch bank;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Bank bank = new Bank("My Fancy Bank");
					BankBranch bankBranch = new BankBranch();
					bank.addBranch(bankBranch);
					Welcome window = new Welcome(bankBranch);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public Welcome(BankBranch bank) throws IOException {

		super();
		this.bank = bank;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {

		this.setTitle("My Fancy Bank ATM\r\n");
		this.setBounds(100, 100, 1000, 800);
		this.setLocationRelativeTo(null);
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
		btnManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (bank.getManagers().size() == 0)
					JOptionPane.showMessageDialog(null, "Manager first needs to be created using the Sign up option");
				loginFrame = new ManagerLogin(bank);
				setVisible(false);
				loginFrame.setVisible(true);
			}
		});

		JButton btnCustomer = new JButton("Customer");
		btnCustomer.setFont(new Font("Tahoma", Font.PLAIN, 55));
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bank.getCustomers().size() == 0)
					JOptionPane.showMessageDialog(null, "Customer first needs to be created using the Sign up option");
				loginFrame = new CustomerLogin(bank);
				setVisible(false);
				loginFrame.setVisible(true);
			}
		});
		panel.add(btnCustomer);

		JLabel background = new JLabel();
		ImageIcon icon = new ImageIcon("src/img/bank.jpg");
		background.setIcon(icon);
		JPanel panel_1 = new JPanel();
		background.setBounds(0, 0, 515, 515);
		panel_1.add(background);
		getContentPane().add(panel_1, BorderLayout.CENTER);
	}

}
