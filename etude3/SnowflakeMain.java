import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * SnowflakeMain class is a main class that is used to run ImagePanel from.
 * It creates a Jframe and adds the ImagePanel class to it as well as the
 * spinner for changing levels.
 * 
 * @author Hamzah Alansi
 */
public class SnowflakeMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel panel = new ImagePanel();

        // Create the spinner
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int level = (int) spinner.getValue();
                panel.setLevels(level);
                panel.repaint();
            }
        });
        // Panel for spinner
        JPanel controlPanel = new JPanel();
        controlPanel.add(spinner);

        // Adds ImagePanel and Spinner panel to the frame.
        frame.add(panel);
        frame.add(controlPanel, "North");

        frame.pack();
        frame.setVisible(true);
    }
}