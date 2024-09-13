import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    // register
    public static boolean register(String username, String email, String password) {

        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int effectedRows = pstmt.executeUpdate();

            System.out.println("Reģistrācijas ietekmētās rindas: " + effectedRows);

            return effectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Datubāzes kļūda reģistrācijas laikā: " + e.getMessage());
            e.printStackTrace();

            return false;
        }

    }

    // login
    public static boolean login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasResult = rs.next();

                System.out.println("Pierakstīšanās vaicājuma rezultāts: " + (hasResult ? "Lietotājs atrasts" : "Lietotājs nav atrasts"));
                return hasResult;
            }

        } catch (SQLException e) {
            System.err.println("Datubāzes kļūda pierakstīšanās laikā: " + e.getMessage());
            e.printStackTrace();

            return false;
        }

    }



}
