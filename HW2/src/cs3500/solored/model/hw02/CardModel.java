package cs3500.solored.model.hw02;

public class CardModel implements Card{
  private final String color;
  private final int number;

  public CardModel(String color, int number) {
    this.color = color;
    this.number = number;
  }

  public String getColor() {
    return color;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return color + number;
  }

}
