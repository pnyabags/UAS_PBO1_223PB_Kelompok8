package main.uas.pbo;

import java.time.LocalDate;

public class dataLaporan {
    private int id_transaksi;
    private int total;
    private LocalDate date;
    private String metode_pembayaran;

    public dataLaporan(int id_transaksi,int total, String metode_pembayaran, LocalDate date) {
        this.id_transaksi = id_transaksi;
        this.total = total;
        this.metode_pembayaran = metode_pembayaran;
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public int getIdTransaksi() {
        return id_transaksi;
    }

    public String getMetodePembayaran() {
        return metode_pembayaran;
    }
    
    public LocalDate getDate() {
        return date;
    }
}
