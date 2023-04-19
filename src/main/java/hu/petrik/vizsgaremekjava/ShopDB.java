package hu.petrik.vizsgaremekjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDB {

    Connection conn;
    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "f1";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public ShopDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }
    public boolean createShop(Shop f1) throws SQLException {
        String sql = "INSERT INTO shop(type, team, size, color, price, quantity) VALUES (?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getType());
        stmt.setString(3, f1.getTeam());
        stmt.setString(4, f1.getSize());
        stmt.setString(4, f1.getColor());
        stmt.setInt(5, f1.getPrice());
        stmt.setInt(7, f1.getQuantity());
        return stmt.executeUpdate() > 0;
    }
    public List<Shop> readShop() throws SQLException {
        List<Shop> shop = new ArrayList<>();
        String sql = "SELECT * FROM shop";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String type = result.getString("type");
            String team = result.getString("team");
            String size = result.getString("size");
            String color = result.getString("color");
            int price = result.getInt("price");
            int quantity = result.getInt("quantity");

            Shop f1 = new Shop(id, type, team, size, color, price, quantity);
            shop.add(f1);
        }
        return shop;
    }
    public boolean updateShop(Shop f1) throws SQLException {
        String sql = "UPDATE shop " +
                "SET type = ?, " +
                "team = ?, " +
                "size = ?" +
                "color = ?" +
                "price = ?" +
                "quantity = ?" +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getType());
        stmt.setString(2, f1.getTeam());
        stmt.setString(3, f1.getSize());
        stmt.setString(4, f1.getColor());
        stmt.setInt(5, f1.getPrice());
        stmt.setInt(6, f1.getQuantity());
        stmt.setInt(7, f1.getId());
        return stmt.executeUpdate() > 0;
    }
    public boolean deleteShop(int id) throws SQLException {
        String sql = "DELETE FROM shop WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }
}
