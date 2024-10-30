
import org.junit.Test;

import cs3500.threetrios.COLOR;
import cs3500.threetrios.CardModel;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;

/**
 * CardModelTest class for testing purposes.
 */
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
    assertEquals("No enum constant cs3500.threetrios.COLOR.GREEN", exception.getMessage());
  }

  @Test
  public void testToString() {
    CardModel card = new CardModel("TestCard", 1, 9, 3, 7, COLOR.RED);
    assertEquals("TestCard: 1 9 3 7 RED", card.toString());
  }


  @Test
  public void testBoundaryAttackValues() {
    CardModel cardMin = new CardModel("TestCard", 1, 1, 1, 1, COLOR.RED);
    CardModel cardMax = new CardModel("TestCard", 10, 10, 10, 10, COLOR.BLUE);
    assertEquals(1, cardMin.getNorth());
    assertEquals(10, cardMax.getSouth());

  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalAttackValues() {
    new CardModel("TestCard", -1, 9, -3, 7, COLOR.RED);
  }

  @Test(expected = IllegalStateException.class)
  public void testOutOfBounds() {
    new CardModel("TooBig", 17, 17, 17, 17, COLOR.BLUE);
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyName() {
    new CardModel("", 17, 17, 17, 17, COLOR.BLUE);
  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalNameObject() {
    new CardModel(null, -1, 9, -3, 7, COLOR.RED);
  }

  @Test
  public void testSwitchColorBackToOriginal() {
    CardModel card = new CardModel("SwitchColorCard", 1, 2, 3, 4, COLOR.RED);
    card.switchColor(COLOR.BLUE);
    assertEquals(COLOR.BLUE, card.getColor());
    card.switchColor(COLOR.RED);
    assertEquals(COLOR.RED, card.getColor());
  }


  @Test
  public void testImmutableAttackValues() {
    CardModel card = new CardModel("ImmutableCard", 2, 3, 4, 5, COLOR.RED);
    assertEquals(2, card.getNorth());
    assertEquals(3, card.getSouth());
    assertEquals(4, card.getEast());
    assertEquals(5, card.getWest());
  }

  @Test
  public void testMaxLengthName() {
    String maxLengthName = "A".repeat(50);
    CardModel card = new CardModel(maxLengthName, 5, 5, 5, 5, COLOR.RED);
    assertEquals(maxLengthName, card.toString().split(":")[0]);
  }

}
