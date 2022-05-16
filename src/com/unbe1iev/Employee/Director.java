package com.unbe1iev.Employee;

import java.io.Serializable;
import java.math.BigDecimal;

public class Director extends Employee implements Serializable {
    private BigDecimal serviceBonus;
    private String serviceCardNumber;
    private BigDecimal costLimitPerMonth;

    public Director() {}

    public Director(String pes, String nam, String sur, String pos, BigDecimal sal,
                    String servicePhoneN, BigDecimal bonus, String serviceCardN, BigDecimal limit) {
        pesel = pes;
        name = nam;
        surname = sur;
        position = pos;
        salary = sal;
        servicePhoneNumber = servicePhoneN;
        serviceBonus = bonus;
        serviceCardNumber = serviceCardN;
        costLimitPerMonth = limit;
    }

    public BigDecimal getServiceBonus() {
        return serviceBonus;
    }

    public void setServiceBonus(BigDecimal serviceBonus) {
        this.serviceBonus = serviceBonus;
    }

    public String getServiceCardNumber() {
        return serviceCardNumber;
    }

    public void setServiceCardNumber(String serviceCardNumber) {
        this.serviceCardNumber = serviceCardNumber;
    }

    public BigDecimal getCostLimitPerMonth() {
        return costLimitPerMonth;
    }

    public void setCostLimitPerMonth(BigDecimal costLimitPerMonth) {
        this.costLimitPerMonth = costLimitPerMonth;
    }
}
