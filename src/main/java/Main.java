import com.zaxxer.hikari.*;
import java.sql.*;
public class Main {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/Test1");
        config.setUsername("root");
        config.setPassword("#Pirates20");
        HikariDataSource dataSource = new HikariDataSource(config);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Table1 (Name VARCHAR(20),ID int PRIMARY KEY)");
            statement.executeUpdate("INSERT IGNORE INTO Table1 (Name, ID) VALUES ('Hassan', 1)");
            statement.executeUpdate("INSERT IGNORE INTO Table1 (Name, ID) VALUES ('Ali', 2)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Table2 (CNIC int,ID int PRIMARY KEY, FOREIGN KEY (ID) REFERENCES Table1(ID))");
            statement.executeUpdate("INSERT IGNORE INTO Table2 (CNIC, ID) VALUES (123456789, 1)");
            statement.executeUpdate("INSERT IGNORE INTO Table2 (CNIC, ID) VALUES (987654321, 2)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Table1 JOIN Table2 ON Table1.ID = Table2.ID");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + ". " + resultSet.getString("name") + " -> CNIC: " + resultSet.getInt("CNIC"));
            }
            dataSource.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
