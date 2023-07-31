package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.cyclonedx.model.Component;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { HashMapper.class, LicenseChoiceMapper.class, ExternalReferenceMapper.class, PropertyMapper.class, OrganizationalEntityMapper.class })
public interface ComponentMapper extends BomRefDescriptorMapper<Component, ComponentDescriptor> {

    @Override
    default String getBomRef(Component value) {
        return value.getBomRef();
    }

    @Override
    @Mapping(target = "affectedByVulnerabilities", ignore = true)
    @Mapping(target = "dependencies", ignore = true)
    @Mapping(target = "licenses", source = "licenseChoice")
    @Mapping(target = "type", source = "type.typeName")
    @Mapping(target = "scope", source = "scope.scopeName")
    @BeanMapping(ignoreUnmappedSourceProperties = { "signature", "swid", "pedigree", "evidence", "releaseNotes", "extensions", "extensibleTypes" })
    ComponentDescriptor toDescriptor(Component value, @Context Scanner scanner);

    default ComponentDescriptor map(String ref, @Context Scanner scanner) {
        return scanner.getContext()
            .peek(BomRefResolver.class)
            .resolve(ref, ComponentDescriptor.class, scanner.getContext());

    }

}
