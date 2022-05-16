package com.unbe1iev.Employee;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tradesman extends Employee implements Serializable {
    private BigDecimal commissionInPercent;
    private BigDecimal commissionLimit;

    public Tradesman() {

    }

    public Tradesman(String pes, String nam, String sur, String pos, BigDecimal sal,
                     String servicePhoneN, BigDecimal commissionInP, BigDecimal limit) {
        pesel = pes;
        name = nam;
        surname = sur;
        position = pos;
        salary = sal;
        servicePhoneNumber = servicePhoneN;
        commissionInPercent = commissionInP;
        commissionLimit = limit;
    }

    public BigDecimal getCommissionInPercent() {
        return commissionInPercent;
    }

    public void setCommissionInPercent(BigDecimal commissionInPercent) {
        this.commissionInPercent = commissionInPercent;
    }

    public BigDecimal getCommissionLimit() {
        return commissionLimit;
    }

    public void setCommissionLimit(BigDecimal commissionLimit) {
        this.commissionLimit = commissionLimit;
    }
}
