package cs3500.simon.model;

import java.util.List;

/**
 * read only interface.
 */
public interface ReadOnlySimon {
  /**
   * Each round of Simon consists of a sequence of colors, generated randomly.
   * @return The current round's sequence of colors, as an unmodifiable list.
   */
  List<ColorGuess> getCurrentSequence();
}
