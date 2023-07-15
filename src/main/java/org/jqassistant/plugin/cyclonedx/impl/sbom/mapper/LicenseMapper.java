package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseType;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface LicenseMapper extends DescriptorMapper<LicenseType, LicenseDescriptor> {

    LicenseMapper INSTANCE = getMapper(LicenseMapper.class);

}
