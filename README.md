How to run the code by Jordan Earle:

1. Download and install IntelliJ community version if you don't have it installed already.
2. Follow this video to setup tomcat server
   https://www.youtube.com/watch?v=kW1v_tvZC3Q
3. clone the project to you desktop and open it in IntelliJ. Refresh the maven dependencies by clicking on the m sign on the right of the IntelliJ and refresh button.
4. Ensure you have postgres installed. Create a postgres database with a name of your choice.
5. copy the src/main/resources/database.properties.example to database.properties and replace the properties with your own.

db.url=jdbc:postgresql://localhost:5432/<yourdatabasename>
db.username=your_username
db.password=your_password

change <yourdatabase> to the database you created. For example, if database name is teachingplatform, username: Jordan and password is abcd4321 the database.properties file should look like this

db.url=jdbc:postgresql://localhost:5432/AEP
db.username=Jordan
db.password=abcd4321

6. Run the project. 