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

class SBOMScannerIT extends AbstractPluginIT {

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

    private SBOMDescriptor scanSBOM(String resource) {
        File file = ClasspathResource.getFile(SBOMScannerIT.class, resource);
        Descriptor descriptor = getScanner().scan(file, file.getAbsolutePath(), CycloneDXScope.SBOM);
        assertThat(descriptor).isInstanceOf(SBOMDescriptor.class);
        return (SBOMDescriptor) descriptor;
    }

    private void verifyComponents(String resource) {
        SBOMDescriptor sbomDescriptor = scanSBOM(resource);
        store.beginTransaction();
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
            verifyHashes(componentDescriptor.getHashes());
            verifyExternalReferences(componentDescriptor.getExternalReferences());
        });
    }

    private static void verifyHashes(List<HashDescriptor> hashDescriptors) {
        assertThat(hashDescriptors).isNotEmpty();
        hashDescriptors.forEach(hashDescriptor -> {
            assertThat(hashDescriptor.getAlgorithm()).isNotEmpty();
            assertThat(hashDescriptor.getValue()).isNotEmpty();
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
        verifyHashes(toolDescriptor.getHashes());
    }

    private void verifyVulnerabilities(String resource) {
        SBOMDescriptor sbomDescriptor = scanSBOM(resource);
        store.beginTransaction();
        List<VulnerabilityDescriptor> vulnerabilities = sbomDescriptor.getVulnerabilities();
        verifyVulnerabilities(vulnerabilities);
        store.commitTransaction();
    }

    private static void verifyVulnerabilities(List<VulnerabilityDescriptor> vulnerabilities) {
        assertThat(vulnerabilities).isNotEmpty();
        vulnerabilities.forEach(vulnerabilityDescriptor -> {
            assertThat(vulnerabilityDescriptor.getId()).startsWith("CVE-");
            assertThat(vulnerabilityDescriptor.getDescription()).isNotEmpty();
            assertThat(vulnerabilityDescriptor.getRecommendation()).isNotEmpty();
            assertThat(vulnerabilityDescriptor.getCreated()).isNotNull();
            assertThat(vulnerabilityDescriptor.getPublished()).isNotNull();
            assertThat(vulnerabilityDescriptor.getUpdated()).isNotNull();
            assertThat(vulnerabilityDescriptor.getCwes()).isNotEmpty();
            verifyAffects(vulnerabilityDescriptor.getAffects());
            verifyAdvisories(vulnerabilityDescriptor.getAdvisories());
            verifyAnalysis(vulnerabilityDescriptor.getAnalysis());
            verifyRatings(vulnerabilityDescriptor.getRatings());
            verifyVulnerabilitySource(vulnerabilityDescriptor.getSource());
        });
    }

    private static void verifyAffects(List<VulnerabilityAffectsDescriptor> affectsDescriptors) {
        assertThat(affectsDescriptors).isNotEmpty();
        affectsDescriptors.forEach(affectsDescriptor -> {
            ComponentDescriptor component = affectsDescriptor.getComponent();
            assertThat(component).isNotNull();
            assertThat(component.getBomRef()).isNotEmpty();
        });
    }

    private static void verifyAdvisories(List<VulnerabilityAdvisoryDescriptor> advisories) {
        assertThat(advisories).isNotEmpty();
        advisories.forEach(advisoryDescriptor -> {
            assertThat(advisoryDescriptor.getTitle()).isNotEmpty();
            assertThat(advisoryDescriptor.getUrl()).startsWith("https://");
        });
    }

    private static void verifyAnalysis(VulnerabilityAnalysisDescriptor analysisDescriptor) {
        assertThat(analysisDescriptor.getState()).isIn("resolved", "resolved_with_pedigree", "exploitable", "in_triage", "false_positive", "not_affected");
        assertThat(analysisDescriptor.getJustification()).isIn("code_not_present", "code_not_reachable", "requires_configuration", "requires_dependency",
            "requires_environment", "protected_by_compiler", "protected_at_runtime", "protected_at_perimeter", "protected_by_mitigating_control");
        assertThat(analysisDescriptor.getResponses()).isNotEmpty()
            .containsAnyOf("can_not_fix", "will_not_fix", "update", "rollback", "workaround_available");
        assertThat(analysisDescriptor.getDetail()).isNotEmpty();
    }

    private static void verifyRatings(List<VulnerabilityRatingDescriptor> ratings) {
        assertThat(ratings).isNotEmpty();
        ratings.forEach(ratingDescriptor -> {
            verifyVulnerabilitySource(ratingDescriptor.getSource());
            assertThat(ratingDescriptor.getScore()).isPositive();
            assertThat(ratingDescriptor.getSeverity()).isIn("unknown", "none", "info", "low", "medium", "high", "critical");
            assertThat(ratingDescriptor.getMethod()).isIn("CVSSv2", "CVSSv3", "CVSSv31", "OWASP", "other");
            assertThat(ratingDescriptor.getVector()).isNotEmpty();
        });
    }

    private static void verifyVulnerabilitySource(VulnerabilitySourceDescriptor sourceDescriptor) {
        assertThat(sourceDescriptor).isNotNull();
        assertThat(sourceDescriptor.getName()).isEqualTo("NVD");
        assertThat(sourceDescriptor.getUrl()).startsWith("https://nvd.nist.gov/");
    }
}
