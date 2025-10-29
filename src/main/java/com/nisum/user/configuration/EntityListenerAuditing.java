package com.nisum.user.configuration;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.aspectj.ConfigurableObject;

import com.nisum.user.infrastructure.persistence.model.Auditable;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configurable
public class EntityListenerAuditing implements ConfigurableObject {

    @PrePersist
    public void touchForCreate(final Auditable target) {

        target.setCreatedDate(LocalDateTime.now());
        target.setModifiedDate(LocalDateTime.now());

    }

    @PreUpdate
    public void touchForUpdate(final Auditable target) {

        target.setModifiedDate(LocalDateTime.now());

    }

}