package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import java.util.List;

import javax.xml.bind.JAXBElement;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.PropertyDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.PropertiesType;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.PropertyType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import static java.util.stream.Collectors.toList;

@Mapper
public interface PropertyMapper extends DescriptorMapper<PropertyType, PropertyDescriptor> {

    default List<PropertyDescriptor> map(PropertiesType propertiesType, @Context Scanner scanner) {
        if (propertiesType == null) {
            return null;
        }
        return propertiesType.getPropertyAndAny()
            .stream()
            .filter(element -> element instanceof JAXBElement && ((JAXBElement<?>) element).getDeclaredType()
                .equals(PropertyType.class))
            .map(element -> ((JAXBElement<PropertyType>) element).getValue())
            .map(propertyType -> toDescriptor(propertyType, scanner))
            .collect(toList());
    }

}
