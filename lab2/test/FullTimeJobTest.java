import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import cs3500.lab2.offers.FullTimeJob;
import cs3500.lab2.offers.Offer;

public class FullTimeJobTest extends AbstractOfferTest {

  /**
   * Tests that a valid FullTimeJob object can be created successfully with correct salary calculation.
   * The FullTimeJob is created with a salary of $1000.
   */
  @Test
  public void testValidFullTimeJobConstruction() {
    FullTimeJob fullTime = new FullTimeJob("Software Dev", 1000, new ArrayList<>());
    assertSalary(fullTime, 1000);
  }

  /**
   * Tests that constructing a FullTimeJob offer with invalid input throws appropriate exceptions.
   * NullPointerException is expected for null fields, and IllegalArgumentException for invalid values.
   */
  @Test
  public void testInvalidFullTimeJobConstruction() {
    Assert.assertThrows(NullPointerException.class,
            () -> new FullTimeJob(null, 1000, new ArrayList<>()));
    Assert.assertThrows(IllegalArgumentException.class,
            () -> new FullTimeJob("Software Dev", -1000, new ArrayList<>()));
    Assert.assertThrows(NullPointerException.class,
            () -> new FullTimeJob("Software Dev", 1000, null));
  }

  /**
   * Tests that the FullTimeJob offer correctly satisfies the required skills.
   * Uses default skills and checks if the FullTimeJob position meets the qualification criteria.
   */
  @Test
  public void testSatisfiesFullTimeJobRequirements() {
    FullTimeJob fullTime = createDefaultFullTimeJob();
    assertOfferSatisfiesRequirements(fullTime, getDefaultSkills(), new ArrayList<>());
  }
}
