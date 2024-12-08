package cs3500.threetrios.level2;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.level1.BattleRule;

/**
 * Plus battle rule: flips a defender's card if the sum of two attack values
 * (one from attacking card and the other from adjacent card) are equal in at least two directions.
 * The defender is flipped if adjacent cards match the sum rule in given direction.
 */
public class PlusBattleRuleImpl implements BattleRule {
  private final Grid grid;

  /**
   * Constructor for PlusBattleRuleImpl.
   *
   * @param grid the game grid where the cards are placed
   */
  public PlusBattleRuleImpl(Grid grid) {
    this.grid = grid;
  }

  /**
   * Determines if defender's card should flip based on the Plus battle rule.
   * The card is flipped if the sum of two attack values from attacking card and two adjacent cards
   * are equal in at least two directions.
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

    for (int i = 0; i < directions.length; i++) {
      int newRow1 = row + directions[i][0];
      int newCol1 = col + directions[i][1];

      for (int j = i + 1; j < directions.length; j++) {
        int newRow2 = row + directions[j][0];
        int newCol2 = col + directions[j][1];

        if (grid.validPosition(newRow1, newCol1) && grid.validPosition(newRow2, newCol2)) {
          Card adjacentCard1 = grid.getCard(newRow1, newCol1);
          Card adjacentCard2 = grid.getCard(newRow2, newCol2);

          if (adjacentCard1 != null
                  && adjacentCard2 != null
                  && adjacentCard1.getColor() != attacker.getColor()
                  && adjacentCard2.getColor() != attacker.getColor()) {

            int sum1 = getAttackValue(attacker, i)
                    + getAttackValue(adjacentCard1, opposingSides[i]);
            int sum2 = getAttackValue(attacker, j)
                    + getAttackValue(adjacentCard2, opposingSides[j]);

            if ((adjacentCard1 == defender && i == direction) ||
                    (adjacentCard2 == defender && j == direction)) {
              if (sum1 == sum2) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * Returns the attack value of the given card in specific direction.
   *
   * @param card      current card
   * @param direction the direction (0=North, 1=East, 2=South, 3=West)
   * @return the attack val in the given direction
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
