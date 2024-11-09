/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

import java.awt.EventQueue;

public class App {
//    static final boolean TEST = true;
    static final boolean TEST = false;

    static void initParam() {

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> Controllers.ChangePanel("dangNhap"));
    }
}
