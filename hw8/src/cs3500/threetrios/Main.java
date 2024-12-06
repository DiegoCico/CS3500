package cs3500.threetrios;

import javax.swing.SwingUtilities;

import cs3500.threetrios.controller.ThreeTriosControllerImpl;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.gui.RedPlayerView;
import cs3500.threetrios.gui.ThreeTriosGameView;
import cs3500.threetrios.provider.model.ReadOnlyBoardAdapter;
import cs3500.threetrios.provider.view.JFrameView;
import cs3500.threetrios.provider.view.ViewFeaturesAdapter;

/**
 * The main entry point for the Three Trios game, setting up the model, view,
 * and controller, and starting the game.
 */
public class Main {

  /**
   * Running the game.
   * @param args for the game set up.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        if (args == null || args.length < 2) {
          throw new IllegalArgumentException("\"human\" \"human\" or \"human\" \"strategy1\"");
        }

        GameModel model = new GameModel("docs/boardNoHoles.config");
        ThreeTriosControllerImpl controller;

        if ("human".equalsIgnoreCase(args[1])) {
          // Human vs Human
          System.out.println("PLAYING HUMAN");
          controller = createHumanVsHumanGame(model);
          displayProviderView(controller, model);
        } else {
          // Human vs AI
          System.out.println("PLAYING AI");
          //controller = createHumanVsAIGame(model, args);
        }

        //displayProviderView(controller, model);
      } catch (Exception e) {
        throw new IllegalArgumentException("Need more arguments!", e);
      }
    });
  }

  /**
   * Sets up a Human vs Human game.
   */
  private static ThreeTriosControllerImpl createHumanVsHumanGame(GameModel model) {
    ThreeTriosGameView redView = new RedPlayerView(model);
    JFrameView providerView = new JFrameView(new ReadOnlyBoardAdapter(model));

    ThreeTriosControllerImpl controller = new ThreeTriosControllerImpl(model, redView);

    return controller;
  }

  /**
   * Sets up a Human vs AI game.

  private static ThreeTriosControllerImpl createHumanVsAIGame(GameModel model, String[] args) {
    ThreeTriosGameView unifiedView = new ThreeTriosViewImpl(model);

    List strategies = new ArrayList<>();
    for (int i = 1; i < args.length; i++) {
      PosnStrategy strategy = pickStrategy(args[i]);
      if (strategy == null) {
        throw new IllegalArgumentException("Invalid strategy: " + args[i]);
      }
      strategies.add(strategy);
    }
    PosnStrategy combinedStrategy = strategies.size() > 1
            ? new HybridStrategy(strategies)
            : strategies.get(0);

    ThreeTriosControllerImpl controller =
            new ThreeTriosControllerImpl(model, unifiedView, combinedStrategy);

    return controller;
  }


   * Maps a strategy name to its corresponding PosnStrategy implementation.

   private static PosnStrategy pickStrategy(String strategyName) {
   switch (strategyName.toLowerCase()) {
   case "strategy1":
   return new Flip();
   case "strategy2":
   return new MinMaxStrategy();
   case "strategy3":
   return new GoForCorner();
   default:
   System.err.println("Unknown strategy: " + strategyName);
   return null;
   }
   }

   * Displays the provider view in a JFrame.
   */
  private static void displayProviderView(ThreeTriosControllerImpl controller, GameModel model) {
    ReadOnlyBoardAdapter boardAdapter = new ReadOnlyBoardAdapter(model);

    JFrameView providerView = new JFrameView(boardAdapter);

    ViewFeaturesAdapter featuresAdapter = new ViewFeaturesAdapter(controller);
    providerView.addListener(featuresAdapter);

    providerView.makeVisible();
  }

}