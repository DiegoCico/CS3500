package cs3500.solored.model.hw02;

/**
 * Represents a card in the game with a color and a number.
 */
public class CardModel implements Card {
  private final String color;
  private final int number;

  /**
   * Constructor for the CardModel.
   * Initializes the card with a color and a number.
   *
   * @param color  the color of the card
   * @param number the number on the card
   */
  public CardModel(String color, int number) {
    this.color = color;
    this.number = number;
  }

  /**
   * Gets the color of the card.
   *
   * @return the color of the card as a String
   */
  public String getColor() {
    return color;
  }

  /**
   * Gets the number of the card.
   *
   * @return the number of the card as an int
   */
  public int getNumber() {
    return number;
  }

  /**
   * Returns a string representation of the card, combining the color and number.
   *
   * @return a string representation of the card
   */
  @Override
  public String toString() {
    return color + number;
  }
}
