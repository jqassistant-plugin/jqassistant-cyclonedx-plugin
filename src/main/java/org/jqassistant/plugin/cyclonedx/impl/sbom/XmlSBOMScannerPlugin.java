package org.jqassistant.plugin.cyclonedx.impl.sbom;

import java.io.IOException;
import java.io.InputStream;

import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.json.api.model.JSONFileDescriptor;

import org.cyclonedx.exception.ParseException;
import org.cyclonedx.model.Bom;
import org.cyclonedx.parsers.XmlParser;

@Requires(JSONFileDescriptor.class)
public class XmlSBOMScannerPlugin extends AbstractSBOMScannerPlugin {

    public XmlSBOMScannerPlugin() {
        super(new XmlParser());
    }

    @Override
    public boolean accepts(FileResource fileResource, String path, Scope scope) {
        return path.endsWith("bom.xml");
    }

    protected Bom parseBom(FileResource fileResource, String s) throws IOException {
        XmlParser jsonParser = new XmlParser();
        try (InputStream inputStream = fileResource.createStream()) {
            return jsonParser.parse(inputStream);
        } catch (ParseException e) {
            throw new IOException("Cannot parse SBOM " + s, e);
        }
    }

}
