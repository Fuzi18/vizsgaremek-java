package hu.petrik.vizsgaremekjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VezerloegysegDB {
    Connection conn;
    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "f1";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public VezerloegysegDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }
    public boolean createVezerloegyseg(Vezerloegyseg f1) throws SQLException {
        String sql = "INSERT INTO vezerloegyseg(leiras, vezerloegysegkomponens, quantity, price) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getLeiras());
        stmt.setString(2, f1.getVezerloegysegkomponens());
        stmt.setInt(3, f1.getQuantity());
        stmt.setInt(4, f1.getPrice());
        return stmt.executeUpdate() > 0;
    }
    public List<Vezerloegyseg> readVezerloegyseg() throws SQLException {
        List<Vezerloegyseg> vezerloegyseg = new ArrayList<>();
        String sql = "SELECT * FROM vezerloegyseg";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String leiras = result.getString("leiras");
            String vezerloegysegkomponens = result.getString("vezerloegysegkomponens");
            int quantity = result.getInt("quantity");
            int price = result.getInt("price");

            Vezerloegyseg f1 = new Vezerloegyseg(id, leiras, vezerloegysegkomponens, quantity, price );
            vezerloegyseg.add(f1);
        }
        return vezerloegyseg;
    }
    public boolean updateVezerloegyseg(Vezerloegyseg f1) throws SQLException {
        String sql = "UPDATE vezerloegyseg " +
                "SET leiras = ?, " +
                "vezerloegysegkomponens = ?, " +
                "quantity = ?" +
                "price = ?" +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getLeiras());
        stmt.setString(2, f1.getVezerloegysegkomponens());
        stmt.setInt(3, f1.getQuantity());
        stmt.setInt(4, f1.getPrice());
        stmt.setInt(5, f1.getId());
        return stmt.executeUpdate() > 0;
    }
    public boolean deleteVezerloegyseg(int id) throws SQLException {
        String sql = "DELETE FROM vezerloegyseg WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }
}
