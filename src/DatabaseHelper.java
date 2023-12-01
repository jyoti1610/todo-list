import java.sql.*;

public class DatabaseHelper {
    private static final String DATABASE_URL = "jdbc:sqlite:todo.db";

    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "user_id INTEGER,"
                    + "description TEXT NOT NULL,"
                    + "completed BOOLEAN NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTask(Task task, int userId) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tasks (user_id, description, completed) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, task.getDescription());
            pstmt.setBoolean(3, task.isCompleted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
