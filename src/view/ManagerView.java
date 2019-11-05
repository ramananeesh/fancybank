package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Bank;
import controller.BankBranch;
import model.*;
import view.CustomerView.TransactionListListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ManagerView extends JFrame implements Observer {

	private BankBranch bank;
	private BankManager manager;
	private JTable customersTable;
	private DefaultTableModel customersModel;
	private JTable loansTable;
	private DefaultTableModel loansModel;
	private JTable transactionsTable;
	private DefaultTableModel transactionsModel;
	private JTable stocksTable;
	private DefaultTableModel stocksModel;
	private JTextArea infoDetailsTextArea;
	private JLabel amountEarnedLbl;
	private JLabel moneyLbl;

	/**
	 * Create the application.
	 */
	public ManagerView(BankBranch bank, BankManager manager) {
		this.bank = bank;
		this.manager = manager;
		this.bank.addObserver(this);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);
		menuBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		JMenu mnOptions = new JMenu("Options");
		mnOptions.setFont(new Font("Segoe UI", Font.PLAIN, 36));
		menuBar.add(mnOptions);

		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mnOptions.add(mntmLogOut);
		mntmLogOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				Welcome welcome;
				try {
					welcome = new Welcome(bank);
					welcome.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Thanks for using the ATM. System will now exit");
				System.exit(0);
			}
		});
		mnOptions.add(mntmExit);

		JMenu mnActions = new JMenu("Actions");
		mnActions.setFont(new Font("Segoe UI", Font.PLAIN, 36));
		menuBar.add(mnActions);

		JMenuItem mntmApproveItem = new JMenuItem("Approve Loan");
		mntmApproveItem.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmApproveItem.addActionListener(new ApproveLoanListener());
		mntmApproveItem.setHorizontalAlignment(SwingConstants.LEFT);
		mnActions.add(mntmApproveItem);

		JMenuItem mntmSettleInterests = new JMenuItem("Settle Interests");
		mntmSettleInterests.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmSettleInterests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bank.settleInterestsForAllCustomers();
			}
		});
		mnActions.add(mntmSettleInterests);

		JMenuItem mntmInterestRate = new JMenuItem("Loan Interest Rate");
		mntmInterestRate.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmInterestRate.addActionListener(new InterestListener("Loan"));
		mnActions.add(mntmInterestRate);

		JMenuItem mntmSavingsInterestRate = new JMenuItem("Savings Interest Rate");
		mntmSavingsInterestRate.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmSavingsInterestRate.addActionListener(new InterestListener("Savings"));
		mnActions.add(mntmSavingsInterestRate);

		JMenuItem mntmHighBalanceAmount = new JMenuItem("High Balance Amount");
		mntmHighBalanceAmount.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmHighBalanceAmount.addActionListener(new BalanceListener());
		mnActions.add(mntmHighBalanceAmount);

		JMenuItem mntmAccountOperationFee = new JMenuItem("Account Operation Fee");
		mntmAccountOperationFee.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmAccountOperationFee.addActionListener(new FeeListener("Account Operation"));
		mnActions.add(mntmAccountOperationFee);

		JMenuItem mntmCheckingFee = new JMenuItem("Checking Fee");
		mntmCheckingFee.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmCheckingFee.addActionListener(new FeeListener("Checking"));
		mnActions.add(mntmCheckingFee);

		JMenuItem mntmWithdrawalFee = new JMenuItem("Withdrawal Fee");
		mntmWithdrawalFee.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmWithdrawalFee.addActionListener(new FeeListener("Withdrawal"));
		mnActions.add(mntmWithdrawalFee);

		JMenuItem mntmAddStocks = new JMenuItem("Add Stocks");
		mntmAddStocks.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmAddStocks.addActionListener(new AddStocks());
		mnActions.add(mntmAddStocks);

		JMenuItem mntmModifyStocks = new JMenuItem("Modify Stocks");
		mntmModifyStocks.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmModifyStocks.addActionListener(new ModifyStocks());
		mnActions.add(mntmModifyStocks);

		JMenuItem mntmBuyStockFee = new JMenuItem("Buy Stock Fee");
		mntmBuyStockFee.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		mntmBuyStockFee.addActionListener(new FeeListener("BuyStock"));
		mnActions.add(mntmBuyStockFee);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 1900, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblWelcome = new JLabel("Welcome " + this.manager.getName());
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblWelcome, BorderLayout.NORTH);

		JPanel westPanel = new JPanel();
		getContentPane().add(westPanel, BorderLayout.WEST);
		westPanel.setLayout(new BorderLayout(0, 0));
		westPanel.setPreferredSize(new Dimension(650, westPanel.getSize().height));

		JPanel customersDisplayPanel = new JPanel();
		customersDisplayPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		westPanel.add(customersDisplayPanel, BorderLayout.CENTER);
		customersDisplayPanel.setLayout(new BorderLayout(0, 0));

		String customersData[][] = new String[][] {};
		String customersHeader[] = new String[] { "ID", "Customer Name" };
		customersModel = new DefaultTableModel(customersData, customersHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		customersModel = addCustomersToTable(customersModel);
		customersTable = new JTable(customersModel);
		customersTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		customersTable.setRowHeight(25);
		customersTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 26));
		customersTable.getSelectionModel().addListSelectionListener(new CustomerListListener());
		JScrollPane customersScrollPane = new JScrollPane(customersTable);
		customersScrollPane.setVisible(true);
		customersDisplayPanel.add(customersScrollPane);

		JLabel lblCustomers = new JLabel("Customers");
		lblCustomers.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblCustomers.setHorizontalAlignment(SwingConstants.CENTER);
		customersDisplayPanel.add(lblCustomers, BorderLayout.NORTH);

		JPanel loansDisplayPanel = new JPanel();
		loansDisplayPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		westPanel.add(loansDisplayPanel, BorderLayout.SOUTH);
		loansDisplayPanel.setLayout(new BorderLayout(0, 0));

		JLabel loansLbl = new JLabel("Loans");
		loansLbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
		loansLbl.setHorizontalAlignment(SwingConstants.CENTER);
		loansDisplayPanel.add(loansLbl, BorderLayout.NORTH);

		String loansData[][] = new String[][] {};
		String loansHeader[] = new String[] { "ID", "Customer", "Amount", "Status", "Active" };
		loansModel = new DefaultTableModel(loansData, loansHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		loansModel = addLoansToTable(loansModel);
		loansTable = new JTable(loansModel);
		loansTable.setFont(new Font("Tahoma", Font.PLAIN, 26));
		loansTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 26));
		loansTable.setRowHeight(25);
		loansTable.getSelectionModel().addListSelectionListener(new LoanListListener());
		JScrollPane loansScrollPane = new JScrollPane(loansTable);
		loansScrollPane.setVisible(true);
		loansDisplayPanel.add(loansScrollPane, BorderLayout.CENTER);

		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setPreferredSize(new Dimension(500, 600));
		centerPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		centerPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel infoPanel = new JPanel();
		panel.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblInformation = new JLabel("Information");
		lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblInformation.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblInformation, BorderLayout.NORTH);
		infoPanel.setPreferredSize(new Dimension(400, 600));

		infoDetailsTextArea = new JTextArea();
		infoDetailsTextArea.setText("N/A");
		infoDetailsTextArea.setColumns(10);
		infoDetailsTextArea.setWrapStyleWord(true);
		infoDetailsTextArea.setLineWrap(true);
		infoDetailsTextArea.setEnabled(false);
		infoDetailsTextArea.setForeground(Color.BLUE);
		infoDetailsTextArea.setFont(new Font("Tahoma", Font.PLAIN, 28));
		JScrollPane infoScrollPane = new JScrollPane(infoDetailsTextArea);
		infoScrollPane.setPreferredSize(new Dimension(450, 400));
		infoPanel.add(infoScrollPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		centerPanel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		amountEarnedLbl = new JLabel("Amount Earned");
		panel_1.add(amountEarnedLbl, BorderLayout.NORTH);
		amountEarnedLbl.setFont(new Font("Tahoma", Font.PLAIN, 40));
		amountEarnedLbl.setHorizontalAlignment(SwingConstants.CENTER);

		moneyLbl = new JLabel("$"+bank.getMoneyEarned());
		moneyLbl.setHorizontalAlignment(SwingConstants.CENTER);
		moneyLbl.setFont(new Font("Tahoma", Font.PLAIN, 34));
		panel_1.add(moneyLbl, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		JPanel eastPanel = new JPanel();
		eastPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		getContentPane().add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new BorderLayout(0, 0));
		eastPanel.setPreferredSize(new Dimension(750, eastPanel.getSize().height));

		JPanel allTransactionPanel = new JPanel();
		allTransactionPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		eastPanel.add(allTransactionPanel, BorderLayout.CENTER);
		allTransactionPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Today's Transactions");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		allTransactionPanel.add(lblNewLabel, BorderLayout.NORTH);

		String transactionsData[][] = new String[][] {};
		String transactionsHeader[] = new String[] { "Customer", "From", "To", "Type", "Amount" };
		transactionsModel = new DefaultTableModel(transactionsData, transactionsHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		transactionsModel = addTransactionsToTable(transactionsModel);
		transactionsTable = new JTable(transactionsModel);
		transactionsTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		transactionsTable.setRowHeight(25);
		transactionsTable.setPreferredSize(new Dimension(700, 1000));
		transactionsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 26));
		transactionsTable.getSelectionModel().addListSelectionListener(new TransactionListListener());
		JScrollPane transactionsScrollPane = new JScrollPane(transactionsTable);
		transactionsScrollPane.setVisible(true);
		allTransactionPanel.add(transactionsScrollPane);

		JPanel stocksDisplayPanel = new JPanel();
		stocksDisplayPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		eastPanel.add(stocksDisplayPanel, BorderLayout.SOUTH);
		stocksDisplayPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblStocks = new JLabel("Stocks");
		lblStocks.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblStocks.setHorizontalAlignment(SwingConstants.CENTER);
		stocksDisplayPanel.add(lblStocks, BorderLayout.NORTH);

		String stocksData[][] = new String[][] {};
		String stocksHeader[] = new String[] {"Stock Name", "Value", "# Stocks"};
		stocksModel = new DefaultTableModel(stocksData, stocksHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		stocksModel = addStocksToTable(stocksModel);
		stocksTable = new JTable(stocksModel);
		stocksTable.setFont(new Font("Tahoma", Font.PLAIN, 26));
		stocksTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 26));
		stocksTable.setRowHeight(25);
		stocksTable.getSelectionModel().addListSelectionListener(new StockListListener());
		JScrollPane stocksScrollPane = new JScrollPane(stocksTable);
		stocksScrollPane.setVisible(true);
		stocksDisplayPanel.add(stocksScrollPane, BorderLayout.CENTER);

	}

	public class ApproveLoanListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox combo1 = new JComboBox();
			JComboBox combo2 = new JComboBox();
			JLabel loanAmountLabel = new JLabel("N/A");
			JLabel collateralAmountLabel = new JLabel("N/A");

			for (BankCustomer cust : bank.getCustomers()) {
				combo1.addItem(cust.getName());
			}

			combo1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ArrayList<Loan> customerLoans = bank.getCustomers().get(combo1.getSelectedIndex()).getLoans();

					for (Loan l : customerLoans) {
						combo2.addItem(l.getLoanId());
					}
				}
			});

			combo2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Loan l = bank.getCustomers().get(combo1.getSelectedIndex()).getLoans()
							.get(combo2.getSelectedIndex());
					loanAmountLabel.setText("$" + l.getLoanAmount());
					collateralAmountLabel.setText("$" + l.getCollateralAmount());
				}
			});

			Object[] fields = new Object[] { "Customer: ", combo1, "Loan ID: ", combo2, "Loan Amount: ",
					loanAmountLabel, "Collateral Amount: ", collateralAmountLabel, };

			while (true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Approve Loan", JOptionPane.OK_CANCEL_OPTION);

				if (reply == JOptionPane.OK_OPTION) {
					if (combo1.getSelectedIndex() == -1 || combo2.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(null, "Select a customer and Loan ID to approve loan", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					BankCustomer customer = bank.getCustomers().get(combo1.getSelectedIndex());
					Loan l = customer.getLoans().get(combo2.getSelectedIndex());
					bank.approveLoanForCustomer(customer, l.getLoanId());
					break;
				} else {
					return;
				}
			}
		}
	}

	public DefaultTableModel addCustomersToTable(DefaultTableModel model) {

		ArrayList<BankCustomer> customers = bank.getCustomers();
		if (customers.size() != 0) {
			for (BankCustomer cust : customers) {
				model.addRow(cust.getDetails());
			}
		}

		return model;
	}

	public DefaultTableModel addTransactionsToTable(DefaultTableModel model) {

		ArrayList<Transaction> transactions = bank.getTransactions();
		if (transactions.size() == 0)
			return model;
		for (Transaction t : transactions) {
			model.addRow(t.shortManagerDisplay());
		}
		return model;
	}

	public DefaultTableModel addLoansToTable(DefaultTableModel model) {
		ArrayList<Loan> loans = bank.getLoans();
		if (loans.size() == 0) {
			return model;
		}
		for (Loan l : loans) {
			model.addRow(l.getShortLoanDisplayForManager());
		}
		return model;
	}

	public DefaultTableModel addStocksToTable(DefaultTableModel model) {
		ArrayList<BankStock> stocks = bank.getAllStocks();
		if(stocks.size() == 0) {
			return model;
		}
		for(BankStock s : stocks) {
			model.addRow(s.getShortAllStockDisplayForManager());
		}
		return model;
	}

	public class BalanceListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JTextField field = new JTextField();
			Object[] fields = new Object[] { "New High Balance Minimum Limit: ", field, };

			while (true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Set High Balance Minimum Limit",
						JOptionPane.OK_CANCEL_OPTION);
				try {
					double newBalance = Double.parseDouble(field.getText());

					if (newBalance <= 0) {
						JOptionPane.showMessageDialog(null, "New Limit has to be more than 0", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					bank.setHighBalance(newBalance);
					break;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Enter a valid value more than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}

		}
	}

	public class InterestListener implements ActionListener {
		String type;

		public InterestListener(String type) {
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			JTextField field = new JTextField();
			Object[] fields = new Object[] { "New Interest Rate(%): ", field, };

			while (true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Set" + this.type + " Interest Rate Percentage",
						JOptionPane.OK_CANCEL_OPTION);
				try {
					double newRate = Double.parseDouble(field.getText());

					if (newRate <= 0 || newRate >= 100) {
						JOptionPane.showMessageDialog(null, "Interest has to be more than 0 and less than 100", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					if (type.equals("Loan"))
						bank.modifyLoanInterestRate(newRate / 100.0);
					else if (type.equals("Savings"))
						bank.modifySavingsInterestRate(newRate / 100.0);

					break;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Enter a valid value between 0 and 100", "Error",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
		}
	}

	public class FeeListener implements ActionListener {
		String type;

		public FeeListener(String type) {
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			JTextField field = new JTextField();
			Object[] fields = new Object[] { "New Fee ($): ", field, };

			while (true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Set" + this.type + " Fee",
						JOptionPane.OK_CANCEL_OPTION);
				try {
					double newFee = Double.parseDouble(field.getText());

					if (newFee <= 0) {
						JOptionPane.showMessageDialog(null, "Fee has to be more than 0", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					bank.modifyFees(type, newFee);
					break;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Enter a valid value more than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
		}
	}

	public class AddStocks implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField stockNameField = new JTextField();
			JTextField valueField = new JTextField();
			JTextField numStocksField = new JTextField();

			Object[] fields = new Object[] {"Stock Name: ", stockNameField, "Value: ", valueField,
					"Number of Stocks: ", numStocksField, };

			while(true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Add Stock",
						JOptionPane.OK_CANCEL_OPTION);
				try{
					String stockName = stockNameField.getText();
					double value = Double.parseDouble(valueField.getText());
					int numStocks = Integer.parseInt(numStocksField.getText());

					if(numStocks <= 0){
						JOptionPane.showMessageDialog(null, "Number of Stocks has to be more than 0", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					if(value <= 0) {
						JOptionPane.showMessageDialog(null, "Stocks value has to be more than 0", "Error",
								JOptionPane.ERROR_MESSAGE);
						continue;
					}
					bank.addAllStocks(stockName, value, numStocks);
					break;
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Please enter a valid value", "Error",
							JOptionPane.ERROR_MESSAGE);
					continue;
				}
			}
		}
	}

	public class ModifyStocks implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bank.getAllStocks().size() == 0){
				JOptionPane.showMessageDialog(null, "No Stocks", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JComboBox<String> stockNameCombo = new JComboBox<String>();
			ArrayList<BankStock> allStocks = bank.getAllStocks();
			for (int i = 0; i < allStocks.size(); i++) {
				stockNameCombo.addItem(allStocks.get(i).getStockName());
			}
			JTextField valueField = new JTextField();
			JTextField numStocksField = new JTextField();

			Object[] fields = {"Stock Name: ", stockNameCombo, "Value: ", valueField, "Number of Stocks: ", numStocksField};
			while(true) {
				int reply = JOptionPane.showConfirmDialog(null, fields, "Modify Stocks", JOptionPane.OK_CANCEL_OPTION);

				if (reply == JOptionPane.OK_OPTION) {
					try{
						int stockIndex = stockNameCombo.getSelectedIndex();
						double value = Double.parseDouble(valueField.getText());
						int numStocks = Integer.parseInt(numStocksField.getText());
						bank.modifyAllStocks(stockIndex, value, numStocks);

						break;
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error",
								"Error", JOptionPane.ERROR_MESSAGE);
						continue;
					}
				}
				else{
					return;
				}
			}
		}
	}

	public class CustomerListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (customersTable.getSelectedRow() == -1) {
				return;
			}

			loansTable.clearSelection();
			transactionsTable.clearSelection();
			ArrayList<BankCustomer> customers = bank.getCustomers();
			BankCustomer customer = customers.get(customersTable.getSelectedRow());
			String info = customer.getCustomerDetails();
			infoDetailsTextArea.setText(info);
		}

	}

	public class TransactionListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (transactionsTable.getSelectedRow() == -1) {
				return;
			}

			loansTable.clearSelection();
			customersTable.clearSelection();

			ArrayList<Transaction> transactions = bank.getTransactions();
			Transaction transaction = transactions.get(transactionsTable.getSelectedRow());

			String info = transaction.detailedDisplay();
			infoDetailsTextArea.setText("");
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

			customersTable.clearSelection();
			transactionsTable.clearSelection();

			ArrayList<Loan> loans = bank.getLoans();
			Loan loan = loans.get(loansTable.getSelectedRow());

			String info = loan.getDetailedLoanDisplayForManager(0);
			infoDetailsTextArea.setText("");
			infoDetailsTextArea.setText(info);
		}

	}

	public class StockListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(stocksTable.getSelectedRow() == -1) {
				return;
			}

			customersTable.clearSelection();
			transactionsTable.clearSelection();

			ArrayList<BankStock> stocks = bank.getAllStocks();
			BankStock stock = stocks.get(stocksTable.getSelectedRow());

			String info = stock.getDetailedAllStockDisplayForManager();
			infoDetailsTextArea.setText("");
			infoDetailsTextArea.setText(info);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

		String customersData[][] = new String[][] {};
		String customersHeader[] = new String[] { "ID", "Customer Name" };
		customersModel = new DefaultTableModel(customersData, customersHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		customersModel = addCustomersToTable(customersModel);
		customersTable.setModel(customersModel);

		String transactionsData[][] = new String[][] {};
		String transactionsHeader[] = new String[] { "Customer", "From", "To", "Type", "Amount" };
		transactionsModel = new DefaultTableModel(transactionsData, transactionsHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		transactionsModel = addTransactionsToTable(transactionsModel);
		transactionsTable.setModel(transactionsModel);

		String loansData[][] = new String[][] {};
		String loansHeader[] = new String[] { "ID", "Customer", "Amount", "Status", "Active" };
		loansModel = new DefaultTableModel(loansData, loansHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		loansModel = addLoansToTable(loansModel);
		loansTable.setModel(loansModel);

		String stocksData[][] = new String[][] {};
		String stocksHeader[] = new String[] {"Stock Name", "Value", "# Stocks"};
		stocksModel = new DefaultTableModel(stocksData, stocksHeader) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		stocksModel = addStocksToTable(stocksModel);
		stocksTable.setModel(stocksModel);

		moneyLbl.setText("$"+bank.getMoneyEarned());

	}

}
