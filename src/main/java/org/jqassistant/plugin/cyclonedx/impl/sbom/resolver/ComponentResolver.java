package org.jqassistant.plugin.cyclonedx.impl.sbom.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

@RequiredArgsConstructor
public class ComponentResolver implements Resolver<Component, ComponentDescriptor> {

    private final Map<String, ComponentDescriptor> components = new HashMap<>();

    @Override
    public ComponentDescriptor resolve(Component component, ScannerContext scannerContext) {
        return resolve(component.getBomRef(), scannerContext);
    }

    ComponentDescriptor resolve(String bomRef, ScannerContext scannerContext) {
        return components.computeIfAbsent(bomRef, key -> {
            ComponentDescriptor componentDescriptor = scannerContext.getStore()
                .create(ComponentDescriptor.class);
            componentDescriptor.setBomRef(bomRef);
            return componentDescriptor;
        });
    }
}
