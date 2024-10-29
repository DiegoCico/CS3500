package cs3500.simon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Represents the Simon Says game, where players match a sequence of colors.
 */
public class SimonSays implements Simon {
  private final List<ColorGuess> colorGuesses;
  private final Random random;
  /**
   * Tracks player progress in guessing the color sequence.
   * INVARIANT: 0 <= currentColorIndex < colorGuesses.size()
   */
  private int currentColorIndex;

  /**
   * Builder for constructing a SimonSays game with custom settings.
   */
  public static class Builder {
    Random random;
    List<ColorGuess> initialSequence;

    /**
     * Initializes the Builder with a default
     * random instance and initial sequence.
     */
    public Builder() {
      this.random = new Random();
      this.initialSequence = new ArrayList<>();
      setInitialLength(1);
    }

    /**
     * Sets a custom Random instance.
     * @param random the Random instance to use
     * @return the Builder instance
     */
    public Builder setRandom(Random random) {
      this.random = Objects.requireNonNull(random);
      return this;
    }

    /**
     * Sets the initial length of the color sequence.
     * @param initialLength the starting length of the sequence
     * @return the Builder instance
     */
    public Builder setInitialLength(int initialLength) {
      if (initialLength < 1) {
        throw new IllegalArgumentException("Length must be positive");
      }
      this.initialSequence.clear();
      for (int i = 0; i < initialLength; i++) {
        this.initialSequence.add(SimonSays.getRandomColor(this.random));
      }
      return this;
    }

    /**
     * Sets a custom initial color sequence.
     * @param colorGuesses the sequence of colors
     * @return the Builder instance
     */
    public Builder setInitialSequence(ColorGuess... colorGuesses) {
      for (ColorGuess s : colorGuesses) {
        Objects.requireNonNull(s);
      }
      this.initialSequence.clear();
      this.initialSequence.addAll(List.of(colorGuesses));
      return this;
    }

    /**
     * Builds and returns a new SimonSays instance.
     * @return the new SimonSays game instance
     */
    public SimonSays build() {
      return new SimonSays(this.random, this.initialSequence);
    }
  }

  /**
   * Initializes the SimonSays game with a random instance and color sequence.
   * @param random the Random instance
   * @param initialSequence the initial sequence of colors
   */
  private SimonSays(Random random, List<ColorGuess> initialSequence) {
    this.random = random;
    this.colorGuesses = new ArrayList<>(initialSequence);
  }

  /**
   * Adds a new random color to the sequence.
   */
  private void addNewColor() {
    this.colorGuesses.add(getRandomColor(this.random));
    this.currentColorIndex = 0;
  }

  /**
   * Generates a random color from available options.
   * @param random the Random instance
   * @return a randomly chosen ColorGuess
   */
  private static ColorGuess getRandomColor(Random random) {
    return ColorGuess.values()[random.nextInt(ColorGuess.values().length)];
  }

  /**
   * Returns the current color sequence in the game.
   * @return an unmodifiable list of color guesses
   */
  @Override
  public List<ColorGuess> getCurrentSequence() {
    return Collections.unmodifiableList(this.colorGuesses);
  }

  /**
   * Checks if the guessed color matches the next color in the sequence.
   * @param guess the guessed color
   * @return true if the guess is correct; false otherwise
   */
  @Override
  public boolean enterNextColor(ColorGuess guess) {
    if (guess == this.colorGuesses.get(this.currentColorIndex)) {
      this.currentColorIndex++;
      if (this.currentColorIndex == this.colorGuesses.size()) {
        addNewColor();
      }
      return true;
    } else {
      this.currentColorIndex = 0;
      return false;
    }
  }
}
