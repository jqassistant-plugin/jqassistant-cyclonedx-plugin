package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Label;
import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Component")
public interface ComponentDescriptor extends CycloneDXDescriptor {

    String getGroup();
    void setGroup(String group);

    String getName();
    void setName(String name);

    String getVersion();
    void setVersion(String version);

}
