package cs3500.simon.view;

import cs3500.simon.model.ColorGuess;
import cs3500.simon.model.ReadOnlySimon;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * Panel for rendering Simon game elements and handling user interaction.
 */
class JSimonPanel extends JPanel {
  private final ReadOnlySimon model;
  private final Stack<ColorGuess> currentRoundOfColorGuesses;
  private boolean mouseIsDown;
  private ColorGuess activeColorGuess;

  private static final Map<ColorGuess, Point2D> CIRCLE_CENTERS = Map.of(
          ColorGuess.Red, new Point2D.Double(10, 0),
          ColorGuess.Yellow, new Point2D.Double(0, 10),
          ColorGuess.Green, new Point2D.Double(-10, 0),
          ColorGuess.Blue, new Point2D.Double(0, -10)
  );
  private static final Map<ColorGuess, Color> CIRCLE_COLORS = Map.of(
          ColorGuess.Red, Color.CYAN,
          ColorGuess.Blue, Color.MAGENTA,
          ColorGuess.Yellow, Color.ORANGE,
          ColorGuess.Green, Color.PINK
  );
  private static final double CIRCLE_RADIUS = 5;

  /**
   * Initializes the panel with a model and sets up mouse interaction.
   *
   * @param model the ReadOnlySimon model representing the game state
   */
  public JSimonPanel(ReadOnlySimon model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    this.currentRoundOfColorGuesses = new Stack<>();
    this.currentRoundOfColorGuesses.addAll(this.model.getCurrentSequence());
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }

  /**
   * Specifies the preferred size for the panel.
   *
   * @return preferred dimension of the panel
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(350, 350);
  }

  /**
   * Advances the game to the next color in the sequence.
   */
  public void advance() {
    this.currentRoundOfColorGuesses.pop();
    if (this.currentRoundOfColorGuesses.isEmpty()) {
      this.currentRoundOfColorGuesses.addAll(this.model.getCurrentSequence());
    }
    this.repaint();
  }

  /**
   * Shows an error message and resets the sequence if the user makes an incorrect guess.
   */
  public void error() {
    JOptionPane.showMessageDialog(this, "Incorrect sequence!", "Error", JOptionPane.ERROR_MESSAGE);
    this.currentRoundOfColorGuesses.clear();
    this.currentRoundOfColorGuesses.addAll(this.model.getCurrentSequence());
    this.repaint();
  }

  /**
   * Renders the game elements on the panel.
   *
   * @param g the Graphics object to render on
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());
    drawCalibrationPattern(g2d);
    for (ColorGuess colorGuess : ColorGuess.values()) {
      boolean isActive = colorGuess == activeColorGuess;
      drawCircle(g2d, CIRCLE_COLORS.get(colorGuess), CIRCLE_CENTERS.get(colorGuess), isActive);
    }
  }

  /**
   * Draws a calibration pattern for the panel.
   *
   * @param g2d the Graphics2D object for drawing
   */
  private void drawCalibrationPattern(Graphics2D g2d) {
    g2d.setColor(Color.RED);
    g2d.drawLine(-20, -20, 20, 20);
    g2d.setColor(Color.BLUE);
    g2d.drawLine(-20, 20, 20, -20);
    drawCircle(g2d, Color.RED, new Point2D.Double(-20, 20), false);
    drawCircle(g2d, Color.YELLOW, new Point2D.Double(20, 20), false);
    drawCircle(g2d, Color.GREEN, new Point2D.Double(-20, -20), false);
    drawCircle(g2d, Color.BLUE, new Point2D.Double(20, -20), false);
  }

  /**
   * Draws a circle at a specified location.
   *
   * @param g2d    the Graphics2D object for drawing
   * @param color  the color of the circle
   * @param center the center point of the circle
   * @param filled true if the circle should be filled, false if outlined
   */
  private void drawCircle(Graphics2D g2d, Color color, Point2D center, boolean filled) {
    AffineTransform oldTransform = g2d.getTransform();
    g2d.translate(center.getX(), center.getY());
    g2d.setColor(color);
    Shape circle = new Ellipse2D.Double(-CIRCLE_RADIUS,
            -CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);
    if (filled) {
      g2d.fill(circle);
    } else {
      g2d.draw(circle);
    }
    g2d.setTransform(oldTransform);
  }

  /**
   * Transforms logical coordinates to physical screen coordinates.
   *
   * @return the AffineTransform for logical to physical transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    ret.scale(1, -1);
    return ret;
  }

  /**
   * Transforms physical screen coordinates to logical coordinates.
   *
   * @return the AffineTransform for physical to logical transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(1, -1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }

  /**
   * Handles mouse interactions for the game.
   */
  private class MouseEventsListener extends MouseInputAdapter {
    /**
     * Handles mouse press events.
     *
     * @param e the MouseEvent triggered by the press
     */
    @Override
    public void mousePressed(MouseEvent e) {
      JSimonPanel.this.mouseIsDown = true;
      this.mouseDragged(e);
    }
  }
}

