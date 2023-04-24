import javax.swing.*;
import java.awt.*;

/**
 *  Class for default values set for our Jframe.
 */
public class DefaultFrame extends JFrame {
    public DefaultFrame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
