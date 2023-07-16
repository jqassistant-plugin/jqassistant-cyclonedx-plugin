package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.HashMap;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.BomRefTemplate;

public class BomRefResolver {

    private final Map<String, BomRefTemplate> bomRefs = new HashMap<>();

    <D extends BomRefTemplate & CycloneDXDescriptor> D resolve(String bomRef, Class<D> bomRefType, ScannerContext scannerContext) {
        return (D) bomRefs.computeIfAbsent(bomRef, key -> {
            BomRefTemplate bomRefTemplate = scannerContext.getStore()
                .create(bomRefType);
            bomRefTemplate.setBomRef(bomRef);
            return bomRefTemplate;
        });
    }

}
