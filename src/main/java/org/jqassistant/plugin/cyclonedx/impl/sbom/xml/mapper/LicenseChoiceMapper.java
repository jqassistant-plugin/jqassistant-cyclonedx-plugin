package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.LicenseChoiceType;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface LicenseChoiceMapper {

    default List<LicenseDescriptor> map(LicenseChoiceType licenseChoiceType, @Context Scanner scanner) {
        if (licenseChoiceType == null) {
            return null;
        }
        LicenseChoiceType.Expression expression = licenseChoiceType.getExpression();
        if (expression != null) {
            return singletonList(getMapper(LicenseExpressionMapper.class).toDescriptor(expression, scanner));
        }
        return licenseChoiceType.getLicense()
            .stream()
            .map(licenseType -> getMapper(LicenseMapper.class).toDescriptor(licenseType, scanner))
            .collect(toList());
    }

}
