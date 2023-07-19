package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonDescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonPrimitiveMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { JsonPrimitiveMapper.class, JsonHashMapper.class })
public interface JsonExternalReferenceMapper extends JsonDescriptorMapper<ExternalReferenceDescriptor> {
}
