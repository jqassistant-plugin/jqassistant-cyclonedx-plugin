package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ToolDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonDescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonPrimitiveMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { JsonHashMapper.class, JsonExternalReferenceMapper.class, JsonPrimitiveMapper.class })
public interface JsonToolMapper extends JsonDescriptorMapper<ToolDescriptor> {
}
