package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.License;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AttachmentTextMapper.class })
public interface LicenseMapper extends DescriptorMapper<License, LicenseDescriptor> {

    LicenseMapper INSTANCE = Mappers.getMapper(LicenseMapper.class);

    @Override
    @Mapping(target = "expression", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = { "extensibleTypes", "extensions" })
    LicenseDescriptor toDescriptor(License value, @Context Scanner scanner);
}
