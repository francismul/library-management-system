
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public final class UsersManager {

    private final List<Member> members;
    private final List<Librarian> librarians;

    public UsersManager() {
        this.members = new ArrayList<>();
        this.librarians = new ArrayList<>();

        retrieveLibrariansFromFile("librarians.txt");
        retrieveMembersFromFile("members.txt");

    }

    public Librarian getLibrarian(String username) {
        for (Librarian librarian : librarians) {
            if (librarian.getUsername().equals(username)) {
                return librarian;
            }
        }
        return null;
    }

    public Member getMember(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null;
    }

    public Member authenticateMember(String username, String password) {
        Member member = getMember(username);

        if (member != null) {
            if (member.getHashedPassword().equals(hashPassword(password))) {
                return member;
            }
        }
        return null;
    }

    public Librarian authenticateLibrarian(String username, String password) {
        Librarian librarian = getLibrarian(username);

        if (librarian != null) {
            if (librarian.getHashedPassword().equals(hashPassword(password))) {
                return librarian;
            }
        }
        return null;
    }

    public Member addNewMember(String username, String password) {
        if (getMember(username) == null && getLibrarian(username) == null) {
            String hashedPassword = hashPassword(password);
            Member member = new Member(username, hashedPassword);
            members.add(member);
            saveMembersToFile("members.txt");
            retrieveMembersFromFile("members.txt");
            return member;
        }
        return null;
    }

    public Librarian addNewLibrarian(String username, String password) {
        if (getLibrarian(username) == null && getMember(username) == null) {
            String hashedPassword = hashPassword(password);
            Librarian librarian = new Librarian(username, hashedPassword);
            librarians.add(librarian);

            saveLibrariansToFile("librarians.txt");
            retrieveLibrariansFromFile("librarians.txt");

            return librarian;
        }
        return null;
    }

    public void saveMembersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Member member : members) {
                writer.write(member.getUsername() + ',' + member.getHashedPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO:
        }
    }

    public void saveLibrariansToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Librarian librarian : librarians) {
                writer.write(librarian.getUsername() + ',' + librarian.getHashedPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            // TODO:
        }
    }

    public void retrieveMembersFromFile(String filename) {
        members.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 2) {
                    Member member = new Member(data[0], data[1]);
                    members.add(member);
                }
            }
        } catch (IOException e) {
            // TODO:
        }
    }

    public void retrieveLibrariansFromFile(String filename) {
        librarians.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 2) {
                    Librarian librarian = new Librarian(data[0], data[1]);
                    librarians.add(librarian);
                }
            }
        } catch (IOException e) {
            // TODO:
        }
    }

    /**
     * A method to hash password
     *
     * @param password
     * @return password
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

}
