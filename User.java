
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Enum for Role
 */
enum Role {
    LIBRARIAN, MEMBER
}

/**
 * User Class
 */
public class User {

    private final String username;
    private String password;
    private final Role role;

    /**
     * Class Constructor method
     *
     * @param username
     * @param password
     * @param role
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = hashPassword(password);
        this.role = role;
    }

    /**
     * Hash password function
     *
     * @return String
     */
    public final String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error handling password", e);
        }
    }

    /**
     * get username
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * get role method
     *
     * @return String
     */
    public Role getRole() {
        return role;
    }

    /**
     * get hashed password
     *
     * @return String
     */
    public String getHashedPassword() {
        return password;
    }

    /**
     * is_librarian method
     *
     * @return boolean
     */
    public boolean isLibrarian() {
        return role == Role.LIBRARIAN;
    }
}
