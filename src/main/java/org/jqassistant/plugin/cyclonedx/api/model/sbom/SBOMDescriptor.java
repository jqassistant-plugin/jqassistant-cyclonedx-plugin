package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("SBOM")
public interface SBOMDescriptor extends CycloneDXDescriptor {

    String getVersion();

    void setVersion(String version);

    String getSerialNumber();

    void setSerialNumber(String serialNumber);

    @Relation("HAS_METADATA")
    MetadataDescriptor getMetadata();

    void setMetadata(MetadataDescriptor metadata);

    @Relation("HAS_COMPONENT")
    List<ComponentDescriptor> getComponents();

}