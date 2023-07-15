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
import org.mapstruct.MappingTarget;

import static java.util.Collections.singletonList;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = LicenseExpressionMapper.class)
public interface LicenseMapper extends DescriptorMapper<LicenseType, LicenseDescriptor> {

    LicenseMapper INSTANCE = getMapper(LicenseMapper.class);

    @Mapping(target = "expression", ignore = true)
    @Override
    LicenseDescriptor toDescriptor(LicenseType type, @Context Scanner scanner);

    @Mapping(target = "expression", ignore = true)
    @Override
    void toDescriptor(LicenseType type, @MappingTarget LicenseDescriptor descriptor, @Context Scanner scanner);

    default List<LicenseDescriptor> map(LicenseChoiceType licenseChoiceType, @Context Scanner scanner) {
        if (licenseChoiceType == null) {
            return null;
        }
        LicenseChoiceType.Expression expression = licenseChoiceType.getExpression();
        return expression != null ?
            singletonList(LicenseExpressionMapper.INSTANCE.toDescriptor(expression, scanner)) :
            map(licenseChoiceType, () -> licenseChoiceType.getLicense(), this, scanner);
    }

}
