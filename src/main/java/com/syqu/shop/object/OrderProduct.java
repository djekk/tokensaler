package com.syqu.shop.object;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@Entity
@Table(name = "order_product")
public class OrderProduct {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    @EmbeddedId
    @JsonIgnore
    private OrderProductPK pk;
           
    @Column
    @NotEmpty
    @NotNull
	private Integer quantity;
    
    // default constructor
    public OrderProduct(Order order, String serialnumber, Integer quantity) {
        pk = new OrderProductPK();
        pk.setOrder(order);
        pk.setSerialnumber(serialnumber);
        this.quantity = quantity;
    }
}
