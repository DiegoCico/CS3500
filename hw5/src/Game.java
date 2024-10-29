import java.util.List;

/**
 * Represents a game with card operations.
 */
public interface Game {

  /**
   * Switches the turn to the next player.
   */
  void switchTurns();

  /**
   * Battles the cards located at the given row and column.
   *
   * @param row the row where the card is located
   * @param col the column where the card is located
   */
  void battleCards(int row, int col);

  /**
   * Places a card at the specified row and column.
   *
   * @param row  the row to place the card
   * @param col  the column to place the card
   * @param card the card to place
   */
  void placeCard(int row, int col, Card card);

  /**
   * Returns a list of cards in the game.
   *
   * @return the list of cards
   */
  List<Card> getCards();

  /**
   * gets a copy of the current player.
   * @return player.
   */
  Player getCurrentPlayer();

  /**
   * gets a copy of the grid.
   * @return grid.
   */
  Grid getGrid();
}
