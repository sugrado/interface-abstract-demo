package com.sugrado.business.abstracts;

import com.sugrado.entities.Customer;

public abstract class BaseCustomerManager implements CustomerService {
    @Override
    public void save(Customer customer) {
        System.out.println("Saved to DB: " + customer.getFirstName());
    }
}
