package org.jqassistant.plugin.cyclonedx.impl.sbom;

import java.io.IOException;
import java.io.InputStream;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

import lombok.RequiredArgsConstructor;
import org.cyclonedx.exception.ParseException;
import org.cyclonedx.model.Bom;
import org.cyclonedx.parsers.Parser;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.sbom.mapper.BomRefResolver;
import org.jqassistant.plugin.cyclonedx.impl.sbom.mapper.SBOMMapper;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
@Requires(FileDescriptor.class)
abstract class AbstractSBOMScannerPlugin extends AbstractScannerPlugin<FileResource, SBOMDescriptor> {

    private final Parser parser;

    @Override
    public final Class<? extends FileResource> getType() {
        return FileResource.class;
    }

    @Override
    public final Class<SBOMDescriptor> getDescriptorType() {
        return SBOMDescriptor.class;
    }

    @Override
    public SBOMDescriptor scan(FileResource fileResource, String path, Scope scope, Scanner scanner) throws IOException {
        ScannerContext scannerContext = scanner.getContext();
        FileDescriptor fileDescriptor = scannerContext.getCurrentDescriptor();
        SBOMDescriptor sbomDescriptor = scannerContext.getStore()
            .addDescriptorType(fileDescriptor, SBOMDescriptor.class);

        Bom bom = parse(fileResource, path);

        scannerContext.push(BomRefResolver.class, new BomRefResolver());
        try {
            return SBOMMapper.INSTANCE.toDescriptor(bom, sbomDescriptor, scanner);
        } finally {
            scannerContext.pop(BomRefResolver.class);
        }
    }

    private Bom parse(FileResource fileResource, String s) throws IOException {
        try (InputStream inputStream = fileResource.createStream()) {
            return parser.parse(inputStream);
        } catch (ParseException e) {
            throw new IOException("Cannot parse SBOM " + s, e);
        }
    }
}
