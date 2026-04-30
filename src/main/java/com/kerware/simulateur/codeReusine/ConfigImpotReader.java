package com.kerware.simulateur.codeReusine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ConfigImpotReader {
    public ConfigImpotReader() {

    }
    public static List<String> readCsv(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("configImpot.csv"));
        String line = br.readLine();
        String[] parts = line.split(",");
    }
}
