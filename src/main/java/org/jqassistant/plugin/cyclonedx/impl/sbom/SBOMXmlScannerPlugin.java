package org.jqassistant.plugin.cyclonedx.impl.sbom;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.shared.xml.JAXBUnmarshaller;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import com.buschmais.jqassistant.plugin.xml.api.scanner.AbstractXmlFileScannerPlugin;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMXmlFileDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.*;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.jqassistant.plugin.cyclonedx.impl.sbom.mapper.BomRefResolver;
import org.jqassistant.plugin.cyclonedx.impl.sbom.mapper.SBOMMapper;
import org.xml.sax.SAXException;

public class SBOMXmlScannerPlugin extends AbstractXmlFileScannerPlugin<SBOMXmlFileDescriptor> {

    private static final String NAMESPACE = "http://cyclonedx.org/schema/bom/1.5";

    private static final String SCHEMA_LOCATION = "/META-INF/xsd/bom-1.5.xsd";

    private JAXBUnmarshaller<Bom> unmarshaller;

    @Override
    public void initialize() {
        URL resource = SBOMXmlScannerPlugin.class.getResource(SCHEMA_LOCATION);
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = schemaFactory.newSchema(resource);
            unmarshaller = new JAXBUnmarshaller<>(Bom.class, schema, NAMESPACE);
        } catch (SAXException saxException) {
            throw new IllegalStateException("Cannot read rules schema.", saxException);
        }

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
        Resolvers resolvers = Resolvers.builder()
            .resolver(new BomRefResolver<>(Component.class, component -> component.getBomRef(), ComponentDescriptor.class))
            .resolver(new BomRefResolver<>(DependencyType.class, dependencyType -> dependencyType.getRef(), ComponentDescriptor.class))
            .resolver(new BomRefResolver<>(LicenseType.class, licenseType -> licenseType.getBomRef(), LicenseDescriptor.class))
            .resolver(new BomRefResolver<>(LicenseChoiceType.Expression.class, expression -> expression.getBomRef(), LicenseDescriptor.class))
            .build();
        scannerContext.push(Resolvers.class, resolvers);
        try {
            SBOMMapper.INSTANCE.toDescriptor(bom, sbomXmlFileDescriptor, scanner);
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
