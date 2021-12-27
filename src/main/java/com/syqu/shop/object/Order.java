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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "distributor_id", nullable = false)
	private Distributor distributor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@Transient
	public BigDecimal getTotalOrderPrice() {
	   
	    return BigDecimal.ZERO;
	}
	
	@JsonManagedReference
	@OneToMany(mappedBy = "pk.order")
	@Valid
	private List<OrderProduct> orderProducts = new ArrayList<>();
	
    @Override
    public String toString() {
        return "Order was dateCreated [status=" + status + " ]";
    }
}