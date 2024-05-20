# To-Do
Todo App with basic funtionalities

## Time taken from start to finish?
  - 14Hrs.

## How to run the code?
  - Clone the Repo to local machine.
  - Run mvn clean install in cloned repo or load maven dependency from IDE itself.
  - Run the main method to start the spring application.
  - Call the API endpoints from postman.

## How code works?
  - The Task Management application is built using the Spring Boot framework. The application follows a layered architecture, separating concerns across different layers: the presentation layer, service layer, and data access layer. This approach promotes modularity, maintainability, and scalability.
  - **Architecture**: 
      - **Presentation Layer**: The TaskController serves as the entry point for client requests. It receives HTTP requests, delegates the processing to the TaskService, and returns HTTP responses. This separation allows the controller to focus on handling web requests and responses.

      - **Service Layer**: The TaskService encapsulates the business logic of the application. It ensures that the application’s core functionality is implemented correctly and interacts with the repository layer to fetch or persist data.

      - **Data Access Layer**: The TaskRepository is responsible for interacting with the database. By extending JpaRepository, it inherits several methods for working with Task persistence, including methods for saving, deleting, and finding Task entities. For simplicity i have used List itself for in-memory database.

      - **DTO and Entity**: The TaskDTO is used to transfer data between layers, ensuring that only the necessary information is exposed. The Task entity represents the data structure used by the JPA to interact with the database.

      - **Mapper**: The TaskMapper converts between Task entities and TaskDTO objects. This separation of concerns helps maintain clean code and promotes reusability.
   
## Libraries Used
  - Lombok.
  - MapStruct.
