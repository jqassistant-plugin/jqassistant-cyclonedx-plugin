package org.jqassistant.plugin.cyclonedx.impl.resolver;

import java.util.HashMap;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class Resolvers {

    private final Map<Class<?>, Resolver<?, ? extends Descriptor>> resolvers;

    public <T, D extends Descriptor> D resolve(T type, Class<D> descriptorType, ScannerContext scannerContext) {
        Resolver<T, ? extends Descriptor> resolver = (Resolver<T, ? extends Descriptor>) resolvers.get(type.getClass());
        if (resolver == null) {
            return scannerContext.getStore()
                .create(descriptorType);
        }
        return (D) resolver.resolve(type, scannerContext);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Class<?>, Resolver<?, ? extends Descriptor>> resolvers = new HashMap<>();

        public <T, D extends Descriptor> Builder resolver(Resolver<T, D> resolver) {
            resolvers.put(resolver.getType(), resolver);
            return this;
        }

        public Resolvers build() {
            return new Resolvers(resolvers);
        }
    }
}
