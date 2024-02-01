Where to Eat?
=
<h3>A GovTech Mini Project</h3>

How it works:
=
1. An admin goes to the `Admin` page to create a session. This inserts a record in `LUNCHSESSION` database 
2. Once notified (out of scope) A Participant can join by going to the `home` page, input his name, session ID, and the restaurant of choice. This will be saved in `PARTICIPANT` table 
3. Any participant may view the session status and all the participant choices by going to the `Results` page.
4. An admin can close the session on the `Admin` page. once a session closes, it will select a random restaurant from all the `PARTICIPANT`s. This result will be saved in the `LUNCHSESSION` database


`URLs`
=
Home Page - http://localhost:8081/
H2 database console - http://localhost:8081/h2-console
username:sa 
password: password  
<br/>
<br/>
<br/>

Thought process for this exam:
=
1. Identify domain objects - a `Participant`, and a `LunchSession`
2. Identify the architecture to be used - MVC front-end with an onion architecture for backend
4. Create a mock-up UI using `html `
5. Create a Controller for http requests
6. Identify a simple server-side rendering framework - `Thymeleaf `was used since it readily comes with Spring boot
7. Identify database - Used `H2` as it came with Spring Boot
8. Create the DAO layer for the 2 domains using `Hibernate`
8. Create the Service Layer and Utility classes - implemented an Observer design pattern for event-based methods
9. Unit Tests using JUnit and Mockito
10. Make it work, then make it beautiful - Used `BootStrap` make the frontend presentable.
11. Create a README