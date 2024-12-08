package cs3500.threetrios.level0;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.ReadOnlyGameModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of the Hint interface.
 * Calculates hints based on card flipping logic in the game.
 */
public class Hint0Impl implements Hint0 {
  private final ReadOnlyGameModel game;

  /**
   * Constructs a Hint0 instance with the given game grid.
   *
   * @param game the game grid
   */
  public Hint0Impl(ReadOnlyGameModel game) {
    this.game = game;
  }

  /**
   * Calculates how many cards would be flipped if the given card
   * is played at the specified grid cell.
   *
   * @param row  the row of the cell
   * @param col  the column of the cell
   * @param card the card selected by the current player
   * @return the number of cards that would be flipped
   */
  @Override
  public int calculateHint(int row, int col, Card card) {
    COLOR currentPlayerColor = game.getCurrentPlayer().getColor();

    if (!game.getGrid().validPosition(row, col)
            || game.getGrid().getCard(row, col) != null) {
      return 0;
    }

    if (card.getColor() != currentPlayerColor) {
      return 0;
    }

    Set<Card> flippedCards = new HashSet<>();
    simulateBattle(row, col, card, flippedCards);
    return flippedCards.size() - 1;
  }

  /**
   * Simulates the battle logic to determine which cards would flip.
   *
   * @param row          the row of the placed card
   * @param col          the column of the placed card
   * @param card         the card being placed
   * @param flippedCards a set to keep track of flipped cards
   */
  private void simulateBattle(int row, int col, Card card, Set<Card> flippedCards) {
    flippedCards.add(card);

    int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    int[] opposingSides = {2, 3, 0, 1};

    for (int i = 0; i < directions.length; i++) {
      int newRow = row + directions[i][0];
      int newCol = col + directions[i][1];

      if (game.getGrid().validPosition(newRow, newCol)) {
        Card adjacentCard = game.getCardAt(newRow, newCol);

        if (adjacentCard != null
                && adjacentCard.getColor() != card.getColor()
                && !flippedCards.contains(adjacentCard)) {
          int placedCardAttack = getAttackValue(card, i);
          int adjacentCardAttack = getAttackValue(adjacentCard, opposingSides[i]);

          if (placedCardAttack > adjacentCardAttack) {
            flippedCards.add(adjacentCard);
            simulateBattle(newRow, newCol, adjacentCard, flippedCards);
          }
        }
      }
    }
  }

  /**
   * Gets the attack value of a card in the specified direction.
   *
   * @param card      the card to evaluate
   * @param direction the direction (0: North, 1: East, 2: South, 3: West)
   * @return the attack value of the card in that direction
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
