package view;

import controller.BankBranch;
import model.BankManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerLogin extends JFrame {

    private BankBranch bank;

    /**
     * Create the application.
     */
    public ManagerLogin(BankBranch bank) {
        this.bank = bank;
        int id = BankManager.generateId(this.bank.getManagers());
        this.bank.addManager("Manager", Integer.toString(id), "manager", "1111", "1234");
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        this.setBounds(SizeManager.getDialogBounds());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        this.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_south = new JPanel();
        FlowLayout fl_panel_south = (FlowLayout) panel_south.getLayout();
        panel.add(panel_south, BorderLayout.SOUTH);

        UIManager.put("OptionPane.minimumSize", new Dimension(800, 800));
        UIManager.put("TextField.font", FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeText()));
        UIManager.put("PasswordField.font", FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeText()));
        UIManager.put("Label.font", FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeText()));
        UIManager.put("Button.font", FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeText()));

        JButton btnManagerSignIn = new JButton("Manager Sign In");
        btnManagerSignIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JTextField emailField = new JTextField();
                JPasswordField passwordField = new JPasswordField();

                Object[] fields = {"Email: ", emailField, "Password: ", passwordField};
                do {
                    UIManager.put("OptionPane.minimumSize", new Dimension(600, 300));
                    int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);

                    if (reply == JOptionPane.OK_OPTION) {
                        String email = emailField.getText();
                        String password = String.copyValueOf(passwordField.getPassword());

                        BankManager manager = bank.loginManager(email, password);
                        UIManager.put("OptionPane.minimumSize", new Dimension(50, 200));
                        if (manager != null) {
                            JOptionPane.showMessageDialog(null, "Login Successful!");
                            ManagerView manageView = new ManagerView(bank, manager);
                            setVisible(false);
                            manageView.setVisible(true);

                            break;
                        }
                        JOptionPane.showMessageDialog(null, "Email ID or Password incorrect!");
                    }
                    if (reply == JOptionPane.CANCEL_OPTION) {
                        break;
                    }

                } while (true);

            }
        });
        btnManagerSignIn.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        panel_south.add(btnManagerSignIn);

        JButton btnAddNewManager = new JButton("Add new Manager");
        btnAddNewManager.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        btnAddNewManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                JTextField nameField = new JTextField();
                JTextField emailField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                JTextField securityField = new JTextField();

                Object[] fields = {"Name: ", nameField, "Email: ", emailField, "Password: ", passwordField,
                        "Security Code: ", securityField,

                };

                UIManager.put("OptionPane.layout", new GridLayout(11, 2));
                while (true) {
                    int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
                    if (reply == JOptionPane.OK_OPTION) {
                        String name = nameField.getText();
                        String email = emailField.getText();
                        String regex = "^(.+)@(.+)$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(email);
                        if (!matcher.matches()) {
                            JOptionPane.showMessageDialog(null, "Email has to be of format sample@email.com", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        String password = String.copyValueOf(passwordField.getPassword());
                        String security = securityField.getText();
                        int id = BankManager.generateId(bank.getManagers());
                        bank.addManager(name, Integer.toString(id), email, security, password);
                        UIManager.put("OptionPane.minimumSize", new Dimension(50, 200));
                        JOptionPane.showMessageDialog(null, "Created New Manager. Please login");
                        break;
                    } else {
                        break;
                    }
                }
            }
        });
        panel_south.add(btnAddNewManager);

        JPanel panel_5 = new JPanel();
        JLabel background = new JLabel();
        ImageIcon icon = new ImageIcon("src/img/manager.png");
        background.setIcon(icon);
        background.setBounds(0, 0, 515, 515);
        panel_5.add(background);
        panel.add(panel_5, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        this.getContentPane().add(panel_1, BorderLayout.WEST);

        JPanel panel_2 = new JPanel();
        this.getContentPane().add(panel_2, BorderLayout.NORTH);

        JLabel lblChooseFromAn = new JLabel("Choose from an option below:");
        lblChooseFromAn.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        panel_2.add(lblChooseFromAn);

        JPanel panel_3 = new JPanel();
        this.getContentPane().add(panel_3, BorderLayout.SOUTH);

        JPanel panel_4 = new JPanel();
        this.getContentPane().add(panel_4, BorderLayout.EAST);
    }
}
