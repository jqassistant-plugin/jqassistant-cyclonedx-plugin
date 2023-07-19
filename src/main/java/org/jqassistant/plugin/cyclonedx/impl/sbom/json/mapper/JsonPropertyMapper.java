package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.PropertyDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonDescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonPrimitiveMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { JsonPrimitiveMapper.class })
public interface JsonPropertyMapper extends JsonDescriptorMapper<PropertyDescriptor> {
}
