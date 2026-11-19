package model;

public class Komisi {

    private long id;
    private String karakter;
    private String jenis;
    private long harga;
    private String status;

    public Komisi(long id, String karakter, String jenis, long harga, String status) {
        if (karakter == null || karakter.isBlank()) {
            throw new IllegalArgumentException("Nama karakter wajib diisi.");
        }
        if (jenis == null || jenis.isBlank()) {
            throw new IllegalArgumentException("Pilih jenis komisi.");
        }
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga harus lebih dari nol.");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status wajib diisi.");
        }
        this.id = id;
        this.karakter = karakter.trim();
        this.jenis = jenis;
        this.harga = harga;
        this.status = status;
    }

    // Komisi baru dari form: id 0, status awal "Antri".
    public Komisi(String karakter, String jenis, long harga) {
        this(0, karakter, jenis, harga, "Antri");
    }

    public long getId() { return id; }
    public String getKarakter() { return karakter; }
    public String getJenis() { return jenis; }
    public long getHarga() { return harga; }
    public String getStatus() { return status; }
}
