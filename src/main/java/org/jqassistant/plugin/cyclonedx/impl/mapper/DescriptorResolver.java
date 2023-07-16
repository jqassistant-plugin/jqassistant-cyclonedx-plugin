package org.jqassistant.plugin.cyclonedx.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

import org.jqassistant.plugin.cyclonedx.impl.resolver.Resolvers;
import org.mapstruct.Context;
import org.mapstruct.MapperConfig;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

@MapperConfig
public interface DescriptorResolver<T, D extends Descriptor> {

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
    default D resolve(T type, @TargetType Class<D> descriptorType, @Context Scanner scanner) {
        return scanner.getContext()
            .peek(Resolvers.class)
            .resolve(type, descriptorType, scanner.getContext());
    }


}
