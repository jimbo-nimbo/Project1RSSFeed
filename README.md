#nimbo2  Project1RSSFeed
#by jimbo

this project goal is to get website url from user and manage new feeds and news of 
those sites and take the main text of news out of the site

to use this service u can use out main and RSSService which is written for the client
to get connect to our service

run mysql server First :)

To use our Code:

definition:
    
       rss of site : the link of the page of site wich containts the link of new feeds
       target class : the special class or tags of main text of news  

functions of our service:
    
    -addWebsite : to add rss of a website to database : it get url of rss and targetClass
    -getWebsites : get all websites in database
    -updateDatabase : fetch new RSS feed of all websites in database
    -updataDatabaseForWebsite : fetch new RSS from specified website
    -getallRSSData : List of all RSSItemModel in database 
    -getWebsiteRSSData : List of RSSItemModel of specified website 
    -getArticle : get string of article of specified website
    
to run the project :

1- initialize config.properties file
 
2- create database with name you specified and grant all privileges to DB_USERNAME

3- create instance from RSSService class and call this functions (or run Test class main function to check simple functionality)

packages :
 
    1- cli : it contains the Cli which handles the user commands and interface and multi threading
    2- core : which contains an instance of all of our services to connect each of them
    3-dataBase : handles the database part and configs
    4- date engine : handles the date patern and queries in relation with database
    5- rssRepo : rssItem are each news of a site which is crawled from that site rssPage and handle request about rssItem
    6- searchEngine : which handle users search in database
    7- webSite repo : which handle the site rssPage and its query in relation with database
     
       
Using our Cli :

    RSS are updated automatically when the service start
    you can type "?list" to see our commands
    our system log exception and error automatically to file
    write the name of function and its parameter with a space like : f p1 p2 p3
    our Commands:
        
        get-web-sites : return all websites which you added
        add-website : added a new website to data base + its parameter is target class of its new
        update : without parameter : updates all of site in data base for new feeds
        update + link : update news for that site
        get-some-last + site link + number : return those number of last news from the site that you mention
        get-news-count-for-day + link + pastDay : return number of news of those much day before from a website
        get-today-news + link : return today news of that site
        get-all-news : return all news
        get-news-for-web-site + link : return all news of that site
        search-title : search in title of newses
        search-article : search in article of newses
        search : search in both article and title of newses
          

 
