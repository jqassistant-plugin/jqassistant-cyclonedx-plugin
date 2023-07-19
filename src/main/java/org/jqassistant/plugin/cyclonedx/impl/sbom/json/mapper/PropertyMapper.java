package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.PropertyDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.json.Property;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper
public interface PropertyMapper extends DescriptorMapper<Property, PropertyDescriptor> {
}
