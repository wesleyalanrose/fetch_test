# fetch_test
Code test for Fetch Rewards backend engineer position

# Prerequisites
Tomcat (or whatever you personally use to deploy WAR files)
Gradle 

# Executing
From within the project folder execute:
  gradle build war
This will generate a .war file in /build/libs/

Deploy that file on your choice of webserver software (I used Tomcat for its simplicity in this case)
Copy the /web_files/ folder into the folder that Tomcat inflated the .war file into

Navigate to wherever the webserver is directing ex: localhost:8000/web_files

This will display a VERY rudimentary UI. 

You can manually add "transactions," or click the second button to just add that whole ul

You can input a number of points to spend. 

You can check the current balances. This will only print to console because of the irregular output requirement in the test prompt.
