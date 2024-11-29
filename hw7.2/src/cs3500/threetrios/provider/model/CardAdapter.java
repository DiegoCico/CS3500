package cs3500.threetrios.provider.model;


import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.player.Player;

import java.awt.Color;
import java.util.Map;

public class CardAdapter implements Slot {
  private final Card myCard;
  private Player myPlayer;

  public CardAdapter(Card myCard, Player myPlayer) {
    this.myCard = myCard;
    this.myPlayer = null;
  }

  @Override
  public boolean canPlayCard() {
    return myPlayer == null;
  }

  @Override
  public void switchPlayer(cs3500.threetrios.provider.model.Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    this.myPlayer = (Player) player;
  }

  @Override
  public boolean battle(Slot other, Direction direction) {
    if (other == null || direction == null) {
      throw new IllegalArgumentException("Other slot or direction cannot be null");
    }

    int thisAttack = getAttackValue(direction);
    Map<Direction, AttackValue> otherValues = other.getDirectionalValues();
    int otherDefense = otherValues.get(direction.getOppositeDirection()).getValue();

    return thisAttack > otherDefense;
  }

  //TODO: FIx
  @Override
  public void addToPlayerCount(Map<cs3500.threetrios.provider.model.Player, Integer> curCount) {
    if (myPlayer != null) {
      curCount.put((cs3500.threetrios.provider.model.Player) myPlayer,
              curCount.getOrDefault(myPlayer, 0) + 1);
    }
  }

  @Override
  public int compareAttackValueTo(Direction dirAttackComingFrom, AttackValue other) {
    int thisValue = getAttackValue(dirAttackComingFrom);
    return thisValue - other.getValue();
  }

  @Override
  public String boardPrint() {
    return myCard.toString();
  }

  public Slot copySlot() {
    return new CardAdapter(new CardModel(myCard.getName(), myCard.getNorth(),
            myCard.getSouth(),
            myCard.getEast(),
            myCard.getWest(),
            myCard.getColor()), myPlayer);
  }

  @Override
  public cs3500.threetrios.provider.model.Player getSlotOwner() {
    return (cs3500.threetrios.provider.model.Player) myPlayer;
  }

  //TODO: FIx
  @Override
  public Color getSlotColor() {
    return (cs3500.threetrios.provider.card.COLOR) myCard.getColor();
  }

  //TODO: FIx
  @Override
  public Map<Direction, AttackValue> getDirectionalValues() {
    return myCard.getDirectionalValues();
  }

  /**
   * Helper method to get the attack value for a given direction.
   */
  //TODO:FIX
  private int getAttackValue(Direction direction) {
    switch (direction) {
      case UP:
        return myCard.getNorth();
      case DOWN:
        return myCard.getSouth();
      case LEFT:
        return myCard.getEast();
      case RIGHT:
        return myCard.getWest();
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
}

