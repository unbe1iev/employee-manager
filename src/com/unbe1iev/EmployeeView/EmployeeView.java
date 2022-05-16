package com.unbe1iev.EmployeeView;

import com.unbe1iev.Employee.Director;
import com.unbe1iev.Employee.Tradesman;

public class EmployeeView<T> {

    public void displayEmployeeDignity(T employee) {
        if (employee instanceof Director)
            System.out.println(((Director) employee).getName() + " " + ((Director) employee).getSurname());

        if (employee instanceof Tradesman)
            System.out.println(((Tradesman) employee).getName() + " " + ((Tradesman) employee).getSurname());
    }

    public void displayAll(T employee) {
        if (employee instanceof Director) {
            System.out.println("PESEL\t\t\t\t\t\t\t\t:\t" + ((Director) employee).getPesel());
            System.out.println("Name\t\t\t\t\t\t\t\t:\t" + ((Director) employee).getName());
            System.out.println("Surname\t\t\t\t\t\t\t\t:\t" + ((Director) employee).getSurname());
            System.out.println("Position\t\t\t\t\t\t\t:\t" + ((Director) employee).getPosition());
            System.out.println("Base salary (zł)\t\t\t\t\t:\t" + ((Director) employee).getSalary());
            System.out.println("Service phone number\t\t\t\t:\t" + ((Director) employee).getServicePhoneNumber());
            System.out.println("Service Bonus (zł)\t\t\t\t\t:\t" + ((Director) employee).getServiceBonus());
            System.out.println("Service Card Number\t\t\t\t\t:\t" + ((Director) employee).getServiceCardNumber());
            System.out.println("Cost limit/per month (zł)\t\t\t:\t" + ((Director) employee).getCostLimitPerMonth());
        }

        if (employee instanceof Tradesman) {
            System.out.println("PESEL\t\t\t\t\t\t\t\t:\t" + ((Tradesman) employee).getPesel());
            System.out.println("Name\t\t\t\t\t\t\t\t:\t" + ((Tradesman) employee).getName());
            System.out.println("Surname\t\t\t\t\t\t\t\t:\t" + ((Tradesman) employee).getSurname());
            System.out.println("Position\t\t\t\t\t\t\t:\t" + ((Tradesman) employee).getPosition());
            System.out.println("Base salary (zł)\t\t\t\t\t:\t" + ((Tradesman) employee).getSalary());
            System.out.println("Service phone number\t\t\t\t:\t" + ((Tradesman) employee).getServicePhoneNumber());
            System.out.println("Commission (%)\t\t\t\t\t\t:\t" + ((Tradesman) employee).getCommissionInPercent());
            System.out.println("Commission limit/per month (zł)\t\t:\t" + ((Tradesman) employee).getCommissionLimit());
        }
    }
}
