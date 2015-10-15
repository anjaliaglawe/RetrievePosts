#Retrive Posts
This program retrieves a list of posts from a compendium feed and displays the 9 most recent posts to the user

-**Prerequisites:**
Install maven on your machine http://maven.apache.org/download.cgi
Java 6 and above

-**Build:**
Clone the project: https://github.com/anjaliaglawe/RetrievePosts.git
Navigate to the retrieveFeed project directory

--**Build project:**
without tests: mvn clean package -DskipTests
with tests: mvn test

-**Run Instructions:**
mvn package will create a jar in target directory. to execute the main program from command line execute following command.

java -cp target/retrieveFeed-1.0-SNAPSHOT.jar com.retrieve.feed.RetrievePosts

The program will ask for enter Feed Id and search parameters if any.
few Valid Feed Ids
b69420d0-1ded-4e4f-a9bd-81fbc1063f1a
137f578c-65c0-42b5-8440-19bb7f99b035
78f4b458-2c97-4c9a-bb88-f8124db03b21

Input for search parameters is a space-delimited list of search terms to filter the results with.






