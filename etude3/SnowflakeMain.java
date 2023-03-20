import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

        JPanel controlPanel = new JPanel();
        controlPanel.add(spinner);

        frame.add(panel);
        frame.add(controlPanel, "North");

        frame.pack();
        frame.setVisible(true);
    }
}
