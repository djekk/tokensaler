package com.syqu.shop.object;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    
    public VerificationToken()
    {
		super();
    	this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
        
    @NotEmpty
    @NotNull
    private String token;
  
    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "customer_id")
    private Customer customer;
    
    @NotEmpty
    @NotNull
    private Date expiryDate;

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}