package com.sugrado.adapters;

import com.sugrado.business.abstracts.CustomerCheckService;
import com.sugrado.entities.Customer;
import connectedServices.mernis.MernisClient;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MernisServiceAdapter implements CustomerCheckService {
    MernisClient mernisClient;

    public MernisServiceAdapter() {
        this.mernisClient = new MernisClient();
    }

    @Override
    public boolean checkIfRealPerson(Customer customer) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(customer.getDateOfBirth());
        return this.mernisClient.TCKimlikNoDogrula(customer.getNationalityId(),
                customer.getFirstName().toUpperCase(),
                customer.getLastName().toUpperCase(),
                String.valueOf(calendar.get(Calendar.YEAR)));
    }
}
