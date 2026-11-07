package com.mycompany.smartliving;

import java.util.ArrayList;
import java.util.Locale;

public class DataKost {
    private ArrayList<Kamar> daftarKamar = new ArrayList<>();
    private ArrayList<Transaksi> riwayat = new ArrayList<>();
    private String pesanTerakhir = "";

    public void tambahKamar(Kamar kamar) {
        daftarKamar.add(kamar);
    }

    public static String rupiah(int angka) {
        return "Rp" + String.format(Locale.forLanguageTag("id-ID"), "%,d", angka);
    }

    public ArrayList<Kamar> getDaftarKamar() {
        return daftarKamar;
    }

    public ArrayList<Transaksi> getRiwayat() {
        return riwayat;
    }

    public String getPesanTerakhir() {
        return pesanTerakhir;
    }

    public Kamar cariKamar(String nomor) {
        for (Kamar k : daftarKamar) {
            if (k.getNomor().equals(nomor)) {
                return k;
            }
        }
        return null;
    }

    private String buatId() {
        return String.format("TRX-%03d", riwayat.size() + 1);
    }

    public boolean bayarAwal(String nomor, String nama, String noHp, int nominal) {
        Kamar kamar = cariKamar(nomor);
        if (kamar == null) {
            pesanTerakhir = "Kamar " + nomor + " tidak ditemukan.";
            return false;
        }
        if (nama.isEmpty() || noHp.isEmpty()) {
            pesanTerakhir = "Nama dan nomor HP penghuni wajib diisi.";
            return false;
        }

        Penghuni penghuni = new Penghuni(nama, noHp);
        Transaksi t = new PembayaranAwal(buatId(), nominal, kamar, penghuni);
        if (t.prosesTransaksi()) {
            riwayat.add(t);
            pesanTerakhir = "Pembayaran berhasil dicatat (" + t.getIdTransaksi() + "). Sisa tagihan: "
                    + rupiah(kamar.getSisaTagihan());
            return true;
        }
        pesanTerakhir = t.getPesan();
        return false;
    }

    public boolean lunasi(String nomor, int nominal) {
        Kamar kamar = cariKamar(nomor);
        if (kamar == null) {
            pesanTerakhir = "Kamar " + nomor + " tidak ditemukan.";
            return false;
        }

        Transaksi t = new Pelunasan(buatId(), nominal, kamar);
        if (t.prosesTransaksi()) {
            riwayat.add(t);
            if (kamar.getSisaTagihan() == 0) {
                pesanTerakhir = "Pembayaran berhasil dicatat (" + t.getIdTransaksi() + "). Status: LUNAS";
            } else {
                pesanTerakhir = "Pembayaran berhasil dicatat (" + t.getIdTransaksi() + "). Sisa tagihan: "
                        + rupiah(kamar.getSisaTagihan());
            }
            return true;
        }
        pesanTerakhir = t.getPesan();
        return false;
    }
}
