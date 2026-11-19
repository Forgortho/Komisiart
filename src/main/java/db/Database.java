package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final Path FILE_DATABASE = Path.of(
            System.getProperty("user.dir"), "data", "komisi.db"
    ).toAbsolutePath().normalize();

    public static Connection bukaKoneksi() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + FILE_DATABASE);
    }

    public static void prepareDatabase() throws IOException, SQLException {
        Files.createDirectories(FILE_DATABASE.getParent());
        System.out.println("DATABASE AKTIF: " + FILE_DATABASE);

        String sql = "CREATE TABLE IF NOT EXISTS komisi ("
                + "id INTEGER PRIMARY KEY, "
                + "karakter TEXT NOT NULL CHECK(length(trim(karakter)) > 0), "
                + "jenis TEXT NOT NULL CHECK(length(trim(jenis)) > 0), "
                + "harga INTEGER NOT NULL CHECK(harga > 0), "
                + "status TEXT NOT NULL CHECK(status IN ('Antri','Dikerjakan','Selesai')))";

        try (Connection koneksi = bukaKoneksi();
             Statement perintah = koneksi.createStatement()) {
            perintah.executeUpdate(sql);
        }
    }
}
