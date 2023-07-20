package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.PropertyDescriptor;
import org.mapstruct.Mapper;

@Mapper(uses = { StringMapper.class })
public interface MapToPropertyMapper extends MapToDescriptorMapper<PropertyDescriptor> {
}
