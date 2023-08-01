package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("License")
public interface LicenseDescriptor extends CycloneDXDescriptor, NameTemplate, UrlTemplate {

    String getId();

    void setId(String id);

    String getExpression();

    void setExpression(String expression);

    @Relation("HAS_ATTACHMENT_TEXT")
    AttachmentTextDescriptor getAttachmentText();

    void setAttachmentText(AttachmentTextDescriptor attachmentText);
}
