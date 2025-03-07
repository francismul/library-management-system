
import java.util.Scanner;

/**
 * Main Applicaton class
 */
public class Main {

    static Library library = new Library();

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Library Management System");
            System.out.println("Please select an option:");
            System.out.println("1. Member");
            System.out.println("2. Librarian");
            System.out.println("3. Register");
            System.out.println("4. Exit");
            System.out.print("Enter your option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.println("Welcome Memeber");
                    System.out.println("Choose an option:");
                    System.out.println("1. Login");
                    System.out.println("2. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 ->
                            loginMember(scanner, library);
                        case 2 ->
                            System.exit(0);
                        default ->
                            System.out.println("Invalid option");
                    }
                }

                case 2 -> {
                    System.out.println("Welcome Librarian");
                    System.out.println("Choose an option:");
                    System.out.println("1. Login");
                    System.out.println("2. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 ->
                            loginLibrarian(scanner, library);
                        case 2 ->
                            System.exit(0);
                        default ->
                            System.out.println("Invalid option");
                    }
                }
                case 3 -> {
                    System.out.println("Welcome to Registration");
                    register(scanner, library);
                }
                case 4 -> {
                    System.out.println("Goodbye");
                    System.exit(0);
                }
                default ->
                    System.out.println("Invalid option");
            }
        }
    }

    /**
     * Register method
     *
     * @param scanner
     */
    public static void register(Scanner scanner, Library library) {
        scanner.nextLine(); // Consume the newline

        try {
            System.out.print("Enter your username:");
            String username = scanner.nextLine();
            System.out.print("Enter your password:");
            String password = scanner.nextLine();
            System.out.print("Enter your role (1 for Librarian, 2 for Member): ");
            int role = scanner.nextInt();

            UsersManager usersManager = new UsersManager();
            switch (role) {
                case 1 -> {
                    Librarian librarian = usersManager.addNewLibrarian(username, password);

                    if (librarian != null) {
                        System.out.println("Librarian added successfully");
                        System.out.println("Please login to continue");
                        loginLibrarian(scanner, library);
                    } else {
                        System.out.println("User with similar username exists, create a new account: ");
                        register(scanner, library);
                    }

                }
                case 2 -> {
                    Member member = usersManager.addNewMember(username, password);

                    if (member != null) {
                        System.out.println("Member added successfully");
                        System.out.println("Please login to continue");
                        loginMember(scanner, library);
                    } else {
                        System.out.println("User with similar username exists, create a new account: ");
                        register(scanner, library);
                    }

                }
                default ->
                    System.out.println("Invalid role");
            }
        } catch (Exception e) {
            // TODO: Handle the exception
        }
    }

    /**
     * Login method
     *
     * @param scanner
     */
    public static void loginLibrarian(Scanner scanner, Library library) {
        scanner.nextLine();

        try {
            System.out.print("Enter your username:");
            String username = scanner.nextLine();
            System.out.print("Enter your password:");
            String password = scanner.nextLine();

            UsersManager usermanager = new UsersManager();

            Librarian librarian = usermanager.authenticateLibrarian(username, password);

            if (librarian != null) {
                System.out.println("You have logged in successfully");
                System.out.println("Welcome " + librarian.getUsername());
                while (true) {
                    librarianLogic(librarian, scanner, library);
                }

            } else {
                System.out.println("Invalid Credentials");
            }

        } catch (Exception e) {
            // TODO:
        }
    }

    public static void loginMember(Scanner scanner, Library library) {
        scanner.nextLine();

        try {
            System.out.print("Enter your username:");
            String username = scanner.nextLine();
            System.out.print("Enter your password:");
            String password = scanner.nextLine();

            UsersManager usermanager = new UsersManager();

            Member member = usermanager.authenticateMember(username, password);

            if (member != null) {
                System.out.println("You have logged in successfully!");
                System.out.println("Welcome " + member.getUsername());
                while (true) {
                    membersLogic(member, scanner, library);
                }
            } else {
                System.out.println("Invalid Credentials");
            }

        } catch (Exception e) {
        }
    }

    /**
     * Members Logic
     *
     * @param member
     * @param scanner
     * @param library
     */
    public static void membersLogic(Member member, Scanner scanner, Library library) {
        System.out.println("Choose an option:");
        System.out.println("1. Display Books");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. Display borrowed Books");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                library.displayBooksByDepartment();
            }
            case 2 -> {
                scanner.nextLine(); // clear the buffer

                System.out.print("Enter the title: ");
                String title = scanner.nextLine();
                System.out.print("Enter the author: ");
                String author = scanner.nextLine();
                // System.out.print("Enter the department: ");
                // String department = scanner.nextLine();

                // Book book = new Book(title, author, department);
                library.borrowBook(member, title, author);
            }
            case 3 -> {
                scanner.nextLine(); // clear the buffer

                System.out.print("Enter the title: ");
                String title = scanner.nextLine();
                System.out.print("Enter the author: ");
                String author = scanner.nextLine();
                // System.out.print("Enter the department: ");
                // String department = scanner.nextLine();

                // Book book = new Book(title, author, department);
                // library.returnBorrowedBook(book, member);
                library.returnBook(member, title, author);
            }
            case 4 -> {
                library.retrieveBorrowedBooksFromFile(member);
                library.displayBorrowedBookByMember(member);
            }
            case 5 ->
                System.exit(0);
            default ->
                System.out.println("Invalid option");
        }
    }

    /**
     * Librarian Logic
     *
     * @param librarian
     * @param scanner
     * @param library
     */
    public static void librarianLogic(Librarian librarian, Scanner scanner, Library library) {
        System.out.println("Choose an option:");
        System.out.println("1. Display Books");
        System.out.println("2. Add Book");
        System.out.println("3. Remove Book");
        System.out.println("4. Display borrowed Books");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                library.displayBooksByDepartment();
            }
            case 2 -> {
                scanner.nextLine(); // clear the buffer

                System.out.print("Enter the title: ");
                String title = scanner.nextLine();
                System.out.print("Enter the author: ");
                String author = scanner.nextLine();
                System.out.print("Enter the department: ");
                String department = scanner.nextLine();

                Book book = new Book(title, author, department);
                library.addBookToLibrary(book);
            }
            case 3 -> {
                scanner.nextLine(); // clear the buffer

                System.out.print("Enter the title: ");
                String title = scanner.nextLine();
                System.out.print("Enter the author: ");
                String author = scanner.nextLine();
                System.out.print("Enter the department: ");
                String department = scanner.nextLine();

                Book book = new Book(title, author, department);
                System.out.println("Attempted to remove: " + book);
            }
            case 4 -> {
                // library.displayAllBorrowedBooks(librarian);
                System.out.println("Welcome to borrowed books");
                System.out.println("Sorry, Not yet implemented");
            }
            case 5 ->
                System.exit(0);
            default ->
                System.out.println("Invalid option");
        }

    }
}
