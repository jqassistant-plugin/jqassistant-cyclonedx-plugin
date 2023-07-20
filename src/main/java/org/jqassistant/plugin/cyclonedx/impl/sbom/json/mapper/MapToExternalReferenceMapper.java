package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.mapstruct.Mapper;

@Mapper(uses = { StringMapper.class, MapToHashMapper.class })
public interface MapToExternalReferenceMapper extends MapToDescriptorMapper<ExternalReferenceDescriptor> {
}
