package webSiteRepository;

import core.Core;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.Assert.*;

public class NewsWebPageModelTest
{
	Core core = Core.getInstance();
	WebSiteRepository webSiteRepository = core.getWebSiteRepository();
	@Test
	public void constructorTest() throws IOException, ParseException
	{
//		NewsWebPageModel n = new NewsWebPageModel("http://www.irna.ir/en/rss.aspx?kind=-1&area=0",
//				"bodytext");
//    	assertNotEquals(n, null);
	}

	@Test
	public void updateTest() throws SQLException, IOException, ParseException {
//		NewsWebPageModel newsWebPageModel = webSiteRepository.getWebsites().get(1);
//    	newsWebPageModel.update();
//    	assertNotEquals(core.getRssRepository().getAllRSSData().size(), 0);
	}
}