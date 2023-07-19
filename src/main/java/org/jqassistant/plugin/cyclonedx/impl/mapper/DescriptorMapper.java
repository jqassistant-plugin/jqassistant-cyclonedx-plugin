package org.jqassistant.plugin.cyclonedx.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.mapstruct.Context;

/**
 * Base interface for mapping objects to XO {@link Descriptor}s.
 *
 * @param <V>
 *     The value type.
 * @param <D>
 *     The {@link Descriptor} type.
 */

public interface DescriptorMapper<V, D extends Descriptor> extends DescriptorResolver<V, D> {

    /**
     * Map a object to a {@link Descriptor}. This method resolves the Descriptor using {@link #resolve(Object, Class, Scanner)}.
     *
     * @param value
     *     The object.
     * @param scanner
     *     The {@link Scanner}.
     * @return The mapped {@link Descriptor}.
     */
    D toDescriptor(V value, @Context Scanner scanner);

}
