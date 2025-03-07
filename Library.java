
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Library Class
 */
public class Library {

    private final Map<String, List<Book>> booksByDepartment = new HashMap<>();
    private final Map<String, List<Book>> borrowedBooksByMembers = new HashMap<>();

    /**
     * Library constructor
     */
    public Library() {
        retrieveLibraryBooksFromFile("books.txt");
    }

    /**
     * Add book to library
     * 
     * @param book
     * @return
     */
    public void addBookToLibrary(Book book) {
        List<Book> books = booksByDepartment.computeIfAbsent(book.getDepartment(), k -> new ArrayList<>());

        if (!books.contains(book)) {
            books.add(book);
            saveLibraryBooksToFile("books.txt");
            retrieveLibraryBooksFromFile("books.txt");
            System.out.println("Book Added: " + book);
        } else {
            System.out.println("Book already exists: " + book);
        }
    }

    public void borrowBook(Member member, String title, String author) {
        for (List<Book> books : booksByDepartment.values()) {
            Iterator<Book> iterator = books.iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();

                if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                    iterator.remove();
                    borrowedBooksByMembers.computeIfAbsent(member.getUsername(), k -> new ArrayList<>()).add(book);
                    saveLibraryBooksToFile("books.txt");
                    retrieveLibraryBooksFromFile("books.txt");
                    saveBorrowedBooks(member);
                    retrieveBorrowedBooksFromFile(member);
                    System.out.println("You have successfully borrowed the book");
                    return;
                }
            }
        }
        System.out.println("Book not available: " + title + " by " + author);
    }

    public void returnBook(Member member, String title, String author) {
        retrieveBorrowedBooksFromFile(member);

        List<Book> borrowedBooks = borrowedBooksByMembers.get(member.getUsername());

        if (borrowedBooks != null) {
            Iterator<Book> iterator = borrowedBooks.iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                    iterator.remove();
                    booksByDepartment.computeIfAbsent(book.getDepartment(), k -> new ArrayList<>()).add(book);
                    saveLibraryBooksToFile("books.txt");
                    retrieveLibraryBooksFromFile("books.txt");
                    saveBorrowedBooks(member);
                    retrieveBorrowedBooksFromFile(member);
                    System.out.println("You have successfully returned borrowed book");
                    return;
                }
            }
        }
        System.out.println("No record of: " + title + " being borrowed by " + member.getUsername());
    }

    public void displayBorrowedBooks() {
        
        System.out.println("Borrowed Books");
        for (Map.Entry<String, List<Book>> entry : borrowedBooksByMembers.entrySet()) {
            System.out.println("Member: " + entry.getKey());
            for (Book book : entry.getValue()) {
                System.out.println("\t - " + book);
            }
        }
    }

    /**
     * 
     * @param filename
     * @return
     */
    public void saveLibraryBooksToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, List<Book>> entry : booksByDepartment.entrySet()) {
                for (Book book : entry.getValue()) {
                    writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getDepartment());
                    writer.newLine();
                }
            }
        } catch (IOException e) {

        }
    }

    /**
     * 
     * @param filename
     * @return
     */
    public final void retrieveLibraryBooksFromFile(String filename) {
        booksByDepartment.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Book book = new Book(data[0], data[1], data[2]);
                    List<Book> books = booksByDepartment.computeIfAbsent(book.getDepartment(), k -> new ArrayList<>());
                    if (!books.contains(book)) {
                        books.add(book);
                    }
                }
            }
        } catch (IOException e) {

        }

    }

    /**
     * 
     * @param member
     * @return
     */
    public void saveBorrowedBooks(Member member) {
        String filename = member.getUsername() + "_borrowed_books.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, List<Book>> entry : borrowedBooksByMembers.entrySet()) {
                for (Book book : entry.getValue()) {
                    writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getDepartment());
                    writer.newLine();
                }
            }
        } catch (IOException e) {

        }

    }

    /**
     * 
     * @param member
     * @return
     */
    public void retrieveBorrowedBooksFromFile(Member member) {
        borrowedBooksByMembers.clear();
        String filename = member.getUsername() + "_borrowed_books.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Book book = new Book(data[0], data[1], data[2]);
                    List<Book> books = borrowedBooksByMembers.computeIfAbsent(member.getUsername(),
                            k -> new ArrayList<>());

                    if (!books.contains(book)) {
                        books.add(book);
                    }
                }
            }
        } catch (IOException e) {

        }

    }

    /**
     * Display books borrowed by member
     * 
     * @param member
     */
    public void displayBorrowedBookByMember(Member member) {
        retrieveBorrowedBooksFromFile(member);

        System.out.println("\n==================================================================\n");
        if (!borrowedBooksByMembers.isEmpty()) {
            System.out.println(member.getUsername());
            List<Book> borrowedBooks = borrowedBooksByMembers.get(member.getUsername());

            if (borrowedBooks != null) {
                for (Book book : borrowedBooks) {
                    System.out.println("\t - " + book);
                }
            }
        } else {
            System.out.println("There are no borrowed books at the moment");
        }
        System.out.println("\n==================================================================\n");
    }

    /**
     * Display books by department
     */
    public void displayBooksByDepartment() {
        System.out.println("\n==================================================================\n");
        if (!booksByDepartment.isEmpty()) {
            for (Map.Entry<String, List<Book>> entry : booksByDepartment.entrySet()) {
                System.out.println("Department: " + entry.getKey());

                for (Book book : entry.getValue()) {
                    System.out.println("\t - " + book.getTitle() + " by " + book.getAuthor());
                }
            }
        } else {
            System.out.println("There are no books in the library currently");
        }
        System.out.println("\n==================================================================\n");
    }

}