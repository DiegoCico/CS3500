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
                "/Users/diegocicotoste/Documents/School/CS3500/hw6/docs/boardNoHoles.config");

        ThreeTriosViewImpl view = new ThreeTriosViewImpl(model);

        PosnStrategy strategy1 = new Flip();
        PosnStrategy strategy2 = new GoForCorner();
        PosnStrategy strategy3 = new LeastFlippableStrategy();
        PosnStrategy strategy4 = new MinMaxStrategy();

        List<PosnStrategy> aI = List.of(strategy1, strategy2, strategy3, strategy4);
        HybridStrategy aIController = new HybridStrategy(aI);
        new ThreeTriosControllerImpl(model, view);
      } catch (FileNotFoundException e) {
        System.err.println("Configuration file not found: " + e.getMessage());
      }
    });
  }
}

