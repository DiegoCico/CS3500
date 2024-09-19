package cs3500.solored.model.hw02;

public class SoloCard implements Card{
  private final String color;
  private final int number;

  public SoloCard(String color, int number) {
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
