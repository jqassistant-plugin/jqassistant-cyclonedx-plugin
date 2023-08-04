package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Relation;

public interface HasHashesTemplate {

    @Relation("HAS_HASH")
    List<HashDescriptor> getHashes();

}
