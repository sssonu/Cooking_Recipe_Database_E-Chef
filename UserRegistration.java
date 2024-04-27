package Recipie_Project;



import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;


    private JPasswordField passwordField;

    private Connection con;
    private JButton registerButton;


    public UserRegistration() {
        initializeComponents();
        setLayout();
        setupEventHandlers();
        connect();
    }
    private boolean registrationSuccessful = false;

    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        this.registrationSuccessful = registrationSuccessful;
    }

    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Register");

        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setLayout() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(registerButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }


    private void setupEventHandlers() {
        registerButton.addActionListener(e -> registerUser());
    }
    private boolean validateEmail(String email) {
        return email.contains("@") && email.toLowerCase().equals(email);
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8 && !password.toLowerCase().equals(password) && !password.toUpperCase().equals(password);
    }



    private void registerUser() {
        String username = usernameField.getText();//fetching the username from username label
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return;
        }

        if (!validatePassword(password)) {
            JOptionPane.showMessageDialog(this, "Invalid password format! Password must contain at least 8 characters and include both lowercase and uppercase letters.");
            return;
        }


        try {
            String insertQuery = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);

            int rowsAffected = pst.executeUpdate(); //returns positive and negative values--- +ve means execution successfull and negative means not execute
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                setRegistrationSuccessful(true);
                E_chef b=new E_chef();
                b.setVisible(true);
                b.setUserDetails(username,email);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!");
            }

            pst.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_chef", "root", "8989");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        // Connect to the database

            SwingUtilities.invokeLater(() -> {
                UserRegistration registrationForm = new UserRegistration();
                registrationForm.setVisible(true);
            });
        }
}
