package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ToolDescriptor;
import org.mapstruct.Mapper;

@Mapper(uses = { MapToHashMapper.class, MapToExternalReferenceMapper.class, StringMapper.class })
public interface MapToToolMapper extends MapToDescriptorMapper<ToolDescriptor> {
}
