package cs3500.simon.view;

import cs3500.simon.model.ReadOnlySimon;
import javax.swing.JFrame;

/**
 * A simple implementation of the SimonView, displaying the Simon game in a window.
 */
public class SimpleSimonView extends JFrame implements SimonView {
  private final JSimonPanel panel;

  /**
   * Constructs a view with a model and initializes the display.
   * @param model the ReadOnlySimon model representing the game state
   */
  public SimpleSimonView(ReadOnlySimon model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new JSimonPanel(model);
    this.add(panel);
    this.pack();
  }

  /**
   * Adds a listener for view features.
   * @param features the listener to add
   */
  @Override
  public void addFeatureListener(ViewFeatures features) {
    this.panel.addFeaturesListener(features);
  }

  /**
   * Displays or hides the view based on the specified flag.
   * @param show true to display, false to hide
   */
  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  /**
   * Advances the game to the next state in the sequence.
   */
  @Override
  public void advance() {
    this.panel.advance();
  }

  /**
   * Shows an error in the game display for an incorrect sequence.
   */
  @Override
  public void error() {
    this.panel.error();
  }
}
