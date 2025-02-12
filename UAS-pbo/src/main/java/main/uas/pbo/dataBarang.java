package main.uas.pbo;

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

    public String getNamaBarang() {
        return nama_barang;
    }

    public String getJenisBarang() {
        return jenis_barang;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }
    
    public void setIdBarang(int id) {
        this.id_barang = id;
    }
    
    public void setNamaBarang(String barang) {
        this.nama_barang = barang;
    }
    
    public void kurangiStok(int jumlah) {
        this.stok -= jumlah;
    }
    
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }
}
