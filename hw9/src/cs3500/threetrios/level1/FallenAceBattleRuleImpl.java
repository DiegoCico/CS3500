package cs3500.threetrios.level1;

import cs3500.threetrios.card.Card;

public class FallenAceBattleRuleImpl implements BattleRule {

  @Override
  public boolean shouldFlip(Card attacker, Card defender, int direction) {
    int attackerValue = getAttackValue(attacker, direction);
    int defenderValue = getAttackValue(defender, (direction + 2) % 4);

    if (attackerValue == 1 && defenderValue == 10) {
      return true;
    }

    return attackerValue > defenderValue;
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
