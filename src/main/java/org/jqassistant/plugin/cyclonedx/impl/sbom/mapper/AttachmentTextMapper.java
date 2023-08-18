package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;

import org.cyclonedx.model.AttachmentText;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.AttachmentTextDescriptor;
import org.mapstruct.Mapper;

@Mapper
public interface AttachmentTextMapper extends DescriptorMapper<AttachmentText, AttachmentTextDescriptor> {
}
