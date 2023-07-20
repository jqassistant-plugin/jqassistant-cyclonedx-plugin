package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label
public interface OrganizationalContactDescriptor extends CycloneDXDescriptor, BomRefTemplate {

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);
}
