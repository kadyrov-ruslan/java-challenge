package jp.co.axa.apidemo.services;

import jakarta.inject.Inject;
import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Inject
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeesResponse retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return new EmployeesResponse();
    }

    public Optional<EmployeeResponse> getEmployee(Long id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        EmployeeResponse s = new EmployeeResponse();
        return Optional.of(s);
    }

    public void saveEmployee(EmployeeCreateRequest createRequest){
        employeeRepository.save(null);
    }

    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(EmployeeUpdateRequest updateRequest) {
        employeeRepository.save(null);
    }
}