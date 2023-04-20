package hu.petrik.vizsgaremekjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDB {

    Connection conn;
    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_DBNAME = "f1";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public DriverDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }
    public boolean createDriver(Driver f1) throws SQLException {
        String sql = "INSERT INTO pilotak(nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getNev());
        stmt.setInt(2, f1.getKor());
        stmt.setString(3, f1.getNemzetiseg());
        stmt.setString(4, f1.getCsapat());
        stmt.setInt(5, f1.getSzerzettpontok());
        stmt.setString(6, f1.getKategoria());
        stmt.setInt(7, f1.getHelyezes());
        return stmt.executeUpdate() > 0;
    }

    public List<Driver> readDriver() throws SQLException {
        List<Driver> pilotak = new ArrayList<>();
        String sql = "SELECT * FROM pilotak";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            int kor = result.getInt("kor");
            String nemzetiseg = result.getString("nemzetiseg");
            String csapat = result.getString("csapat");
            int szerzettpontok = result.getInt("szerzettpontok");
            String kategoria = result.getString("kategoria");
            int helyezes = result.getInt("helyezes");

            Driver f1 = new Driver(id, nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes);
            pilotak.add(f1);
        }
        return pilotak;
    }

    public boolean updateDriver(Driver f1) throws SQLException {
        String sql = "UPDATE pilotak " +
                "SET nev = ?, " +
                "kor = ?, " +
                "nemzetiseg = ?, " +
                "csapat = ?, " +
                "szerzettpontok = ?, " +
                "kategoria = ?," +
                "helyezes = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, f1.getNev());
        stmt.setInt(2, f1.getKor());
        stmt.setString(3, f1.getNemzetiseg());
        stmt.setString(4, f1.getCsapat());
        stmt.setInt(5, f1.getSzerzettpontok());
        stmt.setString(6, f1.getKategoria());
        stmt.setInt(7, f1.getHelyezes());
        stmt.setInt(8, f1.getId());
        return stmt.executeUpdate() > 0;
    }
    public boolean deleteDriver(int id) throws SQLException {
        String sql = "DELETE FROM pilotak WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
