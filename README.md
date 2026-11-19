# Komisiart
Aplikasi manajemen data komisi seni (art commission) berbasis Java dan Maven dengan antarmuka FXML serta dukungan penyimpanan basis data.
KomisiArt/
├── src/
│   └── main/
│       ├── java/
│       │   ├── controller/
│       │   │   └── KomisiController.java      # Pengatur logika UI & alur interaksi pengguna
│       │   ├── data/
│       │   │   └── KomisiRepository.java      # Operasi akses dan pengelolaan data komisi
│       │   ├── db/
│       │   │   └── Database.java              # Konfigurasi & koneksi basis data
│       │   ├── main/
│       │   │   └── MainApp.java               # Titik masuk utama (entry point) aplikasi
│       │   └── model/
│       │       └── Komisi.java                # Representasi entitas/objek data komisi
│       └── resources/
│           ├── image/                         # Aset gambar (logo, background, jenis komisi, dll.)
│           │   ├── background.png
│           │   ├── fullbody.png
│           │   ├── headshot.png
│           │   ├── logo.png
│           │   ├── sedada.png
│           │   └── splashart.png
│           └── view/
│               ├── styles/
│               │   └── app.css                # Pengaturan gaya tampilan antarmuka
│               └── komisi.fxml                # Tata letak antarmuka pengguna (UI)
└── pom.xml                                    # Konfigurasi dependensi & build Maven

Clone repositori ke komputer lokal:Bashgit clone <https://github.com/Forgortho/Komisiart>
cd KomisiArt
Unduh dependensi dan kompilasi proyek menggunakan Maven:Bashmvn clean compile
```

Jalankan aplikasi melalui Maven atau jalankan kelas main.MainApp langsung dari IDE:Bashmvn javafx:run
(Atau jalankan MainApp.java melalui IDE / perintah mvn exec:java sesuai konfigurasi plugin di pom.xml.)   
Cara MenggunakanBuka Aplikasi: Saat dijalankan melalui MainApp, antarmuka utama akan dimuat dari komisi.fxml lengkap dengan gaya visual dari app.css serta aset gambar di folder resources/image/.   Kelola Data Komisi: Gunakan kontrol pada antarmuka yang terhubung ke KomisiController untuk menambah, melihat, memperbarui, atau mengelola data komisi (model Komisi).   Penyimpanan Data: Data diproses melalui KomisiRepository dan dihubungkan ke penyimpanan/basis data melalui kelas 
