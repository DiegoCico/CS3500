package cs3500.threetrios.provider.model;

import java.util.List;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.game.ReadOnlyGameModel;
import cs3500.threetrios.player.PlayerModel;

public class ReadOnlyBoardAdapter implements ReadOnlyBoard {
  private final ReadOnlyGameModel gameModel;

  public ReadOnlyBoardAdapter(ReadOnlyGameModel gameModel) {
    this.gameModel = gameModel;
  }

  @Override
  public boolean isGameOver() {
    return gameModel.isGameOver();
  }

  @Override
  public Player gameWinner() {
    String winnerStr = gameModel.getWinner();
    switch (winnerStr) {
      case "Red Wins":
        return new Player("Red", COLOR.RED);
      case "Blue Wins":
        return new Player("Blue", COLOR.BLUE);
      case "Tie":
        return null;
      default:
        return null;
    }
  }

  @Override
  public Slot[][] getGrid() {
    Grid grid = gameModel.getGrid();
    int size = gameModel.getGridSize();
    Slot[][] slots = new Slot[size][size];

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        Card card = (Card) gameModel.getCardAt(row, col);
        slots[row][col] = convertCardToSlot(card);
      }
    }
    return slots;
  }

  private Slot convertCardToSlot(Card card) {
    if (card == null) {
      return new Card(null, null);
    } else {
      return new CardAdapter((CardModel) card, null);
    }
  }

  @Override
  public int gameWidth() {
    return gameModel.getGridSize();
  }

  @Override
  public int gameHeight() {
    return gameModel.getGridSize();
  }

  @Override
  public Slot getCoord(int x, int y) {
    Card card = (Card) gameModel.getCardAt(x, y);
    return convertCardToSlot(card);
  }

  @Override
  public Player getCellOwner(int x, int y) {
    return (Player) gameModel.getCardAt(x, y);
  }

  @Override
  public boolean isMoveLegal(int x, int y) {
    return gameModel.isMoveLegal(x, y);
  }

  //TODO: FIx
  @Override
  public int score(Player player) {
    return gameModel.getScore(player);
  }

  //TODO: FIx
  @Override
  public int possibleCardsFlipped(Card card, int x, int y) {
    return gameModel.possibleCardsFlipped(card, x, y);
  }

  @Override
  public Player curPlayer() {
    return (Player) gameModel.getCurrentPlayer();
  }

  //TODO: FIx
  @Override
  public List<Card> getHand(Player player) {
    return gameModel.getHand(player);

  }
}
