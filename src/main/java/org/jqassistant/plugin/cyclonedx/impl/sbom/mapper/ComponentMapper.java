package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = { ExternalReferenceMapper.class, HashMapper.class, LicenseMapper.class })
public interface ComponentMapper extends DescriptorMapper<Component, ComponentDescriptor> {

    ComponentMapper INSTANCE = getMapper(ComponentMapper.class);

    @Mapping(target = "dependencies", ignore = true)
    @Mapping(target = "externalReferences", source = "externalReferences.reference")
    @Mapping(target = "hashes", source = "hashes.hash")
    @Override
    ComponentDescriptor toDescriptor(Component type, @Context Scanner scanner);

}
