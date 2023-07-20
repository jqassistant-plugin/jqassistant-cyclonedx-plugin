package org.jqassistant.plugin.cyclonedx.api.model.sbom;

import com.buschmais.xo.neo4j.api.annotation.Relation;

public interface SupplierTemplate {

    @Relation("HAS_SUPPLIER")
    OrganizationalEntityDescriptor getSupplier();

    void setSupplier(OrganizationalEntityDescriptor supplier);
}
