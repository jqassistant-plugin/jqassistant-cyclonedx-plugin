package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import org.cyclonedx.model.ExternalReference;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { HashMapper.class })
public interface ExternalReferenceMapper extends DescriptorMapper<ExternalReference, ExternalReferenceDescriptor> {
}

