SailbotManager
==============

Sailbot Manager provides a GUI to operate the boat over remote serial connection, using XBee.
Its main features are:

* creating a list of waypoints and saving it to a file
* sending waypoints to the boat
* receiving live telemetry data from the robot
* displaying current position of the boat on a map
* reading and rendering logged data


## Compiling

Apache Ant and Java 1.7 are required to build the program.
The script will compile all the classes into bin directory and create 
a runnable jar file called runSailbotManager.jar

To compile, run

>ant compile

then just

>ant

to create a runnable jar.



## Running

To run the program:

> java -jar runSailbotManager.jar
