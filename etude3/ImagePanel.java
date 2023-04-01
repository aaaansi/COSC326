import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.*;
import javax.swing.JPanel;

/**
 * ImagePanel class is a class that deals with drawing the Koch Snowflake
 * 
 * References:
 * Algorithm for drawing the snowflake from:
 * https://medium.com/@mojca.rojko/drawing-a-koch-snowflake-in-java-3268cbed94c8
 *
 * Functions for Zoom in/out from:
 * https://stackoverflow.com/questions/6543453/zooming-in-and-zooming-out-within-a-panel
 * 
 * @author Hamzah Alansi
 */

public class ImagePanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

  private int levels = 1;

  // datafields for Zoom functions
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

  /**
   * ImagePanel constructor that sets the size of the panel with
   * specific dimensions and adds MouseListeners
   */
  public ImagePanel() {
    setPreferredSize(new Dimension(345, 429));
    addMouseWheelListener(this);
    addMouseMotionListener(this);
    addMouseListener(this);
  }

  /**
   * PaintComponent method that takes a Graphics parameter g
   * and sets the dimensions of the triangle then calls the drawKochSnowflake
   * method to draw.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    Graphics2D g2d = (Graphics2D) g;

    double panelWidth = getWidth();
    double panelHeight = getHeight();

    // If user zoomes
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

    // if the user drags
    if (dragger) {
      AffineTransform at = new AffineTransform();
      at.translate(xOffset + xDiff, yOffset + yDiff);
      at.scale(zoomFactor, zoomFactor);
      g2d.transform(at);

      // if the user releases the mouse
      if (released) {
        xOffset += xDiff;
        yOffset += yDiff;
        dragger = false;
      }

    }
    // Antialiasing to smooth out the pixels of the triangle
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_PURE);

    /*
     * Method calls to drawKochSnowflake to draw the lines of the triangle. each
     * method call draws one line.
     */
    drawKochSnowflake(g2d, levels, panelWidth / 2 - ((panelWidth / 2) - 50), panelHeight / 2,
        panelWidth / 2 + ((panelWidth / 2) - 50), panelHeight / 2);
    drawKochSnowflake(g2d, levels, panelWidth / 2 + ((panelWidth / 2) - 50),
        panelHeight / 2, panelWidth / 2, panelHeight / panelWidth);
    drawKochSnowflake(g2d, levels, panelWidth / 2, panelHeight / panelWidth,
        panelWidth / 2 - ((panelWidth / 2) - 50), panelHeight / 2);

  }

  /*
   * drawKochSnowflake method is a method that takes 6 parameters the
   * paintComponents graphic, the level, and the line coordinates of the drawing
   * (x1,y1), (xy, y5).
   * This method recursively calls itself if the level order is greater than 1 to
   * redraw itself to form a snowflake.
   */

  private void drawKochSnowflake(Graphics2D g, int lev, double x1, double y1, double x5, double y5) {
    double deltaX, deltaY, x2, y2, x3, y3, x4, y4;
    if (lev == 1) {
      for (int i = 0; i < 3; i++) {
        g.draw(new Line2D.Double(x1, y1, x5, y5));
      }

    } else {
      deltaX = x5 - x1;
      deltaY = y5 - y1;

      x2 = x1 + deltaX / 3;
      y2 = y1 + deltaY / 3;

      x3 = (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
      y3 = (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);

      x4 = x1 + 2 * deltaX / 3;
      y4 = y1 + 2 * deltaY / 3;

      // recursive call with new calculated coordinates
      drawKochSnowflake(g, lev - 1, x1, y1, x2, y2);
      drawKochSnowflake(g, lev - 1, x2, y2, x3, y3);
      drawKochSnowflake(g, lev - 1, x3, y3, x4, y4);
      drawKochSnowflake(g, lev - 1, x4, y4, x5, y5);
    }
  }

  /*
   * setLevels method takes a single parameter, level and repaints
   * the Snowflake by updating the order of the snowflake.
   */
  public void setLevels(int level) {
    this.levels = level;
    repaint();
  }

  /*
   * Method that checks if the mousewheel is rotating. if it is then repaint the
   * image with the zoom factor
   */
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

  /*
   * Method that Checks if the mouse is dragged to move the zoomed image on the
   * frame.
   */
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

  /**
   * mousePressed method that takes a MouseEvent parameter
   * and checks if the mouse is pressed set released to false then updates the
   * startPoint value.
   */

  @Override
  public void mousePressed(MouseEvent e) {
    released = false;
    startPoint = MouseInfo.getPointerInfo().getLocation();
  }

  /**
   * mouseReleased method that takes a MouseEvent parameter
   * and checks if the mouse is released (True) and repaints it.
   */
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
