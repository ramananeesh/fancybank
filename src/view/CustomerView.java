package view;

import java.awt.EventQueue;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.border.Border;

import model.*;

import java.awt.Font;

import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;

import controller.Bank;
import javafx.collections.SetChangeListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerView extends JFrame implements Observer {
	private BankCustomer customer;
	private Bank bank;
	
	private JList<String> accountsList;
	private JLabel lblCustomerView;
	private DefaultListModel<String> model;
	
	
	/**
	 * Create the application.
	 */
	public CustomerView(Bank bank,BankCustomer customer) {
		
		this.bank = bank;
		this.customer = this.bank.getCustomerByEmail(customer.getEmail());
		this.bank.addObserver(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 1000, 674);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		lblCustomerView = new JLabel("Customer View ");
		lblCustomerView.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblCustomerView.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCustomerView, BorderLayout.NORTH);
		
		JPanel westPanel = new JPanel();
		getContentPane().add(westPanel, BorderLayout.WEST);
		westPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAccounts = new JLabel("Accounts");
		lblAccounts.setFont(new Font("Tahoma", Font.PLAIN, 36));
		westPanel.add(lblAccounts, BorderLayout.NORTH);
		
		JPanel accountsPanel = new JPanel();
//		accountsPanel.setSize(500, 500);
		westPanel.add(accountsPanel, BorderLayout.CENTER);
		
		model = new DefaultListModel<>();
		model = addAccountsToList(this.customer,model);
		accountsList = new JList<String>(model);
		accountsList.setVisible(true);
		
		accountsList.setVisibleRowCount(5);
		accountsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountsList.setBorder(new EmptyBorder(5, 5, 5, 5));
		accountsList.setFont(new Font("Tahoma", Font.PLAIN, 30));
		accountsPanel.add(accountsList);
		
		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel btnsPanel = new JPanel();
		centerPanel.add(btnsPanel, BorderLayout.CENTER);
		btnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnWithdraw);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnDeposit);
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnTransfer);
		
		JButton btnAddAccount = new JButton("Add Account");
		btnAddAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JRadioButton savings = new JRadioButton();
				JRadioButton checking = new JRadioButton();
				
				savings.setSelected(true);
				UIManager.put("OptionPane.minimumSize", new Dimension(600, 300));
				
				Object[] fields = {
					"Savings ", savings,
					"Checking ", checking,
				};
				
				int reply = JOptionPane.showConfirmDialog(null, fields, "Choose Account Type", JOptionPane.OK_CANCEL_OPTION);
				
				if(reply==JOptionPane.OK_OPTION) {
					String accountType; 
					if(savings.isSelected())
						accountType = "Savings";
					else
						accountType = "Checking";
					bank.addAccount(customer, accountType);
				}
			}
		});
		btnAddAccount.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnAddAccount);
		
		JLabel messageLabel = new JLabel("Message");
		messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(messageLabel, BorderLayout.SOUTH);
		
		JPanel eastPanel = new JPanel();
		getContentPane().add(eastPanel, BorderLayout.EAST);
		GridBagLayout gbl_eastPanel = new GridBagLayout();
		gbl_eastPanel.columnWidths = new int[]{376, 0};
		gbl_eastPanel.rowHeights = new int[]{265, 265, 0};
		gbl_eastPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_eastPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		eastPanel.setLayout(gbl_eastPanel);
		
		JPanel balancePanel = new JPanel();
		GridBagConstraints gbc_balancePanel = new GridBagConstraints();
		gbc_balancePanel.fill = GridBagConstraints.BOTH;
		gbc_balancePanel.insets = new Insets(0, 0, 5, 0);
		gbc_balancePanel.gridx = 0;
		gbc_balancePanel.gridy = 0;
		eastPanel.add(balancePanel, gbc_balancePanel);
		balancePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Balance");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		balancePanel.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel balanceDisplayPanel = new JPanel();
		balancePanel.add(balanceDisplayPanel);
		balanceDisplayPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		Border border = BorderFactory.createEmptyBorder(5,5,5,5);
		JLabel accountNameLbl = new JLabel("Account name: ");
		accountNameLbl.setBorder(border);
		balanceDisplayPanel.add(accountNameLbl);
		
		JLabel accountBalanceLbl = new JLabel("$ 0.0");
		accountBalanceLbl.setBorder(border);
		accountBalanceLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		balanceDisplayPanel.add(accountBalanceLbl);
		
		JPanel loansPanel = new JPanel();
		GridBagConstraints gbc_loansPanel = new GridBagConstraints();
		gbc_loansPanel.fill = GridBagConstraints.BOTH;
		gbc_loansPanel.gridx = 0;
		gbc_loansPanel.gridy = 1;
		eastPanel.add(loansPanel, gbc_loansPanel);
		loansPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Loans");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		loansPanel.add(lblNewLabel_1, BorderLayout.NORTH);
		
		JPanel loansDisplayPanel = new JPanel();
		loansPanel.add(loansDisplayPanel, BorderLayout.CENTER);
		
		
	}
	
	public DefaultListModel<String> addAccountsToList(BankCustomer customer, DefaultListModel<String> model) {
		customer = this.bank.getCustomerByEmail(customer.getEmail());
		ArrayList<BankAccount> accounts = customer.getAccounts();
		for (BankAccount acc : accounts) {
			model.addElement(acc.getType());
		}
		return model;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		model = addAccountsToList(this.customer, new DefaultListModel<String>());
		accountsList.setModel(model);
	}

}
