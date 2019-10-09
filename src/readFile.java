import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class readFile {
    /**
     * Takes in file name reads line by line
     * @param filename
     * @return list of lines
     */
    public static List<String> readFile(String filename){
        List<String> lines = new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();
            return lines;
        }
        catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.\n", filename);
            e.printStackTrace();
            return null;
        }
    }
}
