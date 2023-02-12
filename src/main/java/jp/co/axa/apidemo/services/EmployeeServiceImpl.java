package jp.co.axa.apidemo.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;
import jp.co.axa.apidemo.repository.entity.Employee;
import jp.co.axa.apidemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final ObjectMapper objectMapper;

    @Inject
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ObjectMapper objectMapper) {
        this.employeeRepository = employeeRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public EmployeesResponse getAllEmployees() {
        List<EmployeeResponse> employees = employeeRepository.findAll()
                .stream()
                .map(e -> objectMapper.convertValue(e, EmployeeResponse.class))
                .collect(Collectors.toList());
        return new EmployeesResponse(employees, employees.size());
    }

    public Optional<EmployeeResponse> getEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.map(employee -> objectMapper.convertValue(employee, EmployeeResponse.class));
    }

    public void saveEmployee(EmployeeCreateRequest createRequest) {
        employeeRepository.save(objectMapper.convertValue(createRequest, Employee.class));
    }

    public void updateEmployee(Long id, EmployeeUpdateRequest updateRequest) {
        Employee employee = objectMapper.convertValue(updateRequest, Employee.class);
        employee.setId(id);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}