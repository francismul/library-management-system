import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Member extends User {

    private final List<Book> borrowedBooks;

    /**
     * Member class constructor
     *
     * @param username
     * @param password
     */
    public Member(String username, String password) {
        super(username, password, Role.MEMBER);
        this.borrowedBooks = new ArrayList<>();
        retrieveBorrowedBooks();
    }

    /**
     * allows a member to borrow one book at a time
     *
     * @param library
     * @param title
     * @param author
     * @param department
     */
    public void borrowBook(Library library, String title, String author, String department) {
        // Restrict the user to borrow only one book
        if (!borrowedBooks.isEmpty()) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tYou already have borrowed books. Please return them before borrowing more.");
            System.out.println("\n==================================================================\n");
            return;
        }

        Book book = library.findBook(title, author, department);

        if (book != null) {
            borrowedBooks.add(book);
            library.removeBook(book);
            saveBorrowedBooks();

            System.out.println("\n==================================================================\n");
            System.out.println("\tBook borrowed successfully!");
            System.out.println("\n==================================================================\n");

        } else {
            System.out.println("\n==================================================================\n");
            System.out.println("\tBook not found!");
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Check if the user has borrowed any book returns true if any
     *
     * @return bool
     */
    public boolean hasBorrowedBooks() {
        return !borrowedBooks.isEmpty();
    }

    /**
     * Display already borrowed books
     */
    public void displayBorrowedBooks() {
        if (!hasBorrowedBooks()) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tYou don't have any borrowed books!");
            System.out.println("\n==================================================================\n");
            return;
        }
        System.out.println("\n==================================================================\n");
        for (Book book : borrowedBooks) {
            System.out.println("\t - " + book.getTitle() + " by " + book.getAuthor());
        }
        System.out.println("\n==================================================================\n");
    }

    /**
     * Allows user to return borrowed books
     * 
     * @param library
     * @param title
     * @param author
     * @param department
     */
    public void returnBook(Library library, String title, String author, String department) {
        if (hasBorrowedBooks()) {
            Iterator<Book> iterator = borrowedBooks.iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getTitle().equals(title) && book.getAuthor().equals(author)
                        && book.getDepartment().equals(department)) {
                    iterator.remove();
                    library.addBook(book);
                    saveBorrowedBooks();
                    System.out.println("\n==================================================================\n");
                    System.out.println("\tBook returned successfully!");
                    System.out.println("\n==================================================================\n");
                    return;
                }
            }
            System.out.println("\n==================================================================\n");
            System.out.println("\tBook not found in borrowed books!");
            System.out.println("\n==================================================================\n");
        } else {
            System.out.println("\n==================================================================\n");
            System.out.println("\tYou don't have any borrowed books!");
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Retrieve borrowed books
     */
    public void retrieveBorrowedBooks() {
        borrowedBooks.clear();
        String filename = "borrowed_books_" + getUsername() + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                borrowedBooks.add(new Book(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            // System.out.println("\n==================================================================\n");
            // System.out.println("\tError retrieving borrowed books from file: " + e.getMessage());
            // System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Save borrowed books to file
     */
    public void saveBorrowedBooks() {
        String filename = "borrowed_books_" + getUsername() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : borrowedBooks) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getDepartment());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tError saving borrowed books to file: " + e.getMessage());
            System.out.println("\n==================================================================\n");
        }
    }
}
