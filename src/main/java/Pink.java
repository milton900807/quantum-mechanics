
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Pink extends JPanel {
    Floyd machine;
    EnergyPanel epanel;

    public Pink () {
        init();
    }
    public void init() {
        EM espectrum = new EM();
        this.machine = new Floyd(espectrum);
        this.epanel = new EnergyPanel(this.machine, this);
        espectrum.setPreferredSize( new Dimension (900, 100));
        this.machine.setPreferredSize(new Dimension(1000, 1000));
        setLayout ( new BorderLayout( ));
        this.add( "South", this.epanel);
        this.add("North",espectrum);
        this.add("Center", this.machine);
//        this.add( "North", espectrum );
        this.epanel.calculate();//        progBar.dispose();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("FrameDemo");

        Pink p = new Pink();
        p.setBackground(Color.green);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(p);
        frame.setBounds(0, 0, 1000, 1000);
        frame.setVisible(true);
    }


    public static Image loadImage(String imagename) {
        Image image = null;
        try {
            image = ImageIO.read(new Object().getClass().getClassLoader().getResource(imagename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
//
//    public void floatApp() {
//        this.floatFrame = new Frame("Why Things Have Colour...");
//        this.prevD = this.size();
//        this.floatFrame.resize(this.prevD);
//        GridBagLayout gridbag = new GridBagLayout();
//        GridBagConstraints c = new GridBagConstraints();
//        this.floatFrame.setLayout(gridbag);
//        c.fill = 1;
//        c.anchor = 10;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weighty = 0.95D;
//        c.weightx = 1.0D;
//        gridbag.setConstraints(this.machine, c);
//        this.floatFrame.add(this.machine);
//        c.fill = 2;
//        c.anchor = 15;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.weighty = 0.05D;
//        gridbag.setConstraints(this.epanel, c);
//        this.floatFrame.add(this.epanel);
//        this.floatFrame.show();
//    }
//
//    public void unfloatApp() {
//        this.floatFrame.removeAll();
//        this.add(this.machine);
//        this.add(this.epanel);
//        this.validate();
//        this.floatFrame.dispose();
//    }
//
//    public void paintComponent(Graphics g) {
//        Dimension d = this.size();
//        g.setColor(Color.black);
//        g.fillRect(0, 0, d.width, d.height);
//    }

}

