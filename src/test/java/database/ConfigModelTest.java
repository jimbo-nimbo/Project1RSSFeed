package database;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigModelTest
{
    @Test
    public void test()
    {
        ConfigModel configModel = new ConfigModel("src/main/resources/config.properties");

        assertEquals("127.0.0.1", configModel.getHostIP());
        assertEquals("3306", configModel.getHostPort());
        assertEquals("nimbo", configModel.getDbName());
        assertEquals("nimbo", configModel.getUsername());
        assertEquals("nimbo", configModel.getPassword());
    }
}