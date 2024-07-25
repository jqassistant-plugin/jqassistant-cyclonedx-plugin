package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.HashMap;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.BomRefTemplate;

public class BomRefResolver {

    private final Map<String, BomRefTemplate> bomRefs = new HashMap<>();

    public <D extends Descriptor & BomRefTemplate> D resolve(String bomRef, Class<D> descriptorType, ScannerContext scannerContext) {
        if (bomRef == null) {
            return scannerContext.getStore()
                .create(descriptorType);
        }
        return (D) bomRefs.computeIfAbsent(bomRef, key -> scannerContext.getStore()
            .create(descriptorType, bomRefTemplate -> bomRefTemplate.setBomRef(key)));
    }

}
