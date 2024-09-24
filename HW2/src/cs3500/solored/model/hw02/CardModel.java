package cs3500.solored.model.hw02;

import java.util.Objects;

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

  /**
   * Overrides the equals method to compare two CardModel objects.
   *
   * @param obj the object to compare with this card
   * @return true if both the color and number are the same, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    CardModel otherCard = (CardModel) obj;

    return number == otherCard.number && Objects.equals(color, otherCard.color);
  }

  /**
   * Overrides the hashCode method to ensure consistency with equals.
   *
   * @return a hash code based on color and number
   */
  @Override
  public int hashCode() {
    return Objects.hash(color, number);
  }
}
