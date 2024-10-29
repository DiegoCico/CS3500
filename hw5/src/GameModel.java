import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel implements Game {

  Grid grid;
  Player[] players;
  int turn;
  Random rand;

  public GameModel(Grid grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid and Players cannot be null");
    }

    this.grid = grid;
    this.players = new Player[] {
            new PlayerModel("Player Red", COLOR.RED, getCards()),
            new PlayerModel("Player Blue", COLOR.BLUE, getCards())
    };
    this.turn = 0;
    Random rand = new Random();
  }

  @Override
  public void switchTurns() {
    if (turn == 0) {
      turn = 1;
    } else if (turn == 1) {
      turn = 0;
    } else {
      throw new IllegalArgumentException("Invalid turn");
    }
  }

  @Override
  public void placeCard(int row, int col, Card card) {
    Player currentPlayer = players[turn];

    if (!card.getColor().equals(currentPlayer.getName())) {
      throw new IllegalStateException("It is not the current player's turn.");
    }

    if (grid.isEmpty(row, col)) {
      grid.placeCard(row, col, card);
      battleCards(row, col);
      switchTurns();
    } else {
      throw new IllegalStateException("Cannot place a card in an occupied or invalid cell.");
    }
  }

  @Override
  public List<Card> getCards() {
    int numCardCells = grid.getNumCardsCells();
    //INVARIANT: Each player’s hand is filled with exactly (N+1)/2 cards
    // where N is the number of card cells on the grid.
    int numCardsEachPlayer = (numCardCells + 1) / 2;
    List<Card> cards = new ArrayList<>();

    for (int i = 0; i < numCardsEachPlayer; i++) {
      Random random = new Random();

      int north = getRandomCardValue();
      int south = getRandomCardValue();
      int east = getRandomCardValue();
      int west = getRandomCardValue();

      CardModel card = new CardModel(getRandomName(), north, south, east, west, COLOR.RED);
      cards.add(card);
    }
    return cards;
  }

  private int getRandomCardValue() {
    Random random = new Random();
    int value = random.nextInt(10) + 1;

    if (value == 10) {
      System.out.println("A");
    }

    return value;
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
   * @return grid.
   */
  @Override
  public Grid getGrid() {
    return new GameGrid(this.grid);
  }

  private String getRandomName() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder randomName = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < 5; i++) {
      int index = random.nextInt(characters.length());
      randomName.append(characters.charAt(index));
    }

    return randomName.toString();
  }


  @Override
  public void battleCards(int row, int col) {
    Card placedCard = grid.getCard(row,col);
    if (placedCard == null) {
      throw new IllegalArgumentException("Placed card is null at the specified position.");
    }

    int[][] directions = {
            {-1,0},
            {0,1},
            {1,0},
            {0,-1}
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
            comboBattle(newRow, newCol);
          }
        }
      }
    }
  }

  private void comboBattle(int newRow, int newCol) {
    battleCards(newRow, newCol);
  }

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
}
  