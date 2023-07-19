package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.LicenseChoiceType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LicenseExpressionMapper extends DescriptorMapper<LicenseChoiceType.Expression, LicenseDescriptor> {

    @Override
    @Mapping(target = "expression", source = "value")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "properties", ignore = true)
    LicenseDescriptor toDescriptor(LicenseChoiceType.Expression value, @Context Scanner scanner);

}
