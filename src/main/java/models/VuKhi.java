package models;

import utilities.database.Database;
import utilities.database.Table;

public class VuKhi extends Table {

    static Database<VuKhi> db;

    public static Database<VuKhi> getDb() {
        if (db == null) {
            db = new Database<>(VuKhi.class);
        }
        return db;
    }
    static int _id = 0;

    public static int get_ID() {
        if (getDb().COUNT() > 0) {
            int a = getDb().MAP(i -> Integer.valueOf(i.getID())).max((i, j) -> i.compareTo(j)).get();
            _id = a;
        }
        return ++_id;
    }

    String vuKhi;
    int giaThanh;
    boolean thuVe;

    String phanLoai;
    String phieuNhap;

    public VuKhi() {

    }

    public VuKhi(int id, String vuKhi, int giaThanh, boolean thuVe, String phanLoai, String phieuNhap) {
        super(String.format("%d", id));
        this.vuKhi = vuKhi;
        this.giaThanh = giaThanh;
        this.thuVe = thuVe;
        this.phanLoai = phanLoai;
        this.phieuNhap = phieuNhap;
    }

    public String getVuKhi() {
        return vuKhi;
    }

    public void setVuKhi(String vuKhi) {
        this.vuKhi = vuKhi;
    }
   

    public int getGiaThanh() {
        return giaThanh;
    }

    public void setGiaThanh(int giaThanh) {
        this.giaThanh = giaThanh;
    }
    
    

    public PhanLoai getPhanLoai() {
        return PhanLoai.getDb().QUERY_ID(phanLoai);
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }
    
    public PhieuNhap getPhieuNhap() {
        return PhieuNhap.getDb().QUERY_ID(phieuNhap);
    }

    public void setPhieuNhap(String phieuNhap) {
        this.phieuNhap = phieuNhap;
    }
   

    public boolean isThuVe() {
        return thuVe;
    }

    public void setThuVe(boolean thuVe) {
        this.thuVe = thuVe;
    }

    @Override
    public Table export() {
        return new VuKhi(Integer.parseInt(id), vuKhi, giaThanh, thuVe, phanLoai, phieuNhap);
    }
}
