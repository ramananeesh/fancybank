package view;

import java.awt.EventQueue;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.Border;

import model.*;
import model.Currency;

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
	private DefaultTableModel loansModel;

	private JLabel accountNameLbl;
	private JLabel accountBalanceLbl;
	private JTextArea infoDetailsTextArea;
	private JTable loansTable;
	private JComboBox currencyFromCombo;
	private JTextField currencyConverterField;
	private JComboBox currencyToCombo;
	private JLabel convertedAmtLbl;

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

		this.setBounds(100, 100, 1900, 1000);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		lblCustomerView = new JLabel("Welcome " + customer.getName());
		lblCustomerView.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblCustomerView.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCustomerView, BorderLayout.NORTH);

		JPanel westPanel = new JPanel();
		getContentPane().add(westPanel, BorderLayout.WEST);
		westPanel.setLayout(new BorderLayout(0, 0));
		westPanel.setPreferredSize(new Dimension(700, 800));

		JPanel accountsPanel = new JPanel();
		accountsPanel.setPreferredSize(new Dimension(400, 400));
		westPanel.add(accountsPanel, BorderLayout.NORTH);

		String accountsData[][] = new String[][] {};
		String accountsHeader[] = new String[] { "Account Name", "Account Type" };
		accountsModel = new DefaultTableModel(accountsData, accountsHeader);
		accountsTable = new JTable(accountsModel);
		accountsPanel.setLayout(new BorderLayout(0, 0));
		accountsTable.getSelectionModel().addListSelectionListener(new AccountListListener());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		accountsTable.setDefaultRenderer(String.class, centerRenderer);
		accountsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 30));

		accountsTable.setPreferredSize(new Dimension(380, 380));
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

		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(600, 1000));
		getContentPane().add(eastPanel, BorderLayout.EAST);
		GridBagLayout gbl_eastPanel = new GridBagLayout();
		gbl_eastPanel.columnWidths = new int[] { 376, 0 };
		gbl_eastPanel.rowHeights = new int[] { 265, 265, 0, 0, 0 };
		gbl_eastPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_eastPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		eastPanel.setLayout(gbl_eastPanel);

		JPanel balancePanel = new JPanel();
		balancePanel.setPreferredSize(new Dimension(500, 120));
		GridBagConstraints gbc_balancePanel = new GridBagConstraints();
		gbc_balancePanel.fill = GridBagConstraints.BOTH;
		gbc_balancePanel.insets = new Insets(0, 0, 5, 0);
		gbc_balancePanel.gridx = 0;
		gbc_balancePanel.gridy = 0;
		balancePanel.setLayout(new BorderLayout(0, 0));
		eastPanel.add(balancePanel, gbc_balancePanel);

		JLabel lblNewLabel = new JLabel("Balance Info");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 34));
		balancePanel.add(lblNewLabel, BorderLayout.NORTH);

		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);

		JPanel balanceDisplayPanel = new JPanel();
		balanceDisplayPanel.setPreferredSize(new Dimension(500, 100));
		balancePanel.add(balanceDisplayPanel);
		balanceDisplayPanel.setLayout(new GridLayout(0, 1, 0, 0));
		accountNameLbl = new JLabel("Account name: ");
		accountNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		accountNameLbl.setPreferredSize(new Dimension(230, 50));
		accountNameLbl.setBorder(border);
		balanceDisplayPanel.add(accountNameLbl);

		accountBalanceLbl = new JLabel("$ 0.0");
		accountBalanceLbl.setHorizontalAlignment(SwingConstants.CENTER);
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

		String loansData[][] = new String[][] {};
		String loansHeader[] = new String[] { "ID", "Amount", "Status", "Active" };
		loansModel = new DefaultTableModel(loansData, loansHeader);
		loansTable = new JTable(loansModel);
		loansTable.getSelectionModel().addListSelectionListener(new LoanListListener());
		loansTable.setDefaultRenderer(String.class, centerRenderer);
		loansTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
		JScrollPane loansScrollPane = new JScrollPane(loansTable);
		loansScrollPane.setPreferredSize(new Dimension(580, 200));
		loansTable.setPreferredSize(new Dimension(580, 140));
		loansTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loansTable.setBorder(new EmptyBorder(5, 5, 5, 5));
		loansTable.setFont(new Font("Tahoma", Font.PLAIN, 26));
		loansTable.setRowHeight(20);
		loansDisplayPanel.setPreferredSize(new Dimension(600, 350));
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

		String transactionsData[][] = new String[][] {};
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
		balancePanel.setBorder(new LineBorder(Color.black, 3));

		JPanel panel = new JPanel();
		centerPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel infoPanel = new JPanel();
		panel.add(infoPanel, BorderLayout.SOUTH);
		infoPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblInformationDetails = new JLabel("Information / Details");
		lblInformationDetails.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblInformationDetails.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblInformationDetails, BorderLayout.NORTH);

		infoPanel.setPreferredSize(new Dimension(400, 600));
		infoDetailsTextArea = new JTextArea("NA");
		infoDetailsTextArea.setWrapStyleWord(true);
		infoDetailsTextArea.setLineWrap(true);
		infoDetailsTextArea.setEnabled(false);
		infoDetailsTextArea.setForeground(Color.BLUE);
		infoDetailsTextArea.setFont(new Font("Tahoma", Font.PLAIN, 28));

		JScrollPane infoScrollPane = new JScrollPane(infoDetailsTextArea);
		infoScrollPane.setVisible(true);
		infoPanel.add(infoScrollPane, BorderLayout.CENTER);
		infoPanel.setBorder(new LineBorder(Color.black, 3));

		JPanel btnsPanel = new JPanel();
		panel.add(btnsPanel, BorderLayout.CENTER);

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

		btnsPanel.setBorder(new LineBorder(Color.black, 3));

		JButton btnRequestLoan = new JButton("Request Loan");
		btnRequestLoan.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnRequestLoan);

		JButton btnCloseLoan = new JButton("Close Loan");
		btnCloseLoan.addActionListener(new LoanCloseListener());
		btnCloseLoan.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnCloseLoan);
		
		JButton btnCloseAccount = new JButton("Close Account");
		btnCloseAccount.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnsPanel.add(btnCloseAccount);
		btnCloseAccount.addActionListener(new CloseAccountListener());
		
		btnRequestLoan.addActionListener(new RequestLoanActionListener());
		loansPanel.setBorder(new LineBorder(Color.black, 3));

		JPanel moreOptionsPanel = new JPanel();
		moreOptionsPanel.setBorder(new LineBorder(Color.black, 3));
		moreOptionsPanel.setPreferredSize(new Dimension(500, 200));
		GridBagConstraints gbc_moreOptionsPanel = new GridBagConstraints();
		gbc_moreOptionsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_moreOptionsPanel.fill = GridBagConstraints.BOTH;
		gbc_moreOptionsPanel.gridx = 0;
		gbc_moreOptionsPanel.gridy = 2;
		eastPanel.add(moreOptionsPanel, gbc_moreOptionsPanel);
		moreOptionsPanel.setLayout(new BorderLayout(0, 0));

		JButton btnCurrencyConverter = new JButton("Convert");
		btnCurrencyConverter.addActionListener(new CurrencyConverterListener());
		btnCurrencyConverter.setFont(new Font("Tahoma", Font.PLAIN, 30));
		moreOptionsPanel.add(btnCurrencyConverter, BorderLayout.SOUTH);
		
		JLabel lblA = new JLabel("Currency Converter");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		moreOptionsPanel.add(lblA, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		moreOptionsPanel.add(panel_1, BorderLayout.CENTER);
		
		currencyFromCombo = new JComboBox();
		currencyFromCombo.setModel(new DefaultComboBoxModel(new String[] {"From"}));
		ArrayList<Currency> currencies = bank.getCurrencies();
		for(Currency c: currencies) {
			currencyFromCombo.addItem(c.getName()+" - "+c.getAbbreviation());
		}
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(currencyFromCombo, BorderLayout.WEST);
		
		currencyConverterField = new JTextField();
		panel_1.add(currencyConverterField, BorderLayout.CENTER);
		currencyConverterField.setColumns(10);
		
		currencyToCombo = new JComboBox();
		currencyToCombo.setModel(new DefaultComboBoxModel(new String[] {"To"}));
		for(Currency c: currencies) {
			currencyToCombo.addItem(c.getName()+" - "+c.getAbbreviation());
		}
		panel_1.add(currencyToCombo, BorderLayout.EAST);
		
		convertedAmtLbl = new JLabel("Converted Amount");
		convertedAmtLbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
		convertedAmtLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(convertedAmtLbl, BorderLayout.SOUTH);
		

		transactionsPanel.setBorder(new LineBorder(Color.black, 3));
	}

	public class CurrencyConverterListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String field = currencyConverterField.getText();
			ArrayList<Currency> currencies = bank.getCurrencies();
			if(field.equals(""))
				return;
			try {
				double amount = Double.parseDouble(field);
				if(amount<=0) {
					JOptionPane.showMessageDialog(null, "Enter an amount greater than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				int fromIndex = currencyFromCombo.getSelectedIndex();
				int toIndex= currencyToCombo.getSelectedIndex();
				
				if(fromIndex!=toIndex && (fromIndex!=0||fromIndex!=-1||toIndex!=0||toIndex!=-1)) {
					Currency fromCurrency = currencies.get(fromIndex);
					Currency toCurrency = currencies.get(toIndex);
					
					double usdAmount = fromCurrency.getAmountInUSD(amount);
					double toAmount = toCurrency.getAmountInCurrency(usdAmount);
					
					convertedAmtLbl.setText("Converted Amount = "+toCurrency.getAbbreviation()+toAmount);
				}
			} catch(Exception e1) {
				return;
			}
		}
	}
	
	public class CloseAccountListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox<String> combo = new JComboBox<String>();
			ArrayList<BankAccount> accounts = customer.getAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				combo.addItem(accounts.get(i).getAccountName());
			}
			
			Object[] fields= {
				"Account Name: ", combo,	
			};
			
			int reply = JOptionPane.showConfirmDialog(null, fields, "Close Account", JOptionPane.OK_CANCEL_OPTION);
			
			if(reply==JOptionPane.OK_OPTION) {
				BankAccount acc = accounts.get(combo.getSelectedIndex());
				double amountReturned = customer.closeAccountFee(acc.getAccountName());
				bank.closeAccountForCustomer(customer, acc.getAccountName());
				
				String ret="Account closed successfully! ";
				if(amountReturned>0) {
					ret+="You get back $"+amountReturned;
				}
				JOptionPane.showMessageDialog(null, ret);
			}
		}
	}
	
	public class RequestLoanActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (customer.getAccounts().size() == 0) {
				JOptionPane.showMessageDialog(null, "No Accounts exist for Customer", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			else if (!customer.customerHasBalanceInAnyAccount()) {
				JOptionPane.showMessageDialog(null, "At least one account needs to have some balance amount", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			JTextField loanAmountField = new JTextField();
			JTextField tenureField = new JTextField();
			JTextField collateralField = new JTextField();
			JTextField collateralAmountField = new JTextField();

			Object[] fields = { "Loan Amount: ", loanAmountField, "Tenure in Months: ", tenureField,
					"Collateral Name: ", collateralField, "Collateral Value($): ", collateralAmountField, };

			while (true) {
				UIManager.put("OptionPane.minimumSize", new Dimension(600, 600));

				int reply = JOptionPane.showConfirmDialog(null, fields, "Loan Application Window",
						JOptionPane.OK_CANCEL_OPTION);

				if (reply == JOptionPane.OK_OPTION) {
					try {
						UIManager.put("OptionPane.minimumSize", new Dimension(300, 100));
						double loanAmount = Double.parseDouble(loanAmountField.getText());
						if (loanAmount <= 0) {
							JOptionPane.showMessageDialog(null, "Loan amount cannot be less than or equal to 0",
									"Error", JOptionPane.ERROR_MESSAGE);
							continue;
						}
						int tenure = Integer.parseInt(tenureField.getText());
						String collateral = collateralField.getText();
						double collateralAmount = Double.parseDouble(collateralAmountField.getText());
						if (collateralAmount <= loanAmount) {
							JOptionPane.showMessageDialog(null,
									"Collateral Amount Cannot be Less than or Equal to Loan Amount", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						}

						bank.addLoan(customer, loanAmount, bank.getLoanInterestRate(), tenure, collateral,
								collateralAmount);
						break;

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null,
								"Error in processing values. Please enter values in correct format", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					break;
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
			JComboBox<String> currencyCombo = new JComboBox<String>();
			ArrayList<BankAccount> accounts = customer.getAccounts();
			ArrayList<Currency> currencies = bank.getCurrencies();
			for (int i = 0; i < accounts.size(); i++) {
				combo.addItem(accounts.get(i).getAccountName());
			}
			
			for(Currency c: currencies) {
				currencyCombo.addItem(c.getName()+" - "+c.getAbbreviation());
			}
			
			JTextField amountField = new JTextField();

			Object[] fields = { "Account Name: ", combo, "Amount in USD: $", amountField, };
			while (true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, title, JOptionPane.OK_CANCEL_OPTION);

				if (reply == JOptionPane.OK_OPTION) {
					int accountIndex = combo.getSelectedIndex();
					double inputAmount, amount;
					try {
						inputAmount = Double.parseDouble(amountField.getText());
						if (inputAmount <= 0) {
							JOptionPane.showMessageDialog(null, "Deposit should be a real value >=0", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						}
						
						Currency currency = currencies.get(currencyCombo.getSelectedIndex());
						
						amount = currency.getAmountInUSD(inputAmount);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error processing deposit. Deposit should be a real value",
								"Error", JOptionPane.ERROR_MESSAGE);
						continue;
					}

					if (this.type.equals("Deposit")) {
						boolean flag = bank.depositForCustomer(customer, combo.getItemAt(accountIndex), amount);
						double fees = accounts.get(combo.getSelectedIndex()).getFees("Deposit");
						if (!flag) {
							JOptionPane.showMessageDialog(null,
									"Error processing deposit. Fee exceeds account balance including deposit", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						}
						Transaction transaction = bank.addTransaction(customer.getName(), customer.getName(), "Deposit",
								Double.parseDouble(amountField.getText()), "Self",
								accounts.get(accountIndex).getAccountName());
						bank.addTransactionForCustomer(customer, transaction);

						bank.addMoneyEarned(fees);
						transaction = bank.addTransaction(customer.getName(), "Bank", "Transaction Fees",
								fees, accounts.get(accountIndex).getAccountName(), "My Fancy Bank");
						bank.addTransactionForCustomer(customer, transaction);
						break;
					} else if (this.type.equals("Withdraw")) {
						boolean flag = bank.withdrawForCustomer(customer, combo.getItemAt(accountIndex), amount);
						double fees = accounts.get(combo.getSelectedIndex()).getFees("Withdrawal");
						if (!flag) {
							JOptionPane.showMessageDialog(null, "Error processing withdrawal. Fee exceeds account balance",
									"Error", JOptionPane.ERROR_MESSAGE);
							continue;
						}
						Transaction transaction = bank.addTransaction(customer.getName(), customer.getName(), "Withdrawal",
								Double.parseDouble(amountField.getText()), accounts.get(accountIndex).getAccountName(),
								"Self");
						bank.addTransactionForCustomer(customer, transaction);

						bank.addMoneyEarned(fees);
						transaction = bank.addTransaction(customer.getName(), "Bank", "Transaction Fees", fees,
								accounts.get(accountIndex).getAccountName(), "My Fancy Bank");
						bank.addTransactionForCustomer(customer, transaction);
						break;
					}
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
					while (true) {
						int index1 = combo1.getSelectedIndex();
						int index2 = combo2.getSelectedIndex();
						String amountString = amountField.getText();
						double amount;
						try {
							amount = Double.parseDouble(amountString);
							if (amount <= 0) {
								JOptionPane.showMessageDialog(null, "Amount should be >=0", "Error",
										JOptionPane.ERROR_MESSAGE);
								continue;
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Amount should be a real value", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						}
						if (index1 != index2) {
							if (amount > 0) {
								String account1 = accounts.get(index1).getAccountName();
								String account2 = accounts.get(index2).getAccountName();
								boolean flag = bank.transferBetweenAccountsForCustomer(customer, account1, account2,
										amount);
								if (!flag) {
									JOptionPane.showMessageDialog(null,
											"Error processing deposit. Fee exceeds account balance", "Error",
											JOptionPane.ERROR_MESSAGE);
									return;
								}

								Transaction transaction = bank.addTransaction(customer.getName(), customer.getName(),
										"Internal Transfer", amount, account1, account2);
								bank.addTransactionForCustomer(customer, transaction);

								double fees = accounts.get(index1).getFees("Withdrawal")
										+ accounts.get(index2).getFees("Deposit");
								bank.addMoneyEarned(fees);
								transaction = bank.addTransaction(customer.getName(), "Bank",
										"Transaction Fees - Withdrawal", accounts.get(index1).getFees("Withdrawal"), accounts.get(index1).getAccountName(),
										"My Fancy Bank");
								bank.addTransactionForCustomer(customer, transaction);

								transaction = bank.addTransaction(customer.getName(), "Bank",
										"Transaction Fees - Deposit", accounts.get(index2).getFees("Deposit"), accounts.get(index2).getAccountName(),
										"My Fancy Bank");
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

	}

	public class AccountListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (accountsTable.getSelectedRow() == -1) {
				return;
			}

			loansTable.clearSelection();
			transactionsTable.clearSelection();
			
			ArrayList<BankAccount> accounts = customer.getAccounts();
			BankAccount account = accounts.get(accountsTable.getSelectedRow());

			accountNameLbl.setText("Account Name: " + account.getAccountName());
			Double balance = account.getBalance();
			accountBalanceLbl.setText("Account Balance: $" + balance.toString());
		}

	}

	public class TransactionListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (transactionsTable.getSelectedRow() == -1) {
				return;
			}
			
			accountsTable.clearSelection();
			loansTable.clearSelection();
			
			ArrayList<Transaction> transactions = customer.getTransactions();
			Transaction transaction = transactions.get(transactionsTable.getSelectedRow());

			String info = transaction.detailedDisplay();
			infoDetailsTextArea.setText(info);
		}

	}

	public class LoanListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (loansTable.getSelectedRow() == -1) {
				return;
			}
			
			accountsTable.clearSelection();
			transactionsTable.clearSelection();
			
			ArrayList<Loan> loans = customer.getLoans();
			Loan loan = loans.get(loansTable.getSelectedRow());

			String info = loan.getDetailedLoanDisplayForCustomer();
			infoDetailsTextArea.setText(info);
		}

	}

	public class LoanCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ArrayList<BankAccount> accounts = customer.getAccounts();

			ArrayList<Loan> loans = customer.getLoans();

			JLabel loanAmountLbl = new JLabel("N/A");
			JLabel accountBalanceLbl = new JLabel("N/A");
			JComboBox loanCombo = new JComboBox();
			for (Loan l : loans) {
				loanCombo.addItem(l.getLoanId());
			}

			JComboBox accountCombo = new JComboBox();

			for (BankAccount acc : accounts) {
				accountCombo.addItem(acc.getAccountName());
			}

			accountCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					accountBalanceLbl
							.setText(Double.toString(accounts.get(accountCombo.getSelectedIndex()).getBalance()));
				}
			});

			loanCombo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					loanAmountLbl.setText(Double.toString(loans.get(loanCombo.getSelectedIndex()).getPayoffAmount()));
				}

			});

			Object[] fields = { "Loans: ", loanCombo, "Accounts: ", accountCombo, "Loan Ammount: ", loanAmountLbl,
					"Account Balance: ", accountBalanceLbl, };

			double minLoanAmount = customer.getMinimumLoanAmount();
			double maxAccBalance = customer.getMaximumAccountBalance();

			if (minLoanAmount > maxAccBalance) {
				JOptionPane.showMessageDialog(null,
						"The Minimum Settle off Amount for Any loan Exceeds the Maximum account balance from all accounts",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			while (true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Close-off Loan", JOptionPane.OK_CANCEL_OPTION);

				if (reply == JOptionPane.OK_OPTION) {

					int accIndex = accountCombo.getSelectedIndex();
					int loanIndex = loanCombo.getSelectedIndex();

					if (accIndex == -1 || loanIndex == -1) {
						JOptionPane.showMessageDialog(null, "Select a loan id and an account to settle the loan",
								"Error", JOptionPane.ERROR_MESSAGE);
						continue;
					} else {
						BankAccount acc = accounts.get(accIndex);
						Loan loan = loans.get(loanIndex);

						if (acc.getBalance() <= loan.getPayoffAmount()) {
							JOptionPane.showMessageDialog(null,
									"Account balance should be greater than Loan Payoff amount", "Error",
									JOptionPane.ERROR_MESSAGE);
							continue;
						} else {
							boolean flag = bank.settleLoanForCustomer(customer, acc.getAccountName(), loan.getLoanId(),
									loan.getPayoffAmount());
							if (!flag) {
								JOptionPane.showMessageDialog(null,
										"Loan Payoff amount + transaction fees is greater than account balance",
										"Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							break;
						}
					}
				} else {
					break;
				}
			}

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

	public DefaultTableModel addLoansToTable(BankCustomer customer, DefaultTableModel model) {
		customer = this.bank.getCustomerByEmail(customer.getEmail());
		ArrayList<Loan> loans = customer.getLoans();
		for (Loan l : loans) {
			model.addRow(l.getShortLoanDisplayForCustomer());
		}
		return model;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		String accountsData[][] = new String[][] {};
		String accountsHeader[] = new String[] { "Account Name", "Account Type" };
		accountsModel = new DefaultTableModel(accountsData, accountsHeader);
		accountsModel = addAccountsToTable(this.customer, accountsModel);
		accountsTable.setModel(accountsModel);

		String transactionsData[][] = new String[][] {};
		String transactionsHeader[] = new String[] { "From", "To", "Type", "Amount($)" };
		transactionsModel = new DefaultTableModel(transactionsData, transactionsHeader);
		transactionsModel = addTransactionsToTable(customer, transactionsModel);
		transactionsTable.setModel(transactionsModel);

		String loansData[][] = new String[][] {};
		String loansHeader[] = new String[] { "ID", "Amount", "Status", "Active" };
		loansModel = new DefaultTableModel(loansData, loansHeader);
		loansModel = addLoansToTable(customer, loansModel);
		loansTable.setModel(loansModel);

	}

}
