package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseChoiceType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface LicenseExpressionMapper extends DescriptorMapper<LicenseChoiceType.Expression, LicenseDescriptor> {

    LicenseExpressionMapper INSTANCE = getMapper(LicenseExpressionMapper.class);

    @Mapping(target = "expression", source = "value")
    LicenseDescriptor toDescriptor(LicenseChoiceType.Expression type, @Context Scanner scanner);

}
