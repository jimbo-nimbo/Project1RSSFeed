package core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoreTest {

  Core core;
  @Before
  public void setUp() throws Exception {
    core = Core.getInstance();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void getInstance() {}

  @Test
  public void logToFile() {
    core.logToFile("testLog");
  }

}