package cn.nju.ws.bridge;

import cn.nju.ws.njuLinkMatcher;
import eu.sealsproject.platform.res.domain.omt.IOntologyMatchingToolBridge;
import eu.sealsproject.platform.res.tool.api.ToolBridgeException;
import eu.sealsproject.platform.res.tool.api.ToolException;
import eu.sealsproject.platform.res.tool.api.ToolType;
import eu.sealsproject.platform.res.tool.impl.AbstractPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by xinzelv on 17-6-28.
 */
public class njuLinkMatcherBridge extends AbstractPlugin implements IOntologyMatchingToolBridge {

    /**
     * Aligns to ontologies specified via their URL and returns the
     * URL of the resulting alignment, which should be stored locally.
     */
    public URL align(URL source, URL target) throws ToolBridgeException, ToolException {

        njuLinkMatcher demoMatcher;
        try {

            demoMatcher = new njuLinkMatcher();
            try {

                String alignmentString = demoMatcher.align(source.toURI(), target.toURI());
                try {
                    File alignmentFile = File.createTempFile("alignment", ".rdf");
                    FileWriter fw = new FileWriter(alignmentFile);
                    fw.write(alignmentString);

                    System.out.println(alignmentFile.getAbsolutePath());
                    fw.flush();
                    fw.close();
                    return alignmentFile.toURI().toURL();
                } catch (IOException e) {
                    throw new ToolBridgeException("cannot create file for resulting alignment", e);
                }
            } catch (URISyntaxException e1) {
                throw new ToolBridgeException("cannot convert the input param to URI as required");
            }
        } catch (NumberFormatException numberFormatE) {
            throw new ToolBridgeException("cannot correctly read from configuration file", numberFormatE);
        } catch (IOException configE) {
            throw new ToolBridgeException("cannot access configuration file", configE);
        }

    }

    /**
     * This functionality is not supported by the tool. In case
     * it is invoced a ToolException is thrown.https://files.slack.com/files-pri/T5SCUJBMZ-F60DWA4BD/image_uploaded_from_ios.jpg
     */
    public URL align(URL source, URL target, URL inputAlignment) throws ToolBridgeException, ToolException {
        throw new ToolException("functionality of called method is not supported");
    }

    /**
     * In our case the DemoMatcher can be executed on the fly. In case
     * prerequesites are required it can be checked here.
     */
    public boolean canExecute() {
        return true;
    }

    /**
     * The DemoMatcher is an ontology matching tool. SEALS supports the
     * evaluation of different tool types like e.g., reasoner and storage systems.
     */
    public ToolType getType() {
        return ToolType.OntologyMatchingTool;
    }
}

