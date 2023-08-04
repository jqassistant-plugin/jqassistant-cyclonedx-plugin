package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("ExternalReference")
public interface ExternalReferenceDescriptor extends CycloneDXDescriptor, UrlTemplate, TypeTemplate, HashesTemplate {

    String getComment();

    void setComment(String comment);

}
