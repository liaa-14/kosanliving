package com.mycompany.smartliving;

public class Pelunasan extends Transaksi {

    public Pelunasan(String idTransaksi, int nominal, Kamar kamar) {
        super(idTransaksi, nominal, kamar);
    }

    @Override
    public String getJenis() {
        return "Pelunasan";
    }

    @Override
    public boolean prosesTransaksi() {
        Kamar kamar = getKamar();

        if (!kamar.isTerisi()) {
            setPesan("Kamar " + kamar.getNomor() + " belum ada penghuninya.");
            return false;
        }
        if (kamar.getSisaTagihan() == 0) {
            setPesan("Kamar " + kamar.getNomor() + " sudah lunas, tidak ada tagihan.");
            return false;
        }
        if (getNominal() <= 0) {
            setPesan("Nominal pembayaran harus lebih dari 0.");
            return false;
        }
        if (getNominal() > kamar.getSisaTagihan()) {
            setPesan("Nominal melebihi sisa tagihan.");
            return false;
        }

        kamar.setSisaTagihan(kamar.getSisaTagihan() - getNominal());
        return true;
    }
}
