package cn.nju.ws.utility.fileParser;

import cn.nju.ws.utility.threads.ParseInstFileThread;
import cn.nju.ws.unit.instance.Doc;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.nju.ws.utility.threads.ThreadEndJudge.terminateThread;

/**
 * Created by ciferlv on 17-6-5.
 */
public class InstFileParser {

    private static Logger logger = LoggerFactory.getLogger(InstFileParser.class);
    private static InputStream in = null;
    private static Model model = null;

    public static void parseInstFile(String filePath, Doc doc, Model mod) {

        model = mod;
        accessFile(filePath);

//        long time1 = System.currentTimeMillis();
        generateDoc(doc);
//        long time2 = System.currentTimeMillis();
//        logger.info(String.valueOf(time2-time1));
    }

    public static void accessFile(String filePath) {

        in = FileManager.get().open(filePath);
        if (in == null) {
            throw new IllegalArgumentException("File: " + filePath + " not found");
        }

        if (filePath.endsWith(".nt")) {
            model.read(in, "", "N3");
        } else if (filePath.endsWith(".ttl")) {
            model.read(in, "", "TTL");
        } else {
            model.read(in, "");
        }
    }

    public static void generateDocWithThread(Doc doc) {

        StmtIterator iter = model.listStatements();

        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);

        while (iter.hasNext()) {

            Statement stmt = iter.nextStatement();

            Runnable run = new Thread(
                    new ParseInstFileThread(doc, stmt));
            cachedThreadPool.execute(run);
        }
        terminateThread(cachedThreadPool, logger);
    }

    public static void generateDoc(Doc doc) {

        StmtIterator iter = model.listStatements();

        long tripleNum = 0;
        while (iter.hasNext()) {

            tripleNum++;

            Statement stmt = iter.nextStatement();
            Resource sub = stmt.getSubject();
            Property prop = stmt.getPredicate();
            RDFNode val = stmt.getObject();

            if (sub == null || val == null || prop == null) {
                logger.info("Sub or Prop or Val is null!");
                logger.info("sub: " + sub.toString());
                logger.info("prop: " + prop.toString());
                logger.info("val: " + val.toString());
                continue;
            }
            doc.addStmtToGraph(sub, prop, val);
        }

        doc.setTripleNum(tripleNum);
    }

}
