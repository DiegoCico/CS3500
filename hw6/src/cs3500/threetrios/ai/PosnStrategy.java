package cs3500.threetrios.ai;

import cs3500.threetrios.game.Game;

public interface PosnStrategy {
  int[] ChoosePositions(Game game);
}
