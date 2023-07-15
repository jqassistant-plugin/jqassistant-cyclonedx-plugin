package org.jqassistant.plugin.cyclonedx.impl.sbom;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.validation.Schema;

import com.buschmais.jqassistant.core.rule.impl.reader.XmlHelper;
import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.shared.xml.JAXBUnmarshaller;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.xml.api.scanner.AbstractXmlFileScannerPlugin;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMXmlFileDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Bom;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.generated.bom.DependencyType;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.jqassistant.plugin.cyclonedx.impl.sbom.resolver.ComponentResolver;
import org.jqassistant.plugin.cyclonedx.impl.sbom.resolver.DependencyResolver;
import org.jqassistant.plugin.cyclonedx.impl.sbom.mapper.SBOMMapper;

import static org.mapstruct.factory.Mappers.getMapper;

public class SBOMXmlScannerPlugin extends AbstractXmlFileScannerPlugin<SBOMXmlFileDescriptor> {

    private static final String NAMESPACE = "http://cyclonedx.org/schema/bom/1.5";

    private static final String SCHEMA_LOCATION = "/META-INF/xsd/bom-1.5.xsd";

    private static final Schema SCHEMA = XmlHelper.getSchema(SCHEMA_LOCATION);

    private JAXBUnmarshaller<Bom> unmarshaller;

    @Override
    public void initialize() {
        unmarshaller = new JAXBUnmarshaller<>(Bom.class, SCHEMA, NAMESPACE);
    }

    @Override
    public boolean accepts(FileResource fileResource, String path, Scope scope) {
        return path.endsWith("bom.xml");
    }

    @Override
    public SBOMXmlFileDescriptor scan(FileResource fileResource, SBOMXmlFileDescriptor sbomXmlFileDescriptor, String path, Scope scope, Scanner scanner)
        throws IOException {
        ScannerContext scannerContext = scanner.getContext();
        Bom bom = unmarshal(fileResource);
        ComponentResolver componentResolver = new ComponentResolver();
        DependencyResolver dependencyResolver = new DependencyResolver(componentResolver);
        Resolvers resolvers = Resolvers.builder()
            .resolver(Component.class, componentResolver)
            .resolver(DependencyType.class, dependencyResolver)
            .build();
        scannerContext.push(Resolvers.class, resolvers);
        try {
            SBOMMapper sbomMapper = getMapper(SBOMMapper.class);
            sbomMapper.toDescriptor(bom, sbomXmlFileDescriptor, scanner);
            return sbomXmlFileDescriptor;
        } finally {
            scannerContext.pop(Resolvers.class);
        }
    }

    private Bom unmarshal(FileResource fileResource) throws IOException {
        try (InputStream inputStream = fileResource.createStream()) {
            return unmarshaller.unmarshal(inputStream);
        }
    }

}
