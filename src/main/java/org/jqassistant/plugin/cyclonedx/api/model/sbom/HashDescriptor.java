package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Hash")
public interface HashDescriptor extends CycloneDXDescriptor {

    String getAlgorithm();

    void setAlgorithm(String algorithm);

    String getValue();

    void setValue(String value);

}
