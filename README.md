
# College Library Management System

## Overview

The **College Library Management System** is a comprehensive solution designed to streamline the management of library operations within an academic institution. Built using the robust Spring Boot framework, this application provides a user-friendly interface for librarians, students, and college administrators to manage library resources efficiently.


## Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/rahuldora71/College-Library-Management.git
   cd college-library-management
   ```

2. **Configure the Database**:
   - Update `application.properties` with your MySQL database credentials.

3. **Build and Run the Application**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the Application**:
   - Open your browser and navigate to `http://localhost:9090`.

## Images
<div style="display=flex; flex-wrap=wrap;">
  

<img src="https://github.com/user-attachments/assets/aa646492-6461-4f41-895f-964f1eb5afba" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/458dbc71-b6a3-4198-87d4-622dcb43e1e0" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/e5817979-de89-4e8e-982d-a0ded6248a78" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/a2a924c0-44b7-46f3-ac56-fb15e46e2fa7" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/56c821f2-9fa7-4290-bcc0-676dcacddd51" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/774a900a-de0f-44d9-92fa-bfc51390c3de" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/41c9c7a0-9fe6-4d23-91f9-cd9b08171fce" width=40% height=50%>
<img src="https://github.com/user-attachments/assets/a3b7e259-f3da-4bca-af5f-a3d0984bcc43" width=40% height=50%>
</div>


## Features

### For Librarians:
- **Book Management**: Easily add, update, and manage book inventories, including categorization by status (available, issued, lost).
- **Issuing & Returning Books**: Seamlessly issue and return books, with automatic tracking of book status and student borrowing history.
- **User Management**: Manage student and staff profiles, including secure login credentials.
- **Reports & Analytics**: Generate detailed reports on book availability, borrowing trends, and overdue items.

### For Administrators:
- **System Configuration**: Configure global settings such as borrowing limits, overdue penalties, and user roles.
- **Security & Permissions**: Implement role-based access control to ensure secure access to different parts of the system.



## Technology Stack

- **Backend**: 
  - **Spring Boot**: Provides the foundation for the application, leveraging Spring MVC, Spring Data JPA, and Spring Security.
  - **Java**: Core language used for developing the application logic.
  - **MySQL**: Relational database management system for storing and managing library data.
  - **Hibernate**: ORM tool used for database interaction.

- **Frontend**: 
  - **Thymeleaf**: Template engine for rendering dynamic web pages.
  - **Bootstrap**: CSS framework used for creating responsive and visually appealing user interfaces.

- **Security**:
  - **Spring Security**: Handles authentication and authorization, ensuring secure access to the application.
 

- **File Management**:
  - **Local Storage**: Images and files are stored locally in the `images/` directory, categorized by type (e.g., book covers, user photos).

## Project Structure

- **Controllers**: Manage incoming HTTP requests and route them to the appropriate service.
- **Services**: Contain business logic and interact with repositories.
- **Repositories**: Handle data persistence and retrieval from the MySQL database.
- **Entities**: Define the data model and map to database tables.
- **Resources**: Contain static assets like images, and Thymeleaf templates for rendering views.

## Future Enhancements

- **Notification System**: Implement email or SMS notifications for overdue books and upcoming due dates.
- **Enhanced Reporting**: Add more detailed and customizable reports.
- **Mobile App**: Develop a companion mobile application for students and librarians.





### **BooksControllerApi**

| **API Name**         | **HTTP Method** | **Endpoint**                       | **Parameters**                                                                                                                                                        | **Description**                                                   |
|----------------------|-----------------|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|
| Get Books            | GET             | `/librarian/home/books-list`       | `page` (int, optional), `size` (int, optional), `principal` (Principal, auto-injected)                                                                                | Retrieves a paginated list of books available in the library.     |
| Search Books         | GET             | `/librarian/home/searched-books-list` | `page` (int, optional), `size` (int, optional), `search` (String, required), `filter` (String, optional), `principal` (Principal, auto-injected)                    | Searches for books based on a search term and an optional filter. |
| Book Suggestions     | GET             | `/librarian/home/book-suggestions` | `search` (String, required), `filter` (String, optional), `flag` (boolean, optional), `principal` (Principal, auto-injected)                                          | Provides book suggestions based on a search term and filters.     |
| Delete Book          | GET             | `/librarian/home/delete-book/{id}` | `id` (String, path variable, required), `session` (HttpSession, auto-injected), `principal` (Principal, auto-injected)                                                | Deletes a book based on its ID.                                   |
| Issue Book           | POST            | `/librarian/home/book-issue`       | `id` (String, required), `principal` (Principal, auto-injected)                                                                                                       | Approves and processes the issuing of a book to a student.        |
| Count Books          | GET             | `/librarian/home/count-books`      | `principal` (Principal, auto-injected)                                                                                                                                 | Returns a count of books by status for the librarian's college.   |

### **StudentControllerApi**

| **API Name**         | **HTTP Method** | **Endpoint**                       | **Parameters**                                                                                       | **Description**                                                   |
|----------------------|-----------------|------------------------------------|-------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|
| Search Users         | GET             | `/librarian/home/searched-user-list` | `search` (String, required), `principal` (Principal, auto-injected)                                   | Searches for users (students) in the system based on a search term.|
| Issue Book to Student| POST            | `/librarian/home/user-issue-book`  | `studentId` (String, required), `bookId` (String, required)                                           | Issues a book to a student after validating eligibility.          |
| Renew Book           | POST            | `/librarian/home/books_renew`      | `issuedBookId` (String, required)                                                                      | Renews an issued book, extending the borrowing period.            |


## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request.
## Authors

- [@octokatherine](https://www.github.com/octokatherine)

## License

This project is licensed under the [MIT License](LICENSE).
