package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper
public interface ComponentMapper extends DescriptorMapper<Component, ComponentDescriptor> {

}
