package org.jqassistant.plugin.cyclonedx.impl.sbom;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.BomRefTemplate;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

public class BomRefResolverFactory {

    private final Map<String, BomRefTemplate> bomRefs = new HashMap<>();

    public <V, D extends CycloneDXDescriptor & BomRefTemplate> Resolver<V, D> resolver(Class<V> type, Function<V, String> bomRefFunction,
        Class<D> descriptorType) {
        return new BomRefResolver<>(type, bomRefFunction, descriptorType);
    }

    @RequiredArgsConstructor
    private class BomRefResolver<V, D extends CycloneDXDescriptor & BomRefTemplate> implements Resolver<V, D> {

        private final Class<V> type;

        private final Function<V, String> bomRefFunction;

        private final Class<D> descriptorType;

        @Override
        public Class<V> getValueType() {
            return type;
        }

        @Override
        public Class<D> getDescriptorType() {
            return descriptorType;
        }

        @Override
        public D resolve(V value, ScannerContext scannerContext) {
            String bomRef = bomRefFunction.apply(value);
            return (D) bomRefs.computeIfAbsent(bomRef, key -> {
                BomRefTemplate bomRefTemplate = scannerContext.getStore()
                    .create(descriptorType);
                bomRefTemplate.setBomRef(bomRef);
                return bomRefTemplate;
            });
        }
    }
}
