# Some considerations
### Project Lombok
This project is using Lombok to generate getters and the builder pattern. 
If you have compilation problems because of the missing getters or the missing 
builder method on some class, remember to install Lombok to your IDE. You can
find the jar and the instructions on their website https://projectlombok.org/

### Build failure, test failures and stuff not working
Depending on the level that you test for, the code either has tests implemented
for methods that you have to implement, or it misses some parts, or there are some
bugs left in the code. Therefore, when you first run `mvn clean install` 
it might not work.

### H2 Database
This is an in-memory DB, meaning that once you stop the app, all the content is gone.
In the `resources` folder you have two files with wich you can manage the 
initial state of the DB. One is to create the tables, the other is to insert data.

You can access the DB at http://localhost:8080/tasks/h2-console with 
`user: sa` and `password: password`. The jdbc url is `jdbc:h2:mem:tasksdb`.

