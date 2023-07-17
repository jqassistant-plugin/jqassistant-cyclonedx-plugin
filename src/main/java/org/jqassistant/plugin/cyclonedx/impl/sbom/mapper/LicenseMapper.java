package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LicenseMapper extends DescriptorMapper<LicenseType, LicenseDescriptor> {

    @Override
    @Mapping(target = "expression", ignore = true)
    @BeanMapping(ignoreUnmappedSourceProperties = { "text", "licensing", "properties", "any" })
    LicenseDescriptor toDescriptor(LicenseType type, @Context Scanner scanner);

}
