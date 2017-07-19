package cn.nju.ws.utility.fileParser;

import cn.nju.ws.unit.alignment.AlignmentList;
import cn.nju.ws.unit.alignment.CounterPart;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import static cn.nju.ws.utility.ParamDef.refSet;

/**
 * Created by ciferlv on 17-6-8.
 */
public class AlignFileParser {

    private static Logger logger = LoggerFactory.getLogger(AlignFileParser.class);

    public static void parseAlignFile(URL filePath, AlignmentList aligns) {

        SAXReader reader = new SAXReader();
        Document document = null;

        try {

            document = reader.read(filePath);
        } catch (DocumentException e) {

            logger.info("Can't find alignment file!");
            logger.error(e.getMessage());
        }

        Element root = document.getRootElement();

        Iterator<Element> alignmentIterator = root.elements("Alignment").iterator();
        Element alignmentElement = alignmentIterator.next();

        Iterator<Element> mapIterator = alignmentElement.elements("map").iterator();

        while (mapIterator.hasNext()) {

            Element mapElement = mapIterator.next();

            Element cellElement = mapElement.element("Cell");

            Element entity1Element = cellElement.element("entity1");
            Element entity2Element = cellElement.element("entity2");

            String uri1 = entity1Element.attribute(0).getValue().toLowerCase();
            String uri2 = entity2Element.attribute(0).getValue().toLowerCase();

            refSet.add(uri1);
            refSet.add(uri2);

            aligns.addCounterPart(new CounterPart(uri1, uri2));
        }
    }

    public static void main(String[] args) throws MalformedURLException {

        AlignmentList aligns = new AlignmentList();

        URL inputAlign = new URL("file:///media/xinzelv/Disk1/OAEI2017/DataSet/DOREMUS/FPT/refalign.rdf");
        parseAlignFile(inputAlign,aligns);
    }
}
