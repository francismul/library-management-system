import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Library {

    private final Map<String, List<Book>> booksByDepartment;

    /**
     * Library class constructor
     */
    public Library() {
        this.booksByDepartment = new HashMap<>();
        loadBooksFromFile("library.txt");
    }

    /**
     * Add books to library
     *
     * @param book
     * @return void
     */
    public void addBook(Book book) {
        booksByDepartment.putIfAbsent(book.getDepartment(), new ArrayList<>());
        List<Book> books = booksByDepartment.get(book.getDepartment());
        if (!books.contains(book)) {
            books.add(book);
            saveBooksToFile("library.txt");
        }
    }

    /**
     * Remove a book from library
     *
     * @param book
     * @return void
     */
    public void removeBook(Book book) {
        if (booksByDepartment.containsKey(book.getDepartment())) {
            booksByDepartment.get(book.getDepartment()).remove(book);
            saveBooksToFile("library.txt");
            loadBooksFromFile("library.txt");
        }
    }

    /**
     * Check if library is empty
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return booksByDepartment.isEmpty();
    }

    /**
     * Display books in the library
     */
    public void displayBooks() {
        if (isEmpty()) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tNo books are currently available in the library.");
            System.out.println("\n==================================================================\n");
            return;
        }

        for (var entry : booksByDepartment.entrySet()) {
            System.out.println("Department: " + entry.getKey());
            System.out.println("\n==================================================================\n");
            for (Book book : entry.getValue()) {
                System.out.println("\t - " + book.getTitle() + " by " + book.getAuthor());
            }
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Find book in the library
     *
     * @param title
     * @param author
     * @param department
     * @return book | null
     */
    public Book findBook(String title, String author, String department) {
        if (title == null || author == null || department == null) {
            return null;
        }
        return booksByDepartment.getOrDefault(department, new ArrayList<>())
                .stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author))
                .findFirst()
                .orElse(null);
    }

    /**
     * Save books to file
     */
    public void saveBooksToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (var entry : booksByDepartment.entrySet()) {
                for (Book book : entry.getValue()) {
                    writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getDepartment());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("\n==================================================================\n");
            System.out.println("\tError saving Books: \n" + e.getMessage());
            System.out.println("\n==================================================================\n");
        }
    }

    /**
     * Load books from the library file
     */
    public void loadBooksFromFile(String filename) {
        booksByDepartment.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Book book = new Book(parts[0], parts[1], parts[2]);
                    booksByDepartment.putIfAbsent(book.getDepartment(), new ArrayList<>());
                    booksByDepartment.get(book.getDepartment()).add(book);
                }
            }
        } catch (IOException e) {
            // System.out.println("\n==================================================================\n");
            // System.out.println("\tError Loading Books: \n" + e.getMessage());
            // System.out.println("\n==================================================================\n");
        }
    }
}
