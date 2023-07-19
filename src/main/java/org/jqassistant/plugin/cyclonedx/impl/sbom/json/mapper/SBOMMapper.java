package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Bom15Schema;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Dependency;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorEnricher;
import org.jqassistant.plugin.cyclonedx.impl.resolver.ResolverFactory;
import org.mapstruct.*;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = { ComponentMapper.class, ExternalReferenceMapper.class })
public interface SBOMMapper extends DescriptorEnricher<Bom15Schema, SBOMDescriptor> {

    SBOMMapper INSTANCE = getMapper(SBOMMapper.class);

    @Override
    @Mapping(target = "metadata", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = { "metadata", "services", "dependencies", "compositions", "properties", "vulnerabilities", "annotations",
        "formulation", "$schema", "bomFormat", "specVersion", "signature" })
    SBOMDescriptor toDescriptor(Bom15Schema bom, @MappingTarget SBOMDescriptor sbomDescriptor, @Context Scanner scanner);

    @AfterMapping
    default void resolveDependencyTree(Bom15Schema bom, @Context Scanner scanner) {
        ResolverFactory resolverFactory = scanner.getContext()
            .peek(ResolverFactory.class);
        resolveDependencyTree(bom.getDependencies(), resolverFactory, scanner);
    }

    static List<ComponentDescriptor> resolveDependencyTree(Set<?> dependencies, ResolverFactory resolverFactory, Scanner scanner) {
        List<ComponentDescriptor> componentDescriptors = new ArrayList<>();
        for (Object dependency : dependencies) {
            ComponentDescriptor dependentDescriptor = resolverFactory.getResolver(dependency, ComponentDescriptor.class)
                .resolve(dependency, scanner.getContext());
            componentDescriptors.add(dependentDescriptor);
            if (dependency instanceof Dependency) {
                Dependency componentDependency = (Dependency) dependency;
                List<ComponentDescriptor> dependencyDescriptors = resolveDependencyTree(componentDependency.getDependsOn(), resolverFactory, scanner);
                dependentDescriptor.getDependencies()
                    .addAll(dependencyDescriptors);
            }
        }
        return componentDescriptors;
    }

}

