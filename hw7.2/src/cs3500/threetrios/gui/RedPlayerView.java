package cs3500.threetrios.gui;

import java.awt.Color;
import javax.swing.JPanel;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.game.ReadOnlyGameModel;

/**
 * View implementation dedicated to controlling the Red Player.
 */
public class RedPlayerView extends ThreeTriosViewImpl {

  /**
   * Constructs the Red Player view with the game model.
   *
   * @param model the game model
   */
  public RedPlayerView(ReadOnlyGameModel model) {
    super(model);
    setTitle("Three Trios - Red Player");
    getContentPane().setBackground(Color.PINK);
  }

  @Override
  public void refresh() {
    // Only update the red player panel
    updatePlayerPanel(COLOR.RED, getRedPlayerPanel());
    getGridPanel().repaint();
    revalidate();
    repaint();
  }

  /**
   * Overrides to only display updates related to the Red Player.
   *
   * @param message the error message to display
   */
  @Override
  public void displayErrorMessage(String message) {
    // Optional: Customize error messages for Red Player
    super.displayErrorMessage("Red Player: " + message);
  }
}
