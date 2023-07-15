package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Component")
public interface ComponentDescriptor extends CycloneDXDescriptor, BomRefTemplate, HashesTemplate {

    String getType();

    void setType(String key);

    String getPublisher();

    void setPublisher(String publisher);

    String getGroup();

    void setGroup(String group);

    String getName();

    void setName(String name);

    String getVersion();

    void setVersion(String version);

    String getDescription();

    void setDescription(String description);

    String getScope();

    void setScope(String scope);

    String getPurl();

    void setPurl(String purl);

    @Relation("DEPENDS_ON")
    List<ComponentDescriptor> getDependencies();

    @Relation("DECLARES_LICENSE")
    List<LicenseDescriptor> getLicenses();
}