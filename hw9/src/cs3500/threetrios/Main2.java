package cs3500.threetrios;

import java.io.FileNotFoundException;
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

public class Main2 {

  private static boolean blueHint = true;
  private static boolean redHint = true;

  public static void main(String[] args) throws FileNotFoundException {
    if (!validateArgs(args)) {
      return;
    }

    GameModel game = new GameModel("docs/boardNoHoles.config", new ArrayList<>());
    List<BattleRule> rules = parseRules(args, game);

    if (rules.isEmpty()) {
      addDefaultRules(rules);
    }

    System.out.println("Game started with rules:");
    rules.forEach(rule -> System.out.println(rule.getClass().getSimpleName()));

    try {
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
      System.out.println("Usage: [rules] human human [--redHint=false] [--blueHint=false]");
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
