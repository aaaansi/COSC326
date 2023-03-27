import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.*;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 * https://medium.com/@mojca.rojko/drawing-a-koch-snowflake-in-java-3268cbed94c8
 * Algorithm for drawing the snowflake from ^^
 * 
 * 
 * https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
 * for zooming in && out
 */

public class ImagePanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

  private int levels = 1;

  private double zoomFactor = 1;
  private double prevZoomFactor = 1;
  private boolean zoomer;
  private boolean dragger;
  private boolean released;
  private double xOffset = 0;
  private double yOffset = 0;
  private int xDiff;
  private int yDiff;
  private Point startPoint;

  public ImagePanel() {
    setPreferredSize(new Dimension(345, 429));
    addMouseWheelListener(this);
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    Graphics2D g2d = (Graphics2D) g;

    double panelWidth = getWidth();
    double panelHeight = getHeight();
    if (zoomer) {
      AffineTransform at = new AffineTransform();

      double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
      double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

      double zoomDiv = zoomFactor / prevZoomFactor;

      xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
      yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

      at.translate(xOffset, yOffset);
      at.scale(zoomFactor, zoomFactor);
      prevZoomFactor = zoomFactor;
      g2d.transform(at);
      zoomer = false;
    }

    if (dragger) {
      AffineTransform at = new AffineTransform();
      at.translate(xOffset + xDiff, yOffset + yDiff);
      at.scale(zoomFactor, zoomFactor);
      g2d.transform(at);

      if (released) {
        xOffset += xDiff;
        yOffset += yDiff;
        dragger = false;
      }

    }
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_PURE);

    drawKochSnowflake(g2d, levels, panelWidth / 2 - ((panelWidth / 2) - 50), panelHeight / 2,
        panelWidth / 2 + ((panelWidth / 2) - 50), panelHeight / 2);
    drawKochSnowflake(g2d, levels, panelWidth / 2 + ((panelWidth / 2) - 50),
        panelHeight / 2, panelWidth / 2, panelHeight / panelWidth);
    drawKochSnowflake(g2d, levels, panelWidth / 2, panelHeight / panelWidth,
        panelWidth / 2 - ((panelWidth / 2) - 50), panelHeight / 2);

  }

  public static double distance(double x1, double y1, double x2, double y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }

  private void drawKochSnowflake(Graphics2D g, int lev, double x1, double y1, double x5, double y5) {
    double deltaX, deltaY, x2, y2, x3, y3, x4, y4;
    // Graphics2D g2d = (Graphics2D) g;
    if (lev == 1) {
      for (int i = 0; i < 3; i++) {
        g.draw(new Line2D.Double(x1, y1, x5, y5));
      }
      // g.drawLine((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x5),
      // (int) Math.round(y5));
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

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {

    zoomer = true;

    // Zoom in
    if (e.getWheelRotation() < 0) {
      zoomFactor *= 1.1;
      repaint();
    }
    // Zoom out
    if (e.getWheelRotation() > 0) {
      zoomFactor /= 1.1;
      repaint();
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    Point curPoint = e.getLocationOnScreen();
    xDiff = curPoint.x - startPoint.x;
    yDiff = curPoint.y - startPoint.y;

    dragger = true;
    repaint();

  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    released = false;
    startPoint = MouseInfo.getPointerInfo().getLocation();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    released = true;
    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
