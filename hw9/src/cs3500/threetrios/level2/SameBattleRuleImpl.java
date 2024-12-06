package cs3500.threetrios.level2;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.level1.BattleRule;

public class SameBattleRuleImpl implements BattleRule {
  private final Grid grid;

  public SameBattleRuleImpl(Grid grid) {
    this.grid = grid;
  }

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
          int adjacentVal = getAttackValue(adjacentCard, opposingSides[i]);

          if (adjacentVal == attackVal) {
            matchCount++;
          } else if (adjacentCard != defender) {
            matchCount++;
          }

          if (matchCount >= 2) {
            return true;
          }
        }
      }
    }
    return false;
  }

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
