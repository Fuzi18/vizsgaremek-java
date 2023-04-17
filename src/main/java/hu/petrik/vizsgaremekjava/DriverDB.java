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
    public boolean createDriver(Driver driver) throws SQLException {
        String sql = "INSERT INTO f1(nev, kor, nemzetiseg, csapat, szrzettpontok, kategoria, helyezes) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, driver.getNev());
        stmt.setInt(2, driver.getKor());
        stmt.setString(3, driver.getNemzetiseg());
        stmt.setString(4, driver.getCsapat());
        stmt.setInt(5, driver.getSzerzettpontok());
        stmt.setString(6, driver.getKategoria());
        stmt.setInt(7, driver.getHelyezes());
        return stmt.executeUpdate() > 0;
    }

    public List<Driver> readDriver() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM f1";
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

            Driver driver = new Driver(id, nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes);
            drivers.add(driver);
        }
        return drivers;
    }

    public boolean updateDriver(Driver driver) throws SQLException {
        String sql = "UPDATE drivers " +
                "SET pilota = ?, " +
                "kor = ?, " +
                "nemzetiseg = ?" +
                "csapat = ?" +
                "szerzettpontok = ?" +
                "kategoria = ?" +
                "helyezes = ?" +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, driver.getNev());
        stmt.setInt(2, driver.getKor());
        stmt.setString(3, driver.getNemzetiseg());
        stmt.setString(4, driver.getCsapat());
        stmt.setInt(5, driver.getSzerzettpontok());
        stmt.setString(6, driver.getKategoria());
        stmt.setInt(7, driver.getHelyezes());
        stmt.setInt(8, driver.getId());
        return stmt.executeUpdate() > 0;
    }
    public boolean deleteDriver(int id) throws SQLException {
        String sql = "DELETE FROM f1 WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }
}
