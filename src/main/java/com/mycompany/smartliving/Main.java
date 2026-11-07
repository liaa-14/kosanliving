package com.mycompany.smartliving;

import java.util.Scanner;

// Program utama versi terminal SmartKost (input memakai Scanner)
public class Main {

    static Scanner input = new Scanner(System.in);
    static DataKost data = new DataKost();

    static String bacaTeks(String pesan) {
        String teks = "";
        while (teks.isEmpty()) {
            System.out.print(pesan);
            teks = input.nextLine().trim();
            if (teks.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
            }
        }
        return teks;
    }

    static int bacaAngka(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    static void tampilkanKamar(String filter) {
        System.out.println();
        System.out.printf("%-8s %-12s %-10s %-18s %-12s%n",
                "Kamar", "Harga", "Status", "Penghuni", "Sisa Tagihan");
        System.out.println("-----------------------------------------------------------------");
        int jumlah = 0;
        for (Kamar k : data.getDaftarKamar()) {
            if (filter.equals("kosong") && k.isTerisi()) {
                continue;
            }
            if (filter.equals("full") && !k.isTerisi()) {
                continue;
            }
            String sisa = k.isTerisi() ? DataKost.rupiah(k.getSisaTagihan()) : "-";
            System.out.printf("%-8s %-12s %-10s %-18s %-12s%n",
                    k.getNomor(), DataKost.rupiah(k.getHarga()), k.getStatus(), k.getNamaPenghuni(), sisa);
            jumlah++;
        }
        if (jumlah == 0) {
            System.out.println("(tidak ada data)");
        }
    }

    static void inputPembayaran() {
        System.out.println();
        System.out.println("=== Input Pembayaran Awal ===");
        String nomor = bacaTeks("Nomor kamar     : ");
        Kamar kamar = data.cariKamar(nomor);
        if (kamar == null) {
            System.out.println("Kamar " + nomor + " tidak ditemukan.");
            return;
        }
        if (kamar.isTerisi()) {
            System.out.println("Kamar " + nomor + " sudah terisi.");
            return;
        }
        System.out.println("Harga sewa kamar " + nomor + " : " + DataKost.rupiah(kamar.getHarga()));
        String nama = bacaTeks("Nama penghuni   : ");
        String hp = bacaTeks("Nomor HP        : ");
        int bayar = bacaAngka("Jumlah bayar    : ");

        data.bayarAwal(nomor, nama, hp, bayar);
        System.out.println(data.getPesanTerakhir());
    }

    static void pelunasan() {
        System.out.println();
        System.out.println("=== Pelunasan Kost ===");
        String nomor = bacaTeks("Nomor kamar     : ");
        Kamar kamar = data.cariKamar(nomor);
        if (kamar == null) {
            System.out.println("Kamar " + nomor + " tidak ditemukan.");
            return;
        }
        if (!kamar.isTerisi()) {
            System.out.println("Kamar " + nomor + " belum ada penghuninya.");
            return;
        }
        System.out.println("Penghuni     : " + kamar.getNamaPenghuni());
        System.out.println("Sisa tagihan : " + DataKost.rupiah(kamar.getSisaTagihan()));
        if (kamar.getSisaTagihan() == 0) {
            System.out.println("Kamar ini sudah lunas.");
            return;
        }
        int bayar = bacaAngka("Jumlah bayar    : ");

        data.lunasi(nomor, bayar);
        System.out.println(data.getPesanTerakhir());
    }

    static void tampilkanRiwayat() {
        System.out.println();
        System.out.printf("%-9s %-12s %-17s %-8s %-12s%n",
                "ID", "Tanggal", "Jenis", "Kamar", "Nominal");
        System.out.println("-----------------------------------------------------------");
        if (data.getRiwayat().isEmpty()) {
            System.out.println("(belum ada transaksi)");
            return;
        }
        for (Transaksi t : data.getRiwayat()) {
            System.out.printf("%-9s %-12s %-17s %-8s %-12s%n",
                    t.getIdTransaksi(), t.getTanggal(), t.getJenis(),
                    t.getKamar().getNomor(), DataKost.rupiah(t.getNominal()));
        }
    }

    public static void main(String[] args) {
        // membuat objek Kamar di main class
        Kamar kamar1 = new Kamar("101", 800000);
        Kamar kamar2 = new Kamar("102", 800000);
        Kamar kamar3 = new Kamar("103", 850000);
        Kamar kamar4 = new Kamar("104", 900000);
        Kamar kamar5 = new Kamar("105", 950000);
        data.tambahKamar(kamar1);
        data.tambahKamar(kamar2);
        data.tambahKamar(kamar3);
        data.tambahKamar(kamar4);
        data.tambahKamar(kamar5);

        boolean jalan = true;
        while (jalan) {
            System.out.println();
            System.out.println("===== SMARTKOST =====");
            System.out.println("1. Lihat semua kamar");
            System.out.println("2. Lihat kamar kosong");
            System.out.println("3. Lihat kamar full");
            System.out.println("4. Input pembayaran awal");
            System.out.println("5. Pelunasan kost");
            System.out.println("6. Riwayat transaksi");
            System.out.println("0. Keluar");
            int pilihan = bacaAngka("Pilih menu: ");

            switch (pilihan) {
                case 1:
                    tampilkanKamar("semua");
                    break;
                case 2:
                    tampilkanKamar("kosong");
                    break;
                case 3:
                    tampilkanKamar("full");
                    break;
                case 4:
                    inputPembayaran();
                    break;
                case 5:
                    pelunasan();
                    break;
                case 6:
                    tampilkanRiwayat();
                    break;
                case 0:
                    jalan = false;
                    System.out.println("Terima kasih telah menggunakan SmartKost.");
                    break;
                default:
                    System.out.println("Menu tidak tersedia.");
            }
        }
        input.close();
    }
}
