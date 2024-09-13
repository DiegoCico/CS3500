import cs3500.lab2.offers.*;
import cs3500.lab2.skills.*;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOfferTest {

  /**
   * Creates a default list of skills commonly required for offers.
   *
   * @return a list containing an "OOD" skill and 2 years of experience.
   */
  public List<Skill> getDefaultSkills() {
    return List.of(new Ability("OOD"), new Years(2));
  }

  /**
   * Creates a default FullTimeJob offer.
   *
   * @return a FullTimeJob with the title "Software Dev", a salary of 120000, and default skills.
   */
  public FullTimeJob createDefaultFullTimeJob() {
    return new FullTimeJob("Software Dev", 120000, getDefaultSkills());
  }

  /**
   * Creates a default Coop offer.
   *
   * @return a Coop position with the title "Startup Co-op", $30 hourly rate, 40 hours/week, and default skills.
   */
  public Coop createDefaultCoop() {
    return new Coop("Startup Co-op", 30, 40, getDefaultSkills());
  }

  /**
   * Creates a default Volunteer offer.
   *
   * @return a Volunteer position with the title "Big Sibling" and default skills.
   */
  public Volunteer createDefaultVolunteer() {
    return new Volunteer("Big Sibling", getDefaultSkills());
  }

  /**
   * Runs basic assertions on an offer, checking if it satisfies the provided qualified and unqualified skills.
   *
   * @param offer the offer being tested.
   * @param qualified the list of skills that should satisfy the offer's requirements.
   * @param unqualified the list of skills that should not satisfy the offer's requirements.
   */
  public void assertOfferSatisfiesRequirements(Offer offer, List<Skill> qualified, List<Skill> unqualified) {
    Assert.assertTrue(offer.satisfiesRequirements(qualified));
    Assert.assertFalse(offer.satisfiesRequirements(unqualified));
  }

  /**
   * Asserts that the salary calculation of the offer matches the expected value.
   *
   * @param offer the offer being tested.
   * @param expectedSalary the expected salary value.
   */
  public void assertSalary(Offer offer, int expectedSalary) {
    Assert.assertEquals(expectedSalary, offer.calculateSalary());
  }
}
