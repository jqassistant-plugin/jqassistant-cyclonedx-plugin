package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Component;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { HashMapper.class, ExternalReferenceMapper.class })
public interface ComponentMapper extends DescriptorMapper<Component, ComponentDescriptor> {

    @Override
    @Mapping(target = "dependencies", ignore = true)
    @Mapping(target = "licenses", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = { "signature", "supplier", "swid", "pedigree", "properties", "evidence", "releaseNotes", "modelCard", "data",
        "licenses" })
    ComponentDescriptor toDescriptor(Component type, @Context Scanner scanner);

}
