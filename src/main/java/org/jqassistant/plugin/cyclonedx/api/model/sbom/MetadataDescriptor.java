package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.time.ZonedDateTime;
import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Metadata")
public interface MetadataDescriptor extends CycloneDXDescriptor, DeclaresLicensesTemplate, HasPropertiesTemplate, HasSupplierTemplate, ByToolsTemplate {

    ZonedDateTime getTimestamp();

    void setTimestamp(ZonedDateTime timestamp);

    @Relation("FOR_COMPONENT")
    ComponentDescriptor getComponent();

    void setComponent(ComponentDescriptor component);

    @Relation("HAS_AUTHOR")
    List<OrganizationalContactDescriptor> getAuthors();

    @Relation("HAS_MANUFACTURE")
    OrganizationalEntityDescriptor getManufacture();

    void setManufacture(OrganizationalEntityDescriptor manufacture);

}

