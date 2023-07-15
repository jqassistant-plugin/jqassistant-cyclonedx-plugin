package org.jqassistant.plugin.cyclonedx.impl.resolver;

import java.util.Map;
import java.util.function.Function;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import lombok.Builder;
import lombok.Singular;

@Builder
public class Resolvers {

    @Singular
    private final Map<Class<?>, Resolver<?, ? extends Descriptor>> resolvers;

    public <T, D extends Descriptor> D resolve(T type, Class<D> descriptorType, ScannerContext scannerContext) {
        Resolver<T, ? extends Descriptor> resolver = (Resolver<T, ? extends Descriptor>) resolvers.get(type.getClass());
        if (resolver == null) {
            return scannerContext.getStore()
                .create(descriptorType);
        }
        return (D) resolver.resolve(type, scannerContext);
    }
}
