package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.ExternalReferenceDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.ExternalReference;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { HashMapper.class })
public interface ExternalReferenceMapper extends DescriptorMapper<ExternalReference, ExternalReferenceDescriptor> {

    @Override
    @Mapping(target = "urls", source = "url")
    @Mapping(target = "hashes", source = "hashes.hash")
    @BeanMapping(ignoreUnmappedSourceProperties = { "otherAttributes" })
    ExternalReferenceDescriptor toDescriptor(ExternalReference type, @Context Scanner scanner);

}
