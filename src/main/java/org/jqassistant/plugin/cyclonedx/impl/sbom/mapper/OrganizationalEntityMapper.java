package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.OrganizationalEntity;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.OrganizationalEntityDescriptor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = { OrganizationalContactMapper.class })
public interface OrganizationalEntityMapper extends DescriptorMapper<OrganizationalEntity, OrganizationalEntityDescriptor> {

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "extensions", "extensibleTypes" })
    OrganizationalEntityDescriptor toDescriptor(OrganizationalEntity value, @Context Scanner scanner);
}
