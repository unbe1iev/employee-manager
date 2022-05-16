package com.unbe1iev.Employee;

import java.io.Serializable;
import java.math.BigDecimal;

public class Employee implements Serializable{
    protected String pesel;
    protected String name;
    protected String surname;
    protected String position;
    protected BigDecimal salary;
    protected String servicePhoneNumber;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getServicePhoneNumber() {
        return servicePhoneNumber;
    }

    public void setServicePhoneNumber(String servicePhoneNumber) {
        this.servicePhoneNumber = servicePhoneNumber;
    }
}
