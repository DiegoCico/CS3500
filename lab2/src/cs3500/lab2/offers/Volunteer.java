package cs3500.lab2.offers;

import java.util.List;
import java.util.Objects;
import cs3500.lab2.skills.Skill;

/**
 * Represents a volunteer job offer. It includes a job description and skill requirements.
 * This job does not offer any salary or hours.
 */
public class Volunteer extends AbstractJob {

  public final String jobDescription;

  /**
   * Constructs a Volunteer job offer with the description and skill requirements.
   * The salary and hours are set to 0 since it's a volunteer role.
   *
   * @param description the job description
   * @param reqs the list of skills required for the job
   * @throws NullPointerException if description or reqs are null
   */
  public Volunteer(String description, List<Skill> reqs) {
    super(Objects.requireNonNull(reqs), 0, 0);
    this.jobDescription = Objects.requireNonNull(description);
  }
}
