package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.MetadataDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Metadata;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = { ComponentMapper.class, LicenseChoiceMapper.class, OrganizationalContactMapper.class, MapToToolMapper.class, PropertyMapper.class })
public interface MetadataMapper extends DescriptorMapper<Metadata, MetadataDescriptor> {

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "lifecycles", "manufacture", "supplier" })
    MetadataDescriptor toDescriptor(Metadata value, @Context Scanner scanner);

}
