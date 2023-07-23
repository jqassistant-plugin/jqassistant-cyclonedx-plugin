package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.ArrayList;
import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.cyclonedx.model.Bom;
import org.cyclonedx.model.Dependency;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorEnricher;
import org.mapstruct.*;

import static java.util.Collections.emptyList;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = { ComponentMapper.class, ExternalReferenceMapper.class, MetadataMapper.class, PropertyMapper.class, VulnerabilityMapper.class })
public interface SBOMMapper extends DescriptorEnricher<Bom, SBOMDescriptor> {

    SBOMMapper INSTANCE = getMapper(SBOMMapper.class);

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "services", "dependencies", "compositions", "bomFormat", "specVersion", "signature", "extensions",
        "extensibleTypes", "xmlns" })
    SBOMDescriptor toDescriptor(Bom bom, @MappingTarget SBOMDescriptor sbomDescriptor, @Context Scanner scanner);

    @AfterMapping
    default void resolveDependencyTree(Bom bom, @Context Scanner scanner) {
        BomRefResolver bomRefResolver = scanner.getContext()
            .peek(BomRefResolver.class);
        resolveDependencyTree(bom.getDependencies(), bomRefResolver, scanner);
    }

    static List<ComponentDescriptor> resolveDependencyTree(List<Dependency> dependencies, BomRefResolver bomRefResolver, Scanner scanner) {
        if (dependencies == null) {
            return emptyList();
        }
        List<ComponentDescriptor> componentDescriptors = new ArrayList<>();
        for (Dependency dependency : dependencies) {
            ComponentDescriptor dependentDescriptor = bomRefResolver.resolve(dependency.getRef(), ComponentDescriptor.class, scanner.getContext());
            componentDescriptors.add(dependentDescriptor);
            List<ComponentDescriptor> dependencyDescriptors = resolveDependencyTree(dependency.getDependencies(), bomRefResolver, scanner);
            dependentDescriptor.getDependencies()
                .addAll(dependencyDescriptors);
        }
        return componentDescriptors;
    }

}

