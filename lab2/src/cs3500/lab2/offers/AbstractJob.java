package cs3500.lab2.offers;

import cs3500.lab2.skills.Skill;
import java.util.List;

/**
 * Represents an abstract job offer. It includes key details like requirements, money offered,
 * and maximum hours per week. This class handles salary calculations and requirement checks.
 */
public abstract class AbstractJob implements Offer {

  private final List<Skill> requirements;
  private final int money;
  private final int maxHours;

  /**
   * Constructs a job offer with given requirements, salary, and max hours.
   *
   * @param requirements the list of skills needed for the job
   * @param money the salary or money offered
   * @param maxHours the maximum number of working hours per week
   */
  AbstractJob(List<Skill> requirements, int money, int maxHours) {
    this.requirements = requirements;
    this.money = money;
    this.maxHours = maxHours;
  }

  /**
   * Calculates the annual salary based on the money offered and max hours.
   * Returns 0 if neither is provided.
   *
   * @return the calculated annual salary
   */
  @Override
  public int calculateSalary() {
    if (money == 0 && maxHours == 0){
      return 0;
    }
    else if (money > 0 && maxHours > 0){
      return money * maxHours * 52;
    }
    else{
      return money;
    }
  }

  /**
   * Checks if an application satisfies the job requirements.
   *
   * @param application the list of skills from the applicant
   * @return true if the application meets all requirements, false otherwise
   */
  @Override
  public boolean satisfiesRequirements(List<Skill> application) {
    for (Skill req : requirements) {
      if (!application.stream().anyMatch((appSkill) -> appSkill.satisfiesReq(req))) {
        return false;
      }
    }
    return true;
  }
}
