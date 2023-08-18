package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.Hash;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.mapstruct.Mapper;

@Mapper
public interface HashMapper extends DescriptorMapper<Hash, HashDescriptor> {
}
