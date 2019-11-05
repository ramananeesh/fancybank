package view;

import controller.Bank;
import controller.BankBranch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
        this.setBounds(SizeManager.getDialogX(), SizeManager.getDialogY(),
                SizeManager.getDialogWidth(), SizeManager.getDialogHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JLabel lblWelcomeToMy = new JLabel("Welcome to My Fancy Bank");
        lblWelcomeToMy.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeTitle()));
        lblWelcomeToMy.setHorizontalAlignment(SwingConstants.CENTER);
        this.getContentPane().add(lblWelcomeToMy, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        this.getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnManager = new JButton("Manager ");
        btnManager.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        panel.add(btnManager);
        btnManager.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JOptionPane.showMessageDialog(null, "Manager first needs to be created using the Sign up option");
                loginFrame = new ManagerLogin(bank);
                setVisible(false);
                loginFrame.setVisible(true);
            }
        });

        JButton btnCustomer = new JButton("Customer");
        btnCustomer.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        btnCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Customer first needs to be created using the Sign up option");
                loginFrame = new CustomerLogin(bank);
                setVisible(false);
                loginFrame.setVisible(true);
            }
        });
        panel.add(btnCustomer);

        JLabel background = new JLabel(new ImageIcon("src/img/bank.jpg"));
        JPanel panelBackground = new JPanel();
        panelBackground.add(background);
        add(panelBackground, BorderLayout.CENTER);
    }
}
