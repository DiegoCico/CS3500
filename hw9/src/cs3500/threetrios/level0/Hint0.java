package cs3500.threetrios.level0;

import cs3500.threetrios.card.Card;

/**
 * Interface for implementing hint logic in the Three Trios game.
 */
public interface Hint0 {
  /**
   * Calculates the hint for a given grid cell and selected card.
   *
   * @param row    the row of the cell
   * @param col    the column of the cell
   * @param card   the card selected by the player
   * @return the number of cards that would be flipped if the card is played at this cell
   */
  int calculateHint(int row, int col, Card card);
}
