package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("Component")
public interface ComponentDescriptor
    extends CycloneDXDescriptor, BomRefTemplate, HashesTemplate, LicensesTemplate, ExternalReferencesTemplate, ComponentsTemplate, PropertiesTemplate,
    SupplierTemplate {

    String getType();

    void setType(String key);

    String getPublisher();

    void setPublisher(String publisher);

    String getAuthor();

    void setAuthor(String author);

    String getCopyright();

    void setCopyright(String copyright);

    String getCpe();

    void setCpe(String cpe);

    String getMimeType();

    void setMimeType(String mimeType);

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

    Boolean isModified();

    void setModified(Boolean modified);

    @Relation("DEPENDS_ON")
    List<ComponentDescriptor> getDependencies();
}
