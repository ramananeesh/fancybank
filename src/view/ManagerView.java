package view;

import java.awt.EventQueue;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Bank;
import model.*;

public class ManagerView extends JFrame implements Observer{
	
	private Bank bank; 
	private BankManager manager;

	/**
	 * Create the application.
	 */
	public ManagerView(Bank bank, BankManager manager) {
		this.bank = bank;
		this.manager = manager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
