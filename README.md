# Retrive Posts

This program retrieves a list of posts from a compendium feed and displays the 9 most recent posts to the user

---

Table of Contents

* <a href="#prerequisite">Prerequisites</a>
* <a href="#mavenbuild">Build and Run Project with Maven</a>
* <a href="#eclipselink">Build and Run Project with Eclipse</a>

---

<a name="prerequisite"></a>

## Prerequisites:

First, you need `java` and `git` installed and in your user's `PATH`. 

Next, make sure you have the RetrievePosts code  available on your machine.  use following command to download the  code and change to the new directory that contains the downloaded code.

    $ git clone https://github.com/anjaliaglawe/RetrievePosts.git && cd RetrievePosts/retrieveFeed
    
Install [Maven](http://maven.apache.org/) (preferably version 3.x) by following the [Maven installation instructions](http://maven.apache.org/download.cgi).

<a name="mavenbuild"></a>

## Build and Run Project with Maven:

- Compile and Package code by following command
 
    $ mvn clean install -DskipTests=true

- mvn package will create a jar in target directory. To execute the jar and run the main program from command line execute 
  following command.

  java -cp target/retrieveFeed-1.0-SNAPSHOT.jar com.retrieve.feed.RetrievePosts
  
  Input Details :
  -  FeedId : enter a valid feed Id for the user. (eg. 137f578c-65c0-42b5-8440-19bb7f99b035 )
  -  Search parameters : space-delimited list of search terms to filter the results with. press enter if you want to see all the      results and skip searching on specific words.

<a name="eclipselink"></a>

## Build and Run Project with Eclipse:

* Import the project for File -> Import -> Maven -> Import Existing project
* To update dependencies, right click on the project -> Maven -> update project
* Once the dependency errors are resolved, Run RetrievePosts class.

 Input Details :
  -  FeedId : enter a valid feed Id for the user. (eg. 78f4b458-2c97-4c9a-bb88-f8124db03b21 )
  -  Search parameters : space-delimited list of search terms to filter the results with. press enter if you want to see all the      results and skip searching on specific words.



