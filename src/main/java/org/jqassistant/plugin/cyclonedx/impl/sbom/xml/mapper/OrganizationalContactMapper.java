package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.OrganizationalContactDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.OrganizationalContact;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationalContactMapper extends DescriptorMapper<OrganizationalContact, OrganizationalContactDescriptor> {

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "any", "otherAttributes" })
    OrganizationalContactDescriptor toDescriptor(OrganizationalContact value, @Context Scanner scanner);
}
