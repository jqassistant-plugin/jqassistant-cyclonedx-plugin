package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("AttachmentText")
public interface AttachmentTextDescriptor extends CycloneDXDescriptor {

    String getEncoding();

    void setEncoding(String encoding);

    String getContentType();

    void setContentType(String contentType);

    String getText();

    void setText(String text);

}
