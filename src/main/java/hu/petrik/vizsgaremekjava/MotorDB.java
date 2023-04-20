package hu.petrik.vizsgaremekjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotorDB {
    Connection conn;
    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "f1";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public MotorDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }
    public boolean createMotor(Motor f1) throws SQLException {
        String sql = "INSERT INTO motor(leiras, motorkomponens, quantity, price) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getLeiras());
        stmt.setString(2, f1.getMotorkomponens());
        stmt.setInt(3, f1.getQuantity());
        stmt.setInt(4, f1.getPrice());
        return stmt.executeUpdate() > 0;
    }
    public List<Motor> readMotor() throws SQLException {
        List<Motor> motor = new ArrayList<>();
        String sql = "SELECT * FROM motor";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String leiras = result.getString("leiras");
            String motorkomponens = result.getString("motorkomponens");
            int quantity = result.getInt("quantity");
            int price = result.getInt("price");

            Motor f1 = new Motor(id, leiras, motorkomponens, quantity, price );
            motor.add(f1);
        }
        return motor;
    }
    public boolean updateMotor(Motor f1) throws SQLException {
        String sql = "UPDATE motor " +
                "SET leiras = ?, " +
                "motorkomponens = ?, " +
                "quantity = ?, " +
                "price = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getLeiras());
        stmt.setString(2, f1.getMotorkomponens());
        stmt.setInt(3, f1.getQuantity());
        stmt.setInt(4, f1.getPrice());
        stmt.setInt(5, f1.getId());
        return stmt.executeUpdate() > 0;
    }
    public boolean deleteMotor(int id) throws SQLException {
        String sql = "DELETE FROM motor WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }
}
