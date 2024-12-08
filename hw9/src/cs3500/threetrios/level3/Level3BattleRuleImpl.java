package cs3500.threetrios.level3;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.level1.BattleRule;
import cs3500.threetrios.level1.FallenAceBattleRuleImpl;
import cs3500.threetrios.level1.ReverseBattleRuleImpl;
import cs3500.threetrios.level2.PlusBattleRuleImpl;
import cs3500.threetrios.level2.SameBattleRuleImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements Level 3 battle rules, which combine variant battle rules
 * into a single set of rules - Reverse, Fallen Ace, Same, and Plus rules.
 * Depending on the config, any combo of rules can be activated to determine
 * if a card should flip during game.
 */
public class Level3BattleRuleImpl implements BattleRule {
  private final Grid grid;
  private final List<BattleRule> activeRules;

  /**
   * Constructor for the Level3BattleRuleImpl class.
   * Initializes a list of active battle rules based on params below.
   *
   * @param grid        the game grid where the cards are placed.
   * @param useReverse  true -> Reverse battle rule will be active.
   * @param useFallenAce true -> Fallen Ace battle rule will be active.
   * @param useSame     true -> Same battle rule will be active.
   * @param usePlus     true -> Plus battle rule will be active.
   */
  public Level3BattleRuleImpl(Grid grid, boolean useReverse,
                              boolean useFallenAce, boolean useSame, boolean usePlus) {
    this.grid = grid;
    this.activeRules = initializeRules(useReverse, useFallenAce, useSame, usePlus);
  }

  /**
   * Initializes the list of battle rules based on the provided boolean from constructor.
   *
   * @param useReverse  true -> add the Reverse battle rule.
   * @param useFallenAce true ->add the Fallen Ace battle rule.
   * @param useSame     true -> add the Same battle rule.
   * @param usePlus    true -> add the Plus battle rule.
   * @return a list of active battle rules.
   */
  private List<BattleRule> initializeRules(boolean useReverse,
                                           boolean useFallenAce, boolean useSame, boolean usePlus) {
    List<BattleRule> rules = new ArrayList<>();

    if (useReverse) {
      rules.add(new ReverseBattleRuleImpl());
    }
    if (useFallenAce) {
      rules.add(new FallenAceBattleRuleImpl());
    }

    if (useSame) {
      rules.add(new SameBattleRuleImpl(grid));
    } else if (usePlus) {
      rules.add(new PlusBattleRuleImpl(grid));
    }

    return rules;
  }

  /**
   * Determines if a card should flip based on the active battle rules.
   * Iterates through all the active rules and checks if attacker and defender
   * cards should flip according to any of the active rules.
   *
   * @param attacker  the attacking card
   * @param defender  the defending card
   * @param direction the direction of the attack
   * @return true if any of the active rules return true (meaning they flipped)
   */
  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    for (BattleRule rule : activeRules) {
      if (rule.shouldFlip(attacker, defender, direction)) {
        return true;
      }
    }
    return false;
  }
}
