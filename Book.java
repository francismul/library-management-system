

public class Book {

    private final String title;
    private final String author;
    private final String department;

    /**
     * Book class constructor
     *
     * @param title
     * @param author
     * @param department
     */
    public Book(String title, String author, String department) {
        this.title = title;
        this.author = author;
        this.department = department;
    }

    /**
     * get title method
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * get author method
     *
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    /**
     * get department method
     *
     * @return String
     */
    public String getDepartment() {
        return department;
    }
}
