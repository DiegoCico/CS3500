package cs3500.solored.hw2;

import org.junit.Assert;
import org.junit.Test;
import cs3500.solored.model.hw02.CardModel;

/**
 * Test suite for the CardModel class, covering its constructor, getters, and toString method.
 */
public class CardModelTest {

  /**
   * Tests the constructor of the CardModel to ensure it initializes the color and number correctly.
   */
  @Test
  public void testConstructor() {
    CardModel card = new CardModel("R", 5);
    Assert.assertEquals("Card color should be initialized correctly.", "R", card.getColor());
    Assert.assertEquals("Card number should be initialized correctly.", 5, card.getNumber());
  }

  /**
   * Tests the getColor method to ensure it returns the correct color.
   */
  @Test
  public void testGetColor() {
    CardModel card = new CardModel("B", 3);
    Assert.assertEquals("getColor should return the correct color.", "B", card.getColor());
  }

  /**
   * Tests the getNumber method to ensure it returns the correct number.
   */
  @Test
  public void testGetNumber() {
    CardModel card = new CardModel("G", 7);
    Assert.assertEquals("getNumber should return the correct number.", 7, card.getNumber());
  }

  /**
   * Tests the toString method to ensure it returns the color concatenated with the number.
   */
  @Test
  public void testToString() {
    CardModel card = new CardModel("I", 2);
    Assert.assertEquals("return color concatenated with number.", "I2", card.toString());
  }
}
