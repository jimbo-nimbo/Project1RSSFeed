package database;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataBaseConfigTest
{
    @Test
    public void test()
    {
        DataBaseConfig configModel = new DataBaseConfig("src/main/resources/config.properties");

        assertEquals("127.0.0.1", configModel.getHostIP());
        assertEquals("3306", configModel.getHostPort());
        assertEquals("nimbo", configModel.getDbName());
        assertEquals("nimbo", configModel.getUsername());
        assertEquals("nimbo", configModel.getPassword());
    }
}