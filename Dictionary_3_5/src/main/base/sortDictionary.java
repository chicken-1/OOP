package base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sortDictionary {

    public void action() {
        String inputFile = "D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";
        String outputFile = "D:\\Projects_workspace\\JAVAFX\\Dictionary_3_5\\src\\main\\base\\dictionaries.txt";

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        Collections.sort(lines, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
            return;
        }

        System.out.println("File sorted successfully.");
    }

    public static void main(String[] args) {
        sortDictionary obj = new sortDictionary();
        obj.action();
    }
}