package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Property")
public interface PropertyDescriptor extends CycloneDXDescriptor {

    String getName();

    void setName(String name);

    String getValue();

    void setValue(String value);

}
