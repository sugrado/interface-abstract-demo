package com.sugrado.business.concrete;

import com.sugrado.business.abstracts.BaseCustomerManager;
import com.sugrado.business.abstracts.CustomerCheckService;
import com.sugrado.entities.Customer;

public class StarbucksCustomerManager extends BaseCustomerManager {
    CustomerCheckService customerCheckService;

    public StarbucksCustomerManager(CustomerCheckService customerCheckService) {
        this.customerCheckService = customerCheckService;
    }

    @Override
    public void save(Customer customer) {
        if (!customerCheckService.checkIfRealPerson(customer))
            throw new RuntimeException("Not a valid person");
        super.save(customer);
    }
}
