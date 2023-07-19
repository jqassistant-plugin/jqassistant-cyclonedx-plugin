package org.jqassistant.plugin.cyclonedx.impl.sbom.json;

import java.io.IOException;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMJsonFileDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Bom15Schema;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Component;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Dependency;
import org.jqassistant.plugin.cyclonedx.impl.resolver.ResolverFactory;
import org.jqassistant.plugin.cyclonedx.impl.sbom.BomRefResolverFactory;
import org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper.SBOMMapper;

@Requires(FileDescriptor.class)
public class SBOMJsonScannerPlugin extends AbstractScannerPlugin<FileResource, SBOMJsonFileDescriptor> {

    private ObjectMapper objectMapper;

    @Override
    public void initialize() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean accepts(FileResource fileResource, String path, Scope scope) {
        return path.endsWith("bom.json");
    }

    @Override
    public SBOMJsonFileDescriptor scan(FileResource fileResource, String s, Scope scope, Scanner scanner) throws IOException {
        ScannerContext scannerContext = scanner.getContext();
        FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
        SBOMJsonFileDescriptor sbomJsonFileDescriptor = scannerContext.getStore()
            .addDescriptorType(fileDescriptor, SBOMJsonFileDescriptor.class);

        Bom15Schema bom15Schema = objectMapper.readValue(fileResource.createStream(), Bom15Schema.class);

        BomRefResolverFactory bomRefResolverFactory = new BomRefResolverFactory();
        ResolverFactory resolverFactory = ResolverFactory.builder()
            .resolver(bomRefResolverFactory.resolver(Component.class, component -> component.getBomRef(), ComponentDescriptor.class))
            .resolver(bomRefResolverFactory.resolver(Dependency.class, dependency -> dependency.getRef()
                .toString(), ComponentDescriptor.class))
            .resolver(bomRefResolverFactory.resolver(String.class, bomRef -> bomRef, ComponentDescriptor.class))
            .build();
        scannerContext.push(ResolverFactory.class, resolverFactory);
        try {
            SBOMMapper.INSTANCE.toDescriptor(bom15Schema, sbomJsonFileDescriptor, scanner);
            return sbomJsonFileDescriptor;
        } finally {
            scannerContext.pop(ResolverFactory.class);
        }
    }

}
