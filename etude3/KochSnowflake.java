
//import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class KochSnowflake {

    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("Koch Snowflake");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolbar = new JToolBar("test");
        toolbar.setFloatable(false);

        frame.add(toolbar, BorderLayout.NORTH);

        // The main content area is an ImagePanel
        // ImagePanel imagePanel = new ImagePanel();
        // ImageAction.setTarget(imagePanel);
        // JScrollPane scrollPane = new JScrollPane(imagePanel);
        // frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) throws Exception {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    createAndShowGUI();
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        });
    }
}