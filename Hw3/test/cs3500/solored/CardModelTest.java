package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;
import cs3500.solored.model.hw02.CardModel;

/**
 * Test suite for the CardModel class, covering its constructor,
 * getters, toString, equals, and hashCode methods.
 */
public class CardModelTest {

  /**
   * Tests the constructor to ensure correct initialization of color and number.
   */
  @Test
  public void testConstructor() {
    CardModel card = new CardModel("R", 5);
    Assert.assertEquals("Incorrect color.", "R", card.getColor());
    Assert.assertEquals("Incorrect number.", 5, card.getNumber());
  }

  /**
   * Tests the getColor method to verify it returns the correct color.
   */
  @Test
  public void testGetColor() {
    CardModel card = new CardModel("B", 3);
    Assert.assertEquals("Wrong color.", "B", card.getColor());
  }

  /**
   * Tests the getNumber method to verify it returns the correct number.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testGetNumber() {
    CardModel card = new CardModel("G", 7);
  }

  /**
   * Tests the toString method to verify correct concatenation of color and number.
   */
  @Test
  public void testToString() {
    CardModel card = new CardModel("I", 2);
    Assert.assertEquals("Wrong toString.", "I2", card.toString());
  }

  /**
   * Tests equals to verify two cards with the same color and number are equal.
   */
  @Test
  public void testEquals() {
    CardModel card1 = new CardModel("R", 7);
    CardModel card2 = new CardModel("R", 7);
    Assert.assertTrue("Should be equal.", card1.equals(card2));
  }

  /**
   * Tests equals to verify two cards with different colors are not equal.
   */
  @Test
  public void testEqualsDifferentColor() {
    CardModel card1 = new CardModel("R", 7);
    CardModel card2 = new CardModel("B", 7);
    Assert.assertFalse("Should not be equal.", card1.equals(card2));
  }

  /**
   * Tests equals to verify two cards with different numbers are not equal.
   */
  @Test
  public void testEqualsDifferentNumber() {
    CardModel card1 = new CardModel("R", 7);
    CardModel card2 = new CardModel("R", 5);
    Assert.assertFalse("Should not be equal.", card1.equals(card2));
  }

  /**
   * Tests equals to verify it returns false when comparing with a different class.
   */
  @Test
  public void testEqualsDifferentClass() {
    CardModel card = new CardModel("R", 7);
    String other = "NotACard";
    Assert.assertFalse("Should not equal different class.", card.equals(other));
  }

  /**
   * Tests hashCode to verify two identical cards have the same hash code.
   */
  @Test
  public void testHashCode() {
    CardModel card1 = new CardModel("R", 7);
    CardModel card2 = new CardModel("R", 7);
    Assert.assertEquals("Hash codes should match.", card1.hashCode(), card2.hashCode());
  }

  /**
   * Tests hashCode to verify two cards with different colors have different hash codes.
   */
  @Test
  public void testHashCodeDifferentColor() {
    CardModel card1 = new CardModel("R", 7);
    CardModel card2 = new CardModel("B", 7);
    Assert.assertNotEquals("Hash codes should differ.", card1.hashCode(), card2.hashCode());
  }

  /**
   * Tests hashCode to verify two cards with different numbers have different hash codes.
   */
  @Test
  public void testHashCodeDifferentNumber() {
    CardModel card1 = new CardModel("R", 7);
    CardModel card2 = new CardModel("R", 5);
    Assert.assertNotEquals("Hash codes should differ.", card1.hashCode(), card2.hashCode());
  }

  /**
   * Tests edge case: card with an empty color.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testCardWithEmptyColor() {
    CardModel card = new CardModel("", 3);
  }

  /**
   * Tests edge case: card with a negative number.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testCardWithNegativeNumber() {
    CardModel card = new CardModel("G", -5);
  }
}
