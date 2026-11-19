package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Database;
import model.Komisi;

public class KomisiRepository {

    public void add(Komisi komisi) throws SQLException {
        String sql = "INSERT INTO komisi (karakter, jenis, harga, status) VALUES (?, ?, ?, ?)";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setString(1, komisi.getKarakter());
            perintah.setString(2, komisi.getJenis());
            perintah.setLong(3, komisi.getHarga());
            perintah.setString(4, komisi.getStatus());
            perintah.executeUpdate();
        }
    }

    // jenis null = semua jenis
    public ArrayList<Komisi> getAll(String jenis) throws SQLException {
        ArrayList<Komisi> daftar = new ArrayList<Komisi>();
        String sql = "SELECT id, karakter, jenis, harga, status FROM komisi";
        if (jenis != null) {
            sql += " WHERE jenis = ?";
        }
        sql += " ORDER BY id DESC";

        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            if (jenis != null) {
                perintah.setString(1, jenis);
            }
            try (ResultSet hasil = perintah.executeQuery()) {
                while (hasil.next()) {
                    daftar.add(new Komisi(
                            hasil.getLong("id"),
                            hasil.getString("karakter"),
                            hasil.getString("jenis"),
                            hasil.getLong("harga"),
                            hasil.getString("status")));
                }
            }
        }
        return daftar;
    }

    public void updateStatus(long id, String statusBaru) throws SQLException {
        String sql = "UPDATE komisi SET status = ? WHERE id = ?";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setString(1, statusBaru);
            perintah.setLong(2, id);
            perintah.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM komisi WHERE id = ?";
        try (Connection koneksi = Database.bukaKoneksi();
             PreparedStatement perintah = koneksi.prepareStatement(sql)) {
            perintah.setLong(1, id);
            perintah.executeUpdate();
        }
    }
}
