import javax.swing.*;
import java.awt.*;

public class ClientGUI implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    public void run() {
        DefaultFrame dFrame = new DefaultFrame("Boiler Fruit Market");
        dFrame.add(welcomePagePanel());
        // dFrame.pack();
        dFrame.setVisible(true);
    }

    public static JPanel welcomePagePanel() {
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

        return jPanel;
    }
}
