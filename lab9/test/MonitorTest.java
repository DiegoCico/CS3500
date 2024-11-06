import org.junit.Assert;
import org.junit.Test;

/**
 * testing the monitor.
 */
public class MonitorTest {

  @Test
  public void addCount() {
    Monitor monitor = new Monitor();

    monitor.addPill(1);
    Assert.assertEquals(1,monitor.getPillCount());
  }

  @Test
  public void addCount2() {
    Monitor monitor = new Monitor();

    monitor.addPill(10);
    Assert.assertEquals(10,monitor.getPillCount());
  }

  @Test
  public void deleteCount() {
    Monitor monitor = new Monitor();
    monitor.addPill(1);
    monitor.addPill(2);
    monitor.addPill(3);
    monitor.removePill();
    Assert.assertEquals(5,monitor.getPillCount());
  }
}
