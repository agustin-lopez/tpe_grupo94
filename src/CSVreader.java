import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CSVreader {

    private String filePath;
    private BufferedReader reader;
    private String line;
    private String fragment[] = null;

    public CSVreader(String filePath) {
        this.filePath = filePath;
    }

    public LinkedHashMap<String, Integer> fillMap() {
        try {

            reader = new BufferedReader(new FileReader(this.filePath));

            LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

            while ((line = reader.readLine()) != null) {

                fragment = line.split(";");
                if (fragment.length == 2) map.put(fragment[0], Integer.parseInt(fragment[1]));

            }

            reader.close();
            line = null;
            fragment = null;

            return map;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public int getTotalPieces() {
        try {

            reader = new BufferedReader(new FileReader(this.filePath));

            fragment = reader.readLine().split(";");

            return Integer.parseInt(fragment[0]);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
