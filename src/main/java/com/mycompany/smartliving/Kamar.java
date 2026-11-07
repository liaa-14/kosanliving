package com.mycompany.smartliving;

public class Kamar {
    private String nomor;
    private int harga;
    private boolean terisi;
    private int sisaTagihan;
    private Penghuni penghuni;

    public Kamar(String nomor, int harga) {
        this.nomor = nomor;
        this.harga = harga;
        this.terisi = false;
        this.sisaTagihan = 0;
        this.penghuni = null;
    }

    public String getNomor() {
        return nomor;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        if (harga > 0) {
            this.harga = harga;
        }
    }

    public boolean isTerisi() {
        return terisi;
    }

    public void setTerisi(boolean terisi) {
        this.terisi = terisi;
    }

    public int getSisaTagihan() {
        return sisaTagihan;
    }

    public void setSisaTagihan(int sisaTagihan) {
        if (sisaTagihan >= 0) {
            this.sisaTagihan = sisaTagihan;
        }
    }

    public Penghuni getPenghuni() {
        return penghuni;
    }

    public void setPenghuni(Penghuni penghuni) {
        this.penghuni = penghuni;
    }

    public String getStatus() {
        return terisi ? "Full" : "Kosong";
    }

    public String getNamaPenghuni() {
        return penghuni == null ? "-" : penghuni.getNama();
    }
}
