package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ThreeTriosControllerImpl;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.level1.BattleRule;
import cs3500.threetrios.level1.FallenAceBattleRuleImpl;
import cs3500.threetrios.level1.ReverseBattleRuleImpl;
import cs3500.threetrios.level2.PlusBattleRuleImpl;
import cs3500.threetrios.level2.SameBattleRuleImpl;
import cs3500.threetrios.gui.BluePlayerView;
import cs3500.threetrios.gui.RedPlayerView;
import cs3500.threetrios.gui.ThreeTriosGameView;

/**
 * The main entry point for the Three Trios game, setting up the model, view,
 * and controller, and starting the game.
 */
public class Main2 {

  private static boolean blueHint = true;
  private static boolean redHint = true;

  /**
   * Running the game.
   * @param args for the game set up.
   */
  public static void main(String[] args) {
    if (!validateArgs(args)) {
      return;
    }

    List<BattleRule> rules = new ArrayList<>();

    try {
      GameModel temp = new GameModel("docs/boardNoHoles.config", rules);
      rules = parseRules(args, temp);
      GameModel game = new GameModel("docs/boardNoHoles.config", rules);

      if (rules.isEmpty()) {
        addDefaultRules(rules);
      }

      System.out.println("Game started with rules:");
      rules.forEach(rule -> System.out.println(rule.getClass().getSimpleName()));

      createHumanVsHumanGame(game);
    } catch (Exception e) {
      System.out.println("Error initializing game: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Validates the command-line arguments.
   */
  private static boolean validateArgs(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: [rules] [--redHint=false] [--blueHint=false] human human");
      return false;
    }

    if (!args[args.length - 2].equalsIgnoreCase("human") ||
            !args[args.length - 1].equalsIgnoreCase("human")) {
      System.out.println("The last two arguments must be 'human human'");
      return false;
    }

    return true;
  }

  /**
   * Parses rules and hint options from command-line arguments.
   */
  private static List<BattleRule> parseRules(String[] args, GameModel game) {
    List<BattleRule> rules = new ArrayList<>();
    for (String arg : args) {
      switch (arg.toLowerCase()) {
        case "reverse":
          rules.add(new ReverseBattleRuleImpl());
          break;
        case "fallenace":
          rules.add(new FallenAceBattleRuleImpl());
          break;
        case "same":
          rules.add(new SameBattleRuleImpl(game.getGrid()));
          break;
        case "plus":
          rules.add(new PlusBattleRuleImpl(game.getGrid()));
          break;
        case "--redhint=false":
          redHint = false;
          break;
        case "--bluehint=false":
          blueHint = false;
          break;
        default:
          if (!arg.equalsIgnoreCase("human")) {
            System.out.println("Unknown rule or argument: " + arg);
          }
          break;
      }
    }
    return rules;
  }

  /**
   * Adds default rules when none are specified.
   */
  private static void addDefaultRules(List<BattleRule> rules) {
    System.out.println("No rules specified, using default: Reverse and Fallen Ace.");
    rules.add(new ReverseBattleRuleImpl());
    rules.add(new FallenAceBattleRuleImpl());
  }

  /**
   * Sets up a Human vs Human game.
   */
  private static void createHumanVsHumanGame(GameModel model) {
    ThreeTriosGameView redView = new RedPlayerView(model);
    ThreeTriosGameView blueView = new BluePlayerView(model);

    new ThreeTriosControllerImpl(model, redView, redHint);
    new ThreeTriosControllerImpl(model, blueView, blueHint);
  }
}
