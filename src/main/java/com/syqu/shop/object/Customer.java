package com.syqu.shop.object;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "customer")
public class Customer {

	public Customer() {
        super();
        this.enabled=false;
    }
	
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distributor_id", nullable = true)
    private Distributor distributor;
    
    @Column(name = "enabled")
    private boolean enabled;
    
    @Override
    public String toString() {
        return  email;
    }

}
