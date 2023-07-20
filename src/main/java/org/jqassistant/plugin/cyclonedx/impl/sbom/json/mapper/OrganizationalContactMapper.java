package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.OrganizatonalContactDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.OrganizationalContact;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationalContactMapper extends DescriptorMapper<OrganizationalContact, OrganizatonalContactDescriptor> {
}
