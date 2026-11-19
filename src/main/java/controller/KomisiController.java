package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import data.KomisiRepository;
import model.Komisi;

public class KomisiController {

    private static final String SEMUA = "Semua Jenis";

    // Daftar jenis: nama tampil, harga default (Rp), nama file gambar.
    // Ubah harga di sini sesuai harga komisimu.
    private static final String[] JENIS = {"Headshot", "Sedada", "Full Body", "Background", "Splash Art"};
    private static final long[] HARGA = {50000, 80000, 150000, 100000, 300000};
    private static final String[] FOTO = {"headshot.png", "sedada.png", "fullbody.png",
        "background.png", "splashart.png"};

    @FXML private ImageView logoView;
    @FXML private TextField karakterField;
    @FXML private TextField hargaField;
    @FXML private ComboBox<String> jenisBox;
    @FXML private ComboBox<String> filterBox;
    @FXML private Label pesanLabel;
    @FXML private Label jumlahLabel;
    @FXML private TilePane katalogPane;

    private final KomisiRepository repository = new KomisiRepository();

    @FXML
    private void initialize() {
        URL lokasiLogo = getClass().getResource("/image/logo.png");
        if (lokasiLogo != null) {
            logoView.setImage(new Image(lokasiLogo.toExternalForm()));
        }

        filterBox.getItems().add(SEMUA);
        for (String j : JENIS) {
            jenisBox.getItems().add(j);
            filterBox.getItems().add(j);
        }
        jenisBox.setValue(JENIS[0]);
        hargaField.setText(String.valueOf(HARGA[0]));
        filterBox.setValue(SEMUA);

        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private int indeksJenis(String jenis) {
        for (int i = 0; i < JENIS.length; i++) {
            if (JENIS[i].equals(jenis)) {
                return i;
            }
        }
        return -1;
    }

    @FXML
    private void jenisChanged() {
        int i = indeksJenis(jenisBox.getValue());
        if (i >= 0) {
            hargaField.setText(String.valueOf(HARGA[i]));
        }
    }

    @FXML
    private void filterChanged() {
        try {
            showData();
        } catch (SQLException e) {
            pesanLabel.setText("Gagal membaca database. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void showData() throws SQLException {
        String pilihan = filterBox.getValue();
        String filter = (pilihan == null || pilihan.equals(SEMUA)) ? null : pilihan;

        ArrayList<Komisi> daftar = repository.getAll(filter);
        katalogPane.getChildren().clear();
        for (Komisi komisi : daftar) {
            katalogPane.getChildren().add(createCard(komisi));
        }
        jumlahLabel.setText("Jumlah komisi: " + daftar.size());
    }

    private VBox createCard(Komisi komisi) {
        StackPane bingkai = new StackPane();
        bingkai.setPrefSize(180, 112);
        bingkai.setMinSize(180, 112);
        bingkai.setMaxSize(180, 112);

        int i = indeksJenis(komisi.getJenis());
        URL lokasi = (i >= 0) ? getClass().getResource("/image/" + FOTO[i]) : null;
        if (lokasi != null) {
            ImageView fotoView = new ImageView(
                    new Image(lokasi.toExternalForm(), 180, 112, true, true));
            bingkai.getChildren().add(fotoView);
        } else {
            bingkai.getChildren().add(new Label("Gambar tidak tersedia"));
        }

        Label namaLabel = new Label(komisi.getKarakter());
        namaLabel.setWrapText(true);
        namaLabel.getStyleClass().add("nama-karakter");

        Label jenisLabel = new Label(komisi.getJenis());
        jenisLabel.getStyleClass().add("badge-jenis");

        Label hargaLabel = new Label("Rp" + komisi.getHarga());

        Label statusLabel = new Label(komisi.getStatus());
        statusLabel.getStyleClass().add("status");
        statusLabel.getStyleClass().add("status-" + komisi.getStatus().toLowerCase());

        Button lanjutButton = new Button();
        lanjutButton.getStyleClass().add("button-primary");
        String berikutnya = null;
        if (komisi.getStatus().equals("Antri")) {
            berikutnya = "Dikerjakan";
            lanjutButton.setText("Mulai Dikerjakan");
        } else if (komisi.getStatus().equals("Dikerjakan")) {
            berikutnya = "Selesai";
            lanjutButton.setText("Tandai Selesai");
        } else {
            lanjutButton.setText("Selesai");
            lanjutButton.setDisable(true);
        }
        final String statusBaru = berikutnya;
        lanjutButton.setOnAction(e -> changeStatus(komisi, statusBaru));

        Button hapusButton = new Button("Hapus");
        hapusButton.getStyleClass().add("button-hapus");
        hapusButton.setOnAction(e -> deleteKomisi(komisi));

        HBox tombol = new HBox(8, lanjutButton, hapusButton);

        VBox kartu = new VBox();
        kartu.setSpacing(8);
        kartu.setMinWidth(220);
        kartu.setPrefWidth(220);
        kartu.setMaxWidth(220);
        kartu.getStyleClass().add("kartu");
        kartu.getChildren().addAll(bingkai, namaLabel, jenisLabel, hargaLabel, statusLabel, tombol);
        return kartu;
    }

    private void changeStatus(Komisi komisi, String statusBaru) {
        if (statusBaru == null) {
            return;
        }
        try {
            repository.updateStatus(komisi.getId(), statusBaru);
            showData();
            pesanLabel.setText(komisi.getKarakter() + " sekarang: " + statusBaru);
        } catch (SQLException e) {
            pesanLabel.setText("Gagal mengubah status. Lihat Output.");
            e.printStackTrace();
        }
    }

    private void deleteKomisi(Komisi komisi) {
        try {
            repository.delete(komisi.getId());
            showData();
            pesanLabel.setText("Komisi " + komisi.getKarakter() + " dihapus.");
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menghapus. Lihat Output.");
            e.printStackTrace();
        }
    }

    @FXML
    private void saveKomisi() {
        Komisi komisi;
        try {
            String karakter = karakterField.getText();
            String jenis = jenisBox.getValue();
            long harga = Long.parseLong(hargaField.getText().trim());
            komisi = new Komisi(karakter, jenis, harga);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus angka bulat, contoh 80000.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }

        try {
            repository.add(komisi);
        } catch (SQLException e) {
            pesanLabel.setText("Gagal menyimpan. Lihat Output.");
            e.printStackTrace();
            return;
        }

        karakterField.clear();
        try {
            showData();
            pesanLabel.setText("Komisi berhasil disimpan.");
        } catch (SQLException e) {
            pesanLabel.setText("Data tersimpan, tetapi daftar gagal dimuat ulang.");
            e.printStackTrace();
        }
    }
}
