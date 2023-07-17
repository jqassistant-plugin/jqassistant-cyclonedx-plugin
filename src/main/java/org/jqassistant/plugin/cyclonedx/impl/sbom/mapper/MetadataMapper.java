package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.MetadataDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Metadata;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { ComponentMapper.class, LicenseChoiceMapper.class, ToolMapper.class })
public interface MetadataMapper extends DescriptorMapper<Metadata, MetadataDescriptor> {

    @Override
    @Mapping(target = "tools", source = "tools.tool")
    @BeanMapping(ignoreUnmappedSourceProperties = { "lifecycles", "authors", "manufacture", "supplier", "properties", "any", "otherAttributes" })
    MetadataDescriptor toDescriptor(Metadata type, @Context Scanner scanner);

}
