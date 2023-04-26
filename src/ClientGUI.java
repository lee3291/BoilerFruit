import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Flow;

public class ClientGUI implements Runnable {

    static JFrame frame;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    @Override
    public void run() {
        createGUI();
        // loginPage();
        // signUpPage();
//        customerPage();
        sellerPage();
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
        jPanel.setLayout(new GridLayout(3, 0, 10, 10));


        // First Row
        JLabel firstLabel = new JLabel("Welcome to Boiler Fruits");
        firstLabel.setPreferredSize(new Dimension(100, 100));
        firstLabel.setHorizontalAlignment(JLabel.CENTER);
        firstLabel.setVerticalAlignment(JLabel.CENTER);
        firstLabel.setFont(new Font(null, Font.PLAIN, 20));
        jPanel.add(firstLabel);

        // Second Row
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

        JButton logInButton = new JButton("Log-In");

        logInButton.addActionListener(e -> {
            String id = idTxtField.getText();
            String pw = pwTxtField.getText();

            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in blank field!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // TODO: Sign in info is valid, send user to customer page or seller page
            // TODO: Client-Server implementation comes here. Wrong ID & Pw error message with JOptionPane
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            signUpPage();
        });

        JPanel txtFieldPanel = new JPanel();
        txtFieldPanel.setLayout(new GridLayout(3, 1, 10, 10));
        txtFieldPanel.add(idLabel);
        txtFieldPanel.add(idTxtField);
        txtFieldPanel.add(pwLabel);
        txtFieldPanel.add(pwTxtField);
        txtFieldPanel.add(logInButton);
        txtFieldPanel.add(signUpButton);
        jPanel.add(txtFieldPanel);

        frame.add(jPanel);
        updateFrame();
    }

    void signUpPage() {
        //TODO: Modify sizes, and add the remaining components.
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 1, 10, 10));

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

        // mid-panel, for ID and PW labels and text fields.
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(2, 2, 10, 10));
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

        midPanel.add(idLabel);
        midPanel.add(idTxtField);
        midPanel.add(pwLabel);
        midPanel.add(pwTxtField);

        jPanel.add(midPanel);


        // bot panel, email label and text field, and go back and sign up buttons.
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setVerticalAlignment(JLabel.CENTER);
        emailLabel.setFont(new Font(null, Font.PLAIN, 20));
        JTextField emailTxtField = new JTextField(10);

        botPanel.add(emailLabel);
        botPanel.add(emailTxtField);

        // Go back and sign up button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setPreferredSize(new Dimension(100, 100));
        goBackButton.setMaximumSize(goBackButton.getPreferredSize());
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goBackButton.addActionListener(e -> {
            loginPage();
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(100, 100));
        signUpButton.setMaximumSize(signUpButton.getPreferredSize());
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(e -> {
            String id = idTxtField.getText();
            String pw = pwTxtField.getText();
            String email = emailTxtField.getText();

            if (id.isEmpty() || pw.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in blank field!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            System.out.println(id+pw+email);

            //TODO: Client-Server implementation
        });

        botPanel.add(goBackButton);
        botPanel.add(signUpButton);
        jPanel.add(botPanel);

        frame.add(jPanel);
        updateFrame();
    }

    void customerPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // North, search bar, search button, refresh button, to add two components, make an inner panel with boxlayout.
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("Search for product name, store, or description", 10);
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

        // Center, combo-box in box layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Get products arraylist from server, loop and make a String[] of productInfo.
        // Below is an example. TODO: client-server implementation required
        ArrayList<Product> products = new ArrayList<>(); // get from server

        // Example products
        Product product1 = new Product("Apple", "Store", "Some Apples", 2.00, 3);
        Product product2 = new Product("Banana", "Store", "Some Bananas", 3.00, 6);
        Product product3 = new Product("Oranges", "Store", "Some Oranges", 4.00, 12);
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
        JLabel pDescLabel = new JLabel(); // product description label

        ListSelectionModel productList = productListing.getSelectionModel();
        productList.setValueIsAdjusting(false);
        productList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Product p = productListing.getSelectedValue();
                pDescLabel.setText(p.getDescription());
            }
        });

        JButton buyItemButton = new JButton("Purchase Item");
        buyItemButton.setPreferredSize(new Dimension(200, 50));
        buyItemButton.setMaximumSize(buyItemButton.getPreferredSize());
        buyItemButton.addActionListener(e -> {
            // TODO: client-server implementation, error message if nothing is selected(pDescLabel.getText().isEmpty),
            //  confirm purchase with JOptionPain message. Concurrency for purchasing item.
        });
        JButton contactSellerButton = new JButton("Contact Seller");
        contactSellerButton.setPreferredSize(new Dimension(200, 50));
        contactSellerButton.setMaximumSize(contactSellerButton.getPreferredSize());
        contactSellerButton.addActionListener(e -> {
            // TODO: JOptionPane message: "Seller has been notified!" Get seller email from server and display.
            //  Send customer email to seller
        });

        productPagePanel.add(pDescLabel, BorderLayout.NORTH);

        JPanel centerRightBottomPanel = new JPanel();
        centerRightBottomPanel.setLayout(new BoxLayout(centerRightBottomPanel, BoxLayout.X_AXIS));
        centerRightBottomPanel.add(buyItemButton);
        centerRightBottomPanel.add(Box.createRigidArea(new Dimension(18, 0)));
        centerRightBottomPanel.add(contactSellerButton);
        productPagePanel.add(centerRightBottomPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(new JScrollPane(productPagePanel));

        jPanel.add(splitPane, BorderLayout.CENTER);


        frame.add(jPanel);
        updateFrame();
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
        idTxt.setMaximumSize(new Dimension(200, 50));
        JButton idChangeButton = new JButton("Change ID");

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
        pwTxt.setMaximumSize(new Dimension(200, 50));
        JButton pwChangeButton = new JButton("Change PW");

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
        goBackButton.addActionListener(e -> customerPage());

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
        updateFrame();
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
        goBackButton.addActionListener(e -> customerPage());

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

    void sellerPage() {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // North, search bar, search button, refresh button, to add two components, make an inner panel with boxlayout.
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("Search for store", 10);
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
            //TODO: Refresh listing, when new stores are added to server from a seller,
            // this seller should be able to view it after clicking this button.
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

        // South, create & delete store buttons. use flow layout
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        JButton createStoreButton = new JButton("Create Store");
        createStoreButton.setPreferredSize(new Dimension(100, 50));
        createStoreButton.setMaximumSize(createStoreButton.getPreferredSize());
        createStoreButton.addActionListener(e -> {
            // TODO: call to server to create new store
            updateFrame();
        });

        JButton deleteStoreButton = new JButton("Delete Store");
        deleteStoreButton.setPreferredSize(new Dimension(100, 50));
        deleteStoreButton.setMaximumSize(deleteStoreButton.getPreferredSize());
        deleteStoreButton.addActionListener(e -> {
            // TODO: call to server to delete store
            updateFrame();
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

        ArrayList<Store> stores = exampleStores; // TODO: get from server

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
                storePage(store);
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(new JScrollPane(userStores));

        jPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(jPanel);
        updateFrame();
    }

    void storePage(Store store) {
        resetFrame();

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        // North, search bar, search button, refresh button, to add two components, make an inner panel with boxlayout.
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("Search for product name or description", 10);
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

        // West, usertype label, view sale button, export button, back button
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JLabel userType = new JLabel("         " + store.getStoreName()); // Space for alignment with buttons

        JButton viewSaleButton = new JButton("View sale");
        viewSaleButton.setPreferredSize(new Dimension(120, 50));
        viewSaleButton.setMaximumSize(viewSaleButton.getPreferredSize());
//        viewSaleButton.addActionListener(e -> storeViewSale());

        JButton exportProductButton = new JButton("Export Products");
        exportProductButton.setPreferredSize(new Dimension(120, 50));
        exportProductButton.setMaximumSize(exportProductButton.getPreferredSize());
//        exportProductButton.addActionListener(e -> storeExportProduct());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120 ,50));
        backButton.setMaximumSize(backButton.getPreferredSize());
        backButton.addActionListener(e -> sellerPage());

        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(userType);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(viewSaleButton);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(exportProductButton);
        westPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        westPanel.add(backButton);

        jPanel.add(westPanel, BorderLayout.WEST);

        // South, review Purchase History button. use box layout for size
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        JButton importProductButton = new JButton("Import Product");
        importProductButton.setPreferredSize(new Dimension(120, 50));
        importProductButton.setMaximumSize(importProductButton.getPreferredSize());
        importProductButton.addActionListener(e -> {
            // TODO: get client's arraylist and send to server???
            updateFrame();
        });

        JButton addProductButton = new JButton("Add Product");
        addProductButton.setPreferredSize(new Dimension(120, 50));
        addProductButton.setMaximumSize(addProductButton.getPreferredSize());
        addProductButton.addActionListener(e -> {
            // TODO: get client's product detail and send to server
            updateFrame();
        });

        southPanel.add(importProductButton);
        southPanel.add(addProductButton);
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
            // TODO: get product's new info and send to server
            updateFrame();
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100 ,50));
        deleteButton.setMaximumSize(deleteButton.getPreferredSize());
        deleteButton.addActionListener(e -> {
            // TODO: delete the selected product from server
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

    static void updateFrame() {
        frame.revalidate(); // Notifies layout manager that component has changed.
        frame.repaint(); // repaints the components
    }

     static void resetFrame() {
        frame.getContentPane().removeAll(); // Removes all components from frame.
        updateFrame();
    }
}
