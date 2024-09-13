package cs3500.lab2.offers;

import java.util.List;
import java.util.Objects;
import cs3500.lab2.skills.Skill;

/**
 * Represents a full-time job offer. It includes a job description, yearly salary,
 * and skill requirements. Inherits from the abstract job class.
 */
public class FullTimeJob extends AbstractJob {

  private String jobDescription;

  /**
   * Constructs a FullTimeJob offer with the description, yearly salary,
   * and skill requirements.
   *
   * @param description the job description
   * @param yearlySalary the salary offered per year
   * @param reqs the list of skills required for the job
   * @throws IllegalArgumentException if the salary is negative
   * @throws NullPointerException if description or reqs are null
   */
  public FullTimeJob(String description, int yearlySalary, List<Skill> reqs) {
    super(Objects.requireNonNull(reqs), yearlySalary, 0);
    this.jobDescription = Objects.requireNonNull(description);
    if (yearlySalary < 0) {
      throw new IllegalArgumentException("Salary cannot be negative");
    }
  }
}
