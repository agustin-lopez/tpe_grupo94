import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class CSVreader {

    private String filePath;
    private BufferedReader reader;
    private String line;
    private String fragment[] = null;

    public CSVreader(String filePath) {
        this.filePath = filePath;
    }

    public HashMap<String, Integer> fillMap() {
        try {

            reader = new BufferedReader(new FileReader(this.filePath));
            HashMap<String, Integer> temp = new HashMap<>();

            while ((line = reader.readLine()) != null) {

                fragment = line.split(";");
                if (fragment.length == 2) temp.put(fragment[0], Integer.parseInt(fragment[1]));

            }

            reader.close();
            line = null;
            fragment = null;

            return temp;

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
