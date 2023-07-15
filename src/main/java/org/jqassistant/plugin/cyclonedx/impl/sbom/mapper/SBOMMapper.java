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
        resolveDependencies(bom.getDependencies()
            .getDependency(), resolvers, scanner);

    }

    static List<ComponentDescriptor> resolveDependencies(List<DependencyType> dependencies, Resolvers resolvers, Scanner scanner) {
        List<ComponentDescriptor> componentDescriptors = new ArrayList<>();
        for (DependencyType dependency : dependencies) {
            ComponentDescriptor dependentDescriptor = resolvers.resolve(dependency, ComponentDescriptor.class, scanner.getContext());
            componentDescriptors.add(dependentDescriptor);
            List<ComponentDescriptor> dependencyDescriptors = resolveDependencies(dependency.getDependency(), resolvers, scanner);
            dependentDescriptor.getDependencies()
                .addAll(dependencyDescriptors);
        }
        return componentDescriptors;
    }

}

