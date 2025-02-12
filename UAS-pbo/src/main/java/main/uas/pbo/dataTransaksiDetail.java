package main.uas.pbo;


public class dataTransaksiDetail {
    private int id_transaksi;
    private String nama_barang;
    private int jumlah;

    public dataTransaksiDetail(int id_transaksi, int jumlah, String nama_barang){
        this.id_transaksi = id_transaksi;
        this.jumlah = jumlah;
        this.nama_barang = nama_barang;
    }

    public int getIdTransaksi() {
        return id_transaksi;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getNamaBarang() {
        return nama_barang;
    }
    
    public void tambahJumlah(int jumlah) {
        this.jumlah += jumlah;
    }   
}
