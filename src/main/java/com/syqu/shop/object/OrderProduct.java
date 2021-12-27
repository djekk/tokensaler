package com.syqu.shop.object;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
	@ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

    @Column
    @NotEmpty
    @NotNull
    private String serialnumber;
           
    @Column
    @NotEmpty
    @NotNull
	private Integer quantity;
    
    // default constructor
    public OrderProduct(Order order, String serialnumber, Integer quantity) {
        this.order = order;
        this.serialnumber = serialnumber;
        this.quantity = quantity;
    }
}
