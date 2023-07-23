package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.cyclonedx.model.LicenseChoice;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Mapper(uses = { LicenseMapper.class, LicenseExpressionMapper.class })
public interface LicenseChoiceMapper {

    default List<LicenseDescriptor> map(LicenseChoice value, @Context Scanner scanner) {
        if (value == null) {
            return emptyList();
        }
        String expression = value.getExpression();
        if (expression != null) {
            return singletonList(LicenseExpressionMapper.INSTANCE.toDescriptor(expression, scanner));
        }
        return value.getLicenses()
            .stream()
            .map(license -> LicenseMapper.INSTANCE.toDescriptor(license, scanner))
            .collect(toList());
    }

}
