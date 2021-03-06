package com.syqu.shop.validator;

import com.syqu.shop.object.Customer;
import com.syqu.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerValidator implements Validator {
    private final CustomerService userService;

    @Autowired
    public CustomerValidator(CustomerService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;

        //Username and password can't me empty or contain whitespace
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");

        //Email can't be duplicated
        if (userService.findByEmail(customer.getEmail()) != null){
            errors.rejectValue("email", "register.error.duplicated.email");
        }
        //Password must have at least 8 characters and max 32
        if (customer.getPassword().length() < 8) {
            errors.rejectValue("password", "register.error.password.less_8");
        }
        if (customer.getPassword().length() > 32){
            errors.rejectValue("password", "register.error.password.over_32");
        }
        //Password must be the same as the confirmation password
        if (!customer.getPasswordConfirm().equals(customer.getPassword())) {
            errors.rejectValue("passwordConfirm", "register.error.diff_password");
        }

    }
}
