package com.nisum.user.domain.util;

import java.util.function.Function;

public interface GenericValidator<T> extends Function<T, Class<Void>> {

    default GenericValidator<T> and(GenericValidator<T> otherValidator){
        return request -> {
            this.apply(request);

            return (Class) otherValidator.apply(request);
        };
    }

}
