package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.ExternalReference;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.mapstruct.Mapper;

@Mapper(uses = { HashMapper.class })
public interface ExternalReferenceMapper extends DescriptorMapper<ExternalReference, ExternalReferenceDescriptor> {
}

