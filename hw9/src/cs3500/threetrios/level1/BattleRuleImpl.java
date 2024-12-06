package cs3500.threetrios.level1;

import cs3500.threetrios.card.Card;

public class BattleRuleImpl implements BattleRule{

  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    return getAttackValue(attacker, direction) > getAttackValue(defender, (direction + 2) % 4);
  }

  private int getAttackValue(Card card, int direction) {
    switch (direction) {
      case 0: return card.getNorth();
      case 1: return card.getEast();
      case 2: return card.getSouth();
      case 3: return card.getWest();
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

}
