package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.ExternalReference;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonPrimitiveMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { HashMapper.class, JsonPrimitiveMapper.class })
public interface ExternalReferenceMapper extends DescriptorMapper<ExternalReference, ExternalReferenceDescriptor> {
}

