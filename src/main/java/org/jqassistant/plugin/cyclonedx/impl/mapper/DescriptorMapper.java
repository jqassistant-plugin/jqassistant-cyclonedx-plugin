package org.jqassistant.plugin.cyclonedx.impl.mapper;

import java.util.List;
import java.util.function.Supplier;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

import static java.util.stream.Collectors.toList;

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
     * Map a object to an existing {@link Descriptor} which is provided as parameter..
     *
     * @param type
     *     The object.
     * @param descriptor
     *     The existing {@link Descriptor}.
     * @param scanner
     *     The {@link Scanner}.
     * @return The mapped {@link Descriptor}.
     */
    void toDescriptor(T type, @MappingTarget D descriptor, @Context Scanner scanner);

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

    /**
     * Map a {@link List} of elements to a {@link List} of {@link Descriptor}s using the provided mapper.
     *
     * @param elements
     *     The {@link List} of elements.
     * @param mapper
     *     The mapper.
     * @param scanner
     *     The {@link Scanner}.
     * @param <E>
     *     The element type.
     * @param <D>
     *     The {@link Descriptor} type.
     * @param <M>
     *     The mapper type.
     * @return The {@link List} of {@link Descriptor}s.
     */
    default <E, D extends Descriptor, M extends DescriptorMapper<E, D>> List<D> map(List<E> elements, M mapper, Scanner scanner) {
        return elements.stream()
            .map(element -> mapper.toDescriptor(element, scanner))
            .collect(toList());
    }

    /**
     * Map a {@link List} of elements provided by a single wrapper instance to a {@link List} of {@link Descriptor}s using the provided mapper.
     *
     * @param wrapper
     *     The wrapper providing a {@link List} of elements
     * @param elements
     *     The {@link Supplier} for unwrapping the elements from the wrapper.
     * @param mapper
     *     The mapper.
     * @param scanner
     *     The {@link Scanner}.
     * @param <E>
     *     The element type.
     * @param <D>
     *     The {@link Descriptor} type.
     * @param <M>
     *     The mapper type.
     * @return The {@link List} of {@link Descriptor}s.
     */
    default <T, E, D extends Descriptor, M extends DescriptorMapper<E, D>> List<D> map(T wrapper, Supplier<List<E>> elements, M mapper, Scanner scanner) {
        return wrapper == null ? null : map(elements.get(), mapper, scanner);
    }
}
