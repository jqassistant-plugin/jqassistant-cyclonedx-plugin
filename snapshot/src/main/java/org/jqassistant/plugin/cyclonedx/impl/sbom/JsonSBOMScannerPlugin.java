package org.jqassistant.plugin.cyclonedx.impl.sbom;

import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.json.api.model.JSONFileDescriptor;

import org.cyclonedx.parsers.JsonParser;

@Requires(JSONFileDescriptor.class)
public class JsonSBOMScannerPlugin extends AbstractSBOMScannerPlugin {

    public JsonSBOMScannerPlugin() {
        super(new JsonParser());
    }

    @Override
    public boolean accepts(FileResource fileResource, String path) {
        return path.endsWith(".json");
    }

}
