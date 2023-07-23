package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.cyclonedx.model.OrganizationalContact;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.OrganizationalContactDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationalContactMapper extends DescriptorMapper<OrganizationalContact, OrganizationalContactDescriptor> {

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "extensions", "extensibleTypes" })
    OrganizationalContactDescriptor toDescriptor(OrganizationalContact value, @Context Scanner scanner);
}
