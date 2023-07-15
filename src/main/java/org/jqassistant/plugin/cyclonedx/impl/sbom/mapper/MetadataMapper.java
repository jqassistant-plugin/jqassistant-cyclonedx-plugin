package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.MetadataDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ToolDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Metadata;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = ComponentMapper.class)
public interface MetadataMapper extends DescriptorMapper<Metadata, MetadataDescriptor> {

    MetadataMapper INSTANCE = getMapper(MetadataMapper.class);

    default List<ToolDescriptor> map(Metadata.Tools tools, @Context Scanner scanner) {
        return map(tools, () -> tools.getTool(), ToolMapper.INSTANCE, scanner);
    }
}
