package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.Property;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.PropertyDescriptor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper
public interface PropertyMapper extends DescriptorMapper<Property, PropertyDescriptor> {

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = { "extensions", "extensibleTypes" })
    PropertyDescriptor toDescriptor(Property value, @Context Scanner scanner);
}
