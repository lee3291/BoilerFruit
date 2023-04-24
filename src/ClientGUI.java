import javax.swing.*;
import java.awt.*;

public class ClientGUI implements Runnable {

    JFrame frame;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    @Override
    public void run() {
        createGUI();
        // welcomePage();
        loginPage("Customer");

    }

    void createGUI() {
        frame = new JFrame();
        frame.setTitle("Boiler Fruit Market");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void welcomePage() {
        // TODO: Add resetFrame method.
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 0, 10, 10));

        JLabel welcome = new JLabel("Welcome to Boiler Fruits");
        welcome.setPreferredSize(new Dimension(100, 100));
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setVerticalAlignment(JLabel.CENTER);
        welcome.setFont(new Font("", Font.PLAIN, 20));

        JLabel question = new JLabel("Are you a CUSTOMER or a SELLER?");
        question.setPreferredSize(new Dimension(100, 100));
        question.setHorizontalAlignment(JLabel.CENTER);
        question.setVerticalAlignment(JLabel.NORTH);
        question.setFont(new Font("", Font.PLAIN, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 2, 10,20));
        buttonPanel.setPreferredSize(new Dimension(100, 100));
        JButton customerButton = new JButton("CUSTOMER");
        customerButton.setSize(50, 50);
        JButton sellerButton = new JButton("SELLER");
        sellerButton.setSize(50,50);
        buttonPanel.add(customerButton);
        buttonPanel.add(sellerButton);

        jPanel.add(welcome);
        jPanel.add(question);
        jPanel.add(buttonPanel);

        frame.add(jPanel);

    }

    void loginPage(String userType) {
        //TODO: Add action listeners
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 0, 10, 10));


        // First Row
        String message = String.format("Log-In or Sign-up as a %s", userType);
        JLabel firstLabel = new JLabel(message);
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

        JButton goBackButton = new JButton("Go Back");
        JButton logInButton = new JButton("Log-In");

        JPanel txtFieldPanel = new JPanel();
        txtFieldPanel.setLayout(new GridLayout(3, 1, 10, 10));
        txtFieldPanel.add(idLabel);
        txtFieldPanel.add(idTxtField);
        txtFieldPanel.add(pwLabel);
        txtFieldPanel.add(pwTxtField);
        txtFieldPanel.add(goBackButton);
        txtFieldPanel.add(logInButton);
        jPanel.add(txtFieldPanel);

        // Third Row TODO: Change Layout Manager
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new BorderLayout());
        JButton signUpButton = new JButton("Sign Up");
        botPanel.add(signUpButton, BorderLayout.CENTER);
        jPanel.add(botPanel);

        frame.add(jPanel);

    }
}
