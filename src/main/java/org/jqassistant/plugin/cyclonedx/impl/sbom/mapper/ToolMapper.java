package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.HashDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ToolDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.ToolType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface ToolMapper extends DescriptorMapper<ToolType, ToolDescriptor> {

    ToolMapper INSTANCE = getMapper(ToolMapper.class);

    default List<HashDescriptor> map(ToolType.Hashes hashes, @Context Scanner scanner) {
        return map(hashes, () -> hashes.getHash(), HashMapper.INSTANCE, scanner);
    }

}
