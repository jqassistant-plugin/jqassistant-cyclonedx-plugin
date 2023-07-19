package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonDescriptorMapper;
import org.jqassistant.plugin.cyclonedx.impl.mapper.json.JsonPrimitiveMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { JsonPrimitiveMapper.class, JsonPropertyMapper.class })
public interface JsonLicenseMapper extends JsonDescriptorMapper<LicenseDescriptor> {
    JsonLicenseMapper INSTANCE = Mappers.getMapper(JsonLicenseMapper.class);
}
