package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;


import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Bom;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper(uses = MetadataMapper.class)
public interface SBOMDescriptorMapper extends DescriptorMapper<Bom, SBOMDescriptor> {
}
