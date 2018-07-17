package webSiteRepository;

import core.Core;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rssRepository.RssItemRepository;

import static org.junit.Assert.*;

public class WebSiteRepositoryTest {

  Core core = Core.getInstance();
  WebSiteRepository webSiteRepository = new WebSiteRepository(core);
  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void addWebSite() {
  }

  @Test
  public void getWebsite() {}

  @Test
  public void getWebsites() {}
}