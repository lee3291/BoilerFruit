import javax.swing.*;
import java.awt.*;

public class ClientGUI implements Runnable {

    static JFrame frame;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    @Override
    public void run() {
        createGUI();
        loginPage();

    }

    void createGUI() {
        frame = new JFrame();
        frame.setTitle("Boiler Fruit Market");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    void loginPage() {
        //TODO: Add action listeners
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
        JButton signUpButton = new JButton("Sign Up");

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

    }

     static void resetFrame() {
        frame.getContentPane().removeAll(); // Removes all components from frame.
        frame.revalidate(); // Notifies layout manager that component has changed.
        frame.repaint(); // repaints the components
    }
}
