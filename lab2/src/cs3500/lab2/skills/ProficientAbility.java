package cs3500.lab2.skills;

import java.util.Objects;

public class ProficientAbility extends Ability {

  /**
   * Levels of proficiency for an ability.
   */
  public enum ProficiencyLevel {
    NOVICE, INTERMEDIATE, EXPERT;
  }

  private final ProficiencyLevel proficiency;

  /**
   * Constructs a ProficientAbility with the ability and proficiency level.
   *
   * @param ability the name of the ability
   * @param proficiency the proficiency level for the ability
   */
  public ProficientAbility(String ability, ProficiencyLevel proficiency) {
    super(ability);
    Objects.requireNonNull(proficiency);
    this.proficiency = proficiency;
  }

  /**
   * Checks if this ability satisfies the given requirement.
   *
   * @param requirement the skill requirement to check against
   * @return true if the requirement is satisfied, false otherwise
   */
  @Override
  public boolean satisfiesReq(Skill requirement) {
    if (!(requirement instanceof AbstractSkill)) {
      return false;
    }
    AbstractSkill abs = (AbstractSkill) requirement;

    if (requirement instanceof Ability && !(requirement instanceof ProficientAbility)) {
      return this.ability.equals(((Ability) requirement).ability);
    }

    return abs.isSatisfiedBy(this);
  }

  /**
   * Checks if this object is equal to another ProficientAbility.
   *
   * @param obj the object to compare to
   * @return true if they are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ProficientAbility)) {
      return false;
    }
    ProficientAbility other = (ProficientAbility) obj;

    if(ability.equals(other.ability) && proficiency.equals(other.proficiency))
      return true;
    else
      return false;
  }
}
