package cs3500.threetrios.provider.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a card that implements the Slot interface.
 * A card can have an owner, directional values, and a color.
 */
public class Card implements Slot {
  private final Map<Direction, AttackValue> directionalValues;
  private Player owner;
  private final Color color;

  /**
   * Constructs a card with given directional values, an initial owner, and a color.
   *
   * @param directionalValues the attack values in each direction
   * @param color             the color of the card
   */
  public Card(Map<Direction, AttackValue> directionalValues, Color color) {
    this.directionalValues = new HashMap<>(Objects.requireNonNull(directionalValues, "Directional values cannot be null"));
    this.owner = null;
    this.color = Objects.requireNonNull(color, "Color cannot be null");
  }

  @Override
  public boolean canPlayCard() {
    return owner == null; // TODO: FIGURE THIS OUT
  }

  @Override
  public void switchPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    this.owner = player;
  }

  @Override
  public boolean battle(Slot other, Direction direction) {
    if (other == null || direction == null) {
      throw new IllegalArgumentException("Other slot and direction cannot be null");
    }

    Map<Direction, AttackValue> otherValues = other.getDirectionalValues();
    AttackValue thisAttack = directionalValues.get(direction);
    AttackValue otherDefense = otherValues.get(direction.getOppositeDirection());

    if (thisAttack == null || otherDefense == null) {
      throw new IllegalArgumentException("Attack or defense value is missing for this direction");
    }

    return thisAttack.getValue() > otherDefense.getValue();
  }

  @Override
  public void addToPlayerCount(Map<Player, Integer> curCount) {
    if (curCount == null) {
      throw new IllegalArgumentException("Count map cannot be null");
    }
    if (owner != null) {
      curCount.put(owner, curCount.getOrDefault(owner, 0) + 1);
    }
  }

  @Override
  public int compareAttackValueTo(Direction dirAttackComingFrom, AttackValue other) {
    if (dirAttackComingFrom == null || other == null) {
      throw new IllegalArgumentException("Direction and attack value cannot be null");
    }

    AttackValue thisValue = directionalValues.get(dirAttackComingFrom);
    if (thisValue == null) {
      throw new IllegalArgumentException("This card has no value for the given direction");
    }

    return thisValue.getValue() - other.getValue();
  }

  @Override
  public String boardPrint() {
    StringBuilder sb = new StringBuilder();
    sb.append("Card");
    sb.append("Color: ").append(color).append(", ");
    sb.append("Owner: ").append(owner == null ? "None" : owner).append(", ");
    sb.append("Directional Values: ").append(directionalValues);
    sb.append("");
    return sb.toString();
  }

  @Override
  public Slot copySlot() {
    Map<Direction, AttackValue> copiedValues = new HashMap<>(this.directionalValues);
    return new Card(copiedValues, this.color);
  }

  @Override
  public Player getSlotOwner() {
    return this.owner;
  }

  @Override
  public Color getSlotColor() {
    return this.color;
  }

  @Override
  public Map<Direction, AttackValue> getDirectionalValues() {
    return new HashMap<>(this.directionalValues);
  }
}
