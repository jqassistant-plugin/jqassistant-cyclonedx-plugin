package org.jqassistant.plugin.cyclonedx;

import java.io.File;

import com.buschmais.jqassistant.core.shared.io.ClasspathResource;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.test.plugin.AbstractPluginIT;

import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMJsonFileDescriptor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMXmlFileDescriptor;
import org.junit.jupiter.api.Test;

import static com.buschmais.jqassistant.core.scanner.api.DefaultScope.NONE;
import static org.assertj.core.api.Assertions.assertThat;

public class BomScannerIT extends AbstractPluginIT {

    @Test
    void xmlBOM() {
        File file = ClasspathResource.getFile(BomScannerIT.class, "/bom.xml");
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), NONE);
        store.beginTransaction();
        assertThat(descriptor).isInstanceOf(SBOMXmlFileDescriptor.class);
        SBOMXmlFileDescriptor sbomXmlFileDescriptor = (SBOMXmlFileDescriptor) descriptor;
        store.commitTransaction();
    }

    @Test
    void jsonBOM() {
        File file = ClasspathResource.getFile(BomScannerIT.class, "/bom.json");
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), NONE);
        store.beginTransaction();
        assertThat(descriptor).isInstanceOf(SBOMJsonFileDescriptor.class);
        SBOMJsonFileDescriptor sbomJsonFileDescriptor = (SBOMJsonFileDescriptor) descriptor;
        store.commitTransaction();
    }
}
