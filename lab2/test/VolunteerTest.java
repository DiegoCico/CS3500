import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import cs3500.lab2.offers.Offer;
import cs3500.lab2.offers.Volunteer;

public class VolunteerTest extends AbstractOfferTest {

  /**
   * Tests that a valid Volunteer object can be created successfully with correct salary calculation.
   * Since volunteer positions typically don't have a salary, the expected salary is 0.
   */
  @Test
  public void testValidVolunteerConstruction() {
    Volunteer volunteer = new Volunteer("Big Sibling", new ArrayList<>());
    assertSalary(volunteer, 0);
  }

  /**
   * Tests that constructing a Volunteer offer with invalid input throws appropriate exceptions.
   * NullPointerException is expected for null fields, such as the job title or skills list.
   */
  @Test
  public void testInvalidVolunteerConstruction() {
    Assert.assertThrows(NullPointerException.class,
            () -> new Volunteer(null, new ArrayList<>()));
    Assert.assertThrows(NullPointerException.class,
            () -> new Volunteer("Big Sibling", null));
  }

  /**
   * Tests that the Volunteer offer correctly satisfies the required skills.
   * Uses default skills and checks if the Volunteer position meets the qualification criteria.
   */
  @Test
  public void testSatisfiesVolunteerRequirements() {
    Volunteer volunteer = createDefaultVolunteer();
    assertOfferSatisfiesRequirements(volunteer, getDefaultSkills(), new ArrayList<>());
  }
}
