package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { StringMapper.class, MapToPropertyMapper.class })
public interface MapToLicenseMapper extends MapToDescriptorMapper<LicenseDescriptor> {
    MapToLicenseMapper INSTANCE = Mappers.getMapper(MapToLicenseMapper.class);
}
