package cs3500.threetrios.provider.model;

/**
 * Represents the values a Card can take in the game.
 */
public enum AttackValue {
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

  public final int value;

  /**
   * Initializes this attack values value.
   *
   * @param value the value of the specific number
   */
  AttackValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    if (this == AttackValue.A) {
      return "A";
    }
    return Integer.toString(this.value);
  }

  public int getValue() {
    return this.value;
  }

  /**
   * Compares this attack value to another by their numerical value.
   *
   * @param other the attack value to compare
   * @return a positive int if this attack value is greater, a negative int if it's less than,
   *         and 0 if they are equal
   * @throws IllegalArgumentException if other is null
   */
  public int compareToValue(AttackValue other) {
    if (other == null) {
      throw new IllegalArgumentException("other cannot be null");
    }
    return this.value - other.value;
  }
}
