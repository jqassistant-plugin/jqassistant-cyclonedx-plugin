package org.jqassistant.plugin.cyclonedx.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.mapstruct.Context;
import org.mapstruct.MappingTarget;

/**
 * Base interface for enriching existing XO {@link Descriptor}s.
 *
 * @param <T>
 *     The object type.
 * @param <D>
 *     The {@link Descriptor} type.
 */

public interface DescriptorEnricher<T, D extends Descriptor> extends DescriptorResolver<T, D> {

    /**
     * Map a object to a {@link Descriptor}. This method resolves the Descriptor using {@link #resolve(Object, Class, Scanner)}.
     *
     * @param type
     *     The object.
     * @param scanner
     *     The {@link Scanner}.
     * @return The mapped {@link Descriptor}.
     */
    D toDescriptor(T type, @MappingTarget D target, @Context Scanner scanner);

}
