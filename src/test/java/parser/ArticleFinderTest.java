package parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleFinderTest
{
    @Test
    public void makingInstanceAndCheckGetters()
    {
        ArticleFinder articleFinder = new ArticleFinder();

        System.out.println(articleFinder.findArticle("http://www.varzesh3.com/news/1537636/%D9%85%D8%AC%D8%AA%D8%A8%DB%8C-%D8%AC%D8%A8%D8%A7%D8%B1%DB%8C-%D8%A7%D8%B2-%D9%81%D9%88%D8%AA%D8%A8%D8%A7%D9%84-%D8%AE%D8%AF%D8%A7%D8%AD%D8%A7%D9%81%D8%B8%DB%8C-%DA%A9%D8%B1%D8%AF","news-page--news-text"));
    }

}