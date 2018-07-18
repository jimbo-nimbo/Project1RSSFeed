package rssRepository;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class RSSItemModelTest {

  Core core = Core.getInstance();
  RssItemRepository rssItemRepository = core.getRssRepository();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void getNewsWebPageModel() {

  }

  @Test
  public void getArticle() {}

}