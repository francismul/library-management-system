
import java.util.Objects;

/**
 * Book Class
 */
public class Book {

    /**
     * Book Variables
     */
    private final String title;
    private final String author;
    private final String department;

    /**
     * Book Class Constructor
     */
    public Book(String title, String author, String department) {
        this.title = title;
        this.author = author;
        this.department = department;
    }

    /**
     * Get title method
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get author method
     * 
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get department method
     * 
     * @return
     */
    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Book book = (Book) obj;
        return title.equalsIgnoreCase(book.title) && author.equalsIgnoreCase(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title.toLowerCase(), author.toLowerCase());
    }

}