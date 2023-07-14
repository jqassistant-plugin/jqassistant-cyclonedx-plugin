package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Metadata")
public interface MetadataDescriptor extends CycloneDXDescriptor {
}
