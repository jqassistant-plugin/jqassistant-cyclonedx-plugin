package org.jqassistant.plugin.cyclonedx.impl.sbom.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

import lombok.RequiredArgsConstructor;
import org.jqassistant.plugin.cyclonedx.api.model.sbom.LicenseDescriptor;
import org.jqassistant.plugin.cyclonedx.generated.bom.LicenseType;
import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver;

@RequiredArgsConstructor
public class LicenseResolver implements Resolver<LicenseType, LicenseDescriptor> {

    private final BomRefResolver bomRefResolver;

    @Override
    public LicenseDescriptor resolve(LicenseType licenseType, ScannerContext scannerContext) {
        String bomRef = licenseType.getBomRef();
        return bomRef == null ? createLicenseDescriptor(bomRef, scannerContext) : bomRefResolver.resolve(bomRef, LicenseDescriptor.class, scannerContext);
    }

    private static LicenseDescriptor createLicenseDescriptor(String id, ScannerContext scannerContext) {
        LicenseDescriptor licenseDescriptor = scannerContext.getStore()
            .create(LicenseDescriptor.class);
        licenseDescriptor.setId(id);
        return licenseDescriptor;
    }
}
