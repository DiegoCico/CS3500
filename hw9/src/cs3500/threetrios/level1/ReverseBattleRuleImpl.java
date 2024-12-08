package cs3500.threetrios.level1;

import cs3500.threetrios.card.Card;

/**
 * Implementation of the {@link BattleRule} interface for a reverse battle rule. In this rule,
 * a defender card is flipped if its attack value is greater than the attack value of the
 * attacker card in the specified direction.
 */
public class ReverseBattleRuleImpl implements BattleRule {

  /**
   * In this reverse rule,
   * the defender card is flipped if its attack value is greater than the attacker's attack value.
   *
   * @param attacker  the attacking card
   * @param defender  the defending card
   * @param direction the direction of the attack (0=North, 1=East, 2=South, 3=West)
   * @return {@code true} if the defender should be flipped; {@code false} otherwise
   */
  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    return getAttackValue(attacker, direction) < getAttackValue(defender, (direction + 2) % 4);
  }

  /**
   * Returns the attack value of the given card in specific direction.
   *
   * @param card      current card
   * @param direction the direction (0=North, 1=East, 2=South, 3=West)
   * @return the attack val in the given direction.
   */
  private int getAttackValue(Card card, int direction) {
    switch (direction) {
      case 0: return card.getNorth();
      case 1: return card.getEast();
      case 2: return card.getSouth();
      case 3: return card.getWest();
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

}
