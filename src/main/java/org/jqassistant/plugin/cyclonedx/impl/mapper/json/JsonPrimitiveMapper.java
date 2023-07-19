package org.jqassistant.plugin.cyclonedx.impl.mapper.json;

import org.mapstruct.Mapper;

@Mapper
public interface JsonPrimitiveMapper {

    default String map(Object value) {
        return value == null ? null : value.toString();
    }

}
