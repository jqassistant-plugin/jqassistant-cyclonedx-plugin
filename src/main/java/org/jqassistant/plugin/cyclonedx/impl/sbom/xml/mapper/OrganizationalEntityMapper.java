package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.OrganizationalEntityDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.OrganizationalEntity;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper.StringMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrganizationalContactMapper.class, StringMapper.class })
public interface OrganizationalEntityMapper extends DescriptorMapper<OrganizationalEntity, OrganizationalEntityDescriptor> {

    @Override
    @Mapping(target = "urls", source = "url")
    @Mapping(target = "contacts", source = "contact")
    @BeanMapping(ignoreUnmappedSourceProperties = { "any", "otherAttributes" })
    OrganizationalEntityDescriptor toDescriptor(OrganizationalEntity value, @Context Scanner scanner);
}
