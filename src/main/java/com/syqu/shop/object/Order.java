package com.syqu.shop.object;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOrdered;
	
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
        return "order [" + token + "]";
    }
}