package org.jqassistant.plugin.cyclonedx.impl.resolver;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

public interface Resolver<T, D extends Descriptor> {

    D resolve(T type, ScannerContext scannerContext);

}
