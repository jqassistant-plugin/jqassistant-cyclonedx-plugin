package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface StringMapper {

    default String map(Object value) {
        return value == null ? null : value.toString();
    }

}