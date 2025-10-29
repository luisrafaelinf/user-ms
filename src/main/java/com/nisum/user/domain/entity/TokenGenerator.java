package com.nisum.user.domain.entity;

public interface TokenGenerator {

    String generate(String username);
}
