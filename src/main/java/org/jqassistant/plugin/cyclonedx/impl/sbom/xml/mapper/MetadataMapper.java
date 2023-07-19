package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.MetadataDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.Metadata;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { ComponentMapper.class, LicenseChoiceMapper.class, ToolMapper.class, OrganizationalContactMapper.class })
public interface MetadataMapper extends DescriptorMapper<Metadata, MetadataDescriptor> {

    @Override
    @Mapping(target = "authors", source = "authors.author")
    @Mapping(target = "tools", source = "tools.tool")
    @BeanMapping(ignoreUnmappedSourceProperties = { "lifecycles", "manufacture", "supplier", "properties", "any", "otherAttributes" })
    MetadataDescriptor toDescriptor(Metadata value, @Context Scanner scanner);

}
