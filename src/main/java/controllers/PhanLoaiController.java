/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

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
import utilities.ui.Controller;
import utilities.ui.Ui;
import views.PhanLoaiView;

public class PhanLoaiController implements Controller {
    
    List<PhanLoai> _phanLoai;
    PhanLoai temp;
    
    PhanLoaiView panel;
    
    JButton addB, editB, delB, clearB, findB;
    
    JTextField findF, nameF;
    
    JTable table;
    
    public PhanLoaiController() {
        panel = new PhanLoaiView();
        
        addB = panel.getAddB();
        editB = panel.getEditB();
        delB = panel.getDelB();
        clearB = panel.getClearBtn();
        findB = panel.getFindBtn();        
        
        findF = panel.getFindF();
        nameF = panel.getNameF();
        
        table = panel.getTable();
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                onTableClick(evt);
            }
        });
        
        addB.addActionListener(this::insertPhanLoai);
        editB.addActionListener(this::editPhanLoai);
        delB.addActionListener(this::deletePhanLoai);
        clearB.addActionListener(this::clearPhanLoai);
        findB.addActionListener(this::findPhanLoai);
        displayTable(PhanLoai.getDb().QUERY());
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
        findF.setText("");
        nameF.setText("");
        displayTable(PhanLoai.getDb().QUERY());
    }
    
    void insertPhanLoai(ActionEvent evt) {
        if (nameF.getText().isBlank()) {
            return;
        }
        
        PhanLoai t = new PhanLoai(PhanLoai.get_ID(), nameF.getText());
        PhanLoai.getDb().ADD(t);
        resetState();
    }
    
    void editPhanLoai(ActionEvent evt) {
        if (nameF.getText().isBlank()) {
            return;
        }
        temp.setTenPhanLoai(nameF.getText());
        
        PhanLoai.getDb().EDIT(temp);
        resetState();
    }
    
    void deletePhanLoai(ActionEvent evt) {
        PhanLoai.getDb().DELETE(temp);
        resetState();
    }
    
    void clearPhanLoai(ActionEvent evt) {
        resetState();
    }
    
    void findPhanLoai(ActionEvent evt) {
        displayTable(PhanLoai.getDb().QUERY(i -> i.getTenPhanLoai().contains(findF.getText())));
    }
    
    void displayTable(List<PhanLoai> phanLoai) {
        _phanLoai = phanLoai;
        
        List<Object[]> data = new ArrayList<>();
        for (int i = 0; i < _phanLoai.size(); ++i) {
            PhanLoai _p = _phanLoai.get(i);
            data.add(new Object[]{
                i + 1,
                _p.getID(),
                _p.getTenPhanLoai()
            });
        }
        Ui.displayTable(table, data);
    }
    
    void onTableClick(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }
        
        temp = _phanLoai.get(row);
        nameF.setText(temp.getTenPhanLoai());
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
