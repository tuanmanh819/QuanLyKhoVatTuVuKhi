/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import com.toedter.calendar.DateUtil;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import models.PhanLoai;
import models.PhieuNhap;
import models.VuKhi;
import utilities.others.DateUtils;
import utilities.ui.Controller;
import utilities.ui.Ui;
import views.PhanLoaiView;
import views.PhieuNhapView;

public class PhieuNhapController implements Controller {

    List<PhieuNhap> _phieuNhap;
    PhieuNhap temp;

    PhieuNhapView panel;

    JButton addB, editB, delB, clearB;

    JDateChooser sDate, eDate;

    JTable table;

    public PhieuNhapController() {
        panel = new PhieuNhapView();

        addB = panel.getAddB();
        editB = panel.getEditB();
        delB = panel.getDelBtn();
        clearB = panel.getClrB();

        sDate = panel.getSDate();
        eDate = panel.getEDate();

        table = panel.getTable();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                onTableClick(evt);
            }
        });

        addB.addActionListener(this::insert);
        editB.addActionListener(this::edit);
        delB.addActionListener(this::delete);
        clearB.addActionListener(this::clear);

        resetState();
    }

    void updateState(boolean state) {
        addB.setEnabled(state);
        editB.setEnabled(!state);
        delB.setEnabled(!state);
    }

    @Override
    public void resetState() {
        updateState(true);

        temp = null;
        sDate.setDate(null);
        eDate.setDate(null);

        displayTable(PhieuNhap.getDb().QUERY());
    }

    void insert(ActionEvent evt) {
        
        PhieuNhap t = new PhieuNhap(PhanLoai.get_ID(), sDate.getDate(), eDate.getDate());
        PhieuNhap.getDb().ADD(t);
        resetState();
    }

    void edit(ActionEvent evt) {

//        if (sDate.getDate() == null || eDate.getDate() == null) return;
        temp.setThoiGianPhat(sDate.getDate());
        temp.setThoiGianThu(eDate.getDate());

        System.out.println(temp.getID());
        System.out.println(temp.getThoiGianPhat());
        System.out.println(temp.getThoiGianThu());

        PhieuNhap.getDb().EDIT(temp);
        resetState();
    }

    void delete(ActionEvent evt) {
        PhieuNhap.getDb().DELETE(temp);
        resetState();
    }

    void clear(ActionEvent evt) {
        resetState();
    }

    void displayTable(List<PhieuNhap> e) {
        _phieuNhap = e;

        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < _phieuNhap.size(); ++i) {
            PhieuNhap _p = _phieuNhap.get(i);

            data.add(new Object[]{
                i + 1,
                _p.getID(),
                DateUtils.date2Str(_p.getThoiGianPhat()),
                DateUtils.date2Str(_p.getThoiGianThu()),
                VuKhi.getDb().COUNT(j -> j.getPhieuNhap().equals(_p.getID()) && j.isThuVe() == true),
                VuKhi.getDb().COUNT(j -> j.getPhieuNhap().equals(_p.getID()) && j.isThuVe() == false)
            });
        }
        Ui.displayTable(table, data);
    }

    void onTableClick(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }

        temp = _phieuNhap.get(row);
        sDate.setDate(temp.getThoiGianPhat());
        eDate.setDate(temp.getThoiGianThu());

        updateState(false);
    }

    @Override
    public void setVisible(boolean aFlag) {
        panel.setVisible(aFlag);

        if (aFlag == false) {
            return;
        }
        resetState();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
