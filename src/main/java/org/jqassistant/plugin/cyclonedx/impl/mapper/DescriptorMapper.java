package org.jqassistant.plugin.cyclonedx.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.mapstruct.Context;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * Base interface for mapping objects to XO {@link Descriptor}s.
 *
 * @param <T>
 *     The object type.
 * @param <D>
 *     The {@link Descriptor} type.
 */
public interface DescriptorMapper<T, D extends Descriptor> {

    /**
     * Map a object to a {@link Descriptor}. This method resolves the Descriptor using {@link #resolveDescriptor(Object, Class, Scanner)}.
     *
     * @param type
     *     The object.
     * @param scanner
     *     The {@link Scanner}.
     * @return The mapped {@link Descriptor}.
     */
    D toDescriptor(T type, @Context Scanner scanner);

    /**
     * Factory method for {@link Descriptor}s.
     * <p>
     * This method uses the {@link Resolvers} instance provided by the scanner context. This class allow registration of specific {@link org.jqassistant.plugin.cyclonedx.impl.resolver.Resolver}s.
     *
     * @param type
     *     The object the {@link Descriptor} is resolved for.
     * @param descriptorType
     *     The type of the {@link Descriptor} to be resolved.
     * @param scanner
     *     The {@link Scanner}.
     * @return The resolved {@link Descriptor}.
     */
    @ObjectFactory
    default D resolveDescriptor(T type, @TargetType Class<D> descriptorType, @Context Scanner scanner) {
        return scanner.getContext()
            .peek(Resolvers.class)
            .resolve(type, descriptorType, scanner.getContext());
    }

}
