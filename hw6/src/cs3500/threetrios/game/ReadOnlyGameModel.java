package cs3500.threetrios.game;

import cs3500.threetrios.player.Player;

public interface ReadOnlyGameModel {
  int getGridSize();
  String getCardAt(int row, int col);
  boolean isGameOver();
  String getWinner();
  Player getCurrentPlayer();
  boolean isMoveLegal(int row, int col);
  Player[] getPlayers();
}
