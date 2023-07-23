package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.cyclonedx.model.Metadata;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.MetadataDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { ComponentMapper.class, LicenseChoiceMapper.class, OrganizationalContactMapper.class, OrganizationalEntityMapper.class, PropertyMapper.class,
    ToolMapper.class })
public interface MetadataMapper extends DescriptorMapper<Metadata, MetadataDescriptor> {

    @Override
    @Mapping(target = "licenses", source = "licenseChoice")
    @BeanMapping(ignoreUnmappedSourceProperties = { "extensions", "extensibleTypes" })
    MetadataDescriptor toDescriptor(Metadata value, @Context Scanner scanner);

}
