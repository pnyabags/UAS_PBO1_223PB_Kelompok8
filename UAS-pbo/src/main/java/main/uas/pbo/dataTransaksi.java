package main.uas.pbo;

public class dataTransaksi {
    private int id_barang;
    private int harga_satuan;
    private int jumlah;
    private String metode_pembayaran;
    private String nama_barang;

    public dataTransaksi( int id_barang,int harga_satuan, int jumlah, String metode_pembayaran, String nama_barang) {
        this.id_barang = id_barang;
        this.harga_satuan = harga_satuan;
        this.jumlah = jumlah;
        this.metode_pembayaran = metode_pembayaran;
        this.nama_barang = nama_barang;
    }

    public int getHargaSatuan() {
        return harga_satuan;
    }

    public int getIdBarang() {
        return id_barang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getMetodePembayaran() {
        return metode_pembayaran;
    }
    
    public String getNamaBarang() {
        return nama_barang;
    }
    
    public void tambahJumlah(int jumlah) {
        this.jumlah += jumlah;
    }
    
    public void kurangJumlah(int jumlah) {
        this.jumlah -= jumlah;
    }
}
