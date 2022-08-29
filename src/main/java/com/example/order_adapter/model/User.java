package com.example.order_adapter.model;

import com.example.order_adapter.common.constant.AdapterConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class User extends Model {

    @NotBlank(message = "{user_first_name_not_blank}")
    @Size(max = AdapterConstant.USER_FIRST_NAME_MAX_SIZE, message = "{user_last_name_cannot_cross_max_size}")
    @Column(length = 40)
    private String firstName;

    @NotBlank(message = "{user_last_name_not_blank}")
    @Size(max = AdapterConstant.USER_LAST_NAME_MAX_SIZE, message = "{user_last_name_cannot_cross_max_size}")
    @Column(length = 40)
    private String lastName;

    @NotBlank
    @Column(unique = true, length = 40)
    private String email;

    @Column(unique = true)
    @ToString.Include
    private String identityNumber;

    @Column(unique = true)
    @ToString.Include
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_credential_id", referencedColumnName = "id")
    private Credential credential;
}
