package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ToolDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.ToolType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { HashMapper.class, ExternalReferenceMapper.class })
public interface ToolMapper extends DescriptorMapper<ToolType, ToolDescriptor> {

    @Mapping(target = "externalReferences", source = "externalReferences.reference")
    @Mapping(target = "hashes", source = "hashes.hash")
    @Override
    ToolDescriptor toDescriptor(ToolType type, @Context Scanner scanner);

}
