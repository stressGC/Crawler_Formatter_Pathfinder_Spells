import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FSHelper {
    public static void writeToFile(String path, JSONArray json) throws IOException {
        System.out.println(">>WRITING RESULT TO FILE : " + path);
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(json.toString());
        printWriter.close();
    }
}
