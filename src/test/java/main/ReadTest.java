package main;

import models.RSSFeedModel;
import parser.RSSFeedParser;

public class ReadTest
{
        public static void main(String[] args) {
            RSSFeedParser parser = new RSSFeedParser("http://www.varzesh3.com/rss/all");
            RSSFeedModel feed = parser.readFeed();
//        System.out.println(feed);
//        for (RSSItemModel message : feed.getMessages()) {
//            System.out.println(message);
//
//        }

        }
}
