/**
 * Librarian class that extends the user class
 */
public class Librarian extends User {
    public Librarian(String username, String password) {
        super(username, password, Role.LIBRARIAN);
    }
}
