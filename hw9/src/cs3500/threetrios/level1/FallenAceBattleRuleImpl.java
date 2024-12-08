package cs3500.threetrios.level1;

import cs3500.threetrios.card.Card;

/**
 * implementation for the fallenAce battle rule.
 */
public class FallenAceBattleRuleImpl implements BattleRule {

  /**
   * Determines whether the defender card should be flipped based on the attack values
   * of the attacker and defender cards in the specified direction.
   *
   * @param attacker  the attacking card
   * @param defender  the defending card
   * @param direction the direction of the attack (0=North, 1=East, 2=South, 3=West)
   * @return {@code true} if the defender should be flipped; {@code false} otherwise
   * @throws IllegalArgumentException if an invalid direction is provided
   */
  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    int attackerValue = getAttackValue(attacker, direction);
    int defenderValue = getAttackValue(defender, (direction + 2) % 4);

    if (attackerValue == 1 && defenderValue == 10) {
      return true;
    }

    return attackerValue > defenderValue;
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
