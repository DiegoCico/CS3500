package cs3500.threetrios.provider.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.player.PlayerModel;

/**
 * Represents a mutable board adaptor for the ThreeTrios game.
 */
public class ReadOnlyBoardAdapter implements ReadOnlyBoard {
  private final GameModel gameModel;

  /**
   * Constructs a ReadOnlyBoardAdapter with the given GameModel.
   *
   * @param gameModel the game model to adapt
   */
  public ReadOnlyBoardAdapter(GameModel gameModel) {
    if (gameModel == null) {
      throw new IllegalArgumentException("GameModel cannot be null");
    }
    this.gameModel = gameModel;
  }

  /**
   * Determines if the game is over.
   * @return whether the game is over
   */
  @Override
  public boolean isGameOver() {
    return gameModel.isGameOver();
  }

  /**
   * Calculates the winner of the game.
   *
   * @return the winner
   * @throws IllegalStateException if the game is not over
   */
  @Override
  public Player gameWinner() {
    String winner = gameModel.getWinner();
    switch (winner) {
      case "Red Wins":
        return PlayerAdapter.toProviderPlayer((PlayerModel) gameModel.getPlayers()[0]);
      case "Blue Wins":
        return PlayerAdapter.toProviderPlayer((PlayerModel) gameModel.getPlayers()[1]);
      default:
        return null;
    }
  }

  /**
   * Gets the grid from the game going on.
   * @return the grid
   */
  @Override
  public Slot[][] getGrid() {
    int rows = gameModel.getGrid().getRows();
    int cols = gameModel.getGrid().getCols();
    Slot[][] grid = new Slot[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cs3500.threetrios.card.Card card = gameModel.getCardAt(row, col);
        if (card != null) {
          cs3500.threetrios.player.Player owner = findCellOwner(row, col);
          grid[row][col] = new CardAdapter(card, (PlayerModel) owner);
        }
        else {
          grid[row][col] = null;
        }
      }
    }
    return grid;
  }


  /**
   * Finds the owner of the card at the given coordinates.
   */
  private cs3500.threetrios.player.Player findCellOwner(int row, int col) {
    for (cs3500.threetrios.player.Player player : gameModel.getPlayers()) {
      for (cs3500.threetrios.card.Card card : player.getHand()) {
        if (gameModel.getCardAt(row, col) == card) {
          return player;
        }
      }
    }
    return null;
  }


  /**
   * Gives the width of this game in number of slots.
   * @return the width of the game
   */
  @Override
  public int gameWidth() {
    return gameModel.getGrid().getRows();
  }

  /**
   * Gives the height of this game in number of slots.
   * @return the height of the game
   */
  @Override
  public int gameHeight() {
    return gameModel.getGrid().getCols();
  }

  /**
   * Gets the slot at the given x y coordinate in this game, starting in the top left.
   * @param x the x coordinate, 0-indexed
   * @param y the y coordinate, 0-indexed
   * @return a copy of the slot at the given coordinate
   * @throws IllegalArgumentException if x or y are out of range of the board
   */
  @Override
  public Slot getCoord(int x, int y) {
    CardModel card = (CardModel) gameModel.getCardAt(x, y);
    if (card == null) {
      return null;
    }
    return new CardAdapter(card,
            (PlayerModel) gameModel.getCurrentPlayerModel());
  }

  /**
   * Gets the owner of the slot at the given x y coordinate in this game, starting in the top left.
   * @param x the x coordinate, 0-indexed
   * @param y the y coordinate, 0-indexed
   * @return the player that owns the cell, if the cell is of a type that can be owned
   * @throws IllegalArgumentException if the x or y are out of range of the board
   */
  @Override
  public Player getCellOwner(int x, int y) {
    Slot slot = getCoord(x, y);
    return slot != null ? slot.getSlotOwner() : null;
  }

  /**
   * Can the current player play a card at the given x y coordinates, starting in the top left.
   * @param x the x coordinate, 0-indexed
   * @param y the y coordinate, 0-indexed
   * @return true if a card can be played there, false if not
   */
  @Override
  public boolean isMoveLegal(int x, int y) {
    return gameModel.isMoveLegal(x, y);
  }

  /**
   * What is a given players current score in the game, including cards in their hand.
   * @param player the player whose score is being calculated
   * @return the number of cards that that player owns in the board or in their hand
   * @throws IllegalArgumentException if player is null
   */
  @Override
  public int score(Player player) {
    int redCount = 0;
    int blueCount = 0;

    for (int row = 0; row < gameModel.getGrid().getRows(); row++) {
      for (int col = 0; col < gameModel.getGrid().getCols(); col++) {
        CardModel card = (CardModel) gameModel.getCardAt(row, col);
        if (card != null) {
          if (card.getColor() == cs3500.threetrios.card.COLOR.RED) {
            redCount++;
          } else if (card.getColor() == cs3500.threetrios.card.COLOR.BLUE) {
            blueCount++;
          }
        }
      }
    }

    Player redPlayer = PlayerAdapter.toProviderPlayer((PlayerModel) gameModel.getPlayers()[0]);
    Player bluePlayer = PlayerAdapter.toProviderPlayer((PlayerModel) gameModel.getPlayers()[1]);

    if (player.equals(redPlayer)) {
      return redCount;
    } else if (player.equals(bluePlayer)) {
      return blueCount;
    } else {
      throw new IllegalArgumentException("Unknown player");
    }
  }

  /**
   * If a given card is played at the given coordinates, how many cards will be flipped.
   * @param card the card that could be played at those coordinates
   * @param x the x coordinate, 0-indexed, starting from the left
   * @param y the y coordinate, 0-indexed, starting from the top
   * @return the number of cards that would be flipped if this move was played
   * @throws IllegalArgumentException if card is null or x or y are invalid coordinates
   */
  @Override
  public int possibleCardsFlipped(CardAdapter card, int x, int y) {
    Set<cs3500.threetrios.card.Card> flippedCards = new HashSet<>();
    gameModel.battleCards(x, y, flippedCards);
    return flippedCards.size() - 1;
  }

  /**
   * Gets the player that is currently making a turn.
   * @return the current player
   */
  @Override
  public Player curPlayer() {
    return PlayerAdapter.toProviderPlayer((PlayerModel) gameModel.getCurrentPlayer());
  }

  /**
   * Gets the hand for a specific player.
   * @param player the player's whos hand we should get
   * @return the player's hand
   * @throws IllegalArgumentException if player is null
   */
  @Override
  public List<Slot> getHand(Player player) {
    cs3500.threetrios.player.Player modelPlayer = findModelPlayer(player);

    if (modelPlayer == null) {
      throw new IllegalArgumentException("Player not found in the game model.");
    }
    List<Slot> adaptedHand = new ArrayList<>();
    for (cs3500.threetrios.card.Card card : modelPlayer.getHand()) {
      adaptedHand.add(new CardAdapter(card, (PlayerModel) modelPlayer));
    }

    return adaptedHand;
  }


  /**
   * Finds the corresponding PlayerModel in GameModel for the given provider's Player.
   *
   * @param player the provider's Player
   * @return the corresponding PlayerModel in GameModel, or null if not found
   */
  private cs3500.threetrios.player.Player findModelPlayer(Player player) {
    COLOR color;
    if (player.playerColor() == Color.RED) {
      color = cs3500.threetrios.card.COLOR.RED;
    } else {
      color = cs3500.threetrios.card.COLOR.BLUE;
    }
    for (cs3500.threetrios.player.Player modelPlayer : gameModel.getPlayers()) {
      System.out.println("Player found: " + modelPlayer.getName());
      if (modelPlayer.getColor() == color) {
        return modelPlayer;
      }
    }
    return null;
  }

}
