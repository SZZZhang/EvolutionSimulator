package gameEngine;

import java.io.File;
import java.io.FileNotFoundException;
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
    }
}
