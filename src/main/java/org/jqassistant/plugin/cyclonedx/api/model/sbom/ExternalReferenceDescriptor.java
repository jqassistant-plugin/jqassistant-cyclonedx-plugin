package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import java.util.List;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import org.jqassistant.plugin.cyclonedx.api.model.CycloneDXDescriptor;

@Label("ExternalReference")
public interface ExternalReferenceDescriptor extends CycloneDXDescriptor {

    String getComment();

    void setComment(String comment);

    String getType();

    void setType(String type);

    String[] getUrls();

    void setUrls(String[] url);

    @Relation("HAS_HASH")
    List<HashDescriptor> getHashes();

}
