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

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public interface DescriptorMapper<T, D extends Descriptor> {

    D toDescriptor(T type, @Context Scanner scanner);

    void toDescriptor(T type, @MappingTarget D descriptor, @Context Scanner scanner);

    @ObjectFactory
    default D resolve(T type, @TargetType Class<D> targetType, @Context Scanner scanner) {
        return scanner.getContext()
            .peek(Resolvers.class)
            .resolve(type, targetType, scanner.getContext());
    }

    default <T, E, D extends Descriptor, M extends DescriptorMapper<E, D>> List<D> map(T wrapper, Supplier<List<E>> elements, M mapper,
        Scanner scanner) {
        return wrapper == null ?
            emptyList() :
            elements.get()
                .stream()
                .map(element -> mapper.toDescriptor(element, scanner))
                .collect(toList());
    }
}
