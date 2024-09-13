package cs3500.lab2.offers;

import java.util.List;
import java.util.Objects;
import cs3500.lab2.skills.Skill;

/**
 * Represents a co-op job offer. It includes a job description, hourly rate, max hours,
 * and skill requirements. Extends the abstract job class.
 */
public class Coop extends AbstractJob {

  private final String jobDescription;

  /**
   * Constructs a Coop job offer with the description, hourly rate, hours, and skill requirements.
   *
   * @param description the job description
   * @param rate the hourly rate
   * @param hours the maximum number of working hours per week
   * @param reqs the list of skills required for the job
   * @throws IllegalArgumentException if rate or hours are negative
   * @throws NullPointerException if description or reqs are null
   */
  public Coop(String description, int rate, int hours, List<Skill> reqs) {
    super(Objects.requireNonNull(reqs), rate, hours);
    this.jobDescription = Objects.requireNonNull(description);
    if (rate < 0) {
      throw new IllegalArgumentException("Hourly rate cannot be negative.");
    }
    if (hours < 0) {
      throw new IllegalArgumentException("Max hours cannot be negative");
    }
  }
}
