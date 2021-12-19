package com.syqu.shop.object;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", unique = true)
    @Email
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String passwordConfirm;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributor_id", nullable = true)
    private Distributor distributor;

}