package org.jqassistant.plugin.cyclonedx.impl.sbom.xml.mapper;

import java.util.List;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.xml.ExternalReference;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static java.util.stream.Collectors.joining;

@Mapper(uses = { HashMapper.class })
public interface ExternalReferenceMapper extends DescriptorMapper<ExternalReference, ExternalReferenceDescriptor> {

    @Override
    @Mapping(target = "hashes", source = "hashes.hash")
    @BeanMapping(ignoreUnmappedSourceProperties = { "otherAttributes" })
    ExternalReferenceDescriptor toDescriptor(ExternalReference value, @Context Scanner scanner);

    default String map(List<String> value) {
        return value == null ?
            null :
            value.stream()
                .collect(joining(","));
    }

}
