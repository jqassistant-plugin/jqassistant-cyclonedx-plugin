package org.jqassistant.plugin.cyclonedx.impl.sbom.json.mapper;

import java.util.Map;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LicenseMapper extends DescriptorMapper<Map<String, String>, LicenseDescriptor> {

    LicenseMapper INSTANCE = Mappers.getMapper(LicenseMapper.class);

}
