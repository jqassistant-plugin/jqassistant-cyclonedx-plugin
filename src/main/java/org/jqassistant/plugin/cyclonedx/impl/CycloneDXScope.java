package org.jqassistant.plugin.cyclonedx.impl;

import com.buschmais.jqassistant.core.scanner.api.Scope;

public enum CycloneDXScope implements Scope {

    SBOM;

    @Override
    public String getPrefix() {
        return "cyclonedx";
    }

    @Override
    public String getName() {
        return name();
    }
}
