package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

@RequiredArgsConstructor
public class ComponentResolver implements Resolver<Component, ComponentDescriptor> {

    private final BomRefResolver bomRefResolver;

    @Override
    public ComponentDescriptor resolve(Component component, ScannerContext scannerContext) {
        return bomRefResolver.resolve(component.getBomRef(), ComponentDescriptor.class, scannerContext);
    }

}
