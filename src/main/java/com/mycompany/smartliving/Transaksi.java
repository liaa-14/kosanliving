package com.mycompany.smartliving;

import java.time.LocalDate;

public abstract class Transaksi {
    private String idTransaksi;
    private String tanggal;
    private int nominal;
    private Kamar kamar;
    private String pesan;

    public Transaksi(String idTransaksi, int nominal, Kamar kamar) {
        this.idTransaksi = idTransaksi;
        this.nominal = nominal;
        this.kamar = kamar;
        this.tanggal = LocalDate.now().toString();
        this.pesan = "";
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public Kamar getKamar() {
        return kamar;
    }

    // alasan gagal kalau prosesTransaksi() mengembalikan false
    public String getPesan() {
        return pesan;
    }

    protected void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public abstract String getJenis();

    // return true kalau transaksi berhasil diproses
    public abstract boolean prosesTransaksi();
}
