package org.jqassistant.plugin.cyclonedx.impl.mapper;

import org.mapstruct.MapperConfig;

import static org.mapstruct.ReportingPolicy.ERROR;

@MapperConfig(unmappedTargetPolicy = ERROR, unmappedSourcePolicy = ERROR)
public @interface StrictMapping {
}
