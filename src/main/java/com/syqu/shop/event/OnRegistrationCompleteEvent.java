package com.syqu.shop.event;

import org.springframework.context.ApplicationEvent;

import com.syqu.shop.object.Customer;

import lombok.Data;

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
    private Customer customer;

    public OnRegistrationCompleteEvent(
      Customer customer, String appUrl) {
        super(customer);
        
        this.customer = customer;
        this.appUrl = appUrl;
    }    
}