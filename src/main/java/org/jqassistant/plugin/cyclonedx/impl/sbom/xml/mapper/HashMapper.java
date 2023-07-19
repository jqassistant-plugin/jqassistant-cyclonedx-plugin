package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.HashType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper
public interface HashMapper extends DescriptorMapper<HashType, HashDescriptor> {
}
