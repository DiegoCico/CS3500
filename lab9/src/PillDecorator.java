

/**
 * decorator for pill.
 */
public class PillDecorator implements PillCounter {
  private PillCounter counter;

  public PillDecorator() {
    counter = new LoggingPillCounter();
  }

  /**
   * Add the specific number of pills to this counter. This method
   * is general enough to work with machines with different pill-filling
   * capacities.
   *
   * @param count for the pills.
   */
  @Override
  public void addPill(int count) {
    counter.addPill(count);
  }

  /**
   * Remove a pill from this counter. This method is called in case
   * a malfunction in the hardware is detected and it dispenses too
   * many pills. Only one pill may be removed at a time.
   */
  @Override
  public void removePill() {
    counter.removePill();
  }

  /**
   * Reset the counter to 0.
   */
  @Override
  public void reset() {
    counter.reset();
  }

  /**
   * Return how many pills have been counted so far.
   *
   * @return
   */
  @Override
  public int getPillCount() {
    return counter.getPillCount();
  }
}
