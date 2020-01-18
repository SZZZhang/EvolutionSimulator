package gameEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    public static String loadString(String path) {
        StringBuilder string = new StringBuilder();

        File file = new File(path);
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                string.append(scan.nextLine()).append('\n');
            }
            return string.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
            return "";
        }
    }//TODO change to ADT
      public static List<String> readAllLines(String fileName) {
        List<String> list = new ArrayList<>();

        File file = new File(fileName);

        try{
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                list.add(scan.nextLine());
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

        return list;
    }
}
