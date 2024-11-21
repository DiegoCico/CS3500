package cs3500.threetrios.ai;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.Game;
import cs3500.threetrios.player.Player;

public class LeastFlippableStrat implements PosnStrategy {

  @Override
  public int[] ChoosePositions(Game game) {
    int[] bestMove = new int[3];
    int minFlipRisk = Integer.MAX_VALUE;

    Player player = game.getCurrentPlayer();

    for(int row = 0; row < game.getGrid().getRows(); row++) {
      for (int col = 0; col < game.getGrid().getCols(); col++) {
        if (game.isMoveLegal(row, col)) {
          for (int flip = 0; flip < player.getHand().size(); flip++) {
            Card card = player.getHand().get(flip);
            int flipRisk = game.calculateFlipRisk(row, col, card);
              if (flipRisk < minFlipRisk) {
                minFlipRisk = flipRisk;
                bestMove[0] = row;
                bestMove[1] = col;
                bestMove[2] = flip;
              }
            }
          }
        }
      }
      return minFlipRisk < Integer.MAX_VALUE ? bestMove : new int[] {-1, -1, -1};;
  }

  private int calculateFlipRisk(Game game, int row, int col, Card card) {
    int risk = 0;

    int[][] directions = {
            {-1, 0},
            {0, 1},
            {1, 0},
            {0, -1}
    };

    for (int dir = 0; dir < directions.length; dir++) {
      int adjRow = row + directions[dir][0];
      int adjCol = col + directions[dir][1];

      if (game.getGrid().validPosition(adjRow, adjCol)) {
        Card adjacentCard = game.getGrid().getCard(adjRow, adjCol);
        if (adjacentCard != null && adjacentCard.getColor() != card.getColor()) {
          int cardAttack = getAttackValue(card, dir);
          int adjAttack = getAttackValue(adjacentCard, (dir + 2) % 4); // Opposite direction

          if (adjAttack > cardAttack) {
            risk += adjAttack - cardAttack;
          }
        }
      }
    }

    return risk;
  }

  /**
   * Gets a certain attack value associated with a
   * Card and direction (ENUM).
   * @param card a game card
   * @param direction a direction
   * @return the value
   */
  private int getAttackValue(Card card, int direction) {
    switch (direction) {
      case 0:
        return card.getNorth();
      case 1:
        return card.getSouth();
      case 2:
        return card.getEast();
      case 3:
        return card.getWest();
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
}
