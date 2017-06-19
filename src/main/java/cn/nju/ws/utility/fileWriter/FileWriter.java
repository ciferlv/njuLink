package cn.nju.ws.utility.fileWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by ciferlv on 17-6-8.
 */
public class FileWriter {

    public static void printToFile(String filePath, String str) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));

        pw.append(str);
        pw.flush();
        pw.close();

    }
}
