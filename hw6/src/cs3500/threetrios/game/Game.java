package cs3500.threetrios.game;

import java.util.List;

import cs3500.threetrios.card.Card;
import cs3500.threetrios.player.Player;

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

  /**
   * Gets the current player turn.
   * @return the player turn.
   */
  int getTurn();

  /**
   * Gets the current players array.
   * @return the players array
   */
  Player[] getPlayers();

  /**
   * Checks if the game has ended by counting the number
   * of cards owned by each player.
   * @return "Red Wins", "Blue Wins", or "Tie" based  card ownership.
   */
  String checkWinCondition();

  /**
   * Checks if the game is over by verifying that all card cells are filled.
   * @return true if all card cells are filled, false otherwise
   */
  boolean isGameOver();
}
