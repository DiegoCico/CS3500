import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class PlayerModelTest {

  private PlayerModel player;
  private List<Card> initialHand;
  private Card card1;
  private Card card2;

  @Before
  public void setUp() {
    card1 = new CardModel("Ace", 1, 1, 1, 1, COLOR.RED);
    card2 = new CardModel("King", 9,9,9,9, COLOR.BLUE);
    initialHand = new ArrayList<>(List.of(card1, card2));
    player = new PlayerModel("Player1", COLOR.RED, initialHand);
  }

  @Test
  public void testConstructor_withValidParameters() {
    assertEquals(2, player.handSize());
    assertEquals("RED", player.getName());
    assertEquals(COLOR.RED, player.getColor());
  }

  @Test
  public void testConstructor_withNullHand() {
    assertThrows(IllegalStateException.class, () -> new PlayerModel("Player1", COLOR.RED, null));
  }

  @Test
  public void testConstructor_withEmptyHand() {
    assertThrows(IllegalStateException.class, () -> new PlayerModel("Player1", COLOR.RED, new ArrayList<>()));
  }

  @Test
  public void testConstructor_withNullColor() {
    assertThrows(IllegalStateException.class, () -> new PlayerModel("Player1", null, initialHand));
  }

  @Test
  public void testHandSize() {
    assertEquals(2, player.handSize());
  }

  @Test
  public void testGetHand() {
    List<Card> hand = player.getHand();
    assertEquals(2, hand.size());
    assertTrue(hand.contains(card1));
    assertTrue(hand.contains(card2));
  }

  @Test
  public void testRemoveCard_validIndex() {
    player.removeCard(1);
    assertEquals(1, player.handSize());
    assertFalse(player.getHand().contains(card1));
  }

  @Test
  public void testRemoveCard_invalidNegativeIndex() {
    assertThrows(IllegalArgumentException.class, () -> player.removeCard(-1));
  }

  @Test
  public void testRemoveCard_indexOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> player.removeCard(3));
  }

  @Test
  public void testAddCard() {
    Card card3 = new CardModel("Queen", 5,5,5,5, COLOR.BLUE);
    player.addCard(card3);
    assertEquals(3, player.handSize());
    assertTrue(player.getHand().contains(card3));
  }

  @Test
  public void testGetCard_validIndex() {
    Card removedCard = player.getCard(1);
    assertEquals(card1, removedCard);
    assertEquals(1, player.handSize());
  }

  @Test
  public void testGetCard_invalidNegativeIndex() {
    assertThrows(IllegalArgumentException.class, () -> player.getCard(-1));
  }

  @Test
  public void testGetCard_indexOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> player.getCard(3));
  }

  @Test
  public void testGetName() {
    assertEquals("RED", player.getName());
  }

  @Test
  public void testGetColor() {
    assertEquals(COLOR.RED, player.getColor());
  }
}
