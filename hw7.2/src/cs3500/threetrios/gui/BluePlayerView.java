package cs3500.threetrios.gui;

import java.awt.Color;
import javax.swing.JPanel;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.game.ReadOnlyGameModel;

/**
 * View implementation dedicated to controlling the Blue Player.
 */
public class BluePlayerView extends ThreeTriosViewImpl {

  /**
   * Constructs the Blue Player view with the game model.
   *
   * @param model the game model
   */
  public BluePlayerView(ReadOnlyGameModel model) {
    super(model);
    setTitle("Three Trios - Blue Player");
    getContentPane().setBackground(Color.CYAN);
  }

  @Override
  public void refresh() {
    // Only update the blue player panel
    updatePlayerPanel(COLOR.BLUE, getBluePlayerPanel());
    getGridPanel().repaint();
    revalidate();
    repaint();
  }

  /**
   * Overrides to only display updates related to the Blue Player.
   *
   * @param message the error message to display
   */
  @Override
  public void displayErrorMessage(String message) {
    // Optional: Customize error messages for Blue Player
    super.displayErrorMessage("Blue Player: " + message);
  }
}
