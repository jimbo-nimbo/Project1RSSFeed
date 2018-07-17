package webSiteRepository;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsWebPageModelTest
{
	@Test
	public void constructorTest()
	{
		NewsWebPageModel n = new NewsWebPageModel("http://www.irna.ir/en/rss.aspx?kind=-1&area=0",
				"bodytext");
    System.out.println(n);
	}
}