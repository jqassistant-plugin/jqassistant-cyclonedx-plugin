package org.jqassistant.plugin.cyclonedx.impl.resolver;

import java.util.HashMap;
import java.util.Map;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class ResolverFactory {

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class ResolverKey<V, D extends Descriptor> {
        private final Class<V> valueType;

        private final Class<D> descriptorType;
    }

    private final Map<ResolverKey<?, ?>, Resolver<?, ?>> resolvers;

    public <V, D extends Descriptor> Resolver<V, D> getResolver(V value, Class<D> descriptorType) {
        return getResolver((Class<V>) value.getClass(), descriptorType);
    }

    public <V, D extends Descriptor> Resolver<V, D> getResolver(Class<V> valueType, Class<D> descriptorType) {
        ResolverKey key = new ResolverKey(valueType, descriptorType);
        return (Resolver<V, D>) resolvers.computeIfAbsent(key, v -> new AbstractResolver<V, D>(valueType, descriptorType) {
            @Override
            public D resolve(V type, ScannerContext scannerContext) {
                return scannerContext.getStore()
                    .create(descriptorType);
            }
        });
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<ResolverKey<?, ?>, Resolver<?, ?>> resolvers = new HashMap<>();

        public <T, D extends Descriptor> Builder resolver(Resolver<T, D> resolver) {
            ResolverKey<T, D> key = new ResolverKey<>(resolver.getValueType(), resolver.getDescriptorType());
            resolvers.put(key, resolver);
            return this;
        }

        public ResolverFactory build() {
            return new ResolverFactory(resolvers);
        }
    }
}
