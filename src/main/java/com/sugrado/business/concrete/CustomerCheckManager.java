package com.sugrado.business.concrete;

import com.sugrado.business.abstracts.CustomerCheckService;
import com.sugrado.entities.Customer;

public class CustomerCheckManager implements CustomerCheckService {
    @Override
    public boolean checkIfRealPerson(Customer customer) {
        return true;
    }
}
