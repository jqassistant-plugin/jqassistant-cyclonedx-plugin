package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("License")
public interface LicenseDescriptor extends CycloneDXDescriptor {

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getUrl();

    void setUrl(String name);

    String getExpression();

    void setExpression(String expression);
}
