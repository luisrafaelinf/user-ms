package com.nisum.user.domain.common;

public interface QueryCase<T extends Query, R> {

    R fetch(T query);
}
