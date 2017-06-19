package cn.nju.ws.utility.nlp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static cn.nju.ws.utility.ParamDef.stopWordSet;
import static cn.nju.ws.utility.ParamDef.stopwords_file_path;

/**
 * Created by ciferlv on 17-6-6.
 */
public class FormatData {

    private static Logger logger = LoggerFactory.getLogger(FormatData.class);


    public static void getStopWords() {

        try {
            FileReader fr = new FileReader(stopwords_file_path);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while (s != null) {
                stopWordSet.add(s.trim());
                s = br.readLine();
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getLocalizedMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public static String formatWords(String str) {

        String recordStr = new String(str);
        str = str.toLowerCase();

        String delimit = "~!@#$%^&*()_+|`-=\\{}[]:\";'<>?,./'";

        StringBuffer buffer = new StringBuffer();

        for (char ch : delimit.toCharArray()) {

            str = str.replace(ch, ' ');
        }

        String[] strArray = str.split("\\s+");

        for (String tempStr : strArray) {

            if (!stopWordSet.contains(tempStr)) {

                buffer.append(tempStr);
            }
        }

        String res =  String.valueOf(buffer);

        if ( res.equals("")) {
            return recordStr;
        } else {
            return res;
        }
    }

    public static String formatDateTime(String date) {


        return "";
    }
}
