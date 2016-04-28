package dataComputing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ari Weiland
 */
public class FileUtils {

    public static List<String> readFile(String file) {
        List<String> lines = new ArrayList<>();
        String line = null;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(file));
            while ((line = fileReader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error at \'" + line + "\'", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return lines;
    }

}
