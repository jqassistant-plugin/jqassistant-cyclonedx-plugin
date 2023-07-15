package org.jqassistant.plugin.cyclonedx.impl.sbom.resolver;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.DependencyType;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

@RequiredArgsConstructor
public class DependencyResolver implements Resolver<DependencyType, ComponentDescriptor> {

    private final ComponentResolver componentResolver;

    @Override
    public ComponentDescriptor resolve(DependencyType dependencyType, ScannerContext scannerContext) {
        return componentResolver.resolve(dependencyType.getRef(), scannerContext);
    }
}
