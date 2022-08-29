package com.example.order_adapter.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Address extends Model {

    @Column
    @ToString.Include
    private Integer addressId;

    @Column(length = 255)
    @ToString.Include
    private String address1;

    @Column(length = 255)
    @ToString.Include
    private String address2;

    @Column(length = 50)
    @ToString.Include
    private String city;

    @Column(length = 50)
    @ToString.Include
    private String state;

    @Column(length = 64)
    @ToString.Include
    private String country;

    @Column(length = 64)
    @ToString.Include
    private String postalCode;



}
