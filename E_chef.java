package Recipie_Project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class E_chef extends JFrame implements MouseListener{
    private JLabel usernameLabel;
    private JButton updateOrder;
    private JTextField customerNameField;
    private JTextArea billArea;
    private JButton deleteOrder;
    private List<JSpinner> quantitySpinners; //provides spinner
    ///jbutton provides buttons
    private JLabel menuLabel;
    private JButton showBookingButton;
//jlabel provides dynamic data entry
    private JLabel pricefeild;
    private JTextArea detailsTextArea;
    private JButton recipie_details;
    private List<JCheckBox> menuCheckboxes; //for checkbox
    private JLabel emailLabel;
    private  Connection con;
    private PreparedStatement pst;
    private JButton Bill;
    public E_chef() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        connectToDatabase();
    }private void initializeComponents() {
        customerNameField = new JTextField();
        updateOrder =new JButton();
        billArea = new JTextArea();
        pricefeild =new JLabel();
        deleteOrder =new JButton();
        usernameLabel = new JLabel();
        showBookingButton=new JButton();
        recipie_details =new JButton();
        detailsTextArea = new JTextArea(10, 30);
        detailsTextArea.setEditable(false);
        emailLabel = new JLabel();
        menuLabel=new JLabel();
        Bill = new JButton("GET YOUR BILL");
        Bill.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
        menuCheckboxes = new ArrayList<>();
        quantitySpinners =new ArrayList<>();
        JPanel menuPanel = new JPanel(new GridLayout(4, 3));//grid layout
        String[] menuItems = {"Chicken Breasts", "Beef Tenderloin", "Fresh Salmon Fillets", "Arborio Rice", "Organic Spinach", "Garlic Cloves", "Whole Wheat Flour", "Unsalted Butter", "Eggs", "Heavy Cream", "Balsamic Vinegar", "Olive Oil","Chicken Stock","Whole Tomatoes","Basil Leaves","Parmesan Cheese"," Linguine Pasta"," Asparagus Spears","Red Onion","Brown Sugar"};
        for (String item : menuItems) {
            JCheckBox menuCheckbox = new JCheckBox(item);
            menuCheckboxes.add(menuCheckbox);
            menuPanel.add(menuCheckbox);

            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1)); // Initial value, minimum, maximum, step
            quantitySpinners.add(quantitySpinner);
            menuPanel.add(quantitySpinner);
        }
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setPreferredSize(new Dimension(400, 400));
    }private void setupLayout() {
        // Additional layout adjustments can be added here
        setTitle("ECHEF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("ECHEF");
        titleLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 50));

        deleteOrder =new JButton("DELETE YOUR ORDERS");
        deleteOrder.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        updateOrder=new JButton("UPDATE ORDERS");
        updateOrder.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,16));
        recipie_details =new JButton("SEARCH FOR RECIPE DETAIL");
        recipie_details.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,16));
        JPanel menu2Panel = new JPanel(new GridLayout(4, 3));

        usernameLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 10));
        emailLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 10));

        showBookingButton=new JButton("SHOW ORDERS ");
        showBookingButton.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        JLabel customerLabel = new JLabel("CUSTOMER");
        customerLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        menuLabel=new JLabel(" Ingredient");
        menuLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        JLabel priceLabel = new JLabel("PRICE:");
        priceLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        JScrollPane billScrollPane = new JScrollPane(billArea);
        JPanel menuPanel = new JPanel(new GridLayout(6, 2));
        for (int i = 0; i < menuCheckboxes.size(); i++) {
            JCheckBox checkbox = menuCheckboxes.get(i);
            menuPanel.add(checkbox);

            JSpinner spinner = quantitySpinners.get(i);
            menuPanel.add(spinner);
        }
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        // Add the menu scroll pane to the layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(titleLabel)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(customerLabel)
                                        .addComponent(customerNameField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(menuLabel)
                                        .addComponent(menuScrollPane))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(priceLabel)
                                        .addComponent(pricefeild, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                .addComponent(Bill)
                                .addComponent(showBookingButton)
                                .addComponent(recipie_details)
                                .addComponent(deleteOrder)
                                .addComponent(updateOrder))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(billScrollPane,GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(usernameLabel)
                                        .addComponent(emailLabel)))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(customerLabel)
                                .addComponent(customerNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(menuLabel)
                                .addComponent(menuScrollPane))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(priceLabel)
                                .addComponent(pricefeild, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(Bill)
                        .addComponent(showBookingButton)
                        .addComponent(recipie_details)
                        .addComponent(deleteOrder)
                        .addComponent(updateOrder)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(billScrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel)
                                .addComponent(emailLabel))
        );
        pack();
    }
    private void setupEventHandlers() {
        Bill.addActionListener(e->handleBooking());
        showBookingButton.addActionListener(e->ShowAllBooking());
        recipie_details.addActionListener(e->recipie_details());
        deleteOrder.addActionListener(e->deleteorder());
        updateOrder.addActionListener(e->updateorder());
    }
    private void updateorder(){
        try {
            // Prompt user for old and new customer names
            String oldCustomerName = JOptionPane.showInputDialog(this, "Enter Old customer name:");
            String newCustomerName = JOptionPane.showInputDialog(this, "Enter New customer name:");
            // Check if user canceled or entered empty names
            if (oldCustomerName == null || oldCustomerName.trim().isEmpty() || newCustomerName == null || newCustomerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No customer names provided. Update canceled.");
                return;
            }
            // SQL query to update customer names in booked_items table
            String updateQuery = "UPDATE booked_items SET customer_name = ? WHERE customer_name = ?";
            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
            updateStatement.setString(1, newCustomerName);
            updateStatement.setString(2, oldCustomerName);
            // Execute the update query
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Customer name updated successfully from '" + oldCustomerName + "' to '" + newCustomerName + "'.");
            } else {
                JOptionPane.showMessageDialog(this, "No orders found for customer '" + oldCustomerName + "'.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }private void deleteorder(){
        try {
            // Prompt user for customer name
            String customerName = JOptionPane.showInputDialog(this, "Enter customer name:");

            // Check if user canceled or entered an empty name
            if (customerName == null || customerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No customer name provided. Deletion canceled.");
                return;
            }
            // SQL query to delete rows where customer_name matches the provided input
            String deleteQuery = "DELETE FROM booked_items WHERE customer_name = ?";
            PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
            deleteStatement.setString(1, customerName);
            // Execute the deletion query
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Orders for customer " + customerName + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No orders found for customer " + customerName + ".");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }}
    private void recipie_details() {
        String productName = JOptionPane.showInputDialog(this, "Enter the product name:");
        if (productName != null && !productName.isEmpty()) {
            try {
                // Prepare the SQL statement for retrieving information based on product name
                String selectQuery = "SELECT res_info, Res_description, res_ingredients FROM recipes_detail WHERE res_pro = ?";
                pst = con.prepareStatement(selectQuery);
                pst.setString(1, productName);
                // Execute the SQL statement
                ResultSet resultSet = pst.executeQuery();
                if (resultSet.next()) {
                    // Retrieve information from the result set
                    String resInfo = resultSet.getString("res_info");
                    String resDesc = resultSet.getString("Res_description");
                    String resIngredient = resultSet.getString("res_ingredients");
                    // Display the retrieved information
                    String message = "Product Name: " + productName + "\n"
                            + "Information: " + resInfo + "\n"
                            + "Description: " + resDesc + "\n"
                            + "Ingredients: " + resIngredient;
                    JOptionPane.showMessageDialog(this, message);
                } else {
                    JOptionPane.showMessageDialog(this, "No information found for product: " + productName);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } finally {
                try {
                    // Close the resources
                    if (pst != null) {
                        pst.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(E_chef.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No product name entered.");
        }
    }private void ShowAllBooking() {
        try {
            String selectQuery = "SELECT item_name, item_price FROM booked_items";
            PreparedStatement pst = con.prepareStatement(selectQuery);

            ResultSet resultSet = pst.executeQuery();
            StringBuilder bookingsInfo = new StringBuilder();
            bookingsInfo.append("********** Purchase Bill **********\n");
            while (resultSet.next()) {
                bookingsInfo.append("Items: ").append(resultSet.getString("item_name")).append("\n");
                bookingsInfo.append("Price: ").append(resultSet.getDouble("item_price")).append("\n");
                bookingsInfo.append("-----------------------------------\n");
            }
            JTextArea textArea = new JTextArea(bookingsInfo.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));

            JOptionPane.showMessageDialog(this, scrollPane, "********************All ORDERS******************", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_chef", "root", "8989");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }public void setUserDetails(String username, String email) {
        if (usernameLabel != null && emailLabel != null) {
            usernameLabel.setText("Username: " + username);
            emailLabel.setText("Email: " + email);
        }
    }private void handleBooking() {
        StringBuilder billBuilder = new StringBuilder(); //stringbuilder is a class which provides varity of modification of strings
        billBuilder.append("Customer Name: ").append(customerNameField.getText()).append("\n");
        billBuilder.append("Selected Menu Items:\n");
        double totalPrice = 0.0;
        for (int i = 0; i < menuCheckboxes.size(); i++) {
            JCheckBox checkbox = menuCheckboxes.get(i);
            if (checkbox.isSelected()) {
                String itemName = checkbox.getText();
                int quantity = (int) quantitySpinners.get(i).getValue(); // Get quantity from corresponding spinner
                double itemPrice = getItemPrice(itemName); // Get price for the selected item (you need to implement this)
                double itemTotalPrice = itemPrice * quantity * 2; // Calculate total price
                billBuilder.append("- ").append(itemName).append(" (Quantity: ").append(quantity).append(", Total Price: $").append(itemTotalPrice).append(")\n");
                totalPrice += itemTotalPrice;

                // Save the item details to the database
                saveItemDetails(customerNameField.getText(), itemName, (int) itemTotalPrice);
            }
        }
        billBuilder.append("Total Price: $").append(totalPrice);
        pricefeild.setText(totalPrice + "$");
        pricefeild.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));

        billArea.setText(""); // Clear previous content
        billArea.append("*********************** BILL **********************\n");
        billArea.append(billBuilder.toString());
        billArea.append("\n");
        billArea.append("\n THANK YOU FOR VISITING OUR APPLICATION");
    }
    private void saveItemDetails(String customerName, String itemName, int itemPrice) {
        try {
            // Prepare the SQL statement for inserting item details
            String insertItemQuery = "INSERT INTO booked_items(customer_name, item_name, item_price) VALUES (?, ?, ?)";
            pst = con.prepareStatement(insertItemQuery);
            pst.setString(1, customerName);
            pst.setString(2, itemName);
            pst.setInt(3, itemPrice);
            // Execute the SQL statement for inserting item details
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item details saved successfully: " + itemName);
            } else {
                System.out.println("Failed to save item details: " + itemName);
            }
        } catch (SQLException e) {
            System.out.println("Error saving item details: " + e.getMessage());
        } finally {
            try {
                // Close the PreparedStatement
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(E_chef.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }private double getItemPrice(String itemName) {
        switch (itemName) {
            case "Chicken Breasts":
                return 9;
            case "Beef Tenderloin":
                return 20;
            case "Fresh Salmon Fillets":
                return 13;
            case "Arborio Rice":
                return 5;
            case "Organic Spinach":
                return 4;
            case "Garlic Cloves":
                return 3;
            case "Whole Wheat Flour":
                return 4;
            case "Unsalted Butter":
                return 6;
            case "Eggs":
                return 7;
            case "Heavy Cream":
                return 4;
            case "Balsamic Vinegar":
                return 8;
            case "Olive Oil":
                return 10;
            case "Chicken Stock":
                return 4;
            case "Whole Tomatoes":
                return 3;
            case "Basil Leaves":
                return 3;
            case "Parmesan Cheese":
                return 7;
            case "Linguine Pasta":
                return 3; // Ensure consistency in item name here
            case "Asparagus":
                return 5;
            case "Red Onion":
                return 2;
            case "Brown Sugar":
                return 3;
            default:
                return 0;
        }}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Recipie_Project.UserRegistration userRegistration = new Recipie_Project.UserRegistration();
            userRegistration.setVisible(true);
            userRegistration.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (userRegistration.isRegistrationSuccessful()) {
                        E_chef bookingUI= new E_chef();
                        bookingUI.setVisible(true);
                    }
                }
            });});}
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }}
