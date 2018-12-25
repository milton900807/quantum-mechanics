import javax.swing.*;
import java.awt.*;

public class App extends JPanel {
    public void init() {
        this.setLayout((LayoutManager) null);
        this.addNotify();
        this.resize(426, 266);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("FrameDemo");
        App app = new App ();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(app, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
