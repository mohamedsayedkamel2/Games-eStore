# Games eStore

### Description
It's a Spring boot web application, Used for selling video games to the customers.

### Tools & Technologies
1. Java
2. Spring Boot
3. Spring Security
4. Hibernate
5. Spring Data JPA
6. MySQL
7. Thymeleaf
8. AJAX (for REST API calls)
9. Redis (for caching)
12. JUnit
13. Maven
14. Intellij IDEA

### Project Functionalites:

1. The user can buy a video game using website cash
2. the user will have the ability to buy a video game phyically or digitaly
3. diffrent logic will be executed dependinng on the type of the game the customer buys and this functionality is done using Strategy Design Pattern
4. The user can register using email
5. the user will have to verify the email using a sent email which contains verification url
6. if the user isn't verified then the user won't be able to login into the website
7. in case the user forgot the password, the user will have the ability to change the password using a sent email which contains change password url
8. the application caches the data to prevent unnecessary calls to the DB and to improve the performance and this functionality is done using Redis
9. the user will be able to view the games page by page, each page will have a certain amount of games. This is done for better readability.
10. the admin can do full CRUD operations on the application entites such as customers and products
