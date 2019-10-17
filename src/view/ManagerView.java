package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Bank;
import model.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
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

public class ManagerView extends JFrame implements Observer {

	private Bank bank;
	private BankManager manager;
	private JTable customersTable;
	private DefaultTableModel customersModel;
	private JTable loansTable;
	private DefaultTableModel loansModel;
	private JTable transactionsTable;
	private DefaultTableModel transactionsModel;
	private JTextArea infoDetailsTextArea;

	/**
	 * Create the application.
	 */
	public ManagerView(Bank bank, BankManager manager) {
		this.bank = bank;
		this.manager = manager;
		this.bank.addObserver(this);
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
		customersModel = new DefaultTableModel(customersData, customersHeader);
		customersModel = addCustomersToTable(customersModel);
		customersTable = new JTable(customersModel);
		customersTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		customersTable.setRowHeight(25);
		customersTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 26));
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
		String loansHeader[] = new String[] { "ID", "Customer", "Amount", "Approved", "Active" };
		loansModel = new DefaultTableModel(loansData, loansHeader);
		loansModel = addLoansToTable(loansModel);
		loansTable = new JTable(loansModel);
		loansTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 26));
		loansTable.setRowHeight(25);
		JScrollPane loansScrollPane = new JScrollPane(loansTable);
		loansScrollPane.setVisible(true);
		loansDisplayPanel.add(loansScrollPane, BorderLayout.CENTER);

		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		JPanel infoPanel = new JPanel();
		centerPanel.add(infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInformation = new JLabel("Information");
		lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblInformation.setHorizontalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblInformation, BorderLayout.NORTH);
		
		
		
		infoDetailsTextArea = new JTextArea();
		infoDetailsTextArea.setText("N/A");
		infoDetailsTextArea.setColumns(10);
		infoDetailsTextArea.setWrapStyleWord(true);
		infoDetailsTextArea.setLineWrap(true);
		infoDetailsTextArea.setEnabled(false);
		infoDetailsTextArea.setForeground(Color.BLUE);
		infoDetailsTextArea.setFont(new Font("Tahoma", Font.PLAIN, 28));
		JScrollPane infoScrollPane = new JScrollPane(infoDetailsTextArea);
		infoPanel.add(infoScrollPane, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		JPanel eastPanel = new JPanel();
		eastPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		getContentPane().add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new BorderLayout(0, 0));
		eastPanel.setPreferredSize(new Dimension(750, eastPanel.getSize().height));

		JLabel lblNewLabel = new JLabel("Today's Transactions");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		eastPanel.add(lblNewLabel, BorderLayout.NORTH);

		String transactionsData[][] = new String[][] {};
		String transactionsHeader[] = new String[] { "Customer", "From", "To", "Type", "Amount" };
		transactionsModel = new DefaultTableModel(transactionsData, transactionsHeader);
		transactionsModel = addTransactionsToTable(transactionsModel);
		transactionsTable = new JTable(transactionsModel);
		transactionsTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		transactionsTable.setRowHeight(25);
		transactionsTable.setPreferredSize(new Dimension(700, 1000));
		transactionsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 26));
		JScrollPane transactionsScrollPane = new JScrollPane(transactionsTable);
		transactionsScrollPane.setVisible(true);
		eastPanel.add(transactionsScrollPane, BorderLayout.CENTER);

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
			model.addRow(l.getShortLoanDisplayForCustomer());
		}
		return model;
	}
	
	public class CustomerListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (customersTable.getSelectedRow() == -1) {
				return;
			}

			ArrayList<BankCustomer> customers = bank.getCustomers();
			BankCustomer customer = customers.get(customersTable.getSelectedRow());

//			accountNameLbl.setText("Account Name: "+account.getAccountName());
//			Double balance = account.getBalance();
//			accountBalanceLbl.setText("Account Balance: $"+balance.toString());
		}

	}

	public class TransactionListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub

			if (transactionsTable.getSelectedRow() == -1) {
				return;
			}
			ArrayList<Transaction> transactions = bank.getTransactions();
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
			ArrayList<Loan> loans = bank.getLoans();
			Loan loan = loans.get(loansTable.getSelectedRow());

			String info = loan.getDetailedLoanDisplayForCustomer();
			infoDetailsTextArea.setText(info);
		}

	}
	

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
