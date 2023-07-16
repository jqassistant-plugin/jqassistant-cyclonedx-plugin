package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseChoiceType;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Mapper(uses = LicenseExpressionMapper.class)
public interface LicenseMapper extends DescriptorMapper<LicenseType, LicenseDescriptor> {

    @Mapping(target = "expression", ignore = true)
    @Override
    LicenseDescriptor toDescriptor(LicenseType type, @Context Scanner scanner);

    default List<LicenseDescriptor> map(LicenseChoiceType licenseChoiceType, @Context Scanner scanner) {
        if (licenseChoiceType == null) {
            return null;
        }
        LicenseChoiceType.Expression expression = licenseChoiceType.getExpression();
        return expression != null ?
            singletonList(Mappers.getMapper(LicenseExpressionMapper.class)
                .toDescriptor(expression, scanner)) :
            licenseChoiceType == null ?
                emptyList() :
                licenseChoiceType.getLicense()
                    .stream()
                    .map(licenseType -> toDescriptor(licenseType, scanner))
                    .collect(toList());
    }

}
