package controller;

import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private final String URL = "jdbc:sqlite:Rumours.db";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void initializeDatabase() {
        String sqlCreateUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "id TEXT PRIMARY KEY, name TEXT NOT NULL, role TEXT NOT NULL);";

        String sqlCreateRumours = "CREATE TABLE IF NOT EXISTS rumours (" +
                "id TEXT PRIMARY KEY, title TEXT NOT NULL, source TEXT, " +
                "created_date TEXT, credibility_score INTEGER, status TEXT, verified INTEGER);";

        String sqlCreateReports = "CREATE TABLE IF NOT EXISTS reports (" +
                "user_id TEXT, rumour_id TEXT, report_date TEXT, type TEXT, " +
                "PRIMARY KEY (user_id, rumour_id));";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlCreateUsers);
            stmt.execute(sqlCreateRumours);
            stmt.execute(sqlCreateReports);

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("... Database is empty. Inserting sample data ...");
                insertSampleData(stmt);
            }

        } catch (SQLException e) {
            System.out.println("Init DB Error: " + e.getMessage());
        }
    }

    private void insertSampleData(Statement stmt) throws SQLException {
        stmt.execute("INSERT INTO users VALUES ('U001', 'Alice', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U002', 'Bob', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U003', 'Charlie', 'Fact Checker')");
        stmt.execute("INSERT INTO users VALUES ('U004', 'Diana', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U005', 'Evan', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U006', 'Fiona', 'Fact Checker')");
        stmt.execute("INSERT INTO users VALUES ('U007', 'George', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U008', 'Hannah', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U009', 'Ian', 'General User')");
        stmt.execute("INSERT INTO users VALUES ('U010', 'Jack', 'General User')");

        stmt.execute("INSERT INTO rumours VALUES ('10000001', 'Aliens in NY', 'Twitter', '2023-10-25', 10, 'panic', 0)");
        stmt.execute("INSERT INTO rumours VALUES ('20000002', 'Zombie Virus', 'Blog', '2023-10-26', 40, 'panic', 0)");
        stmt.execute("INSERT INTO rumours VALUES ('30000003', 'Public Holiday', 'Gov Web', '2023-10-20', 100, 'verified-true', 1)");
        stmt.execute("INSERT INTO rumours VALUES ('40000004', 'Free Money', 'Spam', '2023-10-21', 0, 'verified-false', 1)");
        stmt.execute("INSERT INTO rumours VALUES ('50000005', '5G Towers', 'Facebook', '2023-10-27', 20, 'normal', 0)");
        stmt.execute("INSERT INTO rumours VALUES ('60000006', 'Celeb Gossip', 'IG', '2023-10-27', 60, 'normal', 0)");
        stmt.execute("INSERT INTO rumours VALUES ('70000007', 'Traffic Jam', 'Radio', '2023-10-28', 80, 'normal', 0)");
        stmt.execute("INSERT INTO rumours VALUES ('80000008', 'New iPhone', 'Forum', '2023-10-28', 50, 'normal', 0)");
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Rumour> getAllRumours() {
        List<Rumour> list = new ArrayList<>();
        String sql = "SELECT * FROM rumours";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String source = rs.getString("source");
                LocalDate date = LocalDate.parse(rs.getString("created_date"));
                int score = rs.getInt("credibility_score");
                
                Rumour r = new Rumour(id, title, source, date, score);
                
                r.setStatus(rs.getString("status"));
                r.setVerified(rs.getInt("verified") == 1);

                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Report> getAllReports() {
        List<Report> list = new ArrayList<>();
        String sql = "SELECT * FROM reports";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String uid = rs.getString("user_id");
                String rid = rs.getString("rumour_id");
                String type = rs.getString("type");
                
                list.add(new Report(uid, rid, type));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public void saveReport(String userId, String rumourId, String type) {
        String sql = "INSERT INTO reports(user_id, rumour_id, report_date, type) VALUES(?,?,?,?)";
        
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            pstmt.setString(2, rumourId);
            pstmt.setString(3, LocalDate.now().toString());
            pstmt.setString(4, type);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Save Report Error: " + e.getMessage());
        }
    }

    public void saveRumour(Rumour r) {
        String sql = "INSERT INTO rumours(id, title, source, created_date, credibility_score, status, verified) VALUES(?,?,?,?,?,?,?)";
        
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, r.getId());
            pstmt.setString(2, r.getTitle());
            pstmt.setString(3, r.getSource());
            pstmt.setString(4, r.getCreatedDate().toString());
            pstmt.setInt(5, r.getCredibilityScore());
            pstmt.setString(6, r.getStatus());
            pstmt.setInt(7, r.isVerified() ? 1 : 0);
            
            pstmt.executeUpdate();
            System.out.println("New rumour saved to database.");
            
        } catch (SQLException e) {
            System.out.println("Save Rumour Error: " + e.getMessage());
        }
    }
    
    public void updateRumourStatus(String id, String status, boolean verified) {
        String sql = "UPDATE rumours SET status = ?, verified = ? WHERE id = ?";
        
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, verified ? 1 : 0);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Update Rumour Error: " + e.getMessage());
        }
    }
}