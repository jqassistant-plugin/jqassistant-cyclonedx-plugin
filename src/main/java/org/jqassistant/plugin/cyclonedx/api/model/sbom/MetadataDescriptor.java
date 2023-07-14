package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Metadata")
public interface MetadataDescriptor extends CycloneDXDescriptor {

    @Relation("HAS_COMPONENT")
    ComponentDescriptor getComponent();
    void setComponent(ComponentDescriptor component);

}
