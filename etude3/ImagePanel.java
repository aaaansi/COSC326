import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
// import java.awt.Point;
// import java.awt.Polygon;
// import java.awt.event.MouseWheelEvent;
// import java.awt.event.MouseWheelListener;

// import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * https://medium.com/@mojca.rojko/drawing-a-koch-snowflake-in-java-3268cbed94c8
 * Algorithm for drawing the snowflake from ^^
 */

public class ImagePanel extends JPanel {

  // private static final double ANGLE = Math.toRadians(60);
  // private static final double SCALE_FACTOR = 1.0 / 3.0;

  // private int panelSize;
  private int levels = 1;
  // private double zoomFactor = 1.0;

  public ImagePanel() {
    // this.panelSize = panelSize;
    setPreferredSize(new Dimension(500, 500));
    // levels = Integer.parseInt(JOptionPane.showInputDialog("Enter the level of the
    // Koch Snowflake"));
    // addMouseWheelListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);

    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int ratio = panelHeight / panelWidth;
    int centerX = panelWidth / 2;
    int centerY = panelHeight / 2;
    int sideLength = Math.min(panelWidth, panelHeight) - 40;
    int startX = centerX - sideLength / 2;
    int startY = centerY - sideLength / 2;

    // drawKochSnowflake(g, levels, startX, startY, startX + sideLength, startY);
    // drawKochSnowflake(g, levels, startX + sideLength, startY, centerX, startY +
    // sideLength);
    // drawKochSnowflake(g, levels, centerX, startY + sideLength, startX, startY);
    System.out.println(startX + "; " + startY + "; " + centerX + "; " + centerY + "; " + sideLength);
    drawKochSnowflake(g, levels, startY, centerY, centerY, centerY);
    drawKochSnowflake(g, levels, centerY, centerY, startX, startY);
    drawKochSnowflake(g, levels, startX, startY, startY, centerY);

  }

  private void drawKochSnowflake(Graphics g, int lev, double x1, double y1, double x5, double y5) {
    double deltaX, deltaY, x2, y2, x3, y3, x4, y4;

    if (lev == 1) {
      g.drawLine((int) x1, (int) y1, (int) x5, (int) y5);
    } else {
      deltaX = x5 - x1;
      deltaY = y5 - y1;

      x2 = x1 + deltaX / 3;
      y2 = y1 + deltaY / 3;

      x3 = (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
      y3 = (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);

      x4 = x1 + 2 * deltaX / 3;
      y4 = y1 + 2 * deltaY / 3;

      drawKochSnowflake(g, lev - 1, x1, y1, x2, y2);
      drawKochSnowflake(g, lev - 1, x2, y2, x3, y3);
      drawKochSnowflake(g, lev - 1, x3, y3, x4, y4);
      drawKochSnowflake(g, lev - 1, x4, y4, x5, y5);
    }
  }

  public void setLevels(int level) {
    this.levels = level;
    repaint();
  }
}
