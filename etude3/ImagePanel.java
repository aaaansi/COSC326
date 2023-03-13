import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements MouseWheelListener {

  private static final double ANGLE = Math.toRadians(60);
  private static final double SCALE_FACTOR = 1.0 / 3.0;

  private int panelSize;
  private int levels;
  private double zoomFactor = 1.0;

  public ImagePanel(int panelSize) {
    this.panelSize = panelSize;
    setPreferredSize(new Dimension(panelSize, panelSize));
    // levels = Integer.parseInt(JOptionPane.showInputDialog("Enter the level of the
    // Koch Snowflake"));
    addMouseWheelListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLUE);

    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    int side = (int) (panelSize / zoomFactor);

    Point p1 = new Point(centerX, centerY - side / 2);
    Point p2 = new Point(centerX + side / 2, centerY + side / 2);
    Point p3 = new Point(centerX - side / 2, centerY + side / 2);

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
    this.levels = level;
    repaint();
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    int notches = e.getWheelRotation();
    if (notches < 0) {
      zoomFactor *= 1.1;
    } else {
      zoomFactor /= 1.1;
    }
    repaint();
  }
}
