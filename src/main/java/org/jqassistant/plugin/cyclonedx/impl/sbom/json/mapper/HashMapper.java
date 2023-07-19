package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Hash;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface HashMapper extends DescriptorMapper<Hash, HashDescriptor> {

    @Override
    @Mapping(target = "value", source = "content")
    HashDescriptor toDescriptor(Hash type, @Context Scanner scanner);
}
