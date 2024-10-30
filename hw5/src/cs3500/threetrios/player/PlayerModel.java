package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel implements Player {

  private List<Card> hand;
  private final COLOR color;
  private final String name;

  public PlayerModel(String name, COLOR color, List<Card> hand) {
    if (hand == null || hand.isEmpty()) {
      throw new IllegalStateException("hand cannot be null or empty");
    }
    if (color == null) {
      throw new IllegalStateException("name cannot be null or empty");
    }
    if(name == null || name.isEmpty()) {
      throw new IllegalStateException("name cannot be null or empty");
    }
    this.name = name;
    this.color = color;
    this.hand = new ArrayList<>(hand);
  }

  public PlayerModel(Player model) {
    this.name = model.getName();
    this.color = model.getColor();
    this.hand = model.getHand();
  }

  @Override
  public int handSize() {
    return hand.size();
  }

  @Override
  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  @Override
  public void removeCard(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("index out of bounds");
    }
    hand.remove(index);
  }

  @Override
  public void addCard(Card card) {
    if (card == null) {
      throw new IllegalStateException("hand cannot be null");
    } else {
      hand.add(card);
    }
  }

  @Override
  public Card getCard(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("index out of bounds");
    }
    return hand.get(index);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public COLOR getColor(){
    return this.color;
  }

}
