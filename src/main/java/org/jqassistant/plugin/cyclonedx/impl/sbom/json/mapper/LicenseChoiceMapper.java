package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import java.util.List;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Mapper
public interface LicenseChoiceMapper {

    default List<LicenseDescriptor> map(Object value, @Context Scanner scanner) {
        if (value == null) {
            return null;
        }
        return (value instanceof List) ? mapLicenses((List<?>) value, scanner) : emptyList();
    }

    default List<LicenseDescriptor> mapLicenses(List<?> licences, @Context Scanner scanner) {
        return licences.stream()
            .filter(value -> value instanceof Map)
            .map(license -> mapLicenseChoice((Map<String, Object>) license, scanner))
            .filter(license -> license != null)
            .collect(toList());
    }

    static LicenseDescriptor mapLicenseChoice(Map<String, Object> licenseChoice, Scanner scanner) {
        Map<String, Object> licenseObject = (Map<String, Object>) licenseChoice.get("license");
        if (licenseObject != null) {
            return MapToLicenseMapper.INSTANCE.toDescriptor(licenseObject, scanner);
        } else if (licenseChoice.containsKey("expression")) {
            return MapToLicenseMapper.INSTANCE.toDescriptor(licenseChoice, scanner);
        }
        return null;
    }

}
