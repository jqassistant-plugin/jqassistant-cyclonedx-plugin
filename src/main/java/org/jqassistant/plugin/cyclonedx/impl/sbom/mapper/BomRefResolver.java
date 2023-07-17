package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.BomRefTemplate;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

@RequiredArgsConstructor
public class BomRefResolver<T, D extends CycloneDXDescriptor & BomRefTemplate> implements Resolver<T, D> {

    private final Map<String, BomRefTemplate> bomRefs = new HashMap<>();

    private final Class<T> type;

    private final Function<T, String> bomRefFunction;

    private final Class<D> descriptorType;

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public D resolve(T type, ScannerContext scannerContext) {
        String bomRef = bomRefFunction.apply(type);
        return (D) bomRefs.computeIfAbsent(bomRef, key -> {
            BomRefTemplate bomRefTemplate = scannerContext.getStore()
                .create(descriptorType);
            bomRefTemplate.setBomRef(bomRef);
            return bomRefTemplate;
        });
    }
}
