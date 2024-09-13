import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.lab2.skills.Ability;
import cs3500.lab2.skills.Skill;
import cs3500.lab2.skills.Years;
import cs3500.lab2.skills.ProficientAbility;
import cs3500.lab2.skills.ProficientAbility.ProficiencyLevel;

public class SkillsTest {

  private ProficientAbility noviceProgramming;
  private ProficientAbility intermediateProgramming;
  private ProficientAbility expertProgramming;
  private ProficientAbility noviceTeaching;
  private ProficientAbility expertTeaching;


  @Before
  public void init() {
    noviceProgramming = new ProficientAbility("programming", ProficiencyLevel.NOVICE);
    intermediateProgramming = new ProficientAbility("programming", ProficiencyLevel.INTERMEDIATE);
    expertProgramming = new ProficientAbility("programming", ProficiencyLevel.EXPERT);
    noviceTeaching = new ProficientAbility("teaching", ProficiencyLevel.NOVICE);
    expertTeaching = new ProficientAbility("teaching", ProficiencyLevel.EXPERT);
  }

  @Test
  public void testYearRequirements() {
    Skill experienced = new Years(4);
    Skill inexperienced = new Years(0);
    Skill exactExperience = new Years(4);
    Skill requiredExperience = new Years(3);

    Assert.assertFalse(inexperienced.satisfiesReq(requiredExperience));
    Assert.assertTrue(experienced.satisfiesReq(requiredExperience));
    Assert.assertTrue(exactExperience.satisfiesReq(requiredExperience));
  }

  @Test
  public void testAbilityRequirements() {
    Skill program = new Ability("program");
    Skill teach = new Ability("teach");
    Skill programReq = new Ability("program");

    Assert.assertTrue(program.satisfiesReq(program));
    Assert.assertFalse(teach.satisfiesReq(program));
    Assert.assertTrue(program.satisfiesReq(programReq));
  }

  @Test
  public void testDifferentSkillRequirements() {
    Skill requiredExperience = new Years(3);
    Skill program = new Ability("program");
    Assert.assertFalse(program.satisfiesReq(requiredExperience));
    Assert.assertFalse(requiredExperience.satisfiesReq(program));
  }

  @Test
  public void testSkillEquality() {
    Skill programAbility = new Ability("program");
    Skill oodAbility = new Ability("OOD");
    Skill oneYear = new Years(1);
    Skill anotherOneYear = new Years(1);
    Skill twoYears = new Years(2);

    Assert.assertTrue(programAbility.equals(programAbility));
    Assert.assertTrue(oneYear.equals(anotherOneYear));

    Assert.assertFalse(programAbility.equals(oodAbility));
    Assert.assertFalse(oodAbility.equals(programAbility));
    Assert.assertFalse(oneYear.equals(twoYears));
    Assert.assertFalse(twoYears.equals(oneYear));

    Assert.assertFalse(programAbility.equals(oneYear));
    Assert.assertFalse(oneYear.equals(programAbility));
  }

  @Test
  public void testSatisfiesReqSameAbilityAndProficiency() {
    Assert.assertTrue(noviceProgramming.satisfiesReq(noviceProgramming));
    Assert.assertTrue(expertProgramming.satisfiesReq(expertProgramming));
  }

  @Test
  public void testSatisfiesReqDifferentProficienciesSameAbility() {
    Assert.assertTrue(noviceProgramming.satisfiesReq(expertProgramming));
    Assert.assertTrue(expertProgramming.satisfiesReq(noviceProgramming));
  }

  @Test
  public void testSatisfiesReqDifferentAbilities() {
    Assert.assertFalse(noviceProgramming.satisfiesReq(noviceTeaching));
    Assert.assertFalse(expertProgramming.satisfiesReq(expertTeaching));
  }

  @Test
  public void testSatisfiesReqNonProficientAbility() {
    Skill basicProgramming = new Ability("programming");
    Assert.assertTrue(noviceProgramming.satisfiesReq(basicProgramming));
    Assert.assertTrue(basicProgramming.satisfiesReq(noviceProgramming));
  }

  @Test
  public void testEquals() {
    ProficientAbility anotherNoviceProgramming = new ProficientAbility("programming", ProficiencyLevel.NOVICE);
    ProficientAbility anotherExpertTeaching = new ProficientAbility("teaching", ProficiencyLevel.EXPERT);

    Assert.assertTrue(noviceProgramming.equals(anotherNoviceProgramming));
    Assert.assertTrue(expertTeaching.equals(anotherExpertTeaching));
    Assert.assertFalse(noviceProgramming.equals(expertProgramming));
    Assert.assertFalse(noviceProgramming.equals(noviceTeaching));
  }

  @Test
  public void testNotEqualsDifferentTypes() {
    Skill basicProgramming = new Ability("programming");

    Assert.assertFalse(noviceProgramming.equals(basicProgramming));
    Assert.assertTrue(basicProgramming.equals(noviceProgramming));
  }
}
