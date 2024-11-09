package models;

import utilities.database.Database;
import utilities.database.Table;
import utilities.others.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import static models.PhanLoai._id;
import static models.PhanLoai.getDb;

public class PhieuNhap extends Table {
    
    static Database<PhieuNhap> db = null;
    
    public static Database<PhieuNhap> getDb() {
        if (db == null) {
            db = new Database<>(PhieuNhap.class);
            
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
    
    Date thoiGianPhat, thoiGianThu;
    
    public PhieuNhap() {
        
    }
    
    public PhieuNhap(int id, Date thoiGianP, Date thoiGianT) {
        super(String.format("%d", id));
        this.thoiGianPhat = new Date(thoiGianP.getTime());
        this.thoiGianThu = new Date(thoiGianT.getTime());
    }
    
    public Date getThoiGianPhat() {
        return thoiGianPhat;
    }
    
    public void setThoiGianPhat(Date thoiGian) {
        this.thoiGianPhat = new Date(thoiGian.getTime());
    }
    
    public Date getThoiGianThu() {
        return thoiGianThu;
    }
    
    public void setThoiGianThu(Date thoiGianThu) {
        this.thoiGianThu = thoiGianThu;
    }
    
    @Override
    public Table export() {
        return new PhieuNhap(Integer.parseInt(id), thoiGianPhat, thoiGianThu);
    }
}
