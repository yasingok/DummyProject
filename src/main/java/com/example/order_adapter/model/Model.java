package com.example.order_adapter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

@ToString(onlyExplicitlyIncluded = true)
@Data
@EqualsAndHashCode(callSuper = true)
abstract class Model extends BaseModel{
    @Column(updatable = false)
    @CreationTimestamp
    @ToString.Include
    protected LocalDateTime created;

    @Column
    @UpdateTimestamp
    @ToString.Include
    protected LocalDateTime updated;
}
