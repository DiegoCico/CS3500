package cs3500.threetrios.game;

public interface ReadOnlyGameView {
  int getGridSize();
  String getCardAt(int row, int col);
  boolean isGameOver();
  String getWinner();
  String getCurrentPlayer();
  boolean isMoveLegal(int row, int col);
}
