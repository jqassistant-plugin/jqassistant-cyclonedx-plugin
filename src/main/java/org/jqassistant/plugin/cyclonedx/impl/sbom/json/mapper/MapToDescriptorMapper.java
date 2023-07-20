package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Context;

public interface MapToDescriptorMapper<D extends Descriptor> extends DescriptorMapper<Map<String, Object>, D> {

    default List<D> toList(Object value, @Context Scanner scanner) {
        if (value == null) {
            return null;
        }
        if (value instanceof List) {
            return ((List<?>) value).stream()
                .filter(v -> v instanceof Map)
                .map(element -> toDescriptor((Map<String, Object>) element, scanner))
                .collect(Collectors.toList());
        }
        return null;
    }
}
