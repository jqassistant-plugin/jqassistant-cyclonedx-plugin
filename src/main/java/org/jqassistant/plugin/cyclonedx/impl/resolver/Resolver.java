package org.jqassistant.plugin.cyclonedx.impl.resolver;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

public interface Resolver<V, D extends Descriptor> {

    Class<V> getValueType();

    Class<D> getDescriptorType();

    D resolve(V type, ScannerContext scannerContext);

}
