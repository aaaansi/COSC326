import javax.swing.JFrame;

public class SnowflakeMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel panel = new ImagePanel(600);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
