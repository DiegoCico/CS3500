import cs3500.lab2.offers.Coop;
import cs3500.lab2.offers.Offer;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CoopTest extends AbstractOfferTest {

  /**
   * Tests that a valid Coop object can be created successfully with correct salary calculation.
   * The Coop position is created with an hourly rate of $30 and 40 hours/week.
   */
  @Test
  public void testValidCoopConstruction() {
    Coop coop = new Coop("Startup Co-op", 30, 40, new ArrayList<>());
    assertSalary(coop, 30 * 40 * 52);
  }

  /**
   * Tests that constructing a Coop offer with invalid input throws appropriate exceptions.
   * NullPointerException is expected for null fields, and IllegalArgumentException for invalid values.
   */
  @Test
  public void testInvalidCoopConstruction() {
    Assert.assertThrows(NullPointerException.class,
            () -> new Coop(null, 10, 7, new ArrayList<>()));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> new Coop("Startup co-op", -5, 7, new ArrayList<>()));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> new Coop("Startup co-op", 10, -1, new ArrayList<>()));
    Assert.assertThrows(NullPointerException.class,
            () -> new Coop("Startup co-op", 10, 7, null));
  }

  /**
   * Tests that the Coop offer correctly satisfies the required skills.
   * Uses default skills and checks if the Coop position meets the qualification criteria.
   */
  @Test
  public void testSatisfiesCoopRequirements() {
    Coop coop = createDefaultCoop();
    assertOfferSatisfiesRequirements(coop, getDefaultSkills(), new ArrayList<>());
  }
}
