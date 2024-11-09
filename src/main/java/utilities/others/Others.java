/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities.others;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;

public interface Others {
    static final String NUM_FORMAT = "#,###";
    static String formatNum(Object number) {
        return new DecimalFormat(NUM_FORMAT).format(number);
    }

    static void CreateFile(String path) {
        File yourFile = new File(path);
        try {
            ArrayList<String> a = new ArrayList<>(Arrays.asList(path.split("/")));
            a.remove(a.size() - 1);
            Files.createDirectories(Paths.get(a.stream().reduce("", (total, element) -> total + element + "/")));

            if (!yourFile.exists())
                yourFile.createNewFile();
        } catch (IOException e) {
        }
    }

    static String formatMoney(int num) {
        return formatNum(num) + " VND";
    }

    static String leftPad(String x, int a) {
        if (x.length() >= a) return x;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a - x.length(); ++i) buf.append(" ");
        return x + buf.toString();
    }

    static String rightPad(String x, int a) {
        if (x.length() >= a) return x;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a - x.length(); ++i) buf.append(" ");
        return buf + x;
    }

}
