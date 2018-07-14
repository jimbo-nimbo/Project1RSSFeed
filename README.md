#nimbo2  Project1RSSFeed
#by jimbo

this project goal is to get website url from user and manage new feeds and news of 
those sites and take the main text of news out of the site

to use this service u can use out main and RSSService which is written for the client
to get connect to our service

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
    

 