package cs3500.threetrios.level2;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.level1.BattleRule;

/**
 * Impl for Same battle rule. Flips defender's card if at least two adjacent
 * cards (N, E, S, W) share the same attack value in the opposite direction.
 * The cards must be owned by the opponent to be flipped.
 */
public class SameBattleRuleImpl implements BattleRule {
  private final Grid grid;

  /**
   * Constructor for SameBattleRuleImpl.
   *
   * @param grid the game grid where the cards are placed
   */
  public SameBattleRuleImpl(Grid grid) {
    this.grid = grid;
  }

  /**
   * Determines if defender's card should flip based on the Same battle rule.
   * Card is flipped if at least two adjacent cards (N, E, S, W)
   * share the same attack value in the opposite direction.
   *
   * @param attacker  the attacking card
   * @param defender  the defending card
   * @param direction the direction of the attack
   * @return true if the defender's card should flip
   */
  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    int[] opposingSides = {2, 3, 0, 1};
    int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    int[] position = grid.getCardPositions(attacker);

    if (position == null) {
      throw new IllegalArgumentException("Attacker card is not on the grid.");
    }

    int row = position[0];
    int col = position[1];
    int attackVal = getAttackValue(attacker, direction);
    int matchCount = 0;

    for (int i = 0; i < directions.length; i++) {
      int newRow = row + directions[i][0];
      int newCol = col + directions[i][1];

      if (grid.validPosition(newRow, newCol)) {
        Card adjacentCard = grid.getCard(newRow, newCol);

        if (adjacentCard != null &&
                adjacentCard.getColor() != attacker.getColor() &&
                getAttackValue(adjacentCard, opposingSides[i]) == attackVal) {
          matchCount++;
          if (adjacentCard == defender && i == direction) {
            matchCount++;
          }
        }
      }
    }
    return matchCount >= 2;
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
