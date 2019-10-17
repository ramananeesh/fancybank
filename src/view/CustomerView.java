package view;

import java.awt.EventQueue;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Bank;
import javafx.collections.SetChangeListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerView extends JFrame implements Observer {
	private BankCustomer customer;
	private Bank bank;

	private JTable accountsTable;
	private JTable transactionsTable;
	private JLabel lblCustomerView;


	private DefaultTableModel accountsModel;
	private DefaultTableModel transactionsModel;

	private JLabel accountNameLbl;
	private JLabel accountBalanceLbl;
	private JTextArea infoDetailsTextArea;

	/**
	 * Create the application.
	 */
	public CustomerView(Bank bank, BankCustomer customer) {

		this.bank = bank;
		this.customer = this.bank.getCustomerByEmail(customer.getEmail());
		this.bank.addObserver(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setBounds(100, 100, 1800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		lblCustomerView = new JLabel("Welcome "+customer.getName());
		lblCustomerView.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblCustomerView.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCustomerView, BorderLayout.NORTH);

		JPanel westPanel = new JPanel();
		getContentPane().add(westPanel, BorderLayout.WEST);
		westPanel.setLayout(new BorderLayout(0, 0));
		westPanel.setPreferredSize(new Dimension(700, 800));

		JPanel accountsPanel = new JPanel();
		accountsPanel.setPreferredSize(new Dimension(400, 300));
		westPanel.add(accountsPanel, BorderLayout.NORTH);

		String accountsData[][] = new String[][] { };
		String accountsHeader[] = new String[] { "Account Name", "Account Type" };
		accountsModel = new DefaultTableModel(accountsData, accountsHeader);
		accountsTable = new JTable(accountsModel);
		accountsPanel.setLayout(new BorderLayout(0, 0));
		accountsTable.getSelectionModel().addListSelectionListener(new AccountListListener());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		accountsTable.setDefaultRenderer(String.class, centerRenderer);
		accountsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 30));

		accountsTable.setPreferredSize(new Dimension(380, 280));
		accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountsTable.setBorder(new EmptyBorder(5, 5, 5, 5));
		accountsTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		accountsTable.setRowHeight(35);
		JScrollPane js = new JScrollPane(accountsTable);
		js.setVisible(true);

		accountsPanel.add(js);

		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		JPanel btnsPanel = new JPanel();
		centerPanel.add(btnsPanel, BorderLayout.CENTER);
		btnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnWithdraw.addActionListener(new TransactionActionListener("Withdraw From Account", "Withdraw"));
		btnsPanel.add(btnWithdraw);

		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new TransactionActionListener("Deposit into Account", "Deposit"));
		btnDeposit.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnDeposit);

		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new TransferBtnListener());
		btnTransfer.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnTransfer);

		JButton btnAddAccount = new JButton("Add Account");
		btnAddAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JTextField nameField = new JTextField();

				UIManager.put("OptionPane.minimumSize", new Dimension(600, 300));
				UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 30));
				JComboBox<String> combo = new JComboBox<String>();
				ArrayList<String> accountType = new ArrayList<String>();
				accountType.add("Savings");
				accountType.add("Checking");

				for (int i = 0; i < accountType.size(); i++) {
					combo.addItem(accountType.get(i));
				}

				Object[] fields = { "Account name", nameField, "Account Type: ", combo };

				int reply = JOptionPane.showConfirmDialog(null, fields, "Choose Account Type",
						JOptionPane.OK_CANCEL_OPTION);

				if (reply == JOptionPane.OK_OPTION) {
					String type;
					type = combo.getItemAt(combo.getSelectedIndex());

					bank.addAccount(customer, nameField.getText(), type);
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
		eastPanel.setPreferredSize(new Dimension(500, 800));
		getContentPane().add(eastPanel, BorderLayout.EAST);
		GridBagLayout gbl_eastPanel = new GridBagLayout();
		gbl_eastPanel.columnWidths = new int[] { 376, 0 };
		gbl_eastPanel.rowHeights = new int[] { 265, 265, 0, 0 };
		gbl_eastPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_eastPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		eastPanel.setLayout(gbl_eastPanel);

		JPanel balancePanel = new JPanel();
		balancePanel.setPreferredSize(new Dimension(500, 150));
		GridBagConstraints gbc_balancePanel = new GridBagConstraints();
		gbc_balancePanel.fill = GridBagConstraints.BOTH;
		gbc_balancePanel.insets = new Insets(0, 0, 5, 0);
		gbc_balancePanel.gridx = 0;
		gbc_balancePanel.gridy = 0;
		balancePanel.setLayout(new BorderLayout(0, 0));
		eastPanel.add(balancePanel, gbc_balancePanel);

		JLabel lblNewLabel = new JLabel("Balance");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 34));
		balancePanel.add(lblNewLabel, BorderLayout.NORTH);

		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);

		JPanel balanceDisplayPanel = new JPanel();
		balanceDisplayPanel.setPreferredSize(new Dimension(500, 100));
		balancePanel.add(balanceDisplayPanel);
		balanceDisplayPanel.setLayout(new GridLayout(0, 2, 0, 0));
		accountNameLbl = new JLabel("Account name: ");
		accountNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		accountNameLbl.setPreferredSize(new Dimension(230, 50));
		accountNameLbl.setBorder(border);
		balanceDisplayPanel.add(accountNameLbl);

		accountBalanceLbl = new JLabel("$ 0.0");
		accountBalanceLbl.setPreferredSize(new Dimension(230, 50));
		accountBalanceLbl.setBorder(border);
		balanceDisplayPanel.add(accountBalanceLbl);

		JPanel loansPanel = new JPanel();
		GridBagConstraints gbc_loansPanel = new GridBagConstraints();
		gbc_loansPanel.insets = new Insets(0, 0, 5, 0);
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
		
		JScrollPane loansScrollPane = new JScrollPane();
		loansDisplayPanel.add(loansScrollPane);

		accountsPanel.setBorder(new LineBorder(Color.black, 3));

		JLabel lblAccounts = new JLabel("Accounts");
		accountsPanel.add(lblAccounts, BorderLayout.NORTH);
		lblAccounts.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccounts.setFont(new Font("Tahoma", Font.PLAIN, 34));

		JPanel transactionsPanel = new JPanel();
		westPanel.add(transactionsPanel, BorderLayout.CENTER);
		transactionsPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblTransactions = new JLabel("Transactions");
		lblTransactions.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransactions.setFont(new Font("Tahoma", Font.PLAIN, 34));
		transactionsPanel.add(lblTransactions, BorderLayout.NORTH);

		String transactionsData[][] = new String[][] {  };
		String transactionsHeader[] = new String[] { "From", "To", "Type", "Amount($)" };
		transactionsModel = new DefaultTableModel(transactionsData, transactionsHeader);
		transactionsTable = new JTable(transactionsModel);
		transactionsTable.getSelectionModel().addListSelectionListener(new TransactionListListener());
		transactionsTable.setVisible(true);

		transactionsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 30));
		transactionsTable.setPreferredSize(new Dimension(380, 380));
		transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionsTable.setBorder(new EmptyBorder(5, 5, 5, 5));
		transactionsTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		transactionsTable.setRowHeight(35);
		transactionsTable.setRowMargin(2);
		transactionsTable.setDefaultRenderer(String.class, centerRenderer);
		JScrollPane js1 = new JScrollPane(transactionsTable);
		js1.setVisible(true);
		transactionsPanel.add(js1);
		
		btnsPanel.setBorder(new LineBorder(Color.black, 3));
		
		JButton btnRequestLoan = new JButton("Request Loan");
		btnRequestLoan.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnRequestLoan);
		

		JPanel infoPanel = new JPanel();
		centerPanel.add(infoPanel, BorderLayout.NORTH);
		infoPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblInformationDetails = new JLabel("Information / Details");
		lblInformationDetails.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblInformationDetails.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblInformationDetails, BorderLayout.NORTH);

		infoPanel.setPreferredSize(new Dimension(600, 300));
		infoDetailsTextArea = new JTextArea("NA");
		infoDetailsTextArea.setWrapStyleWord(true);
		infoDetailsTextArea.setLineWrap(true);
		infoDetailsTextArea.setEnabled(false);
		infoDetailsTextArea.setForeground(Color.BLUE);
		infoDetailsTextArea.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JScrollPane infoScrollPane = new JScrollPane(infoDetailsTextArea);
		infoScrollPane.setVisible(true);
		infoPanel.add(infoScrollPane, BorderLayout.CENTER);
		balancePanel.setBorder(new LineBorder(Color.black, 3));
		infoPanel.setBorder(new LineBorder(Color.black, 3));
		loansPanel.setBorder(new LineBorder(Color.black, 3));
		
		JPanel moreOptionsPanel = new JPanel();
		moreOptionsPanel.setBorder(new LineBorder(Color.black, 3));
		GridBagConstraints gbc_moreOptionsPanel = new GridBagConstraints();
		gbc_moreOptionsPanel.fill = GridBagConstraints.BOTH;
		gbc_moreOptionsPanel.gridx = 0;
		gbc_moreOptionsPanel.gridy = 2;
		eastPanel.add(moreOptionsPanel, gbc_moreOptionsPanel);
		
		JButton btnCurrencyConverter = new JButton("Currency Converter");
		btnCurrencyConverter.setFont(new Font("Tahoma", Font.PLAIN, 30));
		moreOptionsPanel.add(btnCurrencyConverter);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 30));
		moreOptionsPanel.add(btnLogout);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 30));
		moreOptionsPanel.add(btnExit);
		transactionsPanel.setBorder(new LineBorder(Color.black, 3));
	}

	public class RequestLoanActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (customer.getAccounts().size() == 0) {
				JOptionPane.showMessageDialog(null, "No Accounts exist for Customer", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			else if(!customer.customerHasBalanceInAnyAccount()) {
				JOptionPane.showMessageDialog(null, "At least one account needs to have some balance amount", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JTextField loanAmountField = new JTextField();
			JTextField tenureField = new JTextField();
			JTextField collateralField = new JTextField();
			JTextField collateralAmountField = new JTextField();
			
			Object[] fields = {
				"Loan Amount: ", loanAmountField,
				"Tenure in Months: ", tenureField, 
				"Collateral Name: ", collateralField, 
				"Collateral Value($): ", collateralAmountField,
			};
			
			while(true) {
				UIManager.put("OptionPane.minimumSize", new Dimension(600, 600));
				
				int reply = JOptionPane.showConfirmDialog(null, fields, "Loan Application Window", JOptionPane.OK_CANCEL_OPTION);
				
				if(reply==JOptionPane.OK_OPTION) {
					try {
						UIManager.put("OptionPane.minimumSize", new Dimension(300, 100));
						double loanAmount = Double.parseDouble(loanAmountField.getText());
						if(loanAmount<=0) {
							JOptionPane.showMessageDialog(null, "Loan amount cannot be less than or equal to 0", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						}
						int tenure = Integer.parseInt(tenureField.getText());
						String collateral = collateralField.getText();
						double collateralAmount = Double.parseDouble(collateralAmountField.getText());
						if(collateralAmount<=0) {
							JOptionPane.showMessageDialog(null, "Collateral Amount Cannot be Less than or Equal to 0", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						}
						
						bank.addLoan(customer, loanAmount, bank.getLoanInterestRate(), tenure, collateral, collateralAmount);
						break;
						
					}
					catch(Exception e1) {
						System.out.println("Error in processing values. Please enter values in correct format");
						
					}
				}
				
			}
			
		}
		
	}
	
	public class TransactionActionListener implements ActionListener {

		String title;
		String type;

		public TransactionActionListener(String title, String type) {
			// TODO Auto-generated constructor stub
			this.title = title;
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (customer.getAccounts().size() == 0) {
				JOptionPane.showMessageDialog(null, "No Accounts exist for Customer", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			UIManager.put("OptionPane.minimumSize", new Dimension(600, 300));
			UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 30));

			JComboBox<String> combo = new JComboBox<String>();
			ArrayList<BankAccount> accounts = customer.getAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				combo.addItem(accounts.get(i).getAccountName());
			}
			JTextField amountField = new JTextField();

			Object[] fields = { "Account Name: ", combo, "Amount in USD: $", amountField, };

			int reply = JOptionPane.showConfirmDialog(null, fields, title, JOptionPane.OK_CANCEL_OPTION);

			if (reply == JOptionPane.OK_OPTION) {
				int accountIndex = combo.getSelectedIndex();
				if (this.type.equals("Deposit")) {
					bank.depositForCustomer(customer, combo.getItemAt(accountIndex),
							Double.parseDouble(amountField.getText()));
					Transaction transaction = bank.addTransaction(customer.getName(), customer.getName(), "Deposit",
							Double.parseDouble(amountField.getText()), "Self",
							accounts.get(accountIndex).getAccountName());
					bank.addTransactionForCustomer(customer, transaction);
				} else if (this.type.equals("Withdraw")) {
					bank.withdrawForCustomer(customer, combo.getItemAt(accountIndex),
							Double.parseDouble(amountField.getText()));
					Transaction transaction = bank.addTransaction(customer.getName(), customer.getName(), "Withdrawal",
							Double.parseDouble(amountField.getText()), accounts.get(accountIndex).getAccountName(),
							"Self");
					bank.addTransactionForCustomer(customer, transaction);
				}
			}
		}

	}

	public class TransferBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (customer.getAccounts().size() == 0) {
				JOptionPane.showMessageDialog(null, "No Accounts exist for Customer", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			while (true) {
				UIManager.put("OptionPane.minimumSize", new Dimension(600, 300));
				UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 30));

				JComboBox<String> combo1 = new JComboBox<String>();
				JComboBox<String> combo2 = new JComboBox<String>();
				ArrayList<BankAccount> accounts = customer.getAccounts();
				for (int i = 0; i < accounts.size(); i++) {
					combo1.addItem(accounts.get(i).getAccountName());
					combo2.addItem(accounts.get(i).getAccountName());
				}

				JTextField amountField = new JTextField();

				Object[] fields = { "From Account Name: ", combo1, "To Account Name: ", combo2, "Amount in USD: $",
						amountField, };

				int reply = JOptionPane.showConfirmDialog(null, fields, "Transfer between Accounts",
						JOptionPane.OK_CANCEL_OPTION);
				if (reply == JOptionPane.OK_OPTION) {
					int index1 = combo1.getSelectedIndex();
					int index2 = combo2.getSelectedIndex();
					String amountString = amountField.getText();
//					if(!amountString.contains("."))
//						amountString.concat(".0");
					double amount = Double.parseDouble(amountString);
					if (index1 != index2) {
						if (amount > 0) {
							String account1 = accounts.get(index1).getAccountName();
							String account2 = accounts.get(index2).getAccountName();
							bank.transferBetweenAccountsForCustomer(customer, account1, account2, amount);
							Transaction transaction = bank.addTransaction(customer.getName(), customer.getName(),
									"Internal Transfer", amount, account1, account2);
							bank.addTransactionForCustomer(customer, transaction);
							break;
						} else {
							JOptionPane.showMessageDialog(null, "Amount cannot be less than or equal to 0", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					JOptionPane.showMessageDialog(null, "From and to accounts cannot be the same!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		}

	}

	public class AccountListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (accountsTable.getSelectedRow() == -1) {
				return;
			}

			ArrayList<BankAccount> accounts = customer.getAccounts();
			BankAccount account = accounts.get(accountsTable.getSelectedRow());

			accountNameLbl.setText(account.getAccountName());
			Double balance = account.getBalance();
			accountBalanceLbl.setText(balance.toString());
		}

	}

	public class TransactionListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub


			if (transactionsTable.getSelectedRow() == -1) {
				return;
			}
			ArrayList<Transaction> transactions = customer.getTransactions();
			Transaction transaction = transactions.get(transactionsTable.getSelectedRow());

			String info = transaction.detailedCustomerDisplay();
			infoDetailsTextArea.setText(info);
		}

	}

	public DefaultTableModel addAccountsToTable(BankCustomer customer, DefaultTableModel model) {
		customer = this.bank.getCustomerByEmail(customer.getEmail());
		ArrayList<BankAccount> accounts = customer.getAccounts();

		for (BankAccount acc : accounts) {
			model.addRow(acc.getDetails());
		}

		return model;
	}

	public DefaultTableModel addTransactionsToTable(BankCustomer customer, DefaultTableModel model) {
		customer = this.bank.getCustomerByEmail(customer.getEmail());
		ArrayList<Transaction> transactions = customer.getTransactions();
		for (Transaction t : transactions) {
			model.addRow(t.shortCustomerDisplay());
		}
		return model;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		String accountsData[][] = new String[][] { };
		String accountsHeader[] = new String[] { "Account Name", "Account Type" };
		accountsModel = new DefaultTableModel(accountsData, accountsHeader);
		accountsModel = addAccountsToTable(this.customer,
				accountsModel);
		accountsTable.setModel(accountsModel);

		String transactionsData[][] = new String[][] {  };
		String transactionsHeader[] = new String[] { "From", "To", "Type", "Amount($)" };
		transactionsModel = new DefaultTableModel(transactionsData, transactionsHeader);
		transactionsModel = addTransactionsToTable(customer,
				transactionsModel);
		transactionsTable.setModel(transactionsModel);

	}

}
