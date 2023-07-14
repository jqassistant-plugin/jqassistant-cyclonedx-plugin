package org.jqassistant.plugin.cyclonedx.api.model;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.api.annotation.Abstract;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Abstract
@Label("CycloneDX")
public interface CycloneDXDescriptor extends Descriptor {
}
