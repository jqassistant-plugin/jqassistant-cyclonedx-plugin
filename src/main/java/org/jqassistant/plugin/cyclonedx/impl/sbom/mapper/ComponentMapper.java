package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseChoiceType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static java.util.Collections.singletonList;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = HashMapper.class)
public interface ComponentMapper extends DescriptorMapper<Component, ComponentDescriptor> {

    ComponentMapper INSTANCE = getMapper(ComponentMapper.class);

    default List<HashDescriptor> map(Component.Hashes hashes, @Context Scanner scanner) {
        return map(hashes, () -> hashes.getHash(), HashMapper.INSTANCE, scanner);
    }

    default List<LicenseDescriptor> mapLicenses(LicenseChoiceType licenseChoiceType, @Context Scanner scanner) {
        LicenseChoiceType.Expression expression = licenseChoiceType.getExpression();
        return expression != null ?
            singletonList(LicenseExpressionMapper.INSTANCE.toDescriptor(expression, scanner)) :
            map(licenseChoiceType, () -> licenseChoiceType.getLicense(), LicenseMapper.INSTANCE, scanner);
    }

}
