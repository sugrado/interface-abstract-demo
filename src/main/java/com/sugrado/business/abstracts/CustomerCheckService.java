package com.sugrado.business.abstracts;

import com.sugrado.entities.Customer;

public interface CustomerCheckService {
    boolean CheckIfRealPerson(Customer customer);
}
