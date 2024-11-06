/**
 * monitoring how many pills added.
 */
public class Monitor extends PillDecorator {

  private int c;

  /**
   * constructor for monitor.
   */
  public Monitor() {
    c = 0;
  }

  /**
   * adding the pills and counting them.
   * @param count of the pills.
   */
  public void addPill(int count) {
    super.addPill(count);
    c += count;
  }

  /**
   * Reset the counter to 0.
   */
  public void reset() {
    super.reset();
  }

  /**
   * Remove a pill from this counter. This method is called in case
   * a malfunction in the hardware is detected and it dispenses too
   * many pills. Only one pill may be removed at a time.
   */
  public void removePill() {
    super.removePill();
    c -= 1;
  }

  /**
   * getting the counter.
   * @return the counter.
   */
  public int getCounter() {
    return c;
  }

}

