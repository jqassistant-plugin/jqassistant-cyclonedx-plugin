package org.jqassistant.plugin.cyclonedx;

import static com.buschmais.jqassistant.core.scanner.api.DefaultScope.NONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.SBOMXmlFileDescriptor;
import org.junit.jupiter.api.Test;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.test.plugin.AbstractPluginIT;

public class BomScannerIT extends AbstractPluginIT {

    @Test
    void xmlBOM() {
        File file = new File(getClassesDirectory(BomScannerIT.class), "/bom.xml");
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), NONE);
        store.beginTransaction();
        assertThat(descriptor).isInstanceOf(SBOMXmlFileDescriptor.class);
        SBOMXmlFileDescriptor sbomXmlFileDescriptor = (SBOMXmlFileDescriptor) descriptor;
        store.commitTransaction();
    }

}
