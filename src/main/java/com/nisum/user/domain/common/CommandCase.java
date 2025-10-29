package com.nisum.user.domain.common;

public interface CommandCase<T extends Command, R> {

    R process(T command);
}
