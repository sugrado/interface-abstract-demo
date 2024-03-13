package com.sugrado;

import com.sugrado.business.abstracts.BaseCustomerManager;
import com.sugrado.business.concrete.CustomerCheckManager;
import com.sugrado.business.concrete.NeroCustomerManager;
import com.sugrado.business.concrete.StarbucksCustomerManager;
import com.sugrado.entities.Customer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Görkem Rıdvan");
        customer.setLastName("ARIK");
        customer.setDateOfBirth(new GregorianCalendar(1800, Calendar.FEBRUARY, 1).getTime());
        customer.setNationalityId("11111111111");

        BaseCustomerManager starbucksCustomerManager = new StarbucksCustomerManager(new CustomerCheckManager()); // new MernisServiceAdapter()
        BaseCustomerManager neroCustomerManager = new NeroCustomerManager();
        starbucksCustomerManager.save(customer);
        neroCustomerManager.save(customer);
    }
}