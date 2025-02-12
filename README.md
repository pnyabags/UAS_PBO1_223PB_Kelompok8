# Final Proyek Pemrograman Berorientasi Obyek 1 
- Mata Kuliah: Pemrograman Berorientasi Obyek 1
- Dosen Pengampu: Muhammad Ikhwan Fathulloh
  
## Kelompok
Proyek: KasirinYUK (Project UAS OOP 1)
Dikembangkan oleh Kelompok 8  
- Ketua: Bagas Arya Putra  [Github](https://github.com/pnyabags)
- Anggota 1: Rafi Nur Muhammad Fauzi [Github](https://github.com/RafiNur06) 
- Anggota 2: Lesi Siti Nur Anjani  [Github](https://github.com/LesiSitiNurAnjani)
  
## Judul Studi Kasus
Transformasi Manajemen Kasir dengan KasirinYUK

## Penjelasan Studi Kasus
**KasirinYUK** adalah aplikasi Point of Sale (POS) ringan yang dapat berjalan secara offline dan dirancang untuk mendukung usaha kecil dan menengah (UKM) dalam mengelola transaksi penjualan dengan efisien. 
Dengan fokus pada penggunaan offline dan kemudahan operasional, Kasirin menawarkan solusi yang andal bagi bisnis yang ingin mempercepat proses transaksi tanpa harus bergantung pada koneksi internet.

Aplikasi berbasis Java ini memungkinkan pemilik usaha untuk menangani berbagai aktivitas retail seperti manajemen inventaris, pemantauan stok secara real-time, serta pembuatan laporan keuangan yang terstruktur, semuanya dalam satu kendali. 
Kasirin memastikan proses transaksi menjadi lebih cepat, mengurangi kesalahan manusia dalam pencatatan, dan menyediakan gambaran menyeluruh mengenai kinerja bisnis melalui laporan yang lengkap.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

## Fitur
- Transaksi cepat dan mudah. ✔️
- Kelola barang dengan sistem stok otomatis. ✔️
- Laporan transaksi harian, bulanan, dan tahunan. ✔️
- Metode pembayaran fleksibel. ✔️
- Ekspor data ke CSV. ✔️

## Penjelasan 4 Pilar OOP dalam Studi Kasus
### 1. Inheritance
```java
public abstract class dataUser {
    protected int id_user;
    protected String username;
    protected String password;
    protected String jabatan;

    public dataUser(int id_user, String username, String password, String jabatan) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.jabatan = jabatan;
    }
}

public class dataAdmin extends dataUser {
    public dataAdmin(int id_user, String username, String password) {
        super(id_user, username, password, "admin");
    }
}
```

### 2. Encapsulation
```java
public class dataBarang {
    private int id_barang;
    private String nama_barang;
    private String jenis_barang;
    private int harga;
    private int stok;

    public dataBarang(int id_barang, String nama_barang, String jenis_barang, int harga, int stok) {
        this.id_barang = id_barang;
        this.nama_barang = nama_barang;
        this.jenis_barang = jenis_barang;
        this.harga = harga;
        this.stok = stok;
    }

    public int getIdBarang() {
        return id_barang;
    }
    
    public void kurangiStok(int jumlah) {
        this.stok -= jumlah;
    }
}
```

### 3. Polymorphism
```java
public abstract class dataUser {
    public abstract void displayRole();
}
public class dataAdmin extends dataUser {
    @Override
    public void displayRole() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("User Role");
    alert.setHeaderText("Role: " + jabatan);
    alert.setContentText("Selamat Datang " + username + " dan Semangat Bekerja!");
    alert.showAndWait();
    }
}
```

### 4. Abstract
```java
public abstract class dataUser {
    protected int id_user;
    protected String username;
    protected String password;
    protected String jabatan;

    public dataUser(int id_user, String username, String password, String jabatan) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.jabatan = jabatan;
    }

    public int getIdUser() { return id_user; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getJabatan() { return jabatan; }

    public abstract void displayRole();
}

```
## Struktur Tabel Aplikasi
### 1. Tabel user
- id_user : int(3)
- username : varchar(25)
- password : varchar(255)
- jabatan : varchar(10)

### 2. Tabel barang
- id_barang : int(3)
- nama_barang : varchar(15)
- jenis_barang : varchar(35)
- harga : double
- stok : int(5)

### 3. Tabel transaksi
- id_transaksi : int(3)
- date : date
- total : double
- metode_pembayaran : varchar(10)

### 4. Tabel transaksi_detail
- id_transaksi : int(3)
- id_barang : int(3)
- jumlah : int(5)

### 5. Tabel laporan
- id_laporan : int(3)
- id_transaksi : int(3)

## Tampilan Aplikasi
- Google Dokumen: [GoogleDocs](https://docs.google.com/document/d/1RNku4pEiWBdnHIjhDPk1zvWFHPjkNn1HjUj-5MrBBn4/edit?usp=sharing)
## Demo Aplikasi
- Clone: [Github](https://github.com/pnyabags/UAS_PBO1_223PB_Kelompok8.git)

Fakultas Industri Kreatif, Departemen Teknik Informatika,  
Universitas Teknologi Bandung, 2025
