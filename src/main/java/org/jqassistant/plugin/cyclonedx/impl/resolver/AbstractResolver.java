package org.jqassistant.plugin.cyclonedx.impl.resolver;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
public abstract class AbstractResolver<V, D extends Descriptor> implements Resolver<V, D> {

    protected final Class<V> valueType;

    protected final Class<D> descriptorType;

    @Override
    public final Class<V> getValueType() {
        return valueType;
    }

    @Override
    public final Class<D> getDescriptorType() {
        return descriptorType;
    }
}
