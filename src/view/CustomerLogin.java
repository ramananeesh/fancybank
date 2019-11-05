package view;

import controller.BankBranch;
import model.Address;
import model.BankCustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerLogin extends JFrame {

    private BankBranch bank;

    /**
     * Create the application.
     */
    public CustomerLogin(BankBranch bank) {
        super();
        this.bank = bank;
        // test purpose
        this.bank.addCustomer("Aneesh", new Address("", "", "", "", ""), "", "", "test", "1234");
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

        JButton buttonSignIn = new JButton("Sign In");
        buttonSignIn.addActionListener(new ActionListener() {
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

                        BankCustomer customer = bank.login(email, password);
                        UIManager.put("OptionPane.minimumSize", new Dimension(50, 200));
                        if (customer != null) {
                            JOptionPane.showMessageDialog(null, "Login Successful!");
                            CustomerView custView = new CustomerView(bank, customer);
                            setVisible(false);
                            custView.setVisible(true);

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
        buttonSignIn.setFont(FontManager.getFontButton().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        panel_south.add(buttonSignIn);

        JButton buttonSignUp = new JButton("Sign Up");
        buttonSignUp.setFont(FontManager.getFontButton().deriveFont(Font.PLAIN, SizeManager.getTextSizeButton()));
        buttonSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                JTextField nameField = new JTextField();
                JTextField emailField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                JTextField phoneField = new JTextField();
                JTextField streetField = new JTextField();
                JTextField houseField = new JTextField();
                JTextField cityField = new JTextField();
                JTextField stateField = new JTextField();
                JTextField zipcodeField = new JTextField();
                JTextField ssnField = new JTextField();
                JCheckBox checkingCheck = new JCheckBox();
                JCheckBox savingsCheck = new JCheckBox();

                Object[] fields = {"Name: ", nameField, "Email: ", emailField, "Password: ", passwordField,
                        "Phone #: ", phoneField, "SSN: ", ssnField, "Street Address: ", streetField, "House #: ",
                        houseField, "City: ", cityField, "State: ", stateField, "Zip Code: ", zipcodeField,};

                UIManager.put("OptionPane.layout", new GridLayout(11, 2));
                while (true) {
                    int reply = JOptionPane.showConfirmDialog(null, fields, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
                    if (reply == JOptionPane.OK_OPTION) {
                        String name = nameField.getText();

                        String email = emailField.getText();
                        String regex = "^(.+)@(.+)$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(email);

                        String password = String.copyValueOf(passwordField.getPassword());
                        String phone = phoneField.getText();
                        String street = streetField.getText();
                        String house = houseField.getText();
                        String city = cityField.getText();
                        String state = stateField.getText();
                        String zipcode = zipcodeField.getText();
                        String ssn = ssnField.getText();
                        if (!matcher.matches()) {
                            JOptionPane.showMessageDialog(null, "Email has to be of format sample@email.com", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            continue;
                        } else if (!validatePhoneNumber(phone)) {
                            JOptionPane.showMessageDialog(null, "Phone number has to be in the right format", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        if (!ssn.equals("")) {
                            regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(ssn);
                            if (!matcher.matches()) {
                                JOptionPane.showMessageDialog(null, "SSN has to be in the right format", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                        }
                        if (street.equals("") || house.equals("") || city.equals("") || zipcode.equals("") || state.equals("")) {
                            JOptionPane.showMessageDialog(null, "Address fields cannot be empty", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        bank.addCustomer(name, new Address(house, street, city, zipcode, state), phone, ssn, email,
                                password);
                        UIManager.put("OptionPane.minimumSize", new Dimension(50, 200));
                        JOptionPane.showMessageDialog(null, "Created New Customer. Please login");
                        break;
                    } else {
                        break;
                    }
                }
            }
        });
        panel_south.add(buttonSignUp);

        JPanel panelBackground = new JPanel();
        JLabel background = new JLabel(new ImageIcon("src/img/login.png"));
        background.setBounds(0, 0, 515, 515);
        panelBackground.add(background);
        panel.add(panelBackground, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        this.getContentPane().add(panel_1, BorderLayout.WEST);

        JPanel panel_2 = new JPanel();
        this.getContentPane().add(panel_2, BorderLayout.NORTH);

        JLabel lblChooseFromAn = new JLabel("Choose from an option below:");
        lblChooseFromAn.setFont(FontManager.getFontText().deriveFont(Font.PLAIN, SizeManager.getTextSizeDescription()));
        panel_2.add(lblChooseFromAn);

        JPanel panel_4 = new JPanel();
        this.getContentPane().add(panel_4, BorderLayout.EAST);

    }

    private static boolean validatePhoneNumber(String phoneNo) {
        // validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}"))
            return true;
            // validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
            return true;
            // validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
            return true;
            // validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
            return true;
            // return false if nothing matches the input
        else
            return false;
    }
}
