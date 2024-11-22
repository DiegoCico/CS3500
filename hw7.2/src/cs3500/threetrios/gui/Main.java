package cs3500.threetrios.gui;

import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.SwingUtilities;

import cs3500.threetrios.ai.Flip;
import cs3500.threetrios.ai.GoForCorner;
import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.ai.LeastFlippableStrategy;
import cs3500.threetrios.ai.MinMaxStrategy;
import cs3500.threetrios.ai.PosnStrategy;
import cs3500.threetrios.game.GameModel;

/**
 * The main entry point for the Three Trios game, setting up the model, view,
 * and controller, and starting the game.
 */
public class Main {

  /**
   * plays the game.
   * @param args not used.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        GameModel model = new GameModel(
                "/Users/diegocicotoste/Documents/School/CS3500/hw7.2/docs/boardNoHoles.config");

        ThreeTriosGameView redView = new RedPlayerView(model);
        ThreeTriosGameView blueView = new BluePlayerView(model);

        new ThreeTriosControllerImpl(model, redView);
        new ThreeTriosControllerImpl(model, blueView);
      } catch (FileNotFoundException e) {
        System.err.println("Configuration file not found: " + e.getMessage());
      }
    });

  }
}

