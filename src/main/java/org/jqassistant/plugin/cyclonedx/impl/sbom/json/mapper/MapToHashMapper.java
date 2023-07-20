package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { StringMapper.class })
public interface MapToHashMapper extends MapToDescriptorMapper<HashDescriptor> {

    @Override
    @Mapping(target = "value", source = "content")
    HashDescriptor toDescriptor(Map<String, Object> value, @Context Scanner scanner);
}
