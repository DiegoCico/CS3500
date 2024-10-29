
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;


public class CardModelTest {

  @Test
  public void testCardCreation() {
    CardModel card = new CardModel("TestCard", 1, 9, 3, 7, COLOR.RED);
    assertEquals(1, card.getNorth());
    assertEquals(9, card.getSouth());
    assertEquals(3, card.getEast());
    assertEquals(7, card.getWest());
    assertEquals(COLOR.RED, card.getColor());
    assertEquals("TestCard: 1 9 3 7 RED", card.toString());
  }

  @Test
  public void testSwitchColor() {
    CardModel card = new CardModel("TestCard", 1, 9, 3, 7, COLOR.RED);
    card.switchColor(COLOR.BLUE);
    assertEquals(COLOR.BLUE, card.getColor());
  }

  @Test
  public void testEqualsAndHashCode() {
    CardModel card1 = new CardModel("CardA", 1, 9, 3, 7, COLOR.RED);
    CardModel card2 = new CardModel("CardA", 1, 9, 3, 7, COLOR.RED);
    CardModel card3 = new CardModel("CardB", 2, 8, 4, 6, COLOR.BLUE);

    assertEquals(card1, card2);
    assertNotEquals(card1, card3);
    assertEquals(card1.hashCode(), card2.hashCode());
    assertNotEquals(card1.hashCode(), card3.hashCode());
  }

  @Test
  public void testInvalidNumberThrowsException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new CardModel("InvalidCard", 11, 5, 2, 7, COLOR.valueOf("GREEN"));
    });
    assertEquals("No enum constant COLOR.GREEN", exception.getMessage());
  }

  @Test
  public void testToString() {
    CardModel card = new CardModel("TestCard", 1, 9, 3, 7, COLOR.RED);
    assertEquals("TestCard: 1 9 3 7 RED", card.toString());
  }

}
