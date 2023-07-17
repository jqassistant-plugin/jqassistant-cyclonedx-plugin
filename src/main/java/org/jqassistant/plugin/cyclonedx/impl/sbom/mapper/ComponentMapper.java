package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import java.util.List;

import javax.xml.bind.JAXBElement;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ComponentDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.Component;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static java.util.stream.Collectors.toList;

@Mapper(uses = { ExternalReferenceMapper.class, HashMapper.class, LicenseChoiceMapper.class })
public interface ComponentMapper extends DescriptorMapper<Component, ComponentDescriptor> {

    @Override
    @Mapping(target = "components", source = "components.componentAndAny")
    @Mapping(target = "dependencies", ignore = true)
    @Mapping(target = "externalReferences", source = "externalReferences.reference")
    @Mapping(target = "hashes", source = "hashes.hash")
    @BeanMapping(ignoreUnmappedSourceProperties = { "supplier", "swid", "pedigree", "properties", "evidence", "releaseNotes", "modelCard", "data", "any",
        "otherAttributes" })
    ComponentDescriptor toDescriptor(Component type, @Context Scanner scanner);

    default List<ComponentDescriptor> map(List<Object> componentAndAny, @Context Scanner scanner) {
        return componentAndAny == null ?
            null :
            componentAndAny.stream()
                .filter(element -> element instanceof JAXBElement<?> && ((JAXBElement<?>) element).getDeclaredType()
                    .equals(Component.class))
                .map(jaxbElement -> ((JAXBElement<Component>) jaxbElement).getValue())
                .map(component -> toDescriptor(component, scanner))
                .collect(toList());
    }

}
