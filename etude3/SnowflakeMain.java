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

// double centerX = getWidth() / 2;
// double centerY = getHeight() / 2;

// double side = Math.min(getWidth(), getHeight());

// double x1 = centerX - side / 2;
// double y1 = centerY + side / 3 * Math.sqrt(3);
// double x5 = centerX + side / 2;
// double y5 = y1;

// System.out.println(x1 + "; " + y1 + "; " + x5 + "; " + y5);

// drawKochSnowflake(g, levels, x1, y1, x5, y1);
// drawKochSnowflake(g, levels, x1, y1, x5, y1);
// drawKochSnowflake(g, levels, x1, y1, x5, y1);

// drawKochSnowflake(g, levels, 20 * 2, 280 * 2, 280 * 2, 280 * 2);
// drawKochSnowflake(g, levels, 280 * 2, 280 * 2, 150 * 2, 20 * 2);
// drawKochSnowflake(g, levels, 150 * 2, 20 * 2, 20 * 2, 280 * 2);