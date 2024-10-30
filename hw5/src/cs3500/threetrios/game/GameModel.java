package cs3500.threetrios;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A cs3500.threetrios.GameModel class that implements the cs3500.threetrios.Game interface.
 */
public class GameModel implements Game {

  private Grid grid;
  Player[] players;
  int turn;
  private Random rand = new Random();

  /**
   * Constructor for a cs3500.threetrios.GameModel.
   * @param grid game grid and players
   */
  public GameModel(Grid grid, Player[] players) {
    if (grid == null || players == null || players.length != 2) {
      throw new IllegalArgumentException("Grid and Players cannot be null");
    }

    this.grid = grid;
    this.players = players;
    this.turn = 0;
  }

  /**
   * Constructor for GameModel, initializing grid and cards based on a single config file.
   *
   * @param boardConfigPath path to the combined board configuration file
   * @throws FileNotFoundException if the configuration file is not found
   */
  public GameModel(String boardConfigPath) throws FileNotFoundException {
    if (boardConfigPath == null || boardConfigPath.isEmpty()) {
      throw new IllegalArgumentException("Board config path cannot be null");
    }
    GameModel parsedGame = BoardConfigParser.parseBoardConfig(boardConfigPath);
    this.grid = parsedGame.getGrid();
    this.players = parsedGame.getPlayers();
    this.turn = 0;
  }

  /**
   * Constructor for GameModel, initializing players with distributed cards from a provided deck.
   *
   * @param grid the game grid
   * @param deck the deck of cards parsed from configuration
   */
  public GameModel(Grid grid, List<Card> deck) {
    if (grid == null || deck == null || deck.size() < grid.getNumCardsCells() + 1) {
      throw new IllegalArgumentException("Grid and sufficient cards are required.");
    }

    this.grid = grid;
    this.turn = 0;

    // Distribute cards to players
    int numCardsEachPlayer = (grid.getNumCardsCells() + 1) / 2;
    List<Card> redPlayerCards = drawCards(deck, numCardsEachPlayer, COLOR.RED);
    List<Card> bluePlayerCards = drawCards(deck, numCardsEachPlayer, COLOR.BLUE);

    this.players = new Player[]{
            new PlayerModel("Player Red", COLOR.RED, redPlayerCards),
            new PlayerModel("Player Blue", COLOR.BLUE, bluePlayerCards)
    };
  }

  /**
   * Constructor for a cs3500.threetrios.GameModel.
   * @param grid game grid
   */
  public GameModel(Grid grid) {
    if (grid == null) {
      throw new IllegalArgumentException("cs3500.threetrios.Grid and Players cannot be null");
    }

    this.grid = grid;
    this.players = new Player[] {
            new PlayerModel("Player Red", COLOR.RED, getCards()),
            new PlayerModel("Player Blue", COLOR.BLUE, getCards())
    };
    this.turn = 0;
  }

  /**
   * Draws a specified number of cards for a player and assigns a color.
   *
   * @param deck        the available deck of cards
   * @param numCards    number of cards to draw
   * @param playerColor the color to assign to each card
   * @return a list of drawn cards with the assigned color
   */
  private List<Card> drawCards(List<Card> deck, int numCards, COLOR playerColor) {
    List<Card> playerHand = new ArrayList<>();
    for (int i = 0; i < numCards && !deck.isEmpty(); i++) {
      CardModel card = (CardModel) deck.remove(0);
      card.switchColor(playerColor);
      playerHand.add(card);
    }
    return playerHand;
  }

  /**
   * Helps determine which turn it is for a player.
   */
  @Override
  public void switchTurns() {
    if (turn == 0 || turn == 1) {
      turn = 1 - turn;
    } else {
      throw new IllegalArgumentException("Invalid turn");
    }
  }

  /**
   * Places a specific card at a certain row and column.
   * @param row  the row to place the card
   * @param col  the column to place the card
   * @param card the card to place
   */
  @Override
  public void placeCard(int row, int col, Card card) {
    Player currentPlayer = players[turn];

    if (!card.getColor().equals(currentPlayer.getColor())) {
      throw new IllegalStateException("It is not the current player's turn.");
    }

    if (grid.isEmpty(row, col)) {
      currentPlayer.removeCard(currentPlayer.getHand().indexOf(card));
      battleCards(row, col);
      switchTurns();
    } else {
      throw new IllegalStateException("Cannot place a card in an occupied or invalid cell.");
    }
  }

  /**
   * Gets all the cards in the game.
   * @return a List<cs3500.threetrios.Card> a hand
   */
  @Override
  public List<Card> getCards() {
    int numCardCells = grid.getNumCardsCells();
    //INVARIANT: Each player’s hand is filled with exactly (N+1)/2 cards
    // where N is the number of card cells on the grid.
    int numCardsEachPlayer = (numCardCells + 1) / 2;
    List<Card> cards = new ArrayList<>();

    for (int i = 0; i < numCardsEachPlayer; i++) {

      int north = getRandomCardValue();
      int south = getRandomCardValue();
      int east = getRandomCardValue();
      int west = getRandomCardValue();

      CardModel card = new CardModel(getRandomName(), north, south, east, west, COLOR.RED);
      cards.add(card);
    }
    return cards;
  }

  /**
   * Gets a random name based a randomNameGenerator.
   * @return a name
   */
  private String getRandomName() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder randomName = new StringBuilder();

    for (int i = 0; i < 5; i++) {
      int index = this.rand.nextInt(characters.length());
      randomName.append(characters.charAt(index));
    }

    return randomName.toString();
  }

  /**
   * Gets a RandomCardValue 1-10.
   * Index will start at 1.
   * @return
   */
  private int getRandomCardValue() {
    return this.rand.nextInt(10) + 1;
  }

  /**
   * return a copy of current player.
   *
   * @return player.
   */
  @Override
  public Player getCurrentPlayer() {
    return new PlayerModel(players[turn]);
  }

  /**
   * gets a copy of the grid.
   *
   * @return grid.getCurrentPlayer
   */
  @Override
  public Grid getGrid() {
    return new GameGrid(this.grid);
  }


  /**
   * This performs the action of cards battling.
   * @param row the row where the card is located
   * @param col the column where the card is located
   */
  @Override
  public void battleCards(int row, int col) {
    Card placedCard = grid.getCard(row,col);
    boolean flipped = true;

    while(flipped) {
      flipped = false;
      int[][] directions = {
              {-1, 0},
              {0, 1},
              {1, 0},
              {0, -1}
      };

      for (int i = 0; i < directions.length; i++) {
        int newRow = row + directions[i][0];
        int newCol = col + directions[i][1];

        if (grid.validPosition(newRow, newCol)) {
          Card adjacentCard = grid.getCard(newRow, newCol);

          if (adjacentCard != null && adjacentCard.getColor() != placedCard.getColor()) {
            int placedCardAttack = getAttackValue(placedCard, i);
            int adjacentCardAttack = getAttackValue(adjacentCard, (i + 2) % 4);

            if (placedCardAttack > adjacentCardAttack) {
              adjacentCard.switchColor(placedCard.getColor());
              grid.placeCard(newRow, newCol, adjacentCard);
              flipped = true;
            }
          }
        }
      }
    }
  }

  /**
   * Gets a certain attack value associated with a cs3500.threetrios.Card and direction (ENUM)
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
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

  /**
   * Gets the current players array.
   * @return the players array
   */
  public Player[] getPlayers() {
    return players;
  }
}
