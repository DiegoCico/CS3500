package cs3500.threetrios.level1;

import cs3500.threetrios.card.Card;

/**
 * Implementation of the {@link BattleRule} interface for determining whether a defender card
 * should be flipped during a battle based on the attack values in specific directions.
 */
public class BattleRuleImpl implements BattleRule {

  /**
   * Determines whether the defender card should be flipped based on the attack values
   * of the attacker and defender cards in the specified direction.
   *
   * @param attacker  the attacking card
   * @param defender  the defending card
   * @param direction the direction of the attack (0=North, 1=East, 2=South, 3=West)
   * @return {@code true} if the defender should be flipped; {@code false} otherwise
   */
  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    return getAttackValue(attacker, direction) > getAttackValue(defender, (direction + 2) % 4);
  }

  /**
   * Retrieves the attack value of the given card in a specific direction.
   *
   * @param card      the card whose attack value is to be retrieved
   * @param direction the direction (0=North, 1=East, 2=South, 3=West)
   * @return the attack value in the given direction
   * @throws IllegalArgumentException if an invalid direction is provided
   */
  private int getAttackValue(Card card, int direction) {
    switch (direction) {
      case 0:
        return card.getNorth();
      case 1:
        return card.getEast();
      case 2:
        return card.getSouth();
      case 3:
        return card.getWest();
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

}
