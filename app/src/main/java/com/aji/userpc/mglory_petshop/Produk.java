package com.aji.userpc.mglory_petshop;

/**
 * Created by user pc on 3/17/2018.
 */

public class Produk {
    private String id;
    private String namaProduk;
    private String hargaProduk;
    private String deskripsiProduk;
    private String kategoriProduk;

    public Produk(){

    }

    public Produk(String id, String namaProduk, String hargaProduk, String deskripsiProduk, String kategoriProduk) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.kategoriProduk = kategoriProduk;
    }

    public Produk(String id, String namaProduk, String hargaProduk, String kategoriProduk) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.kategoriProduk = kategoriProduk;
    }



    public String getId() {
        return id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public String getDeskripsiProduk() {
        return deskripsiProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }
}
