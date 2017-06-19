package cn.nju.ws.utility.threads;

import cn.nju.ws.unit.instance.Doc;
import org.apache.jena.rdf.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ciferlv on 17-6-7.
 */
public class ParseInstFileThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ParseInstFileThread.class);

    private Doc doc;
    private Statement stmt;

    public ParseInstFileThread(Doc doc, Statement stmt) {

        this.doc = doc;
        this.stmt = stmt;
    }

    @Override
    public void run() {

        Resource sub = stmt.getSubject();
        Property prop = stmt.getPredicate();
        RDFNode val = stmt.getObject();

        if (sub == null || val == null || prop == null) {

            logger.info("Sub or Pred or Obj is null!");
            logger.info("sub: " + sub.toString());
            logger.info("prop: " + prop.toString());
            logger.info("val: " + val.toString());
        }

        doc.addStmtToGraph(sub, prop, val);
    }
}
