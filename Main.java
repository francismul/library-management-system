
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static final Set<User> users = new HashSet<>();
    // private static final Library library = new Library();

    public static void main(String[] args) {
        Library library = new Library();
        library.loadBooksFromFile("books.txt");

        // Load users from file
        loadUsersFromFile("users.txt");

        try (Scanner scanner = new Scanner(System.in)) {
            //
            System.out.println("\n==================================================================\n");
            System.out.println("Welcome to the Library Management System");
            System.out.println("\n==================================================================\n");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 ->
                    login(scanner, library);
                case 2 ->
                    register(scanner, library);
                case 3 ->
                    System.exit(0);
                default -> {
                    System.out.println("\n==================================================================\n");
                    System.out.println("\tInvalid choice");
                    System.out.println("\n==================================================================\n");
                }
            }
        }
    }

    /**
     * login users
     *
     * @param scanner
     */
    public static void login(Scanner scanner, Library library) {
        scanner.nextLine(); // consume the buffer left overs

        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        User user = authenticate(username, password);
        // Library library = new Library();

        if (user != null) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tLogin successful! Welcome " + user.getUsername());

            if (user.isLibrarian()) {
                System.out.println("\tYou are a librarian");
                System.out.println("\n==================================================================\n");

                while (true) {
                    System.out.println("1. See Available Books");
                    System.out.println("2. Manage Books");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 ->
                            displayBooks(library);
                        case 2 -> {
                            scanner.nextLine(); // consume the buffer left overs

                            System.out.println("1. Add Book");
                            System.out.println("2. Remove Book");
                            System.out.println("3. Exit");
                            System.out.print("Enter your choice: ");

                            int manageChoice = scanner.nextInt();

                            switch (manageChoice) {
                                case 1 ->
                                    addBook(library, scanner);
                                case 2 ->
                                    removeBook(library, scanner);
                                case 3 ->
                                    System.exit(0);
                                default -> {
                                    System.out.println(
                                            "\n==================================================================\n");
                                    System.out.println("\tInvalid choice");
                                    System.out.println(
                                            "\n==================================================================\n");
                                }
                            }
                        }
                        case 3 ->
                            System.exit(0);
                        default -> {
                            System.out
                                    .println("\n==================================================================\n");
                            System.out.println("\tInvalid choice");
                            System.out
                                    .println("\n==================================================================\n");
                        }
                    }
                }
            } else {
                System.out.println("\tYou are a Member");
                System.out.println("\n==================================================================\n");

                Member member = (Member) user;

                while (true) {
                    scanner.nextLine(); // consume the buffer left overs

                    System.out.println("1. See Available Books");
                    System.out.println("2. Borrow Books");
                    System.out.println("3. Return Books");
                    System.out.println("4. View Borrowed Books");
                    System.out.println("5. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 ->
                            displayBooks(library);
                        case 2 ->
                            borrowBook(library, member, scanner);
                        case 3 ->
                            returnBook(library, member, scanner);
                        case 4 ->
                            member.displayBorrowedBooks();
                        case 5 ->
                            System.exit(0);
                        default -> {
                            System.out
                                    .println("\n==================================================================\n");
                            System.out.println("\tInvalid choice");
                            System.out
                                    .println("\n==================================================================\n");
                        }
                    }
                }
            }

        } else {
            System.out.println("\n==================================================================\n");
            System.out.println("\tInvalid username or password");
            System.out.println("\n==================================================================\n");
        }

    }

    /**
     * register new users
     *
     * @param scanner
     */
    public static void register(Scanner scanner, Library library) {
        scanner.nextLine(); // consume the buffer left overs

        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();
        System.out.print("Enter your role (1 for Librarian, 2 for Member): ");
        int roleChoice = scanner.nextInt();

        Role role = roleChoice == 1 ? Role.LIBRARIAN : Role.MEMBER;
        User user = role == Role.LIBRARIAN ? new Librarian(username, password) : new Member(username, password);
        try {

            users.add(user);
            saveUsersToFile("users.txt");

            System.out.println("\n==================================================================\n");
            System.out.println("\tUser registered successfully! You can now login.");
            System.out.println("\n==================================================================\n");
            login(scanner, library);
        } catch (Exception e) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tErro occurred while creating user!");
            System.out.println("\t" + e);
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * authentication method
     *
     * @param username
     * @param password
     * @return
     */
    private static User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.hashPassword(password).equals(user.hashPassword(password))) {
                return user;
            }
        }
        return null;
    }

    /**
     * display available books in the library
     *
     * @param library
     */
    private static void displayBooks(Library library) {
        library.displayBooks();
    }

    /**
     * Librarian Activity Logic
     */
    /**
     * Allows the librarian add a book to the library
     *
     * @param library
     * @param scanner
     */
    private static void addBook(Library library, Scanner scanner) {
        scanner.nextLine(); // consume the buffer left overs

        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the department of the book: ");
        String department = scanner.nextLine();

        Book book = new Book(title, author, department);
        try {
            library.addBook(book);
            library.saveBooksToFile("books.txt");
            System.out.println("\n==================================================================\n");
            System.out.println("\tBook added successfully!");
            System.out.println("\n==================================================================\n");
        } catch (Exception e) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tError Adding book to the library!");
            System.out.println("\n " + e);
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Allows the librarian remove a book from the library
     *
     * @param library
     * @param scanner
     */
    private static void removeBook(Library library, Scanner scanner) {
        // check first if the library is empty
        if (library.isEmpty()) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tThe library is empty. No books available for deletion.");
            System.out.println("\n==================================================================\n");
            return;
        }

        scanner.nextLine(); // consume the buffer left overs

        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the department of the book: ");
        String department = scanner.nextLine();

        Book book = new Book(title, author, department);
        try {
            library.removeBook(book);
            System.out.println("\n==================================================================\n");
            System.out.println("\tBook removed successfully!");
            System.out.println("\n==================================================================\n");
        } catch (Exception e) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tError removing book from the library!");
            System.out.println("\n " + e);
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Member Activity Logic
     */
    /**
     * Allows user to borrow book from the library
     *
     * @param library
     * @param member
     * @param scanner
     */
    private static void borrowBook(Library library, Member member, Scanner scanner) {
        // check first if the library is empty
        if (library.isEmpty()) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tThe library is empty. No books available to borrow.");
            System.out.println("\n==================================================================\n");
            return;
        }

        scanner.nextLine(); // consume the buffer left overs

        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the department of the book: ");
        String department = scanner.nextLine();

        member.borrowBook(library, title, author, department);
    }

    /**
     * Allows users to return borrowed books
     *
     * @param library
     * @param member
     * @param scanner
     */
    private static void returnBook(Library library, Member member, Scanner scanner) {
        // check first if the user has any borrowed books
        if (!member.hasBorrowedBooks()) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tYou have not borrowed any books.");
            System.out.println("\n==================================================================\n");
            return;
        }

        scanner.nextLine(); // consume the buffer left overs

        System.out.print("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter the department of the book: ");
        String department = scanner.nextLine();

        member.returnBook(library, title, author, department);
    }

    /**
     * Save users to file
     */
    public static void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getHashedPassword() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tError Adding User: \n" + e.getMessage());
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Load Users from file
     */
    public static void loadUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Role role = data[2].equals("LIBRARIAN") ? Role.LIBRARIAN : Role.MEMBER;
                    User user = (role == Role.LIBRARIAN) ? new Librarian(data[0], data[1])
                            : new Member(data[0], data[1]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            // System.out.println("\n==================================================================\n");
            // System.out.println("\tError Loading Users: \n" + e.getMessage());
            // System.out.println("\n==================================================================\n");
        }
    }
}
