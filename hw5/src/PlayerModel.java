import java.util.List;

public class PlayerModel implements Player {

  private List<Card> hand;
  private COLOR color;
  private final String name;

  public PlayerModel(String name, COLOR color, List<Card> hand) {
    if (hand == null || hand.size() == 0) {
      throw new IllegalStateException("hand cannot be null or empty");
    }
    if (color == null) {
      throw new IllegalStateException("name cannot be null or empty");
    }
    this.name = name;
    this.color = color;
    this.hand = hand;
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
    return List.copyOf(hand);
  }

  @Override
  public void removeCard(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("index out of bounds");
    }
    hand.remove(index - 1);
  }

  @Override
  public void addCard(Card card) {
    hand.add(card);
  }

  @Override
  public Card getCard(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("index out of bounds");
    }
    return hand.remove(index - 1);
  }

  public String getName(){
    return this.color.name();
  }

  @Override
  public COLOR getColor(){
    return this.color;
  }

}
