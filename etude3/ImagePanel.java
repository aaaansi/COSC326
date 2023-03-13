import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

  private static final double ANGLE = Math.toRadians(60);
  private static final double SCALE_FACTOR = 1.0 / 3.0;

  private int panelSize;
  private int levels;

  public ImagePanel(int panelSize) {
    this.panelSize = panelSize;
    setPreferredSize(new Dimension(panelSize, panelSize));

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLUE);

    // Point p1 = new Point(centerX, centerY - side / 2);
    // Point p2 = new Point(centerX + side / 2, centerY + side / 2);
    // Point p3 = new Point(centerX - side / 2, centerY + side / 2);

    drawKochSnowflake(g, levels, 20, 280, 280, 280);
    drawKochSnowflake(g, levels, 280, 280, 150, 20);
    drawKochSnowflake(g, levels, 150, 20, 20, 280);
  }

  private void drawKochSnowflake(Graphics g, int lev, int x1, int y1, int x5, int y5) {
    int deltaX, deltaY, x2, y2, x3, y3, x4, y4;

    if (lev == 0) {

      g.drawLine(x1, y1, x5, y5);
    } else {
      deltaX = x5 - x1;
      deltaY = y5 - y1;

      x2 = x1 + deltaX / 3;
      y2 = y1 + deltaY / 3;

      x3 = (int) (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
      y3 = (int) (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);

      x4 = x1 + 2 * deltaX / 3;
      y4 = y1 + 2 * deltaY / 3;

      drawKochSnowflake(g, lev - 1, x1, y1, x2, y2);
      drawKochSnowflake(g, lev - 1, x2, y2, x3, y3);
      drawKochSnowflake(g, lev - 1, x3, y3, x4, y4);
      drawKochSnowflake(g, lev - 1, x4, y4, x5, y5);
    }
  }

  public void setLevels(int level) {
    levels = Integer.parseInt(JOptionPane.showInputDialog("Enter the level of the Koch Snowflake"));
  }

  // private Point getThirdPoint(Point p1, Point p2) {
  // int dx = p2.x - p1.x;
  // int dy = p2.y - p1.y;
  // int x = p1.x + (int) (dx * 0.5 + dy * 0.5 * Math.sqrt(3));
  // int y = p1.y + (int) (dy * 0.5 - dx * 0.5 * Math.sqrt(3));
  // return new Point(x, y);
  // }

  // private Point getMidpoint(Point p1, Point p2) {
  // int x = (int) ((1 - SCALE_FACTOR) * p1.x + SCALE_FACTOR * p2.x);
  // int y = (int) ((1 - SCALE_FACTOR) * p1.y + SCALE_FACTOR * p2.y);
  // return new Point(x, y);
  // }
}
