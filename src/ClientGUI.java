import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Flow;

public class ClientGUI implements Runnable {

    static JFrame frame;

    public final int USERINFO_MAX_LENGTH = 15; // Max username/password length
    public final int USERINFO_MIN_LENGTH = 5; // Min username/password/email length
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    @Override
    public void run() {
        createGUI();
//        loginPage();
//        signUpPage();
        sellerPage(new ArrayList<>());
//        customerPage(new ArrayList<>());
//        editAccountPage();
//        reviewHistoryPage();
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

        //TODO: Add action listeners, error message for invalid input
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(5, 0));


        // First Row
        JLabel firstLabel = new JLabel("Welcome to Boiler Fruits");
        firstLabel.setHorizontalAlignment(JLabel.CENTER);
        firstLabel.setFont(new Font(null, Font.PLAIN, 20));
        jPanel.add(firstLabel);


        // Second Row
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.X_AXIS));

        JLabel idLabel = new JLabel("ID: ");
        idLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField idTxt = new JTextField( 10);
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
        JTextField pwTxt = new JTextField( 10);
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

            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // TODO: Sign in info is valid, send user to customer page or seller page
            // TODO: Client-Server implementation comes here. Wrong ID & Pw error message with JOptionPane
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
        //TODO: Modify sizes, and add the remaining components.
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
        jcomboBox.addActionListener(e -> {
            if (e.getSource() == jcomboBox) {
                String userTypeStr = (String) jcomboBox.getSelectedItem();
                //TODO: Client-Server implementation here
            }
        });
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
        goBackButton.addActionListener(e -> {
            loginPage();
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setMaximumSize(new Dimension(200, 50));
        signUpButton.addActionListener(e -> {
            String id = idTxtField.getText();
            String pw = pwTxtField.getText();
            String email = emailTxtField.getText();

            // id, pw, email validity check
            if (id.isEmpty() || pw.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "Error",
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
            }

            //TODO: Client-Server implementation, send id and email to server and check if they already exist!
        });
        buttonPanel.add(Box.createRigidArea(new Dimension(172, 0)));
        buttonPanel.add(goBackButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(signUpButton);

        jPanel.add(buttonPanel);

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
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for product name, store, or description")) {
                    searchBar.setText("");}
                searchBar.setForeground(Color.BLACK);
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
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "Error",
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
        logOutButton.setPreferredSize(new Dimension(100 ,50));
        logOutButton.setMaximumSize(logOutButton.getPreferredSize());
        logOutButton.addActionListener(e -> loginPage());

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
            pDescTxtPane.setText(p.toString()+ '\n' + p.getDescription());
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

    void editAccountPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 1));

        // first
        JLabel firstPanel = new JLabel();
        firstPanel.setLayout(new GridLayout(2, 1));

        String userEmail = "example123@purdue.edu"; //TODO: Get from server
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
        idTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idTxt.getText().equals("Enter new user name")) {
                    idTxt.setText("");}
                idTxt.setForeground(Color.BLACK);
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
                String idErrorMessage = String.format("Username must be %d-%d characters!", USERINFO_MIN_LENGTH, USERINFO_MAX_LENGTH);
                JOptionPane.showMessageDialog(frame, idErrorMessage, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            //TODO: send to server to check if it already exists and change field
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
        pwTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (pwTxt.getText().equals("Enter new password")) {
                    pwTxt.setText("");}
                pwTxt.setForeground(Color.BLACK);
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
                JOptionPane.showMessageDialog(frame, pwErrorMessage, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            //TODO: send to server and change field
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
            // TODO: Get products ArrayList from server
            // customerPage(allProducts);
        });

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setMaximumSize(new Dimension(200, 50));
        deleteAccountButton.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(frame, "Are you sure?");
            // TODO: Depending on response, delete account or don't. Null pointer exception needs to be taken care of
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
                JOptionPane.showMessageDialog(frame, "Please give valid file path!", "Error",
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
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search for store")) {
                    searchBar.setText("");}
                    searchBar.setForeground(Color.BLACK);
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
            String query = searchBar.getText();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in blank field!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            //TODO: Client-Server implementation, send query to server
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            // TODO: Get stores ArrayList from server
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
        logOutButton.setPreferredSize(new Dimension(100 ,50));
        logOutButton.setMaximumSize(logOutButton.getPreferredSize());
        logOutButton.addActionListener(e -> {
            // TODO: send Log out message to server
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

            // User close the GUI; terminal the program; do nothing
            if (storeName == null) {
                return;
            }

            // Store name is empty
            else if (storeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Store name cannot be empty!",
                        "ERROR - Create Store", JOptionPane.ERROR_MESSAGE);
            }

            // Make query and send to server
            else {
                String serverQuery = String.format("CRTSTR_%s", storeName);

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
            else if (storeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Store name cannot be empty!",
                        "ERROR - Delete Store", JOptionPane.ERROR_MESSAGE);
            }

            // Make query and send to server
            else {
                String serverQuery = String.format("DELSTR_%s", storeName);

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

        Store store1 = new Store("Amazon", "Bezos", 0, exampleProducts, new ArrayList<>(),
                new ArrayList<>());
        Store store2 = new Store("Tiki", "Bezos");
        Store store3 = new Store("Lazada", "Bezos");
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
            String query = searchBar.getText();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in blank field!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            //TODO: Client-Server implementation, send query to server
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            storePage(store, products); //TODO: call to server for new store?
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
        addProductButton.addActionListener(e -> {
            // TODO: send query and read from server
            String query = addProductPage(store);
            ArrayList<Product> newProductList = new ArrayList<>();
            storePage(store, products);
        });

        JButton backButton = new JButton("Go Back");
        backButton.setPreferredSize(new Dimension(120 ,50));
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

        JButton importProductButton = new JButton("Import Products");
        importProductButton.setPreferredSize(new Dimension(120, 50));
        importProductButton.setMaximumSize(importProductButton.getPreferredSize());
        importProductButton.addActionListener(e -> {
            // TODO: get client's arraylist and send to server???
            updateFrame();
        });

        JButton exportProductButton = new JButton("Export Products");
        exportProductButton.setPreferredSize(new Dimension(120, 50));
        exportProductButton.setMaximumSize(exportProductButton.getPreferredSize());
        exportProductButton.addActionListener(e -> {
            // TODO: get client's path and ArrayList from server?
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
        modifyButton.setPreferredSize(new Dimension(100 ,50));
        modifyButton.setMaximumSize(modifyButton.getPreferredSize());
        modifyButton.addActionListener(e -> {
            Product selectedProduct = currentProducts.getSelectedValue();
            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(null, "Need to select a product first!",
                        "ERROR - Modify Product", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // TODO: get product's new info and send to server
            String query = modifyProductPage(selectedProduct);
            ArrayList<Product> newProductList = new ArrayList<>();
            storePage(store, products);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100 ,50));
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
                    store.getSellerName(), selectedProduct.getStore(), selectedProduct.getName());
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
     * @param store the store of the new product
     * @return a new query string that contain necessary information to send to server
     */
    static String addProductPage(Store store) {
//        Product newProduct;
//        // TODO: get user input
//        return String.format("ADDPROD_%s_%s_%s_%.2f_%d",
//                newProduct.getName(), newProduct.getStore(), newProduct.getDescription(), newProduct.getPrice(),
//                newProduct.getQuantity());
        return new String();
    }

    /**
     * Page for modify product
     * @param oldProduct the product to be modified
     */
    static String modifyProductPage(Product oldProduct) {
        return new String();
    }

    static void updateFrame() {
        frame.revalidate(); // Notifies layout manager that component has changed.
        frame.repaint(); // repaints the components
    }

     static void resetFrame() {
        frame.getContentPane().removeAll(); // Removes all components from frame.
        updateFrame();
    }
}
