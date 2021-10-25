# Job Processing

Steps to run the application :

1. Set username and password in application.properties
2. To create Database run script.sql (Assumed that jobs are already submitted in DB)
3. In this application SpringBoots scheduled cron has been used - run-jobs. 
4. The value is configurable currently the cron runs for every minute.
5. Specify file path to store JobStats in  
6. To run the application through commandline use command
**mvn clean install**

