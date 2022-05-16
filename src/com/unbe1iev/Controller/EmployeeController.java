package com.unbe1iev.Controller;

import com.unbe1iev.EmployeeView.EmployeeView;

public class EmployeeController<T> {
    private final EmployeeView view;
    private T employee;

    public EmployeeController() {
        this.view = new EmployeeView();
    }

    public void setEmployee(T employee) {
        this.employee = employee;
    }

    public void displayEmployeeDignity() {
        view.displayEmployeeDignity(employee);
    }

    public void displayAll() {
        view.displayAll(employee);
    }
}
