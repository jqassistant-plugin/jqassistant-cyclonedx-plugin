package org.jqassistant.plugin.cyclonedx.impl.resolver;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;

public interface Resolver<T, D> {

    D resolve(T type, ScannerContext scannerContext);

}
