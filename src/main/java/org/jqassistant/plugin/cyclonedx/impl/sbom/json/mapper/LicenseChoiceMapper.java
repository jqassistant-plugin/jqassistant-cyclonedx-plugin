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

    static List<LicenseDescriptor> mapLicenses(List<?> licences, Scanner scanner) {
        return licences.stream()
            .filter(value -> value instanceof Map)
            .map(license -> mapLicenseChoice((Map<String, ?>) license, scanner))
            .filter(license -> license != null)
            .collect(toList());
    }

    static LicenseDescriptor mapLicenseChoice(Map<String, ?> licenseChoice, Scanner scanner) {
        Map<String, String> licenseObject = (Map<String, String>) licenseChoice.get("license");
        if (licenseObject != null) {
            return LicenseMapper.INSTANCE.toDescriptor(licenseObject, scanner);
        } else if (licenseChoice.containsKey("expression")) {
            return LicenseMapper.INSTANCE.toDescriptor((Map<String, String>) licenseChoice, scanner);
        }
        return null;
    }

}
