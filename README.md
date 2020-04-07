![](https://github.com/radoslawik/FootballCoach/blob/master/app/src/main/res/drawable/footballcoach.png)

Android application to save football match history and show detailed statistics. User can track the progress by observing and comparing scores from played games.

## Features implemented

### 1. Design
Application was created with a special attention to **modern design** and **ergonomic interface**. To achieve our goal we used tools like **RecyclerView, ViewPager, NavigationView, AlertDialog** and many more! Activities, fragments and communication between them - everything can be found in our code. We did not forget about landscape view, application will adjust its design to the current orientation (we did it programatically instead of creating new xml files). Besides English language we also implemented French version of the application - to switch the version you should change current language of your phone.

### 2. Functionalities
Here are the main functionalities we implemented:
- browsing sorted match history
- displaying statistics from each game (shots, fouls, ball possession, pass accurracy, etc.)
- checking average / best / worst stats withing played games
- adding or deleting matches
- renaming the team

We also identified possible improvements / features to do:
- login page with authentication
- possibility to edit the stats of played match

### 3. Location of matches
Our application is able to automatically locate the matches. After giving the permission to use the sensor, app will get the current location, then using geocoding, longitude and latitude will be transformed into a **street and city name** and saved to match stats.

### 4. Picture of the match
While adding a new game you can **take a photo of the match** with the camera. Photo will be saved locally and will be private (accessible only from application level). After unistalling/reinstalling all the pictures are lost! Thumbnail of the picture is visible in the main activity. Remember that this feature requires permissions as well.

### 5. External database
Whole match history with statistics is saved in **external MySQL database**. To connect to database we decided to use JDBC driver. Database design is simple and consists of only one table with many rows to store information about all the stats (~30 columns). Connection with external database is made to obtain match history with the stats, after adding new game or deleting existing game.

### 6. Local database
Last 5 matches played and their stats are saved locally in **SQLite database**. Connection with local database is made just after application startup, after adding new game or after deleting one of 5 recent games. Local database allows us to provide better user experience, because matches are visible on the screen just after starting the app. When the user touches the screen, more games are loaded from external database.

## Application architecture
This is a simplified schema of our app architecture. Layouts were omitted.

![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/architecture.svg)


## Screenshots
TODO
