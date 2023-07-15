package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Bom;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.generated.bom.ComponentsType;
import org.jqassistant.plugin.cyclonedx.generated.bom.DependencyType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static java.util.stream.Collectors.toList;

@Mapper(uses = { MetadataMapper.class, ComponentMapper.class })
public interface SBOMMapper extends DescriptorMapper<Bom, SBOMDescriptor> {

    @Mapping(target = "components")
    default List<ComponentDescriptor> map(ComponentsType componentsType, @Context Scanner scanner) {
        return componentsType.getComponentAndAny()
            .stream()
            .filter(element -> element instanceof JAXBElement<?> && ((JAXBElement<?>) element).getDeclaredType()
                .equals(Component.class))
            .map(jaxbElement -> ((JAXBElement<Component>) jaxbElement).getValue())
            .map(component -> ComponentMapper.INSTANCE.toDescriptor(component, scanner))
            .collect(toList());
    }

    @AfterMapping
    default void resolveDependencies(Bom bom, @Context Scanner scanner) {
        Resolvers resolvers = scanner.getContext()
            .peek(Resolvers.class);
        process(scanner, resolvers, bom.getDependencies()
            .getDependency());

    }

    static List<ComponentDescriptor> process(Scanner scanner, Resolvers resolvers, List<DependencyType> dependencies) {
        List<ComponentDescriptor> componentDescriptors = new ArrayList<>();
        for (DependencyType dependency : dependencies) {
            ComponentDescriptor parent = resolvers.resolve(dependency, ComponentDescriptor.class, scanner.getContext());
            List<ComponentDescriptor> children = process(scanner, resolvers, dependency.getDependency());
            parent.getDependencies()
                .addAll(children);
            componentDescriptors.add(parent);
        }
        return componentDescriptors;
    }

}

