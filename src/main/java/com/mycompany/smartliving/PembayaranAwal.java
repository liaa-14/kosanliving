package com.mycompany.smartliving;

public class PembayaranAwal extends Transaksi {
    private Penghuni penghuni;

    public PembayaranAwal(String idTransaksi, int nominal, Kamar kamar, Penghuni penghuni) {
        super(idTransaksi, nominal, kamar);
        this.penghuni = penghuni;
    }

    @Override
    public String getJenis() {
        return "Pembayaran Awal";
    }

    @Override
    public boolean prosesTransaksi() {
        Kamar kamar = getKamar();

        if (kamar.isTerisi()) {
            setPesan("Kamar " + kamar.getNomor() + " sudah terisi.");
            return false;
        }
        if (getNominal() <= 0) {
            setPesan("Nominal pembayaran harus lebih dari 0.");
            return false;
        }
        if (getNominal() > kamar.getHarga()) {
            setPesan("Nominal melebihi harga sewa kamar.");
            return false;
        }

        kamar.setPenghuni(penghuni);
        kamar.setTerisi(true);
        kamar.setSisaTagihan(kamar.getHarga() - getNominal());
        return true;
    }
}
