package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.Tool;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.ToolDescriptor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = { ExternalReferenceMapper.class, HashMapper.class })
public interface ToolMapper extends DescriptorMapper<Tool, ToolDescriptor> {

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "extensibleTypes", "extensions" })
    ToolDescriptor toDescriptor(Tool value, @Context Scanner scanner);
}
