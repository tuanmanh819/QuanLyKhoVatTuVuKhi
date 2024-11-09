package models;

import utilities.database.Database;
import utilities.database.Table;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class PhanLoai extends Table {

    static Database<PhanLoai> db = null;

    public static Database<PhanLoai> getDb() {
        if (db == null) {
            db = new Database<>(PhanLoai.class);
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

    String tenPhanLoai;

    public PhanLoai() {
    }

    public PhanLoai(int id, String tenPhanLoai) {
        super(String.format("%d", id));
        this.tenPhanLoai = tenPhanLoai;
    }

    public String getTenPhanLoai() {
        return tenPhanLoai;
    }

    public void setTenPhanLoai(String tenPhanLoai) {
        this.tenPhanLoai = tenPhanLoai;
    }

    @Override
    public Table export() {
        return new PhanLoai(Integer.parseInt(id), tenPhanLoai);
    }
}
