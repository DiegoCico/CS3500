package cs3500.threetrios.level1;

import cs3500.threetrios.card.Card;

public interface BattleRule {
  /**
   * Determines if the defender card should flip based on the attacker card.
   *
   * @param attacker the attacking card
   * @param defender the defending card
   * @param direction the direction of the attack (0 = North, 1 = East, 2 = South, 3 = West)
   * @return true if the defender should flip, false otherwise
   */
  boolean shouldFlip(Card attacker, Card defender, int direction);
}
