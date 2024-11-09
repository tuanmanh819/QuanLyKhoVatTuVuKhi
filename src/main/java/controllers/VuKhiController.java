/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import app.Controllers;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import models.PhanLoai;
import models.PhieuNhap;
import models.VuKhi;
import utilities.ui.Controller;
import utilities.ui.Ui;
import views.PhanLoaiView;
import views.VuKhiView;

public class VuKhiController implements Controller {

    List<VuKhi> _vuKhi;
    VuKhi temp;

    VuKhiView panel;

    JButton addB, editB, delB, clearB, findB;
    JSpinner priceF, maxF, minF;

    JTextField nameF;

    JComboBox phanloai, phieuNhap;

    JCheckBox thuhoi;

    JTable table;

    public VuKhiController() {
        panel = new VuKhiView();

        addB = panel.getAddB();
        editB = panel.getEditB();
        delB = panel.getDelBtn();
        clearB = panel.getClrB();
        priceF = panel.getGia();
        findB = panel.getFindB();

        phanloai = panel.getPhanLoai();
        phieuNhap = panel.getPhieuNhap();
        nameF = panel.getTen();
        thuhoi = panel.getThuHoi();

        table = panel.getTable();

        minF = panel.getMinP();
        maxF = panel.getMaxP();

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

        findB.addActionListener(this::find);

        resetState();
    }

    void updateState(boolean state) {
        addB.setEnabled(state);
        editB.setEnabled(!state);
        delB.setEnabled(!state);
    }

    public void resetState() {
        updateState(true);

        temp = null;

        Ui.updateComboBox(phanloai, PhanLoai.getDb().MAP(i -> i.getID() + " " + i.getTenPhanLoai()).toList());
        Ui.updateComboBox(phieuNhap, PhieuNhap.getDb().MAP(i -> i.getID()).toList());
        nameF.setText("");
        thuhoi.setSelected(false);
        minF.setValue(0);
        maxF.setValue(1000000);

        displayTable(VuKhi.getDb().QUERY());
    }

    void insert(ActionEvent evt) {
        System.out.println();
        VuKhi t = new VuKhi(
                VuKhi.get_ID(),
                nameF.getText(),
                (Integer) priceF.getValue(),
                thuhoi.isSelected(),
                ((String) phanloai.getSelectedItem()).split(" ")[0],
                (String) phieuNhap.getSelectedItem());
        VuKhi.getDb().ADD(t);
        resetState();
    }

    void edit(ActionEvent evt) {
        if (nameF.getText().isBlank()) {
            return;
        }

        temp.setThuVe(thuhoi.isSelected());
        temp.setGiaThanh((Integer) priceF.getValue());
        temp.setVuKhi(nameF.getText());

        temp.setPhanLoai(((String) phanloai.getSelectedItem()).split(" ")[0]);
        temp.setPhieuNhap((String) phieuNhap.getSelectedItem());

        VuKhi.getDb().EDIT(temp);
        resetState();
    }

    void find(ActionEvent evt) {
        int min = (int) minF.getValue(), max = (int) maxF.getValue();
        displayTable(VuKhi.getDb().QUERY(i -> i.getGiaThanh() >= min && i.getGiaThanh() <= max));
    }

    void delete(ActionEvent evt) {
        VuKhi.getDb().DELETE(temp);
//        PhanLoai.getDb().DELETE(temp);
        resetState();
    }

    void clear(ActionEvent evt) {
        resetState();
    }

    void displayTable(List<VuKhi> phanLoai) {
        _vuKhi = phanLoai;

        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < _vuKhi.size(); ++i) {
            VuKhi _p = _vuKhi.get(i);
            data.add(new Object[]{
                i + 1,
                _p.getVuKhi(),
                _p.isThuVe() ? "Đã thu" : "Chưa thu",
                _p.getPhanLoai().getTenPhanLoai(),
                _p.getPhieuNhap().getID(),
                utilities.others.Others.formatNum(_p.getGiaThanh())
            });
        }
        Ui.displayTable(table, data);
    }

    void onTableClick(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }

        temp = _vuKhi.get(row);
        nameF.setText(temp.getVuKhi());
        priceF.setValue(temp.getGiaThanh());
        thuhoi.setSelected(temp.isThuVe());

        Ui.selectItem(phanloai, temp.getPhanLoai().getID() + " " + temp.getPhanLoai().getTenPhanLoai());
        Ui.selectItem(phieuNhap, temp.getPhieuNhap().getID());

        updateState(false);
    }

    public void setVisible(boolean aFlag) {
        panel.setVisible(aFlag);

        if (aFlag == false) {
            return;
        }
        resetState();
    }

    public JPanel getPanel() {
        return panel;
    }
}
