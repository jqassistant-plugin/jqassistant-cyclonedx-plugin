package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.DependencyType;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

@RequiredArgsConstructor
public class DependencyResolver implements Resolver<DependencyType, ComponentDescriptor> {

    private final BomRefResolver bomRefResolver;

    @Override
    public ComponentDescriptor resolve(DependencyType dependencyType, ScannerContext scannerContext) {
        return bomRefResolver.resolve(dependencyType.getRef(), ComponentDescriptor.class, scannerContext);
    }
}
