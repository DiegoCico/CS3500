package cs3500.threetrios.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import cs3500.threetrios.ai.Flip;
import cs3500.threetrios.ai.GoForCorner;
import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.ai.MinMaxStrategy;
import cs3500.threetrios.ai.PosnStrategy;
import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.controller.AIController;
import cs3500.threetrios.controller.ThreeTriosControllerImpl;
import cs3500.threetrios.controller.ThreeTriosGameView;
import cs3500.threetrios.game.Game;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.game.ReadOnlyGameModel;

/**
 * The main entry point for the Three Trios game, setting up the model, view,
 * and controller, and starting the game.
 */
public class Main {

  public static void main(String[] args) throws IllegalArgumentException {
    SwingUtilities.invokeLater(() -> {
      try {
        if (args.length == 0 || args.length == 1) {
          throw new IllegalArgumentException("Expected at least 2 arguments");
        }

        GameModel model = new GameModel("docs/boardNoHoles.config");
        configurePlayer(List.of(args[0]), model, COLOR.RED);
        configurePlayer(List.of(args).subList(1, args.length),
                model, COLOR.BLUE);
      } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
      }
    });
  }

  private static void configurePlayer(List<String> playerList, ReadOnlyGameModel model, COLOR color) {
    String playerInp = playerList.get(0).toLowerCase();

    if ("human".equalsIgnoreCase(playerInp)) {
      ThreeTriosGameView view = color == COLOR.RED
              ? new RedPlayerView(model)
              : new BluePlayerView(model);
      new ThreeTriosControllerImpl((Game) model, view);
    } else {
      List<PosnStrategy> strategies = new ArrayList<>();
      for (String strategyName : playerList) {
        PosnStrategy strategy = pickStrategy(strategyName);
        if (strategy == null) {
          throw new IllegalArgumentException("Invalid strategy: " + strategyName);
        }
        strategies.add(strategy);
      }
      PosnStrategy combinedStrategy = strategies.size() > 1
              ? new HybridStrategy(strategies)
              : strategies.get(0);

      new AIController((Game) model, combinedStrategy, color);
    }
  }

  private static PosnStrategy pickStrategy(String playerInp) {
    switch (playerInp.toLowerCase()) {
      case "strategy1":
        return new Flip();
      case "strategy2":
        return new MinMaxStrategy();
      case "strategy3":
        return new GoForCorner();
      default:
        return null;
    }
  }
}

