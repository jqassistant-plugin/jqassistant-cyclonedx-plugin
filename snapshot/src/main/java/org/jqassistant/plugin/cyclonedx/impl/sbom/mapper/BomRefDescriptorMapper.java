package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.BomRefTemplate;
import org.mapstruct.Context;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

public interface BomRefDescriptorMapper<V, D extends Descriptor & BomRefTemplate> extends DescriptorMapper<V, D> {

    @Override
    @ObjectFactory
    default D resolve(V value, @TargetType Class<D> descriptorType, @Context Scanner scanner) {
        return scanner.getContext()
            .peek(BomRefResolver.class)
            .resolve(getBomRef(value), descriptorType, scanner.getContext());
    }

    String getBomRef(V value);
}
