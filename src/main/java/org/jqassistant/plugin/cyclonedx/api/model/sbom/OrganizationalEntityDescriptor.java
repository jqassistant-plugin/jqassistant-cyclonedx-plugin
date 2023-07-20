package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("OrganizationalEntity")
public interface OrganizationalEntityDescriptor extends CycloneDXDescriptor, BomRefTemplate {

    String getName();

    void setName(String name);

    String[] getUrls();

    void setUrls(String[] urls);

    @Relation("HAS_CONTACT")
    List<OrganizationalContactDescriptor> getContacts();

}
