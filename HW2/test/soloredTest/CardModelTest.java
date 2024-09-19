// File: CardModelTest.java
package soloredTest;

import org.junit.Assert;
import org.junit.Test;
import cs3500.solored.model.hw02.CardModel;

/**
 * Test suite for the CardModel class.
 */
public class CardModelTest {

  @Test
  public void testConstructor() {
    CardModel card = new CardModel("R", 5);
    Assert.assertEquals("Card color should be initialized correctly.", "R", card.getColor());
    Assert.assertEquals("Card number should be initialized correctly.", 5, card.getNumber());
  }

  @Test
  public void testGetColor() {
    CardModel card = new CardModel("B", 3);
    Assert.assertEquals("getColor should return the correct color.", "B", card.getColor());
  }

  @Test
  public void testGetNumber() {
    CardModel card = new CardModel("G", 7);
    Assert.assertEquals("getNumber should return the correct number.", 7, card.getNumber());
  }

  @Test
  public void testToString() {
    CardModel card = new CardModel("I", 2);
    Assert.assertEquals("toString should return color concatenated with number.", "I2", card.toString());
  }
}
