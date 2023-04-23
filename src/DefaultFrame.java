import javax.swing.*;

/**
 *  Class for default values set for our Jframes.
 */
public class DefaultFrame extends JFrame {
    public DefaultFrame(String title, String size) {

        // Sizes will be promised, default or small.
        // Default will be the basic frame size, small will be for error messages and other.

        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);
        this.setVisible(true);
    }
}
