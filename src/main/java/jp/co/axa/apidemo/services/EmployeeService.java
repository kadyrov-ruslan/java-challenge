package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;

import java.util.Optional;

public interface EmployeeService {
    EmployeesResponse retrieveEmployees();

    Optional<EmployeeResponse> getEmployee(Long id);

    void saveEmployee(EmployeeCreateRequest createRequest);

    void deleteEmployee(Long id);

    void updateEmployee(EmployeeUpdateRequest updateRequest);
}