package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.time.ZonedDateTime;
import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Metadata")
public interface MetadataDescriptor extends CycloneDXDescriptor {

    ZonedDateTime getTimestamp();

    void setTimestamp(ZonedDateTime timestamp);

    @Relation("BY_TOOL")
    List<ToolDescriptor> getTools();

    @Relation("HAS_COMPONENT")
    ComponentDescriptor getComponent();

    void setComponent(ComponentDescriptor component);

}
