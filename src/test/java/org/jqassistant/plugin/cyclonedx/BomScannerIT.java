package org.jqassistant.plugin.cyclonedx;

import java.io.File;

import com.buschmais.jqassistant.core.shared.io.ClasspathResource;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.test.plugin.AbstractPluginIT;

import org.jqassistant.plugin.cyclonedx.api.CycloneDXScope;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMDescriptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BomScannerIT extends AbstractPluginIT {

    @Test
    void xmlBOM() {
        verify("/bom.xml");
    }

    @Test
    void xmlBOMWithVulnerabilities() {
        verify("/vulnerabilities-bom.xml");
    }

    @Test
    void jsonBOM() {
        verify("/bom.json");
    }

    @Test
    void jsonBOMWithVulnerabilities() {
        verify("/vulnerabilities-bom.json");
    }

    private void verify(String resource) {
        File file = ClasspathResource.getFile(BomScannerIT.class, resource);
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), CycloneDXScope.SBOM);
        store.beginTransaction();
        assertThat(descriptor).isInstanceOf(SBOMDescriptor.class);
        SBOMDescriptor sbomDescriptor = (SBOMDescriptor) descriptor;
        store.commitTransaction();
    }
}
