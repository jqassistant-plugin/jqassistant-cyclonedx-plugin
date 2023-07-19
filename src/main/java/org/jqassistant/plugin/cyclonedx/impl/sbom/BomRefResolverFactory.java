package org.jqassistant.plugin.cyclonedx.impl.sbom;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.BomRefTemplate;
import org.jqassistant.plugin.cyclonedx.impl.resolver.AbstractResolver;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

public class BomRefResolverFactory {

    private final Map<String, BomRefTemplate> bomRefs = new HashMap<>();

    public <V, D extends CycloneDXDescriptor & BomRefTemplate> Resolver<V, D> resolver(Class<V> type, Function<V, String> bomRefFunction,
        Class<D> descriptorType) {
        return new BomRefResolver<>(type, bomRefFunction, descriptorType);
    }

    private class BomRefResolver<V, D extends CycloneDXDescriptor & BomRefTemplate> extends AbstractResolver<V, D> {

        private final Function<V, String> bomRefFunction;

        private BomRefResolver(Class<V> valueClass, Function<V, String> bomRefFunction, Class<D> descriptorClass) {
            super(valueClass, descriptorClass);
            this.bomRefFunction = bomRefFunction;
        }

        @Override
        public D resolve(V value, ScannerContext scannerContext) {
            String bomRef = bomRefFunction.apply(value);
            return (D) bomRefs.computeIfAbsent(bomRef, key -> scannerContext.getStore()
                .create(descriptorType, bomRefTemplate -> bomRefTemplate.setBomRef(key)));
        }
    }
}
