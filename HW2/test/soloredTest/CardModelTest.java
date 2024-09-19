// File: CardModelTest.java
package cs3500.solored.model;

import org.junit.Test;
import cs3500.solored.model.hw02.CardModel;

import static org.junit.Assert.*;

/**
 * Test suite for the CardModel class.
 */
public class CardModelTest {

  @Test
  public void testConstructor() {
    CardModel card = new CardModel("R", 5);
    assertEquals("Card color should be initialized correctly.", "R", card.getColor());
    assertEquals("Card number should be initialized correctly.", 5, card.getNumber());
  }

  @Test
  public void testGetColor() {
    CardModel card = new CardModel("B", 3);
    assertEquals("getColor should return the correct color.", "B", card.getColor());
  }

  @Test
  public void testGetNumber() {
    CardModel card = new CardModel("G", 7);
    assertEquals("getNumber should return the correct number.", 7, card.getNumber());
  }

  @Test
  public void testToString() {
    CardModel card = new CardModel("I", 2);
    assertEquals("toString should return color concatenated with number.", "I2", card.toString());
  }
}
