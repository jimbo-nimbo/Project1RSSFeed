package database;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DataBaseConfigTest {
  @Test
  public void test() throws IOException
  {
      DataBaseConfig configModel = new DataBaseConfig("config.properties");

    assertEquals("127.0.0.1", configModel.getHostIP());
    assertEquals("3306", configModel.getHostPort());
    assertEquals("nimboTest", configModel.getDbName());
    assertEquals("nimbo", configModel.getUsername());
    assertEquals("nimbo", configModel.getPassword());
  }
}
