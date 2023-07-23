package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LicenseExpressionMapper extends DescriptorMapper<String, LicenseDescriptor> {

    LicenseExpressionMapper INSTANCE = Mappers.getMapper(LicenseExpressionMapper.class);

    @Override
    @Mapping(target = "expression", source = ".")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "url", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = { "empty", "bytes", "blank" })
    LicenseDescriptor toDescriptor(String value, @Context Scanner scanner);
}
