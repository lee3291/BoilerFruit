import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientGUI implements Runnable {

    JFrame frame;
    Socket socket;
    ObjectInputStream ois;
    PrintWriter printWriter;
    public final int USERINFO_MAX_LENGTH = 15; // Max username/password length
    public final int USERINFO_MIN_LENGTH = 5; // Min username/password/email length

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    @Override
    public void run() {
        createGUI();
//        ipAddressPage();
//        loginPage();
//        signUpPage();
//        sellerPage(new ArrayList<>());
        customerPage(new ArrayList<>());
//        editAccountPage();
//        reviewHistoryPage();
    }

    void ipAddressPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(5, 0));

        JLabel welcomeMessage = new JLabel("Welcome to BoilerMarket");
        welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
        welcomeMessage.setFont(new Font(null, Font.PLAIN, 30));

        JLabel promptMessage = new JLabel("Enter Server IP Address");
        promptMessage.setHorizontalAlignment(JLabel.CENTER);
        promptMessage.setFont(new Font(null, Font.PLAIN, 20));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        JTextField ipAddressTxt = new JTextField("localhost", 10);
        ipAddressTxt.setForeground(Color.GRAY);
        ipAddressTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ipAddressTxt.getText().equals("localhost")) {
                    ipAddressTxt.setForeground(Color.BLACK);
                    ipAddressTxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ipAddressTxt.getText().isEmpty()) {
                    ipAddressTxt.setForeground(Color.GRAY);
                    ipAddressTxt.setText("localhost");
                }
            }
        });
        ipAddressTxt.setMaximumSize(new Dimension(200, 50));

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            String ipAddress = ipAddressTxt.getText();
            if (ipAddress.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "IP Address must be filled in!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    socket = new Socket(ipAddress, 8080);
                    ois = new ObjectInputStream(socket.getInputStream());
                    printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.flush();
                    loginPage();
                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(frame, "Unknown Host! Please Try Again!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to connect to Server!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        centerPanel.add(Box.createRigidArea(new Dimension(270, 0)));
        centerPanel.add(ipAddressTxt);
        centerPanel.add(enterButton);

        jPanel.add(welcomeMessage);
        jPanel.add(promptMessage);
        jPanel.add(centerPanel);

        frame.add(jPanel);

        updateFrame();
    }

    void createGUI() {
        frame = new JFrame();
        frame.setTitle("Boiler Fruit Market");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    void loginPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(5, 0));

        // First Row
        JLabel firstLabel = new JLabel("Welcome to Boiler Fruits");
        firstLabel.setHorizontalAlignment(JLabel.CENTER);
        firstLabel.setFont(new Font(null, Font.PLAIN, 20));
        jPanel.add(firstLabel, BorderLayout.NORTH);

        // Second Row
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));

        JLabel idLabel = new JLabel("ID: ");
        idLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField idTxt = new JTextField(10);
        idTxt.setMaximumSize(new Dimension(200, 50));

        secondPanel.add(Box.createRigidArea(new Dimension(255, 0)));
        secondPanel.add(idLabel);
        secondPanel.add(idTxt);

        jPanel.add(secondPanel);

        // Third Row
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.X_AXIS));

        JLabel pwLabel = new JLabel("PW: ");
        pwLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField pwTxt = new JTextField(10);
        pwTxt.setMaximumSize(new Dimension(200, 50));

        thirdPanel.add(Box.createRigidArea(new Dimension(250, 0)));
        thirdPanel.add(pwLabel);
        thirdPanel.add(pwTxt);

        jPanel.add(thirdPanel);

        // Fourth Row
        JLabel fourthPanel = new JLabel();
        fourthPanel.setLayout(new BoxLayout(fourthPanel, BoxLayout.X_AXIS));

        JButton logInButton = new JButton("Log-In");
        logInButton.setMaximumSize(new Dimension(200, 50));
        logInButton.addActionListener(e -> {
            String id = idTxt.getText();
            String pw = pwTxt.getText();

            // Empty fields
            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fields are filled
            String query = String.format("LOGIN_%s_%s", id, pw);
            try {
                printWriter.println(query); // query for login
                printWriter.flush();

                int response = (Integer) ois.readObject();

                if (response == -1) {
                    JOptionPane.showMessageDialog(frame, "Incorrect Account Information!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } else if (response == 0) {
                    // CustomerPage, get products from server.
                    customerPage(custPageQuery());
                } else if (response == 1) {
                    // SellerPage, get stores query
                    // Get user email query, since log in is successful, "currentUser" in server is this user.
                    // get stores query
                    printWriter.println("GETSELLSTR_-1");
                    printWriter.flush();
                    ArrayList<Store> userStores = (ArrayList<Store>) ois.readObject();
                    sellerPage(userStores);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setMaximumSize(new Dimension(200, 50));
        signUpButton.addActionListener(e -> signUpPage());

        fourthPanel.add(Box.createRigidArea(new Dimension(172, 0)));
        fourthPanel.add(logInButton);
        fourthPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        fourthPanel.add(signUpButton);

        jPanel.add(fourthPanel);

        frame.add(jPanel);
        updateFrame();
    }

    void signUpPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // top panel first row, Sign Up label.
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 10, 10));
        JLabel firstLabel = new JLabel("Sign Up");
        firstLabel.setPreferredSize(new Dimension(100, 50));
        firstLabel.setHorizontalAlignment(JLabel.CENTER);
        firstLabel.setVerticalAlignment(JLabel.CENTER);
        firstLabel.setFont(new Font(null, Font.PLAIN, 20));
        topPanel.add(firstLabel);

        // top panel second row, combo box
        JPanel secondRowPanel = new JPanel();
        secondRowPanel.setLayout(new BoxLayout(secondRowPanel, BoxLayout.Y_AXIS));
        String[] userType = {"Customer", "Seller"};
        JComboBox jcomboBox = new JComboBox(userType);
        jcomboBox.setPreferredSize(new Dimension(200, 30));
        jcomboBox.setMaximumSize(jcomboBox.getPreferredSize());
        secondRowPanel.add(jcomboBox);
        topPanel.add(secondRowPanel);
        jPanel.add(topPanel);

        // mid panel, for ID, PW, email labels and text fields
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(3, 2, 10, 10));
        JLabel idLabel = new JLabel("ID: ");
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        idLabel.setVerticalAlignment(JLabel.CENTER);
        idLabel.setFont(new Font("", Font.PLAIN, 20));
        JTextField idTxtField = new JTextField(10);

        JLabel pwLabel = new JLabel("PW: ");
        pwLabel.setHorizontalAlignment(JLabel.CENTER);
        pwLabel.setVerticalAlignment(JLabel.CENTER);
        pwLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField pwTxtField = new JTextField(10);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setVerticalAlignment(JLabel.CENTER);
        emailLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField emailTxtField = new JTextField(10);

        midPanel.add(idLabel);
        midPanel.add(idTxtField);
        midPanel.add(pwLabel);
        midPanel.add(pwTxtField);
        midPanel.add(emailLabel);
        midPanel.add(emailTxtField);

        jPanel.add(midPanel);

        // Go back and sign up button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setMaximumSize(new Dimension(200, 50));
        goBackButton.addActionListener(e -> loginPage());

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setMaximumSize(new Dimension(200, 50));
        signUpButton.addActionListener(e -> {
            String id = idTxtField.getText();
            String pw = pwTxtField.getText();
            String email = emailTxtField.getText();

            // id, pw, email validity check
            if (id.isEmpty() || pw.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else if ((id.length() < USERINFO_MIN_LENGTH) || (id.length() > USERINFO_MAX_LENGTH)) {
                String idErrorMessage = String.format("Please enter a username that is between %d-%d characters!",
                        USERINFO_MIN_LENGTH, USERINFO_MAX_LENGTH);
                JOptionPane.showMessageDialog(frame, idErrorMessage, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ((pw.length() < USERINFO_MIN_LENGTH) || (pw.length() > USERINFO_MAX_LENGTH)) {
                String pwErrorMessage = String.format("Please enter a password that is between %d-%d characters!",
                        USERINFO_MIN_LENGTH, USERINFO_MAX_LENGTH);
                JOptionPane.showMessageDialog(frame, pwErrorMessage, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if ((email.length() < USERINFO_MIN_LENGTH) || (!((email.contains("@")) && (email.contains("."))))) {
                String emailErrorMessage = String.format("Please enter an email in the correct format!\n" +
                        "It must be minimum of %d characters and include '@' and '.'", USERINFO_MIN_LENGTH);
                JOptionPane.showMessageDialog(frame, emailErrorMessage, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (id.contains("_") || pw.contains("_") || email.contains("_")) {
                JOptionPane.showMessageDialog(frame, "Do not include '_' in the fields!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Send query to server
                String signUpQuery = String.format("SIGNUP_%s_%s_%s_%s", jcomboBox.getSelectedItem(), id, email, pw);
                try {
                    printWriter.println(signUpQuery);
                    printWriter.flush();
                    int response = (Integer) ois.readObject();
                    if (response == 1) {
                        JOptionPane.showMessageDialog(frame,
                                "Sign Up Complete. Please Log-In Again!",
                                "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                        loginPage();
                    } else if (response == -1) {
                        JOptionPane.showMessageDialog(frame, "Username already exists!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (response == 0) {
                        JOptionPane.showMessageDialog(frame, "Email already exists!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(Box.createRigidArea(new Dimension(172, 0)));
        buttonPanel.add(goBackButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(signUpButton);

        jPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(jPanel);
        frame.revalidate();
        frame.repaint();
    }

    void customerPage(ArrayList<Product> products) { // this products will be allProducts, or sorted/searched products.
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // North, search bar, search button, refresh button, to add two components, make an inner panel with boxlayout.
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("Search for product name, store, or description", 10);
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for product name, store, or description")) {
                    searchBar.setForeground(Color.BLACK);
                    searchBar.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText("Search for product name, store, or description");
                }
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String query = searchBar.getText();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            //TODO: Client-Server implementation, send query to server
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            //TODO: Refresh listing, when new products are added to server from a seller,
            // this customer should be able to view it after clicking this button.
        });

        northPanel.add(searchBar);
        northPanel.add(searchButton);
        northPanel.add(refreshButton);
        jPanel.add(northPanel, BorderLayout.NORTH);

        // West, usertype label, username label, edit account button, logout button
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JLabel userType = new JLabel("  Customer"); // Space for alignment with buttons
        String userNameStr = "  UserName123"; //TODO: Get from server
        JLabel userNameLabel = new JLabel(userNameStr);

        JButton editAccountButton = new JButton("Edit Account");
        editAccountButton.setPreferredSize(new Dimension(100, 50));
        editAccountButton.setMaximumSize(editAccountButton.getPreferredSize());
        editAccountButton.addActionListener(e -> editAccountPage());

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize(new Dimension(100, 50));
        logOutButton.setMaximumSize(logOutButton.getPreferredSize());
        logOutButton.addActionListener(e -> {
            printWriter.println("LOGOUT");
            printWriter.flush();
            loginPage();
        });

        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(userType);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(userNameLabel);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(editAccountButton);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(logOutButton);

        jPanel.add(westPanel, BorderLayout.WEST);

        // South, review Purchase History button. use box layout for size
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

        JButton reviewHistoryButton = new JButton("Review Purchase History");
        reviewHistoryButton.setPreferredSize(new Dimension(300, 100));
        reviewHistoryButton.setMaximumSize(reviewHistoryButton.getPreferredSize());
        reviewHistoryButton.addActionListener(e -> reviewHistoryPage());

        southPanel.add(Box.createRigidArea(new Dimension(250, 0)));
        southPanel.add(reviewHistoryButton);
        jPanel.add(southPanel, BorderLayout.SOUTH);

        // Center, combobox in box layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Get products arraylist from server, loop and make a String[] of productInfo.
        // Below is an example. TODO: client-server implementation required

        // Example products
        Product product1 = new Product("Apple", "FruitStore1", "Some Apples", 2.00, 3);
        Product product2 = new Product("Banana", "FruitStore2", "Some Bananas", 3.00, 6);
        Product product3 = new Product("Oranges", "FruitStore3", "Some Oranges", 4.00, 12);
        products.add(product1);
        products.add(product2);
        products.add(product3);

        // JList
        JList<Product> productListing = new JList<>();
        DefaultListModel<Product> model = new DefaultListModel<>();
        productListing.setModel(model);

        for (Product p : products) {
            model.addElement(p); // Add products to list
        }

        //Left
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JScrollPane(productListing));

        //Right
        JPanel productPagePanel = new JPanel();
        productPagePanel.setLayout(new BorderLayout());

        JTextPane pDescTxtPane = new JTextPane(); // product description label
        pDescTxtPane.setEditable(false);

        productListing.getSelectionModel().addListSelectionListener(e -> {
            Product p = productListing.getSelectedValue();
            pDescTxtPane.setText(p.toString() + '\n' + p.getDescription());
        });

        JButton sortByPriceButton = new JButton("Sort By Price");
        sortByPriceButton.setPreferredSize(new Dimension(100, 50));
        sortByPriceButton.setMaximumSize(sortByPriceButton.getPreferredSize());
        sortByPriceButton.addActionListener(e -> {
            // TODO:
        });

        JButton sortByQtyButton = new JButton("Sort By Qty");
        sortByQtyButton.setPreferredSize(new Dimension(100, 50));
        sortByQtyButton.setMaximumSize(sortByQtyButton.getPreferredSize());
        sortByQtyButton.addActionListener(e -> {
            // TODO:
        });


        JButton buyItemButton = new JButton("Purchase Item");
        buyItemButton.setPreferredSize(new Dimension(100, 50));
        buyItemButton.setMaximumSize(buyItemButton.getPreferredSize());
        buyItemButton.addActionListener(e -> {
            // TODO: client-server implementation, error message if nothing is selected(pDescLabel.getText().isEmpty),
            //  confirm purchase with JOptionPain message. Concurrency for purchasing item.
        });
        JButton contactSellerButton = new JButton("Contact Seller");
        contactSellerButton.setPreferredSize(new Dimension(100, 50));
        contactSellerButton.setMaximumSize(contactSellerButton.getPreferredSize());
        contactSellerButton.addActionListener(e -> {
            // TODO: JOptionPane message: "Seller has been notified!" Get seller email from server and display.
            //  Send customer email to seller
        });

        productPagePanel.add(pDescTxtPane, BorderLayout.NORTH);

        JPanel centerRightBottomPanel = new JPanel();
        centerRightBottomPanel.setLayout(new BoxLayout(centerRightBottomPanel, BoxLayout.Y_AXIS));
        centerRightBottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerRightBottomPanel.add(sortByPriceButton);
        centerRightBottomPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        centerRightBottomPanel.add(sortByQtyButton);
        centerRightBottomPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        centerRightBottomPanel.add(buyItemButton);
        centerRightBottomPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        centerRightBottomPanel.add(contactSellerButton);
        jPanel.add(centerRightBottomPanel, BorderLayout.EAST);

        splitPane.setRightComponent(new JScrollPane(productPagePanel));

        jPanel.add(splitPane, BorderLayout.CENTER);

        frame.add(jPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     *  Query server to get the recent list of all the products in the market.
     * @return allProducts, all the products in the market.
     * @return null if exception occurs.
     */
    ArrayList<Product> custPageQuery() {
        try {
            printWriter.println("GETMRKPROD_-1"); // get all products query.
            printWriter.flush();
            ArrayList<Product> allProducts = (ArrayList<Product>) ois.readObject();
            return allProducts;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    void editAccountPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 1));

        // first
        JLabel firstPanel = new JLabel();
        firstPanel.setLayout(new GridLayout(2, 1));


        String userEmail = "";
        // Get user email from server.
        Object obj;
        try {
            printWriter.println("EMAIL");   // get email from server
            printWriter.flush();
            obj = ois.readObject();
            userEmail = (String) obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String emailStr = String.format("Email: %s", userEmail);

        JLabel titleLabel = new JLabel("Edit Account Page");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font(null, Font.PLAIN, 30));
        firstPanel.add(titleLabel);

        JLabel emailLabel = new JLabel(emailStr);
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setFont(new Font(null, Font.PLAIN, 20));
        firstPanel.add(emailLabel);

        jPanel.add(firstPanel);

        // second
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));

        JLabel idLabel = new JLabel("ID: ");
        idLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField idTxt = new JTextField("Enter new user name", 10);
        idTxt.setForeground(Color.GRAY);
        idTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idTxt.getText().equals("Enter new user name")) {
                    idTxt.setForeground(Color.BLACK);
                    idTxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idTxt.getText().isEmpty()) {
                    idTxt.setForeground(Color.GRAY);
                    idTxt.setText("Enter new user name");
                }
            }
        });
        idTxt.setMaximumSize(new Dimension(200, 50));
        JButton idChangeButton = new JButton("Change ID");
        idChangeButton.addActionListener(e -> {
            String newId = idTxt.getText();
            if ((newId.length() < USERINFO_MIN_LENGTH) || (newId.length() > USERINFO_MAX_LENGTH)) {
                String idErrorMessage = String.format("Username must be %d-%d characters!",
                        USERINFO_MIN_LENGTH, USERINFO_MAX_LENGTH);
                JOptionPane.showMessageDialog(frame, idErrorMessage, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else if (newId.contains("_")) {
                JOptionPane.showMessageDialog(frame, "Do not include '_' in the fields!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Object obj2;
                    // send query to server
                    String query = String.format("MODID_%s", newId);
                    printWriter.println(query);
                    printWriter.flush();
                    obj2 = ois.readObject();
                    boolean success = (boolean) obj2;
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "ID has been changed!", "SUCCESS",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "ID already exists!", "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        secondPanel.add(Box.createRigidArea(new Dimension(250, 0)));
        secondPanel.add(idLabel);
        secondPanel.add(idTxt);
        secondPanel.add(idChangeButton);

        jPanel.add(secondPanel);

        // Third
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.X_AXIS));

        JLabel pwLabel = new JLabel("PW: ");
        pwLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField pwTxt = new JTextField("Enter new password", 10);
        pwTxt.setForeground(Color.GRAY);
        pwTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (pwTxt.getText().equals("Enter new password")) {
                    pwTxt.setForeground(Color.BLACK);
                    pwTxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pwTxt.getText().isEmpty()) {
                    pwTxt.setForeground(Color.GRAY);
                    pwTxt.setText("Enter new password");
                }
            }
        });

        pwTxt.setMaximumSize(new Dimension(200, 50));
        JButton pwChangeButton = new JButton("Change PW");
        pwChangeButton.addActionListener(e -> {
            String newPw = pwTxt.getText();
            if ((newPw.length() < USERINFO_MIN_LENGTH) || (newPw.length() > USERINFO_MAX_LENGTH)) {
                String pwErrorMessage = String.format("Password must be %d-%d characters!",
                        USERINFO_MIN_LENGTH, USERINFO_MAX_LENGTH);
                JOptionPane.showMessageDialog(frame, pwErrorMessage, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else if (newPw.contains("_")) {
                JOptionPane.showMessageDialog(frame, "Do not include '_' in the fields!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // send query to change password
                    String query = String.format("MODPW_%s", newPw);
                    printWriter.println(query);
                    printWriter.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(frame, "Password has been changed!",
                        "ERROR", JOptionPane.PLAIN_MESSAGE);
            }
        });

        thirdPanel.add(Box.createRigidArea(new Dimension(240, 0)));
        thirdPanel.add(pwLabel);
        thirdPanel.add(pwTxt);
        thirdPanel.add(pwChangeButton);

        jPanel.add(thirdPanel);

        // fourth, bottom last
        JLabel fourthPanel = new JLabel();
        fourthPanel.setLayout(new BoxLayout(fourthPanel, BoxLayout.X_AXIS));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setMaximumSize(new Dimension(200, 50));
        goBackButton.addActionListener(e -> {
            Object obj1;
            // query usertype from server
            try {
                printWriter.println("TYPE");
                printWriter.flush();
                obj1 = ois.readObject();
                String userType = (String) obj1;
                if (userType.equals("Customer")) {
                    // go back to CustomerPage, get products from server.
                    try {
                        customerPage(custPageQuery());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // SellerPage, get stores query
                    // Get user email query, since log in is successful, "currentUser" in server is this user.
                    // get stores query
                    try {
                        printWriter.println("GETSELLSTR_-1");
                        printWriter.flush();
                        obj1 = ois.readObject();
                        ArrayList<Store> userStores = (ArrayList<Store>) obj1;
                        sellerPage(userStores);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Something went wrong, Please try again!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setMaximumSize(new Dimension(200, 50));
        deleteAccountButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(frame, "Are you sure?",
                    "Delete Account", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                printWriter.println("DELACC"); // account has been deleted and currentUser set to null.
                printWriter.flush();
                JOptionPane.showMessageDialog(frame, "Account has been deleted! Redirecting to Log-in Page...",
                        "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                loginPage();
            }
        });

        fourthPanel.add(Box.createRigidArea(new Dimension(172, 0)));
        fourthPanel.add(goBackButton);
        fourthPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        fourthPanel.add(deleteAccountButton);

        jPanel.add(fourthPanel);

        frame.add(jPanel);
        frame.revalidate();
        frame.repaint();
    }

    void reviewHistoryPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // north
        JLabel titleLabel = new JLabel("Purchase History");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font(null, Font.PLAIN, 30));
        jPanel.add(titleLabel, BorderLayout.NORTH);

        // Center, list
        ArrayList<String> purchaseHistory = new ArrayList<>(); //TODO: Get from server
        for (int i = 0; i < 15; i++) {
            purchaseHistory.add("Some purchase history example" + i);
        }

        JList<String> historyList = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        historyList.setModel(model);

        for (String s : purchaseHistory) {
            model.addElement(s);
        }

        jPanel.add(new JScrollPane(historyList), BorderLayout.CENTER);

        // south
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setMaximumSize(new Dimension(200, 50));
        goBackButton.addActionListener(e -> {
            // TODO: Get allProducts ArrayList from server
            // customerPage(allProducts);
        });

        JButton exportHistoryButton = new JButton("Export History");
        exportHistoryButton.setMaximumSize(new Dimension(200, 50));
        exportHistoryButton.addActionListener(e -> {
            String filePath = JOptionPane.showInputDialog(frame, "Enter file path for export: ",
                    "Export Purchase History", JOptionPane.PLAIN_MESSAGE);
            if (filePath == null || filePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please give valid file path!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
            // TODO: server, export purchase history
        });
        southPanel.add(Box.createRigidArea(new Dimension(170, 0)));
        southPanel.add(goBackButton);
        southPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        southPanel.add(exportHistoryButton);

        jPanel.add(southPanel, BorderLayout.SOUTH);

        frame.add(jPanel);
        updateFrame();
    }

    void sellerPage(ArrayList<Store> stores) { // this products will be allStores
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // North, search bar, search button, refresh button, to add two components, make an inner panel with boxlayout.
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("Search for store", 10);
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for store")) {
                    searchBar.setForeground(Color.BLACK);
                    searchBar.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText("Search for store");
                }
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            if (searchBar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please fill in blank field!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = String.format("GETSELLSTR_%s", searchBar.getText());
            //TODO: Client-Server implementation, send query to server
            System.out.println(query);
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            String query = "GETSELLSTR_-1";
            // TODO: Get stores ArrayList from server
            System.out.println(query);
            // sellerPage(stores);
        });

        northPanel.add(searchBar);
        northPanel.add(searchButton);
        northPanel.add(refreshButton);
        jPanel.add(northPanel, BorderLayout.NORTH);

        // West, usertype label, username label, edit account button, logout button
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JLabel userType = new JLabel("  Seller"); // Space for alignment with buttons
        String userNameStr = "  UserName123"; //TODO: Get from server
        JLabel userNameLabel = new JLabel(userNameStr);

        JButton editAccountButton = new JButton("Edit Account");
        editAccountButton.setPreferredSize(new Dimension(100, 50));
        editAccountButton.setMaximumSize(editAccountButton.getPreferredSize());
        editAccountButton.addActionListener(e -> editAccountPage());

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize(new Dimension(100, 50));
        logOutButton.setMaximumSize(logOutButton.getPreferredSize());
        logOutButton.addActionListener(e -> {
            printWriter.println("LOGOUT");
            printWriter.flush();
            loginPage();
        });

        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(userType);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(userNameLabel);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(editAccountButton);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(logOutButton);

        jPanel.add(westPanel, BorderLayout.WEST);

        // South, create & delete store buttons. use flow layout
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        JButton createStoreButton = new JButton("Create Store");
        createStoreButton.setPreferredSize(new Dimension(100, 50));
        createStoreButton.setMaximumSize(createStoreButton.getPreferredSize());
        createStoreButton.addActionListener(e -> {
            // Getting Create store name
            String storeName = JOptionPane.showInputDialog(null, "Enter a New Store Name:",
                    "Create Store", JOptionPane.QUESTION_MESSAGE);

            // User close the GUI; terminate the program; do nothing
            if (storeName == null) {
                return;
            }

            // Store name is empty
            if (storeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Store name cannot be empty!",
                        "ERROR - Create Store", JOptionPane.ERROR_MESSAGE);
            } else { // Make query and send to server
                String serverQuery = String.format("CRTSTR_%s", storeName);
                System.out.println(serverQuery);

                ArrayList<Store> newStores = new ArrayList<>(); // TODO: send query & receive newStores from server
                sellerPage(newStores);
            }
        });

        JButton deleteStoreButton = new JButton("Delete Store");
        deleteStoreButton.setPreferredSize(new Dimension(100, 50));
        deleteStoreButton.setMaximumSize(deleteStoreButton.getPreferredSize());
        deleteStoreButton.addActionListener(e -> {
            // Getting Delete store name
            String storeName = JOptionPane.showInputDialog(null,
                    "Enter a Store Name to be deleted:", "Delete Store", JOptionPane.QUESTION_MESSAGE);

            // User close the GUI; terminal the program; do nothing
            if (storeName == null) {
                return;
            }

            // Input is empty
            if (storeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Store name cannot be empty!",
                        "ERROR - Delete Store", JOptionPane.ERROR_MESSAGE);
            } else { // Make query and send to server
                String serverQuery = String.format("DELSTR_%s", storeName);
                System.out.println(serverQuery);

                ArrayList<Store> newStores = new ArrayList<>(); // TODO: send query & receive newStores from server
                sellerPage(newStores);
            }
        });

        southPanel.add(createStoreButton);
        southPanel.add(deleteStoreButton);
        jPanel.add(southPanel, BorderLayout.SOUTH);

        // Center, combo-box in box layout
        // TODO: client-server implementation required

        // Example stores for testing.
        ArrayList<Product> exampleProducts = new ArrayList<>();
        Product product1 = new Product("Apple", "Store", "Some Apples", 2.00,
                3);
        Product product2 = new Product("Banana", "Store", "Some Bananas", 3.00,
                6);
        Product product3 = new Product("Oranges", "Store", "Some Oranges", 4.00,
                12);
        exampleProducts.add(product1);
        exampleProducts.add(product2);
        exampleProducts.add(product3);

        Store store1 = new Store("Amazon", "@Bezos.1", 0, exampleProducts, new ArrayList<>(),
                new ArrayList<>());
        Store store2 = new Store("Tiki", "@Bezos.1");
        Store store3 = new Store("Lazada", "@Bezos.1");
        ArrayList<Store> exampleStores = new ArrayList<>();
        exampleStores.add(store1);
        exampleStores.add(store2);
        exampleStores.add(store3);

        stores = exampleStores; // TODO: get from server

        // JList
        JList<Store> userStores = new JList<>();
        DefaultListModel<Store> model = new DefaultListModel<>();
        userStores.setModel(model);
        for (Store s : stores) {
            model.addElement(s); // Add stores to list
        }

        // Implement ListSelectionModel (avoid it from fire twice)
        ListSelectionModel storeList = userStores.getSelectionModel();
        storeList.setValueIsAdjusting(false);
        storeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Store store = userStores.getSelectedValue();
                storePage(store, store.getCurrentProducts());
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new JScrollPane(userStores));

        jPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(jPanel);
        updateFrame();
    }

    void storePage(Store store, ArrayList<Product> products) {
        // Setting up store page frame
        resetFrame();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // North, search bar, search button, refresh button, to add two components, make an inner panel with boxlayout.
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("Search for product name or description", 10);
        searchBar.setForeground(Color.GRAY);
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for product name or description")) {
                    searchBar.setForeground(Color.BLACK);
                    searchBar.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setText("Search for product name or description");
                }
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            if (searchBar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            //TODO: Client-Server implementation, send query to server
            String query = String.format("GETSTRPROD_%s_%s_%s",
                    store.getSellerEmail(), store.getStoreName(), searchBar.getText());
            System.out.println(query);
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            //TODO: call to server for new Product
            String query = String.format("GETSTRPROD_%s_%s_%d",
                    store.getSellerEmail(), store.getStoreName(), -1);
            System.out.println(query);
            storePage(store, products);
        });

        northPanel.add(searchBar);
        northPanel.add(searchButton);
        northPanel.add(refreshButton);
        jPanel.add(northPanel, BorderLayout.NORTH);

        // West, usertype label, view sale button, export button, back button
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JLabel userType = new JLabel("         " + store.getStoreName()); // Space for alignment with buttons

        JButton viewSaleButton = new JButton("View sale");
        viewSaleButton.setPreferredSize(new Dimension(120, 50));
        viewSaleButton.setMaximumSize(viewSaleButton.getPreferredSize());
//        viewSaleButton.addActionListener(e -> storeViewSale());

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setPreferredSize(new Dimension(120, 50));
        addProductButton.setMaximumSize(addProductButton.getPreferredSize());
        addProductButton.addActionListener(e -> addProductPage(store));

        JButton backButton = new JButton("Go Back");
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setMaximumSize(backButton.getPreferredSize());
        backButton.addActionListener(e -> {
            // TODO: Get stores ArrayList from server.
            String query = "GETSELLSTR_-1";
            ArrayList<Store> updateStores = new ArrayList<>();
            sellerPage(updateStores);
        });

        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(userType);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(viewSaleButton);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(addProductButton);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(backButton);

        jPanel.add(westPanel, BorderLayout.WEST);

        // South, review Purchase History button. use box layout for size
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        FileIO fileIO = new FileIO();

        JButton importProductButton = new JButton("Import Products");
        importProductButton.setPreferredSize(new Dimension(120, 50));
        importProductButton.setMaximumSize(importProductButton.getPreferredSize());
        importProductButton.addActionListener(e -> {
            // Getting input path
            String inputPath = JOptionPane.showInputDialog(null, "Enter a CSV file path: ",
                    "Import Product", JOptionPane.QUESTION_MESSAGE);

            // User close the GUI; terminate the program; do nothing
            if (inputPath == null) {
                return;
            }

            // File path is empty
            if (inputPath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Path cannot be empty!",
                        "ERROR - Import Product", JOptionPane.ERROR_MESSAGE);
            } else {
                // Import product
                ArrayList<Product> importedProduct = fileIO.importCSV(inputPath);
                if (importedProduct == null) {
                    JOptionPane.showMessageDialog(null,
                            "Either path or the file format is incorrect",
                            "ERROR - Import Product", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add products
                for (Product p : importedProduct) {
                    String serverQuery = String.format(String.format("ADDPROD_%s_%s_%s_%.2f_%d",
                            p.getName(), store.getStoreName(), p.getDescription(), p.getPrice(), p.getQuantity()));
                    // TODO: send query to add product to server
                    System.out.println(serverQuery);
                }

                // Refresh
                //TODO: call to server for new Product
                String query = String.format("GETSTRPROD_%s_%s_%d",
                        store.getSellerEmail(), store.getStoreName(), -1);
                System.out.println(query);
                storePage(store, products);
            }
        });

        JButton exportProductButton = new JButton("Export Products");
        exportProductButton.setPreferredSize(new Dimension(120, 50));
        exportProductButton.setMaximumSize(exportProductButton.getPreferredSize());
        exportProductButton.addActionListener(e -> {
            // Getting output path
            String outputPath = JOptionPane.showInputDialog(null, "Enter a path for output: ",
                    "Export Product", JOptionPane.QUESTION_MESSAGE);

            // User close the GUI; terminate the program; do nothing
            if (outputPath == null) {
                return;
            }

            // File path is empty
            if (outputPath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Path cannot be empty!",
                        "ERROR - Export Product", JOptionPane.ERROR_MESSAGE);
            } else { // Exporting
                ArrayList<Product> exportProducts = store.getCurrentProducts();
                if (fileIO.exportCSV(outputPath, exportProducts)) {
                    JOptionPane.showMessageDialog(null,
                            "Product is written into " + outputPath,
                            "SUCCESS - Export Product", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Something went wrong. Please try again later!",
                            "ERROR - Export Product", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        southPanel.add(importProductButton);
        southPanel.add(exportProductButton);
        jPanel.add(southPanel, BorderLayout.SOUTH);

        // Center, combo-box in box layout
        JSplitPane centralSplitPanel = new JSplitPane();

        // JList
        JList<Product> currentProducts = new JList<>();
        DefaultListModel<Product> model = new DefaultListModel<>();
        currentProducts.setModel(model);
        for (Product p : store.getCurrentProducts()) {
            model.addElement(p); // Add stores to list
        }

        JLabel noProductAnnouncement = new JLabel();
        noProductAnnouncement.setText("Store has no product yet.\nYou can add more product to the store!");

        // Left
        if (store.getCurrentProducts().isEmpty()) {
            centralSplitPanel.setLeftComponent(noProductAnnouncement);
        } else {
            centralSplitPanel.setLeftComponent(currentProducts);
        }

        // Right
        JPanel detailProductPanel = new JPanel();
        detailProductPanel.setLayout(new BorderLayout());
        JLabel detailLabel = new JLabel();

        // Implement ListSelectionModel (avoid it from fire twice)
        ListSelectionModel productList = currentProducts.getSelectionModel();
        productList.setValueIsAdjusting(false);
        productList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product p = currentProducts.getSelectedValue();
                detailLabel.setText(p.getDescription());
            }
        });

        JButton modifyButton = new JButton("Modify");
        modifyButton.setPreferredSize(new Dimension(100, 50));
        modifyButton.setMaximumSize(modifyButton.getPreferredSize());
        modifyButton.addActionListener(e -> {
            Product selectedProduct = currentProducts.getSelectedValue();
            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(null, "Need to select a product first!",
                        "ERROR - Modify Product", JOptionPane.ERROR_MESSAGE);
                return;
            }

            modifyProductPage(store, selectedProduct);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 50));
        deleteButton.setMaximumSize(deleteButton.getPreferredSize());
        deleteButton.addActionListener(e -> {
            Product selectedProduct = currentProducts.getSelectedValue();
            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(null, "Need to select a product first!",
                        "ERROR - Delete Product", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: delete the selected product from server
            String query = String.format("DELPROD_%s_%s_%s",
                    store.getSellerEmail(), selectedProduct.getStore(), selectedProduct.getName());
            System.out.println(query);
            ArrayList<Product> newProductList = new ArrayList<>();
            storePage(store, products);
            updateFrame();
        });

        detailProductPanel.add(detailLabel, BorderLayout.NORTH);

        JPanel centerRightBottomPanel = new JPanel();
        centerRightBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        centerRightBottomPanel.add(modifyButton);
        centerRightBottomPanel.add(deleteButton);
        detailProductPanel.add(centerRightBottomPanel, BorderLayout.SOUTH);

        centralSplitPanel.setRightComponent(new JScrollPane(detailProductPanel));

        jPanel.add(centralSplitPanel, BorderLayout.CENTER);

        frame.add(jPanel);
        updateFrame();
    }

    /**
     * Page for adding new product
     *
     * @param store the store of the new product
     */
    void addProductPage(Store store) {
        resetFrame();

        JPanel jPanel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Add Product");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font(null, Font.PLAIN, 20));
        titleLabel.setPreferredSize(new Dimension(200, 100));
        jPanel.add(titleLabel, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nameLabel = new JLabel("Enter a Product Name: ");
        nameLabel.setPreferredSize(new Dimension(200, 50));
        nameLabel.setMinimumSize(nameLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(nameLabel, gbc);

        JTextField productName = new JTextField();
        productName.setPreferredSize(new Dimension(380, 50));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productName, gbc);

        JLabel descriptionLabel = new JLabel("Enter a Product Description: ");
        descriptionLabel.setPreferredSize(new Dimension(200, 200));
        descriptionLabel.setMinimumSize(descriptionLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(descriptionLabel, gbc);

        JTextPane productDescription = new JTextPane();
        productDescription.setPreferredSize(new Dimension(380, 180));
        productDescription.setMinimumSize(productDescription.getPreferredSize());
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productDescription, gbc);

        JLabel priceLabel = new JLabel("Enter a Product Price: ");
        priceLabel.setPreferredSize(new Dimension(200, 50));
        priceLabel.setMinimumSize(priceLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(priceLabel, gbc);

        JTextField productPrice = new JTextField();
        productPrice.setPreferredSize(new Dimension(380, 50));
        productPrice.setMinimumSize(productPrice.getPreferredSize());
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productPrice, gbc);

        JLabel quantityLabel = new JLabel("Enter a Product Quantity: ");
        quantityLabel.setPreferredSize(new Dimension(200, 50));
        quantityLabel.setMinimumSize(quantityLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(quantityLabel, gbc);

        JTextField productQuantity = new JTextField();
        productQuantity.setPreferredSize(new Dimension(380, 50));
        productQuantity.setMinimumSize(productQuantity.getPreferredSize());
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productQuantity, gbc);

        jPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setPreferredSize(new Dimension(120, 50));
        goBackButton.addActionListener(e -> {
            String query = String.format("GETSELLSTR_%s_%d", store.getStoreName(), -1);

            // TODO: send and get from server
            System.out.println(query);
            storePage(store, new ArrayList<>());
        });

        JButton confirmButton = new JButton("Add");
        confirmButton.setPreferredSize(new Dimension(120, 50));
        confirmButton.addActionListener(e -> {
            // Get inputs
            String inputName = productName.getText();
            String inputDescription = productDescription.getText();
            String inputPrice = productPrice.getText();
            String inputQty = productQuantity.getText();

            // Make sure inputs are not empty
            if (inputName.isEmpty() || inputDescription.isEmpty() ||
                    inputPrice.isEmpty() || inputQty.isEmpty()) {
                JOptionPane.showMessageDialog(null, "None of the fields can be empty!",
                        "ERROR-Add Product", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Make sure price is double and quantity is integer
            int quantity;
            double price;
            try {
                price = Double.parseDouble(inputPrice);
                quantity = Integer.parseInt(inputQty);
            } catch (NumberFormatException en) {
                JOptionPane.showMessageDialog(null,
                        "Price or Quantity is in an incorrect format",
                        "ERROR-Add Product", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Making query and send to server
            String query = String.format("ADDPROD_%s_%s_%s_%.2f_%d",
                    inputName, store.getStoreName(), inputDescription, price, quantity);

            // TODO: send query and get from server
            System.out.println(query);
            storePage(store, new ArrayList<>());
        });

        bottomPanel.add(goBackButton);
        bottomPanel.add(confirmButton);
        jPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add to frame and update
        frame.add(jPanel);
        updateFrame();
    }

    /**
     * Page for modify product
     *
     * @param oldProduct the product to be modified
     */
    void modifyProductPage(Store store, Product oldProduct) {
        resetFrame();

        JPanel jPanel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Modify Product");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font(null, Font.PLAIN, 20));
        titleLabel.setPreferredSize(new Dimension(200, 100));
        jPanel.add(titleLabel, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nameLabel = new JLabel("Enter a New Name: ");
        nameLabel.setPreferredSize(new Dimension(200, 50));
        nameLabel.setMinimumSize(nameLabel.getPreferredSize());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(nameLabel, gbc);

        JTextField productName = new JTextField("Enter a new product name; Leave blank for old value!");
        productName.setForeground(Color.GRAY);
        productName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productName.getText().equals("Enter a new product name; Leave blank for old value!")) {
                    productName.setForeground(Color.BLACK);
                    productName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productName.getText().isEmpty()) {
                    productName.setForeground(Color.GRAY);
                    productName.setText("Enter a new product name; Leave blank for old value!");
                }
            }
        });
        productName.setPreferredSize(new Dimension(380, 50));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productName, gbc);

        JLabel descriptionLabel = new JLabel("Enter a New Description: ");
        descriptionLabel.setPreferredSize(new Dimension(200, 200));
        descriptionLabel.setMinimumSize(descriptionLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(descriptionLabel, gbc);

        JTextPane productDescription = new JTextPane();
        productDescription.setText("Enter a new product description; Leave blank for old value!");
        productDescription.setForeground(Color.GRAY);
        productDescription.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productDescription.getText().equals("Enter a new product description; " +
                        "Leave blank for old value!")) {
                    productDescription.setForeground(Color.BLACK);
                    productDescription.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productDescription.getText().isEmpty()) {
                    productDescription.setForeground(Color.GRAY);
                    productDescription.setText("Enter a new product description; Leave blank for old value!");
                }
            }
        });
        productDescription.setPreferredSize(new Dimension(380, 180));
        productDescription.setMinimumSize(productDescription.getPreferredSize());
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productDescription, gbc);

        JLabel priceLabel = new JLabel("Enter a New Price: ");
        priceLabel.setPreferredSize(new Dimension(200, 50));
        priceLabel.setMinimumSize(priceLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(priceLabel, gbc);

        JTextField productPrice = new JTextField("Enter a new product price; Leave blank for old value!");
        productPrice.setForeground(Color.GRAY);
        productPrice.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productPrice.getText().equals("Enter a new product price; Leave blank for old value!")) {
                    productPrice.setForeground(Color.BLACK);
                    productPrice.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productPrice.getText().isEmpty()) {
                    productPrice.setForeground(Color.GRAY);
                    productPrice.setText("Enter a new product price; Leave blank for old value!");
                }
            }
        });
        productPrice.setPreferredSize(new Dimension(380, 50));
        productPrice.setMinimumSize(productPrice.getPreferredSize());
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productPrice, gbc);

        JLabel quantityLabel = new JLabel("Enter a New Quantity: ");
        quantityLabel.setPreferredSize(new Dimension(200, 50));
        quantityLabel.setMinimumSize(quantityLabel.getPreferredSize());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(quantityLabel, gbc);

        JTextField productQuantity = new JTextField("Enter a new product quantity; Leave blank for old value!");
        productQuantity.setForeground(Color.GRAY);
        productQuantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productQuantity.getText().equals("Enter a new product quantity; Leave blank for old value!")) {
                    productQuantity.setForeground(Color.BLACK);
                    productQuantity.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productQuantity.getText().isEmpty()) {
                    productQuantity.setForeground(Color.GRAY);
                    productQuantity.setText("Enter a new product quantity; Leave blank for old value!");
                }
            }
        });
        productQuantity.setPreferredSize(new Dimension(380, 50));
        productQuantity.setMinimumSize(productQuantity.getPreferredSize());
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(productQuantity, gbc);

        jPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setPreferredSize(new Dimension(120, 50));
        goBackButton.addActionListener(e -> {
            String query = String.format("GETSELLSTR_%s_%d", store.getStoreName(), -1);

            // TODO: send and get from server
            System.out.println(query);
            storePage(store, new ArrayList<>());
        });

        JButton confirmButton = new JButton("Modify");
        confirmButton.setPreferredSize(new Dimension(120, 50));
        confirmButton.addActionListener(e -> {
            // Adjust fields of the selected product
            String adjustName;
            String adjustDescription;
            double adjustPrice;
            int adjustQty;

            // Input name
            String inputName = productName.getText();
            if (inputName.equals("Enter a new product name; Leave blank for old value!")) {
                adjustName = oldProduct.getName();
            } else {
                adjustName = inputName;
            }

            // Input description
            String inputDescription = productDescription.getText();
            if (inputDescription.equals("Enter a new product description; Leave blank for old value!")) {
                adjustDescription = oldProduct.getDescription();
            } else {
                adjustDescription = inputDescription;
            }

            // Input price
            String inputPrice = productPrice.getText();
            if (inputPrice.equals("Enter a new product price; Leave blank for old value!")) {
                adjustPrice = oldProduct.getPrice();
            } else {
                try {
                    adjustPrice = Double.parseDouble(inputPrice);
                } catch (NumberFormatException en) {
                    JOptionPane.showMessageDialog(null,
                            "Price is in an incorrect format",
                            "ERROR-Add Product", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Input quantity
            String inputQty = productQuantity.getText();
            if (inputPrice.equals("Enter a new product quantity; Leave blank for old value!")) {
                adjustQty = oldProduct.getQuantity();
            } else {
                try {
                    adjustQty = Integer.parseInt(inputQty);
                } catch (NumberFormatException en) {
                    JOptionPane.showMessageDialog(null,
                            "Quantity is in an incorrect format",
                            "ERROR-Add Product", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Making query and send to server
            String query = String.format("MODPROD_%s_%s_%s_%s_%.2f_%d",
                    oldProduct.getName(), adjustName, store.getStoreName(), adjustDescription, adjustPrice, adjustQty);

            // TODO: send query and get from server
            System.out.println(query);
            storePage(store, new ArrayList<>());
        });

        bottomPanel.add(goBackButton);
        bottomPanel.add(confirmButton);
        jPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add to frame and update
        frame.add(jPanel);
        updateFrame();
    }

    void updateFrame() {
        frame.revalidate(); // Notifies layout manager that component has changed.
        frame.repaint(); // repaints the components
    }

    void resetFrame() {
        frame.getContentPane().removeAll(); // Removes all components from frame.
        updateFrame();
    }
}
