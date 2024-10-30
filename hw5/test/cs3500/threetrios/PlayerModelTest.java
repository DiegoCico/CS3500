package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.player.PlayerModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * cs3500.threetrios.PlayerModelTest meant for testing purposes.
 */
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
  public void testConstructorWithValidParameters() {
    assertEquals(2, player.handSize());
    assertEquals("Player1", player.getName());
    assertEquals(COLOR.RED, player.getColor());
  }

  @Test
  public void testConstructorWithNullHand() {
    assertThrows(IllegalStateException.class, () -> new PlayerModel("Player1", COLOR.RED, null));
  }

  @Test
  public void testConstructorWithEmptyHand() {
    assertThrows(IllegalStateException.class,
        () -> new PlayerModel("Player1", COLOR.RED, new ArrayList<>()));
  }

  @Test
  public void testConstructorWithNullColor() {
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
  public void testRemoveCardValidIndex() {
    player.removeCard(0);
    assertEquals(1, player.handSize());
    assertFalse(player.getHand().contains(card1));
  }

  @Test
  public void testRemoveCardInvalidNegativeIndex() {
    assertThrows(IllegalArgumentException.class, () -> player.removeCard(-1));
  }

  @Test
  public void testRemoveCardIndexOutOfBounds() {
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
  public void testGetCardValidIndex() {
    Card removedCard = player.getCard(1);
    assertEquals(card2, removedCard);
    assertEquals(2, player.handSize());
  }

  @Test
  public void testGetCardInvalidNegativeIndex() {
    assertThrows(IllegalArgumentException.class, () -> player.getCard(-1));
  }

  @Test
  public void testGetCardIndexOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> player.getCard(3));
  }

  @Test
  public void testGetName() {
    assertEquals("Player1", player.getName());
  }

  @Test
  public void testGetColor() {
    assertEquals(COLOR.RED, player.getColor());
  }

  @Test
  public void testAddCardIncreasesHandSize() {
    Card newCard = new CardModel("Jack", 6, 6, 6, 6, COLOR.RED);
    int initialSize = player.handSize();
    player.addCard(newCard);
    assertEquals(initialSize + 1, player.handSize());
  }

  @Test
  public void testRemoveCardDecreasesHandSize() {
    int initialSize = player.handSize();
    player.removeCard(0);
    assertEquals(initialSize - 1, player.handSize());
  }

  @Test
  public void testGetHandReturnsImmutableList() {
    List<Card> handCopy = player.getHand();
    handCopy.add(new CardModel("Ten", 5, 5, 5, 5, COLOR.RED));
    assertEquals(2, player.handSize());
  }

  @Test
  public void testMultipleAddAndRemoveOperations() {
    player.addCard(new CardModel("Red1", 7, 7, 7, 7, COLOR.RED));
    player.removeCard(1);
    player.addCard(new CardModel("Blue1", 8, 8, 8, 8, COLOR.BLUE));
    assertEquals(3, player.handSize());
    assertTrue(player.getHand().contains(card1));
    assertFalse(player.getHand().contains(card2));
  }


  @Test
  public void testGetCardAfterRemoval() {
    player.removeCard(0);
    assertEquals(card2, player.getCard(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardWithNegativeIndex() {
    player.removeCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardWithOutOfBoundsIndex() {
    player.removeCard(player.handSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardWithNegativeIndex() {
    player.getCard(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardWithOutOfBoundsIndex() {
    player.getCard(player.handSize());
  }

  @Test(expected = IllegalStateException.class)
  public void testAddNullCardToHand() {
    player.addCard(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorWithNullName() {
    new PlayerModel(null, COLOR.RED, List.of(card1, card2));
  }

}
