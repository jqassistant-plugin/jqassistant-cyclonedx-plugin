package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Tool")
public interface ToolDescriptor extends CycloneDXDescriptor, HasHashesTemplate, HasExternalReferencesTemplate, VersionTemplate, NameTemplate {

    String getVendor();

    void setVendor(String vendor);

}
