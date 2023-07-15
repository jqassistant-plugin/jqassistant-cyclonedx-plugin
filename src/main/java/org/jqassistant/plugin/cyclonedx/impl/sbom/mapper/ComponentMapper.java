package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = HashMapper.class)
public interface ComponentMapper extends DescriptorMapper<Component, ComponentDescriptor> {

    ComponentMapper INSTANCE = getMapper(ComponentMapper.class);

    default List<HashDescriptor> map(Component.Hashes hashes, @Context Scanner scanner) {
        return map(hashes, () -> hashes.getHash(), HashMapper.INSTANCE, scanner);
    }

}
