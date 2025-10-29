package com.nisum.user.infrastructure.persistence.model;

import java.time.LocalDateTime;

import com.nisum.user.configuration.EntityListenerAuditing;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntityListenerAuditing.class)
public abstract class Auditable {
        
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

}
