package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.ArrayList;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Bom;
import org.jqassistant.plugin.cyclonedx.generated.bom.DependencyType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorEnricher;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.mapstruct.*;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = { MetadataMapper.class, ComponentMapper.class, ExternalReferenceMapper.class })
public interface SBOMMapper extends DescriptorEnricher<Bom, SBOMDescriptor> {

    SBOMMapper INSTANCE = getMapper(SBOMMapper.class);

    @Override
    @Mapping(target = "externalReferences", source = "externalReferences.reference")
    @Mapping(target = "components", source = "components.componentAndAny")
    SBOMDescriptor toDescriptor(Bom bom, @MappingTarget SBOMDescriptor sbomDescriptor, @Context Scanner scanner);

    @AfterMapping
    default void resolveDependencyTree(Bom bom, @Context Scanner scanner) {
        Resolvers resolvers = scanner.getContext()
            .peek(Resolvers.class);
        resolveDependencyTree(bom.getDependencies()
            .getDependency(), resolvers, scanner);
    }

    static List<ComponentDescriptor> resolveDependencyTree(List<DependencyType> dependencies, Resolvers resolvers, Scanner scanner) {
        List<ComponentDescriptor> componentDescriptors = new ArrayList<>();
        for (DependencyType dependency : dependencies) {
            ComponentDescriptor dependentDescriptor = resolvers.resolve(dependency, ComponentDescriptor.class, scanner.getContext());
            componentDescriptors.add(dependentDescriptor);
            List<ComponentDescriptor> dependencyDescriptors = resolveDependencyTree(dependency.getDependency(), resolvers, scanner);
            dependentDescriptor.getDependencies()
                .addAll(dependencyDescriptors);
        }
        return componentDescriptors;
    }
}

