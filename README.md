# library-management-system

---

## :book: Project Overview

The Library Management System is a Java-based application designed to efficiently manage book borrowing and returning within a library. This system ensures that library members can borrow and return books seamlessly while maintaining accurate records of available and borrowed books.

## :rocket: Features

### User Authentication

- Supports different user roles (Librarian, Member).

### Book Management

- Admins can add, remove, and view books.

### Borrow & Return System

- Members can borrow one book at a time and return it before borrowing another.
- Borrowed books are stored persistently using file handling.

### Data Persistence

- Borrowed books are saved and retrieved from text files (you can use JSON or SQL Database for advanced implementations).

## :hammer_and_wrench: Technologies Used

- Java - Core programming language
- File Handling (TXT) - To store borrowed book records
- OOP Principles - Encapsulation, Inheritance, and Polymorphism

## :computer: How the Code Works

- To run the Library Management System, follow these steps:

1. **Clone the repository**:

   ```sh
   git clone https://github.com/francismul/library-management-system.git
   ```

2. **Ensure that you have the Java Development Kit (JDK) installed**:

   - You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

3. **Navigate to the project folder**:

   ```sh
   cd library-management-system
   ```

4. **Compile the Java files**:

   ```sh
   javac -d bin *.java
   ```

5. **Run the main class**:
   ```sh
   java -cp bin Main
   ```

## :pushpin: Future Enhancements

- Migrate book storage to a database (SQL/NoSQL).
- Implement a GUI version using JavaFX or Swing.
- Enhance authentication with encryption & user sessions.

## :handshake: Contributing

- Feel free to contribute! Open an issue or submit a pull request if you find any bugs or have improvements.

---

## :link: Connect with Me

- Have questions? Reach out via [email](mailto:francismule300@gmail.com) or [Github](https://github.com/francismul)!
