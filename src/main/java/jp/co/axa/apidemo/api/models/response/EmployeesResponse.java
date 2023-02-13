package jp.co.axa.apidemo.api.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Employees list response model
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeesResponse {
    private List<EmployeeResponse> employees = new ArrayList<>();
    private long employeesCount;

    public EmployeesResponse(List<EmployeeResponse> employees, long employeesCount) {
        this.employees = employees;
        this.employeesCount = employeesCount;
    }
}
