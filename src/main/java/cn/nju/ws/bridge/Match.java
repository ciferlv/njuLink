package cn.nju.ws.bridge;

import eu.sealsproject.platform.res.tool.api.ToolBridgeException;
import eu.sealsproject.platform.res.tool.api.ToolException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by xinzelv on 17-6-28.
 */
public class Match {
    public static void main(String[] args) {

        try {
//            URL source = new URL("http://repositories.seals-project.eu/tdrs./TESTdata/persistent/conference/conference-v1/suite/cmt-ekaw/component/source/");
            URL source = new URL("file:///media/xinzelv/Disk1/OAEI2017/DataSet/DOREMUS/FPT/source.ttl");
//            URL source = new URL("file:///media/xinzelv/Disk1/OAEI2017/DataSet/SPIMBENCH_small/Abox1.nt");
//            URL target = new URL("http://repositories.seals-project.eu/tdrs./TESTdata/persistent/conference/conference-v1/suite/cmt-ekaw/component/target/");
            URL target = new URL("file:///media/xinzelv/Disk1/OAEI2017/DataSet/DOREMUS/FPT/target.ttl");
//            URL target = new URL("file:///media/xinzelv/Disk1/OAEI2017/DataSet/SPIMBENCH_small/Abox2.nt");

            URL inputAlign = new URL("file:///media/xinzelv/Disk1/OAEI2017/DataSet/DOREMUS/FPT/refalign.rdf");


            njuLinkMatcherBridge njuM = new njuLinkMatcherBridge();
            njuM.align(source, target);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ToolException e) {
            e.printStackTrace();
        } catch (ToolBridgeException e) {
            e.printStackTrace();
        }
    }
}
