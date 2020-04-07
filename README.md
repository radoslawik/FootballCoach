![](https://github.com/radoslawik/FootballCoach/blob/master/app/src/main/res/drawable/footballcoach.png)

Android application to save football match history and show detailed statistics. User can track the progress by observing and comparing scores from played games.

## Features implemented

### 1. Design
Application was created with a special attention to **modern design** and **ergonomic interface**. To achieve our goal we used tools like **RecyclerView, ViewPager, NavigationView, ScrollView, CardView, AlertDialog** and many more! Activities, fragments and communication between them - everything can be found in our code. We did not forget about landscape view, application will adjust its design to the current orientation (we did it programatically instead of creating new xml files). Besides English language we also implemented French version of the application - to switch the version you should change current language of your phone.

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
Main screen (portrait) <sup>1)</sup>  | Navigation drawer <sup>2)</sup> | Add game screen <sup>3)</sup> 
--- | --- | ---
![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss1.PNG)  | ![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss3.PNG) | ![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss4.PNG) 

Main screen (landscape) <sup>1)</sup>  | Match display (landscape) <sup>4)</sup> 
--- | ---
![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss2.PNG)  | ![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss7.PNG)

Match display <sup>4)</sup> | Statistics screen <sup>5)</sup> | Rename team alert <sup>6)</sup> 
--- | --- | ---
![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss5.PNG)     | ![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss6.PNG)        | ![](https://github.com/radoslawik/FootballCoach/blob/master/screenshots/ss8.PNG)      

#### 1) Main activity screen
User is able to scroll through match history. Recycler view in potrait mode has only one column. After clicking on the item detailed stats of this game will be displayed. User can also toggle navigation drawer from here or add a new game by clicking on the floating button. In case of no matches to browse, there is a notification in the background.
#### 2) Navigation drawer 
This allows user to access quickly most of the functionalities of the app, for example adding new game or renaming the team. User can also verify his current team name and email (login and edit match features not implemented yet)
#### 3) Add game 
This screen consists mostly of empty editable text fields. At the top there is current location and date (user cannot change it) and there are rest of the paramaters that can be modified by user (in case of empty field 0 will be assigned). To see all the parameters (and "add game" button) screen can be scrolled down.
#### 4) Match display
At the top there is scoreboard with score and names of teams. Lower part consists of all the stats taken into account. Small green arrow means that home team was better in particular category (red arrow opposite). If the dimensions of the screen are too small to show entire view, user will be able to scroll through the stats. On the bottom we places the date and location of the match.
#### 5) Statistics display
Layout is similar to the match display. However, at the top we have 3 tabs with average, best and worst stats based on played matches. Statistics are shown for both home and away teams. Green and red arrows have the same meaning as in match display.
#### 6) Rename team
This simple dialog window allows user to type new team name. Maximum number of characters is set to 16 and for too long names user will be able to see the red borders as a warning. Only valid names are accepted.

## Contributors
PUDEŁKO Radosław
ZAHRAN Noha
