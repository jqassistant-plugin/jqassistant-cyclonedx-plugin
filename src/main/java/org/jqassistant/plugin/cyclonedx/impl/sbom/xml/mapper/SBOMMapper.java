package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import java.util.ArrayList;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.Bom;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.DependencyType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorEnricher;
import org.jqassistant.plugin.cyclonedx.impl.resolver.ResolverFactory;
import org.mapstruct.*;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = { MetadataMapper.class, ComponentMapper.class, ExternalReferenceMapper.class, PropertyMapper.class, VulnerabilityMapper.class })
public interface SBOMMapper extends DescriptorEnricher<Bom, SBOMDescriptor> {

    SBOMMapper INSTANCE = getMapper(SBOMMapper.class);

    @Override
    @Mapping(target = "externalReferences", source = "externalReferences.reference")
    @Mapping(target = "components", source = "components.componentAndAny")
    @Mapping(target = "vulnerabilities", source = "vulnerabilities.vulnerabilityAndAny" ,ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = { "services", "dependencies", "compositions", "vulnerabilities", "annotations", "formulation", "any",
        "otherAttributes" })
    SBOMDescriptor toDescriptor(Bom bom, @MappingTarget SBOMDescriptor sbomDescriptor, @Context Scanner scanner);

    @AfterMapping
    default void resolveDependencyTree(Bom bom, @Context Scanner scanner) {
        ResolverFactory resolverFactory = scanner.getContext()
            .peek(ResolverFactory.class);
        resolveDependencyTree(bom.getDependencies()
            .getDependency(), resolverFactory, scanner);
    }

    static List<ComponentDescriptor> resolveDependencyTree(List<DependencyType> dependencies, ResolverFactory resolverFactory, Scanner scanner) {
        List<ComponentDescriptor> componentDescriptors = new ArrayList<>();
        for (DependencyType dependency : dependencies) {
            ComponentDescriptor dependentDescriptor = resolverFactory.getResolver(dependency, ComponentDescriptor.class)
                .resolve(dependency, scanner.getContext());
            componentDescriptors.add(dependentDescriptor);
            List<ComponentDescriptor> dependencyDescriptors = resolveDependencyTree(dependency.getDependency(), resolverFactory, scanner);
            dependentDescriptor.getDependencies()
                .addAll(dependencyDescriptors);
        }
        return componentDescriptors;
    }
}

