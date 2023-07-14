package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.MetadataDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Metadata;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper(uses = ComponentMapper.class)
public interface MetadataMapper extends DescriptorMapper<Metadata, MetadataDescriptor> {

}
