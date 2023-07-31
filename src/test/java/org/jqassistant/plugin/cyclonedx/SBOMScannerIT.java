package org.jqassistant.plugin.cyclonedx;

import java.io.File;
import java.util.List;

import com.buschmais.jqassistant.core.shared.io.ClasspathResource;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.test.plugin.AbstractPluginIT;

import org.jqassistant.plugin.cyclonedx.api.CycloneDXScope;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SBOMScannerIT extends AbstractPluginIT {

    @Test
    void xmlSBOM() {
        verifyComponents("/bom.xml");
    }

    @Test
    void jsonSBOM() {
        verifyComponents("/bom.json");
    }

    @Test
    void xmlSBOMWithVulnerabilities() {
        verifyVulnerabilities("/vulnerabilities-bom.xml");
    }

    @Test
    void jsonSBOMWithVulnerabilities() {
        verifyVulnerabilities("/vulnerabilities-bom.json");
    }

    private void verifyComponents(String resource) {
        File file = ClasspathResource.getFile(SBOMScannerIT.class, resource);
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), CycloneDXScope.SBOM);
        store.beginTransaction();
        assertThat(descriptor).isInstanceOf(SBOMDescriptor.class);
        SBOMDescriptor sbomDescriptor = (SBOMDescriptor) descriptor;
        verifySBOM(sbomDescriptor);
        verifyMetadata(sbomDescriptor.getMetadata());
        verifyComponents(sbomDescriptor.getComponents());
        ComponentDescriptor rootComponent = sbomDescriptor.getMetadata()
            .getComponent();
        assertThat(rootComponent.getDependencies()).isNotEmpty();
        store.commitTransaction();
    }

    private static void verifySBOM(SBOMDescriptor sbomDescriptor) {
        assertThat(sbomDescriptor.getVersion()).isEqualTo("1");
        assertThat(sbomDescriptor.getSerialNumber()).isNotEmpty()
            .startsWith("urn:uuid:");
    }

    private static void verifyComponents(List<ComponentDescriptor> componentDescriptors) {
        assertThat(componentDescriptors).isNotEmpty();
        componentDescriptors.forEach(componentDescriptor -> {
            assertThat(componentDescriptor.getType()).isEqualTo("library");
            assertThat(componentDescriptor.getGroup()).isNotEmpty();
            assertThat(componentDescriptor.getName()).isNotEmpty();
            assertThat(componentDescriptor.getVersion()).isNotEmpty();
            assertThat(componentDescriptor.getPurl()).isNotEmpty();
            assertThat(componentDescriptor.getDescription()).isNotEmpty();
            assertThat(componentDescriptor.getScope()).isEqualTo("required");
            verifyExternalReferences(componentDescriptor.getExternalReferences());
        });
    }

    private static void verifyMetadata(MetadataDescriptor metadataDescriptor) {
        assertThat(metadataDescriptor).isNotNull();
        assertThat(metadataDescriptor.getTimestamp()).isNotNull();

        List<ToolDescriptor> toolDescriptors = metadataDescriptor.getTools();
        assertThat(toolDescriptors).hasSize(1);
        verifyTool(toolDescriptors.get(0), "OWASP Foundation", "CycloneDX Maven plugin", "2.7.9");
        verifyRootComponent(metadataDescriptor.getComponent(), "org.jqassistant.plugin", "jqassistant-cyclonedx-plugin", "1.0.0-SNAPSHOT",
            "jQAssistant Development Team");
        verifyProperties(metadataDescriptor.getProperties(), 2);
    }

    private static void verifyRootComponent(ComponentDescriptor componentDescriptor, String expectedGroup, String expectedName, String expectedVersion,
        String expectedPublisher) {
        assertThat(componentDescriptor).isNotNull();
        assertThat(componentDescriptor.getType()).isEqualTo("library");
        assertThat(componentDescriptor.getGroup()).isEqualTo(expectedGroup);
        assertThat(componentDescriptor.getName()).isEqualTo(expectedName);
        assertThat(componentDescriptor.getVersion()).isEqualTo(expectedVersion);
        assertThat(componentDescriptor.getPurl()).isEqualTo("pkg:maven/%s/%s@%s?type=jar", expectedGroup, expectedName, expectedVersion);
        assertThat(componentDescriptor.getPublisher()).isEqualTo(expectedPublisher);
        assertThat(componentDescriptor.getDescription()).isNotEmpty();
        verifyLicenses(componentDescriptor.getLicenses());
        verifyExternalReferences(componentDescriptor.getExternalReferences());
    }

    private static void verifyLicenses(List<LicenseDescriptor> licenseDescriptors) {
        assertThat(licenseDescriptors).isNotEmpty();
        licenseDescriptors.forEach(licenseDescriptor -> {
            assertThat(licenseDescriptor).satisfiesAnyOf(l -> {
                assertThat(l.getName()).isNotEmpty();
                assertThat(l.getUrl()).isNotEmpty();
            }, l -> assertThat(l.getId()).isNotEmpty(), l -> assertThat(l.getExpression()).isNotEmpty());
        });
    }

    private static void verifyExternalReferences(List<ExternalReferenceDescriptor> externalReferences) {
        assertThat(externalReferences).isNotEmpty();
        externalReferences.forEach(externalReferenceDescriptor -> {
            assertThat(externalReferenceDescriptor.getType()).isNotEmpty();
            assertThat(externalReferenceDescriptor.getUrl()).isNotEmpty();
        });
    }

    private static void verifyProperties(List<PropertyDescriptor> propertyDescriptors, int expectedSize) {
        assertThat(propertyDescriptors).hasSize(expectedSize);
        propertyDescriptors.forEach(propertyDescriptor -> {
            assertThat(propertyDescriptor.getName()).isNotEmpty();
            assertThat(propertyDescriptor.getValue()).isNotEmpty();
        });
    }

    private static void verifyTool(ToolDescriptor toolDescriptor, String expectedVendor, String expectedName, String expectedVersion) {
        assertThat(toolDescriptor.getVendor()).isEqualTo(expectedVendor);
        assertThat(toolDescriptor.getName()).isEqualTo(expectedName);
        assertThat(toolDescriptor.getVersion()).isEqualTo(expectedVersion);
        List<HashDescriptor> hashDescriptors = toolDescriptor.getHashes();
        assertThat(hashDescriptors).isNotEmpty();
        hashDescriptors.forEach(hashDescriptor -> {
            assertThat(hashDescriptor.getAlgorithm()).isNotEmpty();
            assertThat(hashDescriptor.getValue()).isNotEmpty();
        });
    }

    private void verifyVulnerabilities(String resource) {
        File file = ClasspathResource.getFile(SBOMScannerIT.class, resource);
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), CycloneDXScope.SBOM);
        store.beginTransaction();
        assertThat(descriptor).isInstanceOf(SBOMDescriptor.class);
        SBOMDescriptor sbomDescriptor = (SBOMDescriptor) descriptor;
        store.commitTransaction();
    }
}
