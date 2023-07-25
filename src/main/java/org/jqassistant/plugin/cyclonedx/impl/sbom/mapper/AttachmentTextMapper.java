package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import org.cyclonedx.model.AttachmentText;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.AttachmentTextDescriptor;
import org.jqassistant.plugin.cyclonedx.impl.mapper.DescriptorMapper;
import org.mapstruct.Mapper;

@Mapper
public interface AttachmentTextMapper extends DescriptorMapper<AttachmentText, AttachmentTextDescriptor> {
}
