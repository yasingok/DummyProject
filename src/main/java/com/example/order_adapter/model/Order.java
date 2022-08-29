package com.example.order_adapter.model;

import com.example.order_adapter.common.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_post"
    )
    @SequenceGenerator(
            name = "seq_post",
            initialValue = 100000,
            allocationSize = 1
    )
    @Column
    @ToString.Include
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    private Pizza name;

    @OneToOne(cascade = CascadeType.ALL)
    private PizzaSize size;

    @OneToOne(cascade = CascadeType.ALL)
    private PizzaCrust crust;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    @ToString.Include
    private OrderStatus status;

    @Column
    @ToString.Include
    private Integer orderId;

    @Column(updatable = false)
    @CreationTimestamp
    @ToString.Include
    protected LocalDateTime created;

    @Column
    @UpdateTimestamp
    @ToString.Include
    protected LocalDateTime updated;
}
