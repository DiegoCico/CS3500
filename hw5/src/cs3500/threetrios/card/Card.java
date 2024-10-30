package cs3500.threetrios;

/**
 * The {@code cs3500.threetrios.Card} interface defines the behavior for a card, including switching its color.
 */
public interface Card {

  /**
   * Enum representing card numbers (1-9 and A).
   */
  enum NUMBER {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    A(10);

    private final int value;

    NUMBER(int value) {
      this.value = value;
    }

    /**
     * Converts an integer to a {@code NUMBER}.
     *
     * @param value the integer value
     * @return the corresponding {@code NUMBER}
     * @throws IllegalArgumentException if the value is invalid
     */
    public static NUMBER fromValue(int value) {
      for (NUMBER number : NUMBER.values()) {
        if (number.getValue() == value) {
          return number;
        }
      }
      throw new IllegalArgumentException("Invalid number: " + value);
    }

    /**
     * @return the numeric value of the {@code NUMBER}
     */
    public int getValue() {
      return value;
    }
  }

  /**
   * Switches the card's color.
   *
   * @param color the new color
   */
  void switchColor(COLOR color);

  /**
   * Gets the card color.
   *
   * @return return the enum of the card color.
   */
  COLOR getColor();

  /**
   * gets the integer for south.
   *
   * @return integer for south.
   */
  int getSouth();

  /**
   * gets the integer for north.
   *
   * @return integer for north.
   */
  int getNorth();

  /**
   * gets the integer for east.
   *
   * @return integer for east.
   */
  int getEast();

  /**
   * gets the integer for west.
   *
   * @return integer for west.
   */
  int getWest();
}
