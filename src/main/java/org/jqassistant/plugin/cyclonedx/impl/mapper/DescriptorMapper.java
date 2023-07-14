package org.jqassistant.plugin.cyclonedx.impl.mapper;

import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;

public interface DescriptorMapper<T, D extends Descriptor> {

    D toDescriptor(T type, @Context Scanner scanner);

    void toDescriptor(T Type, @MappingTarget D descriptor, @Context Scanner scanner);

    default D create(@TargetType Class<D> type, @Context Scanner scanner) {
        return scanner.getContext().getStore().create(type);
    }
}
