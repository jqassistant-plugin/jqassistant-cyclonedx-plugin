package org.jqassistant.plugin.cyclonedx.impl.sbom;

import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.xml.api.model.XmlFileDescriptor;

import org.cyclonedx.parsers.XmlParser;

@Requires(XmlFileDescriptor.class)
public class XmlSBOMScannerPlugin extends AbstractSBOMScannerPlugin {

    public XmlSBOMScannerPlugin() {
        super(new XmlParser());
    }

    @Override
    public boolean accepts(FileResource fileResource, String path) {
        return path.endsWith(".xml");
    }

}
