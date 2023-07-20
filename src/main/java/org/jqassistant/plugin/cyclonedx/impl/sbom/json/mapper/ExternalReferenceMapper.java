package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.ExternalReference;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { HashMapper.class, StringMapper.class })
public interface ExternalReferenceMapper extends DescriptorMapper<ExternalReference, ExternalReferenceDescriptor> {
}

