package com.syqu.shop.object;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotEmpty
	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;
	
	@NotEmpty
	@NotNull
	private String status;
	
	@ManyToOne(targetEntity = Distributor.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "distributor_id", nullable = false)
	private Distributor distributor;
	
	@ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@NotEmpty
	@NotNull
	private BigDecimal totalPrice;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderProduct> orderProducts;
	
	@NotEmpty
	@NotNull
	private String token;
	
    @Override
    public String toString() {
        return "order [dateCreated=" + dateCreated + 
        		", distributor=" + distributor +
        		", customer=" + customer
        		+ "]";
    }
}