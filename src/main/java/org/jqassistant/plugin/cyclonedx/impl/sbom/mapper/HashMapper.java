package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.HashType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface HashMapper extends DescriptorMapper<HashType, HashDescriptor> {

    HashMapper INSTANCE = getMapper(HashMapper.class);

}
